# **Iterator(반복자) 패턴**

동일한 데이터 항목을 여러개 가진 것을 컨테이너(혹은 Aggregator)라고한다.
>  **Container / Aggregator**
- Array
- Linked List
- Tree
- Graph
- Table(DNMS)
등등..

<br>

Aggregator에 구성된 데이터를 하나씩 가져와야 할 때, Aggregator의 종류에 따라 그 방법이 다를 수 밖에 없는데(자료구조가 다르기 때문) 이럴 경우 개발자는 데이터 구조를 파악해야하는 수고가 생긴다.

<br>

Iterator 패턴은 다양한 형태의 Aggregator의 구성 데이터를 참조할 수 있는 표준화된 공통 API를 개발하는 패턴이다.

이렇게 될 경우 개발자는 데이터 구조를 파악하지 않아도 표준화된 한개의 API 만으로도 다양한 구조의 Aggregator의 구성 데이터를 참조 할 수 있다. 


<br><hr><br>

## **예제 코드**
*작성된 예제코드는 Array(배열)의 iterator 데이터를 순차적으로 가져오는 코드이다.*

<br>

### **코드 구조**
![IteratorPattern.png](/img/IteratorPattern.png)

<br>

- ### **설명** 

    `Aggregator와` `Iterator의` 인터페이스는 의 구성 데이터를 하나씩 가져올 수 있는 통일된 인터페이스이다.

    `Array`, `ArrayIterator` 클래스는 **Iterator 패턴**을 적용하기 위해 Aggregator(Container)의 종류 중 배열 자료구조의 특화된 클래스이다.

    `Item` 클래스는 Aggregator(Container)의 구성 데이터에 대한 타입을 말한다.


![diagram-explain.png](/img/diagram-explain.png)