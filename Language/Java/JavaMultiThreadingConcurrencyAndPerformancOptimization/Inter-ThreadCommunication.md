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
