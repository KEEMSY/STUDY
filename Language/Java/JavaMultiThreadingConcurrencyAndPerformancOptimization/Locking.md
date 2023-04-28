# **ReentrantLock of Locking**

## **ReentrantLock**

```java
Lock lockObject = new ReentrantLock();
Resource resource = new Resource();

public void method() {
    lockObject.lock();
    // ...
    throwExceptionMethod(); // Disadvantage
    lockObject.unlock();
}
```

객체에 적용된 `synchronized` 키워드 처럼 작동하나, 동기화 블록과 달리 명확한 `locking` 과 `unlocking` 이 필요하다.

- `ReentrantLock` 객체를 통해 동시 접속으로부터 리소스를 보호하려면 `lock` 메서드를 호출해야한다. 그리고 `lock` 을 해제하려면 `unlock` 메서드를 호출해야 한다.

  - 공유된 리소스를 사용하고 나서 lock 객체를 그대로 놔둘 경우, 버그 혹은 **교착상태(deadlock)** 의 원인이 된다.
  - throwExceptionMethod 가 중간에 발생할 경우, unlock 메서드가 호출되지 않는 문제가 발생할 수 있다.

    ```java
    Lock lockObject = new ReentrantLock();
    Resource resource = new Resource();

    public void method() {
        try {
        lockObject.lock();
            someOperation();
            return value;
        }
        finally {
            lockObject.unlock();
        }
    }
    ```

    - 이를 해결하기 위해 `try` 블록에 임계 영역 전체를 둘러쌓아 `finally` 블록에 `unlock` 메서드를 추가한다.
- `ReentrantLock` 은 공정성을 제어할 때 유용하다.
  - `synchronized` 는 공정성을 보장하지 않아 `ReentrantLock` 이 유용할 수 있다.
  - 하지만 `lock` 을 얻으려면 시간이 오래걸려 애플리케이션의 처리량이 줄어들 수 있다.

<br>

이는 과정이 복잡하여, `lock` 을 관리하기 위해 더 고도의 작업을 해야한다.

- `getQeueueThreads()`: `lock` 을 대기하고 있는 스레드 목록을 반환한다.
- `getOwner()`: `lock` 을 가지고 있는 스레드를 반환한다.
- `isHeldByCurrentThread()`: 현재 스레드에 `lock` 이 있으면 참을 반환한다.
- `isLocked()`: 스레드에 `lock` 이 있는지 없는지 알려준다.

<br>

> **ReentrantLock.lockInterruptibly()**

```java
@Override
public void run() {
    while(true) {
        try {
            lockObject.lockInterruptibly(); // somethread.interrupt();
            // ...
        } catch(InterruptedException) {
            cleanUpAndExit();
        }
    }
}
```

다른 스레드에 이미 `lock` 객체가 있고 lock 메서드가 아닌 `lockInterruptibly` 메서드를 호출하여, 중단상태에서 벗어날 수 있다.

- 감시 장치를 실행할 때 이 메서드를 사용하면 유용하다.
  - 감시 장치로 교착상태 스레드를 검출하고 교착상태 스레드에 `Interrupt` 를 실행해 스레드를 복구한다.

<br>

> **ReentrantLock.tryLock()**

```java
if(lockObject.tryLock()) {
    try {
        useResource();
    }
    finally {
        lockObject.unlock();
    }
}
else {
    // ...
}
```

`tryLock` 메서드를 사용하면 `lock` 메서드처럼 `lock` 객체를 얻는다.

- `lock` 객체가 있다면 `tryLock` 메서드는 `lock` 객체를 얻고 `true` 을 반환한다.
- `lock` 객체가 없다면, 스레드를 차단하는 대신 `false` 를 반환하고 다음 명령으로 넘어간다.
  - 일반적인 `lock` 메서드를 사용할 경우, `lock` 객체를 얻을 때 까지 스레드를 차단한다.
  - 하지만 `tryLock` 메서드를 사용할경우, 메서드가 차단되지 않고, `else` 문에 있는 다른 코드를 실행한다.
    - 이 후, `try` 블록으로 다시 돌아와 lock 객체를 얻는다.
- `lock` 객체가 어떤 스레드에 있건 즉시 값을 반환한다.
- 실시간 스레드를 차단하지 않고 애플리케이션의 반응 속도록 증진 시킬 수 있다.(`lock` 메서드로 스레드를 중단할 수 없다.)
  - 실시간 처리(거래용 시스템, 사용자 인터페이스 등)에 유용하다

- 많은 기능이 서로 연결되고 경쟁상태를 막을 수 있다.

<br>

>**Ex. ReentrantLock.tryLock(): Financial Assets Dashboard**

*CodeFiles/23.reentrantlock-example 참고*

<br>

> ### **ReentrantReadWriteLock**

`ReentrantReadWriteLock` 은 `Read lock` 과 `Write Lock` 을 합쳐서 하나의 `lock` 을 만든것을 의미한다.

- `Read` 가 빈번하고, `Update(Write)` 를 하지 않는 경우, `Reader 스레드`와 `Write 스레드`간 **경쟁 상태를 막는 `lock` 이 필요하다.**

- `Reader 스레드` 사이에는 **경쟁상태** 를 막을 필요가 **없다.**
  - 이는 읽기 작업이 일반적으로 빠르기 때문이다.(임계영역을 짧게 유지하면 `lock` 이 경쟁상태를 막는 일도 거의 발생하지 않는다.)

  - 하지만 **데이터 구조가 복잡**하면 읽기작업이 느려질 수 있다.
    - 이런 경우 **공유자원을 읽는 것을 막으면 애플리케이션의 성능에 문제**가 발생항 수 있다.
    - `ReentrantReadWriteLock` 이 필요한 순간이다.(`Read` 작업 끼리에 대한 제한을 완전히 없앤다.)

<br>

`ReentrantReadWriteLock` 은 `lock` 기능을 제공하지 않는, `쿼리메서드` 이다.

```java
// common
ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
Lock readLock = rwLock.readLock();
Lock writeLock = rwLock.writeLock();
```

```java
// writeLock
writeLock.lock();
try {
    modifySharedResources();
} finally {
    writeLock.unlock();
}
```

```java
// readLock
readLock.lock();
try {
    modifySharedResources();
} finally {
    readLock.unlock();
}
```

- 내부 `lock`(`readLock`, `writeLock`) 이 두개 존재한다.
  - `writeLock` 을 통해 **임계영역에 접근하는 것을 막고, 스레드는 그 영역에서 공유 리소스를 수정**한다.
    - 오직 하나의 `Thread` 만이 `writeLock` 이 허용된다.
  - `readLock` 을 통해 임계영역을 보호하고 작업이 완료되면 `lock` 을 해제하며, `Reader 스레드` 여러개가 `readLock` 이 보호하는 **임계 영역에 진입할 수 있다.**
    - `Reader 스레드`를 몇개 보관하고 있는지 기록한다.
  - `readLock` 과 `writeLock` 은 **서로 차단** 한다.
    - `Thread` 하나가 `writeLock` 을 얻었다면, 다른 `Thread` 는 `readLock` 을 **얻을 수 없다**.
    - **최소 하나 이상**의 `Thread` 가 `readLock` 을 얻었다면, 어떤 `Thread` 도 `writeLock` 을 얻을 수 없다.

<br>

> **ex.Inventory Database**

*CodeFiles/25.read-write-lock-example 참고*