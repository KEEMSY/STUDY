# Critical Sections And Synchronization

## **임계영역, Critical Sections**

```java
void aggregateFunction() {
    operation1();
    operation2();
    operation3();
}
```

두개의 스레드가 하나의 카운터를 공유하는데 둘 다 그 카운터를 읽고 수정할 수 있다는 것은 문제가 된다.

- 원자적 연산이 아니게 되어 문제가 된다.

<br>

```java
void aggregateFunction() {
    // enter critical section
    operation1();
    operation2();
    operation3();
    // exit critical section
}
```

동시에 실행되지 않게 보호해야 하는 코드가 있는 영역을 임계영역(Critical Section)이라 부른다.

- 임계영역에 이미 스레드가 들어와 있다면, 다른 어떤 스레드는 현재 들어있는 스레드가 종료되어 임계영역에서 나갈 때 까지 중단되게 된다.
- 이를 통해 모든 수의 개별 작업에 대해 원자성을 확보할 수 있다.

<br><hr><hr>

## **Synchronized**

> ### **Monitor**

```java
public class ClssWithCriticalSections {
    public synchronized method1() {
        // ...
    }

    public synchronized method2() {
        // ...
    }
}
```

여러개의 스레드가 해당 클래스의 동일한 객체에서 해당 메서드를 호출하려고 하면, **한 개의 스레드만 메서드 중 하나를 실행할 수 있다.**

- `TheadA` 가 `method1` 을 실행하면 `ThreadB` 는 `method1`과 `method2` **모두 실행할 수 없다.**
  - 이러한 기능을 `Monitor` 라고 한다.

<br>

> ### **Lock**

```java
public class ClassWithCriticalSections() {
    Object lock1 = new Object();
    Object lock2 = new Object();

    public void method1() {
        synchronized(lockingObject) {
            // enter critical section
            // .,.
            // exitcritical section
        }
    }
}
```

```java
public class ClassWithCriticalSections() {
    Object lock1 = new Object();

    public void method1() {
        synchronized(lockingObject) {
            // ... 
            // ... 
            synchronized(lockingObject) {
            // enter critical section
            // .,.
            // exitcritical section
            }
            // ... 
            // ... 
        }
    }
}
```

**임계영역 으로 간주되는 코드 블록을 정의하고, 전체 메서드를 동기화하지는 않으면서 해당 영역에 대한 엑세스만 제한하는 방법** 을 말한다.

- 임계역역내의 코드는 **로직을 제대로 수행하는 최소한만 남겨두며 최소화** 한다.
  - 더 많은 코드가 여러 스레드로 동시에 실행될 수 있으며, 스레드 간 동기화가 필요한 코드는 더 적어지게 된다.
- `lock` 의 역할을 담당할 동기화 할 객체를 만든다.
  - 클래스의 한 개 객체 내부에 서로 다른 객체에 동기화된 각각의 별도의 임계영역을 지정한다.
  - `ThreadA` 가 첫번째 임계영역을 실행하는 동안 `ThreadB` 가 두번째 임계영역에 엑세스 할 수 있다.
  - 하지만 `ThreadB` 가 두번째 임계영역을 실행하는 동안에는 `ThreadA` 는 `ThreadB`가 끝날 때까지 기다려야 한다.
- `synchronized block`은 `재진입(Reentrant)` 가능한 요소이다.
  - 기본적으로 스레드는 **자신이 임계영역에 진입하는 것을 막지 못한다.**
  - `ThreadA` 가 이미 다른 동기화 메서드나 블록에 있는 상태에서 또 동기화 메서드에 엑세스하면 별 문제 없이 그 동기화 메서드에 엑세스 할 수 있다.
