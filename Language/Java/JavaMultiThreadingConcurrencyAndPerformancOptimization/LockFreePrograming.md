# **Lock Free Programing**

멀티스레드 프로그래밍의 대부분은 Lock 을 통해 이뤄진다.

- 병행성 문제는 대부분 lock 으로 쉽고 안전하게 해결할 수 있다.
- 경쟁상태와 데이터 경쟁과 같은 모든 병행성 문제를 lock을 통해 해결한다.

하지만 lock에는 몇가지 문제가 존재한다.

- **교착상태(deadlock)**
  - 교착상태(deadlock)은 구현할 노력과 시간이 없다면 데드락은 해결할 수 없다.
  - 애플리케이션에 lock이 많을수록 교착상태에 걸릴 확률이 높다.
- **임계영역의 지연(Slow Critical Section)**
  - 스레드 하나만 같은 lock 으로 보호되는 임계영역에 들어갈 수 있는데, 긴 임계영역이 다른 스레드를 느리게 할 수 있다.
  - 이로 인해 나머지 스레드가 전부 느려지는 상황에 놓이게 된다.
- **우선순위 역전(Pririty Inversion)**
  - 리소스 하나와 그 리소스의 lock을 공유하는 스레드가 두개일 경우 발생한다.
  - 낮은 우선순위 스레드가 공유 lock 을 획득하고 운영체제에 의해 선택되면, 운영체제가 낮은 우선순위 스레드를 다시 충분히 스케줄링하지 않아, 우선순위 스레드가 lock을 획득하지 못한다.
  - 이는 애플리케이션의 무응답성 뿐만아니라, 실행에도 문제를 일으킬 수 있다.
- **스레드가 Lock 을 가진 채, 죽거나 인터럽트 될 경우(Thread Not Releasing a lock(Kill tolerance))**
  - 모든 스레드가 영원히 정체되고 교착상태(deadlock)처럼 상황은 회복되지 않는다.
  - 이를 방지하기 위해 tryLock을 사용해 try와 block, 타임아웃을 설정한다.
- **성능 오버헤드(Performance)**
  - lock 이 릴리스 될 때 다른 스레드를 가져오는 오버헤드가 발생한다.
  - 밀리초보다 짧은 지연시간을 연산하는 초고속 거래시스템과 같은 애필리케이션에서 이러한 오버헤드는 고려해야 할 사항이다.

<br><hr><hr>

## **왜 lock 이 필요한가?( lock이 필요했던 이유가 무엇인가, lock 의 대인은 무엇인가?)**

lock 이 필요한 핵심 이유는, 공유 자원에 대하여, 원자적 연산을 보장하기 위함이다.

> ### **Lock Free Solution**

 연산을 활용하여 단일 하드웨어 명령어로 실행될 수 있도록 보장한다.

- 단일 하드웨어 명령어는 의미상 원자적이며, 스레드는 안전하다.

 > **원자적 연산의 종류**

- `long`/ `double` 을 제외한 모든 원시 유형(`primitive types`)
- `Read`/`Assignment`
- `volatile primitive types`
- `java.util.concurrent.atomic` package

> **AtomicX Classes**

- `AtomicBoolean`
- `AtomicInteger`
- `AtomicIntegerArray`
- `AtomicIntegerFieldUpdater<T>`
- `AtomicLong`
- `AtomicLongArray`
- `AtomicFieldUpdateer<T>`
- `AtomicMarkableReference<V>`
- `AtomicReference<V>`
- `AtomicReferenceArray<E>`
- `AtomicReferenceFieldUpdater<T,V>`
- `AtomicStamperdReference<V>`
- `DoubleAccumlator`
- `DoubleAdder`
- `LongAccumulator`
- `LongAdder`

<br><hr>

> ### AtomicInteger

```java
int initialValue = 0;
AtomicInteger atomicInteger = new AtomicInteger(initialValue);

// atomically increment the integer by one
atomicInteger.incrementAndGet(); // return new value
atomicInteger.getAndIncrement(); // return the previous value

// atomically decrement the integer by one
atomicInteger.decrementAndGet(); // return the new valuel
atomicInteger.getAndDecrement(); // return the previous value

int delta = 5;
// atomically add any integer
atomicInteger.addAndGet(delta); // return new value
atomicInteger.getAndAdd(delta); // return the previous value
```

```java
int initialValue = 0;
AtomicInteger atomicInteger = new AtomicInteger(initialValue);

atomicInteger.incrementAndGet();
atomicInteger,addAndGet(-5) // Race Condition
```

원자적 정수는 정수 값으로, 실행할 수 있는 원자적 연산을 제공하는 클래스이다.

- 원자적 정수는 계산이나 집합, 행렬 이벤트 등 수를 확인해야하는 작업을 lock 을 사용하지 않고 간단하게 병렬 수행을 가능하게 해준다.
  - 원자적 연산은 비원자적 연산을 보호하기 위해 lock을 가진 일반 정수를 사용하는 연산과 동등하거나 더 나은 성능을 보인다.
  - 하지만 싱글스레드 환경에서는 일반 정수사용보다 느려질 수 있기에 원자적 정수가 제공하는 연산이 중요하지 않은 경우, 정규 정수를 사용한다.
- 원자적 연산의 장점은 lock 이나 synchronization(동기화)가 전혀 필요하지 않아, 경쟁상태 혹은 데이터 경쟁을 걱정하지 않아도 된다.
- 하지만 atomic 메서드를 다른 연산과 함께 원자적으로 실행할 수 없다.
  - 원자적 연산이지만 서로 분리되어 있기 때문이다.

> **관련 메서드**

- `incrementAndGet()`: 정수를 원자적으로 1 증가시키며, 새로운 값을 반환한다.
- `getAndincrement()`: 정수를 원자적으로 1 증가시키며, 이전 값을 반환한다.
- `decrementAndGet()`: 정수를 원자적으로 1 감소시키며, 새로운 값을 반환한다.
- `getAndDecrement()`: 정수를 원자적으로 1 감소시키며, 이전 값을 반환한다.

- `addAndGet()`: 원자적 정수 현재 값에 어떤 정수일지라도 더한 뒤, 새로운 값을 반환한다.
- `getAndAdd()`: 원자적 정수 현재 값에 어떤 정수일지라도 더한 뒤, 이전 값을 반환한다.
