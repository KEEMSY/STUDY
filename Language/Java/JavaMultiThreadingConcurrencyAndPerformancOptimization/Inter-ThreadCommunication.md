# **Inter-Thread Communication**

스레드 간 동기화 도구로 세마포어(Semaphore)를 이야기 할 수 있다.

- 세마포어는 스레드 간 통신을 위한 수단이다.
- 세마포어를 사용하여 권한을 부여하고 허가를 얻는다.
  - 임계영역이나 리소스마다 사용자 수를 제한할 수 있다.
- consumer-producer 시나리오를 실행할 수 있다.

<br><hr><hr>

## **Semaphoere**

```java
int NUMBER_OF_PERMITS = 1;
Semaphoer semaphore = new Semaphore(NUMBER_OF_PERMITS);
semaphore.acquire(); // lock

useResource();

semaphore.release(); // unlock
```

`Semaphoere` 는 허가하고 권한을 부여한다.

- 사용자 수를 특정 리소스나 리소스 그룹을 제한하는데 사용할 수 있다.
- 리소스 당 한명만 허가하는 `lock` 과 달리, **사용자 수가 많든 적든 사용자 수를 리소스에 제한할 수 있다.**
- 허가할 수 있는 초기값을 지정한다.
- `acquire()` 을 호출하면 허가를 내주며, 다음 명령으로 넘어간다.
  - `aquire` 메서드에 허가한 수를 전달하면 스레드는 한 번에 허가를 여러개 얻을 수 있다.
- `release` 메서드는 허가를 한 개 이상 내준다.
  - 허가가 없는 세마포어에 `aquire` 메서드를 호출하면 스레드가 블록된다.

  ```java
  Semaphoer semaphore = new Semaphore(NUMBER_OF_PERMITS);
  semaphore.acquire(NUMBER_OF_PERMITS); //  0 now available
  semaphore.acquire(); // Thread is bloocked
  ```

- **허가를 얻는 것**은 resourece 에 `lock` 을 거는 것과 같다.
  - 세마포어를 `release` 할 때까지 다른 스레드는 `Semaphoere` 를 얻을 수 없다.

<br>

`Semaphoere` 는 `lock` 으로 사용하기에 좋지 않다.

- `Semaphoere` 는 소유자 스레드 개념이 없다.
  - 스레드 여러개가 허가를 얻기 때문이다.
  - 스레드 하나가 `Semaphoere` 를 여러번 얻을 수 있다.
  - 이진 세마포어(초기 값을 1로 맞춘 세마포어)는 블록된다.
  - 스레드는 블록되고 다른 스레드가 이진 세마포어를 릴리스하기까지 기다린다.
  - 스레드가 실행 대기 상태가될 때까지 기다린다.
- 어떤 스레드이든지, `Semaphoere`를 릴리스한다.
  - `Semaphoere`를 얻지 않는 스레드도 `Semaphoere`를 릴리스 한다.
    - 공유 리소스가 있다는 가정하에 왼쪽에 있는 스레드가 이진 세마포어를 얻는 상황이 생길 수 있다.
    - 오른쪽 스레드가 실수로 `Semaphoere`를 릴리스하고 그 결과, **두 개를 임계 영역에 넣는 상황**이 생길 수 있다.

<br>

> ### **Producer-Consumer 시나리오 1**

**`Producer-Consumer`** 는 스레드가 여러개 있는 애플리케이션에 흔히 적용된다.

![SingleProducerAndConsumer.png](/img/SingleProducerAndConsumer.png)

```java
Semaphore full = new Semaphore(0);
Semaphore empty = new Semaphore(1);
Item item = null;
```

```java
// Consumer Thread
while(true) {
    full.acquire(); // <- consumer
    consume(item);
    empty().release();
}
```

```java
// Producer Thread
while(true) {
    empty.acquire();
    item = produceNewItem();
    full.release();
}
```

1. **consumer thread 는 full 세마포어를 처음에 얻으려고한다.**

    - `consumer thread` 는 **block** 되고 상품을 만드는 `producer thread` 를 기다린다.

2. **producer thread 는 empty 세마포어를 처음에 얻고 상품을 생산한다.**

    - 생산한 상품을 `공유변수(item)`에 저장하고 `full Semaphoere` 를 `release` 한다

        - `consumer thread` 를 깨우고 상품을 소비하도록 허가한다.
        - `consumer thread` 가 상품을 소비하기 전에 `proudcer thread` 가 작업을 진행하면, `producer thread` 는 `empty Semaphoere`를 얻게되고, 허가를 얻을 수 없다.
        - `producer thread` 는 **block** 되고 `consumer thread` 가 상품을 다 소비할 때까지 기다린다.

3. **consumer thread 가 상품을 다 소비하면, empty 세마포어를 릴리스한다.**

    - `producer thread` 를 깨우고 새로운 상품을 만들 수 있다.

<br>

- `consumer` 가 `producer` 보다 **빠르면** 일시 중지 모드가 되어 **CPU를 사용하지 않는다.**
- `consumer` 가 `producer` 보다 **느리면** `consumer` 가 상품을 다 소비할 때 까지 `producer` 는 **상품을 만들지 않는다.**

<br>

> ### **Producer-Consumer 시나리오 2**

![MultipleProducersAndConsumers.png](/img/MultipleProducersAndConsumers.png)

```java
Semaphore full = new Semaphore(0);
Semaphore empty = new Semaphore(CAPACITY);
Queue queue = new ArrayDeque();
Lock lock = new ReentrantLock();
```

```java
// Producer
while(true) {
    item = produceNewItem();
    empty.acquire();
    lock.lock()
    queue.offer(item);
    lock.unlock();
    full.release();
}
```

```java
while(true) {
    full.acquire();
    lock.lock();
    Item item = queue.poll();
    lock.unlock();
    consume(item);
    empty.release();
}
```

상품을 `Queue` 로 변경할 수 있다.

- `Queue` 용량을 결정하고, `empty Semaphoere` 를 용량 값으로 설정한다.
- `consumer` 와 `producer` 여러 개가 `Queue` 에 동시에 접속하는 상황을 대비하여 `lock` 을 추가해야한다.
  - `ReentrantLock` 을 사용하기 좋다.
- `Queue` 의 크기는 `consumer` 가 생산하는 양을 따라 갈 수 있게 한다.
  - `Queue` 용량은 무한대로 늘어나지 않는다.(메모리가 부족할 수 있다.)
  - 필요에 따라 CPU 를 최대한 활용할 수 있도록 `Consumer` 와 `Producer` 의 수를 조정한다.

<br><hr>

![ActorModelWithSemaphore.png](/img/ActorModelWithSemaphore.png)

- **액터 모델**을 사용하는 스레드와 프레임워크 간에 작업을 나눌 때 이 패턴을 사용한다.

![SocketChanelWithSemaphore.png](/img/SocketChanelWithSemaphore.png)

- **소켓 채널** 로 작업한 웹 애플리케이션에서도 사용할 수 있다.
  - `TCP` 나 `UDP` 패킷을 사용한 소켓 채널이 대응하는 핸들러에 전달된다.

<br><hr><hr>

## **Inter-Thread communication**

> **Thread.interrupt()**

![Inter-thread-Thread.interrupt.png](/img/Inter-thread-Thread.interrupt.png)

스레드 간 통신하는 방법에는 `Thread.interrupt()` 를 사용하여 **다른 스레드에 신호**를 보낸다.

- 보낸 신호는 하나의 컨텍스트에만 사용되어 `Thread B` 에 **interrupt** 하라고 알려준다.

<br>

> **Thread.join()**

![Inter-thread-Thread.join.png](/img/Inter-thread-Thread.join.png)

`Thread A` 가 CPU를 포기하고, `Thread B` 가 **종료될 때까지 기다린다.**

- `Thread A` 에서 `Thread B` 까지 `Thread.join()` 를 통해 **스레드 간 통신**이 이뤄진다.
  - `Thread B` 가 **종료**될 때 `Thread A` 를 깨우려 **신호** 를 보낸다.

<br>

> **Semaphore**

![Inter-thread-Semaphore.png](/img/Inter-thread-Semaphore.png)

`Semaphore` 권한이 **종료** 되고 `Thread` 가 `Thread A` **정지**하는 권한을 얻으려고 시도할 때 **스레드간 통신을 초기화하는데 `Semaphore` 를 사용한다.**

- `Thread B` 가 `Thread A` 를 깨우기 위해 `Semaphore` 를 `release` 한다.

<br>

  `Semaphore` 는 `조건 변수(condition variable)` 의 특별한 사례라고 이야기 할 수 있다. `Thread` 가 `Semaphore` 를 얻으려한다면, **사용 가능한 권한의 수가 0보다 많은 지 확인하는 것**과 같다.

- 조건이 충족되지 않으면 `Thread A` 는 `sleep(대기)` 하고, `Thread B` 가 `semaphore` **상태를 변경** 할 때 까지 기다린다.
- `Thread B` 가 `release()` 를 호출하면 `Thread A` 가 깨어나 사용 가능한 권한 **조건이 충족됬는지 확인**한다.
- 조건이 충족됬다면, `Thread A` 는 다음 명령어를 계속 실행한다.

<br>

> **Condition Variable(조건변수)**

![Inter-thread-ConditionVariable.png](/img/Inter-thread-ConditionVariable.png)

`조건변수`는 스레드 간 통신의 **제네릭** 방법이다.

- 연속적인 `Thread` 실행을 명시하기 위해 원하는 모든 **조건**을 사용하게한다.
- `조건 변수`는 항상 `lock` 과 연관된다.
- `await()` 를 통해 **스레드를 조건이 부합될 때까지 기다리게하고, `signal()`을 통해 스레드를 깨운다.**
  
  - `lock` 은 **조건확인**과 조건에 들어있는 **공유 변수의 수정이 원자적으로 실행되었는지 확인** 하는데 사용된다.

<br>

> **예시: 사용자 인터페이스 스레드와 인증 스레드 사이의 통신**

  ```java
  Lock lock = new ReentrantLock();
  Condtion condition = lock.newCondition();
  String username = null, password = null;

  lock.lock();
  try {
    while(username == null || password == null) {
      condition.await(); // 공유변수를 unlock 하고, 스레드를 기다리게 한다.
    } 
  }
  finally {
    lock.unlock();
  }
  doStuff(); // 로직 진행 사용자가 존재하는지, 암호가맞는지 확인
  ```

  ```java
  // UI Thread
  lock.lock();
  try {
    username = userTextbox.getText();
    password = passwordTextbox.getText();
    condition.signal(); // 인증 스레드를 깨운다.
  } finally {
    lock.unlock()
  }
  ```

- `username` 과 `password` 의 **입력은, 공유 변수를 통해 이뤄진다.**
- `try block` 에서 **조건이 충족되었는지 확인** 한다.
  - 조건이 충족되지 않은 경우, `await()` 를 호출한다.
    - `condition.await()`: 다른 스레드가 조건 신호를 보낼 때까지 스레드를 슬립시키고, 깨어나기까지 조건변수와  관련된 lock을 unlock 한다.
    - 이를 통해 UI스레드가 lock을 얻을 수 있다.

> **await 관련 메서드**

```java
void await() // unlock lock, signal 이 올 때까지 기다린다.
long awaitNanos(long nanosTimeout) // 나노초로 일정 초과 시간 이상을 기다리지 않는다.
boolean await(long time, TimeUnit unit) // 기다릴 수 있는 시간의 상한과 시간을 측정하려는 단위를 두 인수로 갖을 때까지 기다린다.
boolean awaitUntil(Date deadline) // 특정 시간 또는 날짜가 되기를 기다린다.
```

> **Condition.signal()**

```java
void signal() // 현재 조건변수에서 기다리는 스레드는 깨운다.
void signalAll() // 현재 조건 변수에서 기다리는 모든 스레드를 깨운다.
```

1. **`signal()`**

- `조건 변수`에서 기다리는 `Thread` 가 하나 이상일 경우, 오직 하나만 깨어나고 나머지는 `sleep` 상태로 남는다.
  - 깨어나는 `Thread` 는 `조건변수`와 연관된 `lock` 을 다시 **얻어야 한다.**
  - `조건 변수`에 기다리는 `Thread` 가 없을 때에는 `signal` 이 가는 스레드도 없다.(*semaphore와 차이점*)

2. **`signalAll()`**

- `조건 변수` 에서 기다리는 `Thread` 의 수와 `Thread` **정보를 몰라도 된다**는 장점이 존재한다.
- `signalAll` 와 같은 결과를 얻기 위해, `Semaphore` 에서는  현재 기다리는 `Thread` 만큼의 `Semaphore` 를 `release` 해야한다.

<br><hr><hr>

## **Signal Methods**

```java
public final void wait() throws InterruptedException 
public final void notify()
public final void notifyAll()
```

Java의 객체 클래스에는 wait, notify, notifyAll 메서드가 존재한다.

- Java의 모든 클래스가 객체 클래스에서 상속하기에 어떤 유형의 객체에서도 해당 메서드들을 호출 할 수 있다.
- 어떤 객체라도 조건 변수로 사용할 수 있다.
  - synchronized 키워드를 사용하여 모든 객체를 lock 으로 사용할 수 있다.

> **wait()**

공유 객체에서 호출된 wait 메서드는 다른 스레드가 깨어날 때까지 현재 스레드를 기다리게 한다.

- `wait` 상태에서 스레드는 CPU를 전혀 사용하지 않는다.
- 깨우는 방법에는 2가지가 존재한다.
  - `notify()`: 다른 스레드에서 해당 메서드를 호출하여, 현재 객체에서 대기하는 단일 스레드를 깨운다.

    *여러 스레드가 객체에서 대기하고 있다면, 그 중 하나가 임의로 선택된다.*

  - `notifyAll()`: 객체의 모든 스레드를 깨우기 위해서 사용한다.

깨우기 전(`wait`, `notify`, `notifyAll` 메서드를 호출하기 전)에는 객체를 `동기화` 해야한다.

<br>

> **Example - wait(), notify() and notifyAll()**

```java
// 여러 스레드가 클래스 객체를 공유하는 상황이며, 클래스는 작업의 완료 상태를 알려주는 변수를 포함하고 있다.

// CASE 1

public class MySharedClass {
  private boolean isComplete = false;
  public void waitUntilComplete() {
    synchronized(this) {
      while(isComplete == false) {
        this.wait()
      }
    }
  }
  // ...

  public void complete() {
    synchronized(this) {
      isComplete = true;
      this.notify();
    }
  }
}
```

```java
// CASE 2

public class MySharedClass {
  private boolean isComplete = false;
  public void synchronized waitUntilComplete() {
    while(isComplete == false) {
      this.wait()
    }
  }
  
  // ...

  public void synchronized complete() {
    isComplete = true;
    this.notify();
  }
}

```

|Object Signalling|Condition Variable|
|---|---|
|`synchronized`(object) {|lock.`lock()`|
|}|lock.`unlock()`|
|object.`wait()`|condition.`await()`|
|object.`notify()`|condition.`signal()`|
|object.`notifyAll()`|condition.`signalAll()`|

조건변수와 매우 비슷하지만, 현재 객체를 `lock` 와 `조건변수`를 통해 동시성을 구현하고 있다.

- 어떤 객체라도 사용할 수 있지만, 현재 객체를 사용하면, 메서드 선언에 synchronized 키워드를 옮기고 암시적인 wait 메서드 앞의 키워드를 제거하여 코드를 정리할 수 있다.

> **CountDownLatch**

```java
public class SimpleCountDownLatch {
    private int count;
 
    public SimpleCountDownLatch(int count) {
        this.count = count;
        if (count < 0) {
            throw new IllegalArgumentException("count cannot be negative");
        }
    }
 
    public void await() throws InterruptedException {
        synchronized (this) {
            while (count > 0) {
                this.wait();
            }
        }
    }
 
    public void countDown() {
        synchronized (this) {
            if (count > 0) {
                count--;
                
                if (count == 0) {
                    this.notifyAll();
                }
            }
        }
    }

    public int getCount() {
        return this.count;
    }
}
```

- `countDown()`
  - 각 latch의 count 를 감소시켜, count가 0에 도달하면 대기하고 있던 모든 스레드를 릴리스한다.
  - 만약 현재 count가 이미 0이라면, 아무 일도 일어나지 않는다.

- `await()`
  - 현재 스레드를 latch의 count가 0으로 감소할 때까지 기다리게 한다.
  - 만약 현재 count가 이미 0이라면, 해당 메서드가 바로 반환된다.
