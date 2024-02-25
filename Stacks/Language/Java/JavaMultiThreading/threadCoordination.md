# **Thread Coordination**

스레드는 서로 다른 스레드와 독립적으로 실행된다.

- 스레드의 실행순서는 우리가 통제할 수 없다.
- `threadA` 가 `threadB` 가 먼저 작업을 완료할 수 있고, 그 반대가 될 수 있다.
- 혹은 동시에 실행되거나 병렬로 실행될 수 있다.

<br>

> ### **한 스레드(threadA)가 다른 스레드(threadB)에 의존할 경우**

`threadB` 가 루프를 실행하여 `threadA` 가 작업을 마쳤는지 확인하여 작업을 진행할 수 있다.

- 하지만 이는 매우 비효율적이다. `threadA` 는 CPU를 이용하여 작업을 완료하려하며, 결과값을 `threadB` 에게 주려는 동안 `threadB` 는 루프를 돌며 CPU 를 계속 사용한다.
- 결국 `threadA` 를 느리게 만들게 된다.

차라리 `threadB` 를 재운 뒤, `threadA` 가 작업을 마칠 수 있게 CPU를 사용하지 않는 것이 낫다.

- `threadA` 가 일을 마치면 `threadB` 가 일어나 완료된 결과를 얻는다.
- 이는 `Thread.join()` 을 통해 구현한다.

<br><hr><hr>

## **Thread.join()**

```java
public final void join()
public final void join(long millis, int nanos)
public final void join(long millis)
```

<br>

```java

// FactorialThread 클래스는 Factorial(계승연산)을 계산한다. 계승연산은 곱셈 연산이 많아 CPU를 많이 사용한다.

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        List<Long> inputNumbers = Arrays.asList(100000000L, 3435L, 35435L, 2324L, 4656L, 23L, 5556L);

        List<FactorialThread> threads = new ArrayList<>();

        for (long inputNumber : inputNumbers) {
            threads.add(new FactorialThread(inputNumber));
        }

        for (Thread thread : threads) {
            thread.setDaemon(true);
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join(2000); //  .join(2000): 각 스레드가 기다릴 시간을 결정한다.(2초가 지나도 스레드가 종료되지 않으면 thread.join()이 호출된다.
        }

        for (int i = 0; i < inputNumbers.size(); i++) {
            FactorialThread factorialThread = threads.get(i);
            if (factorialThread.isFinished()) {
                System.out.println("Factorial of " + inputNumbers.get(i) + " is " + factorialThread.getResult());
            } else {
                System.out.println("The calculation for " + inputNumbers.get(i) + " is still in progress");
            }
        }
    }

    public static class FactorialThread extends Thread {
        private long inputNumber;
        private BigInteger result = BigInteger.ZERO;
        private boolean isFinished = false;

        public FactorialThread(long inputNumber) {
            this.inputNumber = inputNumber;
        }

        @Override
        public void run() {
            this.result = factorial(inputNumber);
            this.isFinished = true;
        }

        public BigInteger factorial(long n) {
            BigInteger tempResult = BigInteger.ONE;

            for (long i = n; i > 0; i--) {
                tempResult = tempResult.multiply(new BigInteger((Long.toString(i))));
            }
            return tempResult;
        }

        public BigInteger getResult() {
            return result;
        }

        public boolean isFinished() {
            return isFinished;
        }
    }
}
```

- 각각의 계산을 다른 **스레드**에 맡겨 **병렬** 로 계승을 계산한다.
- `join()`을 통해 **FactorialThread 가 작업을 마칠 때까지 main 스레드가 기다리게 된다.**
- `.join(time)`:  `time` 내에 반환되지 않은 작업은 `interrupt` 하는 것이 좋다.

<br><hr><hr>

## **Summary**

스레드를 조정할 수 있다면, 다른 스레드를 시작하고 멈추는 기능 그 이상을 할 수 있다.

- 신뢰하는 스레드가 **예상시간안에 작업을 완료**하게 할 수 있다.
- 특정 작업을 **병렬**로 실행하고 **처리속도**를 높여, **안전하고 정확하게 결과를 종합** 할 수 있다.
- 믿을만한 결과를 위해 **늘 스레드를 조정해야한다.**
    - 한 스레드가 완료되는데 지나치게 오랜 시간이 걸리는 상황을 고려해야한다.
    - `thread.join()` 에 기다릴 **시간을 지정한다.**
    - **제 시간에 작업하지 못한 스레드를 멈춘다.**
