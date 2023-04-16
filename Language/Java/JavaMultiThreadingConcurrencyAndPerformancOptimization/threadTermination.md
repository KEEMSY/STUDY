# **Thread Termination**

애플리케이션에서 스레드를 사용할 때, 사용하지 않는 스레드가 사용하는 리소스를 정리하거나, 오작동한 스레드를 중지해야한다.

- 스레드는 아무것도 사용하지 않을 때에도 메모리와 일부 커널 리소스를 사용한다.
- 스레드가 실행 중이라면 CPU 시간 뿐만 아니라 CPU 캐시 공간도 사용한다.
- 하나의 스레드만 실행되고 있어도 애플리케이션은 종료되지 않는다.(메인 스레드가 이미 멈췄어도 종료되지 않는다.)
- interruptedException을 발생시켜 신호를 직접 처리할 수 있다.
- Daemon 스레드로 설정하여 스레드가 애플리케이션의 종료를 방해하지 않을 수 있다.


<br><hr><hr>

## **Thread.interrupt()**

각 스레드 객체는 `interrupt()` 메서드를 가진다.

- `threadA` 에서 `threadB.interrupt()` 를 실행하면 `threadB` 에 인터럽트 신호를 준다.

<br>

> ### **Ex.1 InterruptedException 발생시키는 경우**

```java
// BlockingTask 클래스는 잘못된 시간을 차단하는 작업을 수행한다.

public class Main1 {
    public static void main(String [] args) {
        Thread thread = new Thread(new BlockingTask());

        thread.start();

        thread.interrupt();  // interrupt가 없다면, Main 스레드는 이미 오래전에 종료되었음에도 불구하고 blocking 스레드가 종료될 때 까지 기다린다.
    }

    private static class BlockingTask implements Runnable {

        @Override
        public void run() {
            //do things
            try {
                Thread.sleep(500000);
            } catch (InterruptedException e) {
                System.out.println("Existing blocking thread");
            }
        }
    }
}
```

- 현재 `thread` 외부에서 `interrupt()` 가 발생하면 예외가 발생한다.

<br>

> ### **Ex.2 Interrupt 하려는 스레드가 신호를 명시적으로 처리하는 경우**

```java
// LongComputationTask 클래스는 숫자를 거듭제곱으로 계산한다.

import java.math.BigInteger;

public class Main2 {

    public static void main(String[] args) {
        Thread thread = new Thread(new LongComputationTask(new BigInteger("200000"), new BigInteger("100000000")));

        thread.start();
        thread.interrupt();
    }

    private static class LongComputationTask implements Runnable {
        private BigInteger base;
        private BigInteger power;

        public LongComputationTask(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            System.out.println(base + "^" + power + " = " + pow(base, power));
        }

        private BigInteger pow(BigInteger base, BigInteger power) {
            BigInteger result = BigInteger.ONE;

            for (BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)) { // hotspot
                if (Thread.currentThread().isInterrupted()) { // 긴 시간이 소요되는 처리 과정에서 외부에서 interrupted 되었는지 확인하여 작업도중 스레드를 interrupt 한다.
                    System.out.println("Prematurely interrupted computation");
                    return BigInteger.ZERO;
                }
                result = result.multiply(base);
            }

            return result;
        }
    }
}
```

- **숫자가 클 경우** 계산이 끝날 때까지 상당히 오래걸리며, 작업이 진행되는 도중에는 스레드를 `interrupt()` 를 호출하여도 해서 종료할 수 없다.
- `interrupt` 가 보내졌지만 이를 처리할 메서드나 로직이 없어 **intrerupt 가 발생해도 LongComputation은 계속 돌아간다.**
- 코드 내 시간이 오래걸리는 핫스팟을 찾아, **interrupted 되었는지 확인하여 thread를 중단한다.**
    - `isInterrupted()` 를 통해 확인한다.

<br>

> ###  **Daemon Thread**

```java
import java.math.BigInteger;

/**
 * Thread Termination & Daemon Threads
 * https://www.udemy.com/java-multithreading-concurrency-performance-optimization
 */
public class Main3 {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new LongComputationTask(new BigInteger("200000"), new BigInteger("100000000")));

        thread.setDaemon(true); // main 스레드가 종료 되었을 때, 전체 애플리케이션이 종료된다.
        thread.start();
        Thread.sleep(100);
        thread.interrupt();
    }

    private static class LongComputationTask implements Runnable {
        private BigInteger base;
        private BigInteger power;

        public LongComputationTask(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            System.out.println(base + "^" + power + " = " + pow(base, power));
        }

        private BigInteger pow(BigInteger base, BigInteger power) {
            BigInteger result = BigInteger.ONE;

            for (BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)) {
                result = result.multiply(base);
            }

            return result;
        }
    }
}
```

- `deamon 스레드` 는 **백그라운드 에서 실행되는 스레드** 로, 메인스레드가 종료되어도 애플리케이션의 종료를 막지 않는다.
- 스레드를 daemon으로 생성하면, 애플리케이션의 주 작업이 아닌 백그라운드 작업을 맡는다.

    > Ex. 텍스트 편집기 애플리케이션

    텍스트 편집기 애플리케이션은 몇 분 마다 작업 파일을 저장하는 스레드가 존재한다.

    - 갑자기 애플리케이션을 종료하고 싶다면 백그라운드 스레드의 실행여부는 신경쓰지 않는다.(완료될 때 까지 기다리지 않는다.)
    - Daemon 스레드가 텍스트편집기 애플리케이션을 종료하는것을 방해하는 일은 없어야한다.