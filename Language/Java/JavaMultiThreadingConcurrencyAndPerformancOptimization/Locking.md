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

**Ex. ReentrantLock.tryLock(): Financial Assets Dashboard**

> **요구사항**

- 알고싶은 가격은 뭐든지 실실간으로 볼 수 있다.
- 스레드는 2개를 사용한다.
  - UI Thread: 배경에 애니매이션을 만든다.
    - 마우스 이벤트에 반응해서 투자 자산의 현재 가격을 보여준다.
    - Background 스레드로부터 새로운 가격을 얻어 사용자에게 보여준다.
  - Background Thread: 정기적으로 자산에 해당하는 최신 가격을 얻는다.
- UI스레드와 Background 스레드 간 공유 리소스가 필요하다.

*CodeFiles/23.reentrantlock-example 참고*