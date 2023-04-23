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
