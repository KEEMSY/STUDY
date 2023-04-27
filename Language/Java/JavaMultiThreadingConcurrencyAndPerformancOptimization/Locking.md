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

