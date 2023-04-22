# **Sharing Resources Between Threads**

![ProcessWithContext.png](/img/ProcessWithContext.png)

**메모리 관리** 는 멀티스레드 애플리케이션을 써서 `일관적`이고 `정확한` 결과 및 뛰어난 `성능`을 얻기 위해 확실한 이해가 필요하다.

- `객체`, `클래스 멤버` 그리고 `정적 변수`는 `힙`에 할당되고 `스레드`에서 `공유`된다.
- `로컬 변수`는 `원시 타입`이든 `로컬 레퍼런스`이든 `스택`에 할당되고 **특정 스레드에만** 속한다.

<br><hr><hr>

## 스**택(Stack) 메모리 영역**

특정 스레드에는 각 `스택` 영역이 존재한다.

- 스택은 `메서드`가 실행되는 메모리 영역이다.
- 함수에 **인수를 입력할 때마다** 스택에 입력되고 모든 로컬 변수 또한 스택에 저장된다.
- 각 스레드가 다른 코드 라인 또는 메서드를 실행할 수 있어, `스택`과 `명령어 포인터`를 통해 **스레드의 실행 상태**를 알수 있다.

<br>

> ### **스택 사용방법**

- 모든 메서드 프레임이 할당되고, `후입 선출` 순서로 무효화가 일어난다.(**=스택과 같다.**)
- 스택에 입력된 모든 변수는 특정 스레드에 속하기에 **다른 스레드는 변수에 접근 불가** 하다.
- 스레드가 생성될 때 스택은 정적으로 할당되기 때문에 **고정된 크기** 를 갖고 런타임을 변경할 수 없다.
- **너무 많은 메서드 호출을 중첩하여 호출 계층 구조가 너무 길면**, 스택에 할당된 메모리가 고갈되고 `StackOverflow`가 발생한다.

    *`재귀` 메서드를 사용할 때 조심하자.*

<br><hr><hr>

## **힙(Heap) 메모리 영역**

`힙`은 처리에 속하는 `공유 메모리 영역`으로 모든 `스레드`가 `힙`에 있는 **모든 데이터를 공유하고 언제나 힙에 객체를 할당하거나 그 객체에 접근할 수 있다.**

- 모든 `객체`는 `힙`에 저장된다.
- 새로운 연산자를 사용하여 할당할 수 있는 객체는 모두 포함되고, **객체에 속한 모든 것**도 힙에 저장된다.
- 객체 그 `자체`이든 `원시 멤버`이든 상관 없이 저장된다.
- `정적 변수` 또한 `힙`에 할당된다.(정적 변수가 클래스와 관련된 클래스 객체의 멤버이기 때문이다.)
- 객체는 **레퍼런스가 최소 하나라도** 있으면 `힙`에 머무른다.
- 객체의 모든 레러펀스가 사라지면 가비지가 수거된다.
- 멤버 변수는 부모 객체와 묶이며 **부모 객체와 같은 수명 주기**를 갖는다.

<br>

> ### **레퍼런스(References)와 객체(Object)의 차이**

![reference.png](/img/reference.png)

```java
Object referenceVar1 = new Object();
Object referenceVar2 = new Object();
```

- 현재 별개의 3개의 객체 중, **두개는 레퍼런스 변수**이고, 하나는 레퍼런스 변수가 가리키는 **단일 객체**이다.
- 레퍼런스가 `메서드`에 `로컬변수`로 선언되면 레퍼런스는 `스택`에 할당된다.
- `클래스의 멤버`일 경우 `부모객체`와 함께 `힙`에 할당된다.


<br><hr><hr>

## **Resource Sharing Between Threads**

리소스는 데이터나 어떠한 상태를 나타낸다. 우리가 생성하는 모든 객체가 해당한다.

- 스레드가 프로세스 내에서 공유할 수 있는 리소스는 힙(메모리)에 저장하는 모든 항목(모든 객체와 정적변수)를 말한다.

<br>

> ### **멀티스레드 소프트웨어 아키텍처 설계 - 디스패처(Dispatcher)**

![ResourceShareingBetweenThreads.png](/img/ResourceShareingBetweenThreads.png)

한개의 스레드를 지니는데 그 스레드가 사용자에게 직접 또는 입력값으로 받아서 작업을 배포하고 또 공유된 큐(Queue)를 사용해 적은 수의 작업자 스레드만 사용되게한다.

- 작업자 스레드는 해당 큐를 통해 작업이 도착하기를 기다렸다가 현재 작업이 끝나자마자 바로 다음 작업을 착수한다.
- 큐는 힙에 저장된 데이터 공유 리소스에 해당한다.(=새 작업이 생길 때마다 매번 새 스레드를 다시 만들 필요가 없다.)

<br>

> **멀티스레드 소프트웨어 아키텍처**

![DatabaseMicroservice.png](/img/DatabaseMicroservice.png)

데이터베이스와 소프트웨어 추상화 계층의 역할을 하는 마이크로 서비스에서 각 요청은 서로 다른 스레드로 처리된다.

- 해당 데이터베이스에 대한 연결은 하나의 객체 또는 집합으로 나타내는데 모든 요청 스레드 간에 공유된다.

<br>

멀티스레드 아키텍처에서 단일 스레드 애플리케이션 프로그래밍으로 빌드하면 문제가 발생한다.

- 공유된 객체라서 items 멤버 변수 또한 두 스레드에서 공유되고 엑세스 가능해진다는 것이다.
- 여러 스레드가 사용하는 메서드는 원자적 작업이 아니라면(비원자적 연산), 매번 다른 시나리오가 나오게 된다.(실행 순서는 스케줄링을 하는 방법에 따라 달라지기 때문이다.)

*Atomic Operation 이란, 기능적으로 분할할 수 없거나 분할되지 않도록 보증된 조작. 원자와 같이 분할할 수 없다는 것을 비유하여 이렇게 부른다.*

<br>

> ### **수량 증가 스레드(IncrementingThread), 수량 감소 스레드(DecrementingThread)**

```java
public class Main {
    public static void main(String[] args) throws InterruptedException {
        InventoryCounter inventoryCounter = new InventoryCounter();
        IncrementingThread incrementingThread = new IncrementingThread(inventoryCounter);
        DecrementingThread decrementingThread = new DecrementingThread(inventoryCounter);

        incrementingThread.start();
        decrementingThread.start();

        incrementingThread.join();
        decrementingThread.join();

        System.out.println("We currently have " + inventoryCounter.getItems() + " items");
    }

    public static class DecrementingThread extends Thread {

        private InventoryCounter inventoryCounter;

        public DecrementingThread(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                inventoryCounter.decrement();
            }
        }
    }

    public static class IncrementingThread extends Thread {

        private InventoryCounter inventoryCounter;

        public IncrementingThread(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                inventoryCounter.increment();
            }
        }
    }

    private static class InventoryCounter {
        private int items = 0;

        public void increment() {
            items++;
        }

        public void decrement() {
            items--;
        }

        public int getItems() {
            return items;
        }
    }
}
```

- `DecrementingThread`, `IncrementingThread` 를 **동시**에 **실행**된다면, 결과값은 `0` 이 아닌 다른 **엉뚱한 값**이 나온다.

  - 이는 `InventoryCounter` 가 두 스레드로 전달되는 공유된 객체이기 때문이다.
  - 두 스레드간 `increment`, `decrement` 메서드는 동시에 실행되어, **단일작업(`atomic operation`)** 이 아니다.
    - 두 스레드 간 실행되는 순서는 스케줄링 방식에 따라 달라져, 매번 다른 시나리오(결과)가 나오게 되는 것이다.
  - `item ++` 는 **3개의 작업** 이다.
    1. `items` 의 `currentValue` 의 값을 가져온다.
    2. `currentValue` 에 1을 더한 값인 `newValue` 를 만든다.
    3. `newValue` 의 값을 `items` 변수에 저장한다.
