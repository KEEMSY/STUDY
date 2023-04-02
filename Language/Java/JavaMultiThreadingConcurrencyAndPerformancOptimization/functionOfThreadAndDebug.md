**Thread 생성하기 방법 1** 

*Runnable 인터페이스를 구현하는 클래스의 객체를 스레드 객체 생성자에 전달하여 다른 스레드에서 실행되는 코드를 스케줄링 한다.*

```java
public class Main1 {
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //Code that will run in  a new thread
                System.out.println("we are now in thread "+Thread.currentThread().getName());
                System.out.println("Current thread priority is " + Thread.currentThread().getPriority());
            }
        });

        thread.setName("New Worker Thread");

        thread.setPriority(Thread.MAX_PRIORITY);

        System.out.println("We are in thread: " + Thread.currentThread().getName()+ " before starting a new thread");
        thread.start();
        System.out.println("We are in thread: " + Thread.currentThread().getName()+ " after starting a new thread");

        Thread.sleep(1000);
    }
}

// Java 8 이상 버전을 사용한다면 람다로 줄일 수 있다.
public class Main {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            // Code that will run in a new thread

        })
    }
}
```

*[공식문서: Thread](https://docs.oracle.com/javase/10/docs/api/java/lang/Thread.State.html)*

스레드 객체 자체는 기본적으로 비어있어, Runnable 인터페이스를 구현하는 클래스의 객체를 해당 생성자에 전달해야 한다.

- run 메서드에 어떤 코드를 넣든 운영체제가 스케줄링 하자마자 새 스레드에서 실핸된다.
- 스레드 객체에 start 메서드를 호출하여 스레드를 시작한다.(-> JVM이 새 스레드를 생성하여 운영체제에 전달한다.)

<br>

> ### **Thread 클래스의 유용한 메서드**

**`Thread.currentThread` [static]**

- 현재 실행중인 스레드 출력한다.

<br>

**`sleep()`**

- 현재 스레드를 주어진 만큼 멈추게 한다.(이는 반복되는 명령문이 아닌, 운영체제에게 지시하는 것이다.)

    *현재 스레드에 주어진 시간(밀리초(ms))이 지날 때까지 CPU를 사용하지 않는다.*

    *스케줄링은 운영체제에 의해 비동기적으로 발생한다.*
<br>

**`setName()`**

- 스레드 이름을 설정한다.

<br>

**동적 우선순위의 정적 요소를 계획적으로 설정(1~10)**

*동작에는 차이가 없지만, 복잡한 프로그램의 스레드가 보다 많은 응답성을 필요로 하면 아주 중요한 역할을 할 수 있다.*

- `setPriority()`: 1~10 까지 설정
- `getPriority()`
- `MAX_PRIORITY`
- `MIN_PRIORITY`

<br>

**`setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler())`**

```java
public class Main2 {

    public static void main(String [] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //Code that will run in a new thread
                throw new RuntimeException("Intentional Exception");
            }
        });

        thread.setName("Misbehaving thread");

        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("A critical error happened in thread " + t.getName()
                        + " the error is " + e.getMessage());
            }
        });
        thread.start();

    }

}
```

- 처음부터 전체 스레드에 해당되는 예외 핸들러 지정한다.

- 스레드 내에서 발생한 예외가 어디에서도 캐치되지 않으면 핸들러가 호출되고, 캐치되지 않은 스레드와 예외를 출력한다.

    *현실적으로, 예외가 발생하면, 리소스 일부를 정리하거나 추가 데이터를 고르하여 문제가 발생한 이후 우리가 해결 할 수 있도록 한다.*