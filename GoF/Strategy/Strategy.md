# **Strategy(전략) 패턴**

 어떤 하나의 기능을 구성하는 특정 부분을 실행 중에 다른 것으로 효과적으로 변경할 수 있는 방안을 제공한다.(전략을 바꾼다.)

즉, 하나의 기능에 대해서 서로 다른 방식의 구현을 실행 중에 변경할 수 있는 패턴이다.

<br><hr><br>

## **예제 코드**


<br>

### **코드 구조**
![Strategy.png](/img/Strategy.png)

<br>

- ### **설명** 

    `SumPrinter`는  SumStrategy 인터페이스만을 알뿐 실제 총합을 계산하는 SimpleSumStrategy 와 GaussSumStrategy 클래스의 존재는 모른다.

    추후의 총합의 계산 방법이 추가되었을 떄, `SumPrinter` 의 코드를 수정할 필요가 전혀 없다. 

