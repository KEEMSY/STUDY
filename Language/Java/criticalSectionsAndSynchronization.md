# **Critical Sections And Synchronization**

## **임계영역, Critical Sections**

```java
void aggregateFunction() {
    operation1();
    operation2();
    operation3();
}
```

두개의 스레드가 하나의 카운터를 공유하는데 둘 다 그 카운터를 읽고 수정할 수 있다는 것은 문제가 된다.

- `원자적 연산`이 아니게 되어 문제가 된다.

<br>

> **원자적 연산, Atomic Operation**

멀티스레드 애플리케이션을 사용하면서, 많은 작업을 병렬 실행하면서도 정확한 결과값을 도출할 수 있는 고성능 애플리케이션을 구축하는데 핵심이 된다.

- 대부분의 연산은 비원자적 연산이다.
- 모든 레퍼런스 할당은 원자적 연산이다.(=참조를 하는 객체(레퍼런스)는 단일 연산을 통해 안전하게 변경할 수 있다.)
  - 레퍼런스를 가져오거나 배열, 문자열 등 객체에 설정하는 작업을 syncronized 할 필요가 없다.
- long 과 double을 제외한 원시형에 대한 모든 할당도 원시적 작업에 해당한다.
  - int, short, byte, float, char, boolean은 동기화 할 필요없이 안전하게 읽고 쓸 수 있다.  
  - long 과 double은 길이가 64비트 이기 때문에 Java가 보장하지 않기 때문에, CPU 두개 연산을 통해 완료할 가능성이 높다.
  - volatile 키워드로 long, dobule 변수를 선언하면 해당 변수에 읽고 쓰는 작업이 원자적 연산이 될 수 있다.

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

동시에 실행되지 않게 보호해야 하는 코드가 있는 영역을 `임계영역(Critical Section)`이라 부른다.

- `임계영역`에 이미 스레드가 들어와 있다면, 다른 어떤 스레드는 현재 들어있는 스레드가 종료되어 `임계영역` 에서 나갈 때 까지 중단되게 된다.
- 이를 통해 모든 수의 개별 작업에 대해 `원자성`을 확보할 수 있다.

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
- 공유변수에 엑세스 할 수 있는 모든 메서드를 동기화(synchronized)한다면 스레드가 한개 있는 것과 별반 차이가 나지 않는다.
  - 오히려 여러 스레드를 유지하기 위해 컨텍스트 스위칭과 메모리 오버헤드의 손해를 감수하는 상황에 놓이게 된다.

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


