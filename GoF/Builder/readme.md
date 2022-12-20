# **Builder 패턴**
`Builder` 패턴은 복잡한 구성의 객체를 효과적으로 생성하는 패턴을 말한다.

다음의 경우 `Builder` 패턴을 사용한다.
- 생성 시 지정해야 할 인지가 많을 때 사용한다.
- 객체 생성 시 여러 단계를 순차적으로 거칠 때, 이 단계의 순서를 결정해 두고 각 단계를 다양하게 구현할 수 있을 경우

<br><hr><br>

## **예제 코드**

<br>

### **코드 구조**
Builder1
![Builder1.png](/img/Builder1.png)

<br>

- ### **설명** 

    - `Car` 클래스는 실제로 생성하고자 하는 클래스이다.

    <br>

    - `CarBuilder` 클래스는 Car 객체를  생성해주는 Builder 클래스이다.
    `Car` 클래스를 생성할 때, 생성자의 인자에 `Car` 를 구성하는 스펙 항목들을 지정받게 된다.여기서는 총 6개의 인자를 받는다.

    `Car` 클래스를 생성하는 방법은 2가지이다.
    1. new 키워드와 함께 `Car` 클래스를 생성하는 방법
    2. `CarBuilder` 클래스를 이용하는 방법

<br><hr><br>

### **코드 구조**
Builder2
![Builder2.png](/img/Builder2.png)

<br>

- ### **설명** 

    - `Builder` 클래스는 추상 클래스이며, 무언가를 만드는 책임을 갖는다.
        무언가를 만들어 반환하는 메스드 들을 제공해야 하며, 이 메서드들은 추상메서드 이다.

    <br>

    - `Director` 클래스는 Builder 에서 제공하는 메서드들을 정해진 순서대로 정확하게 호출해야 할 책임을 갖는다.

    - `Builder` 추상클래스를 구현하는 `PlainTextBuilder`,       `JSONBuilder`, `XMLBuilder` 클래스들은 무언가를 만드는 `Builder` 클래스에 추상 메서드들을 구체적으로 구현한다.

    <br>

    - `PlainTextBuilder` 는 어떠한 텍스트 객체, `Data` 객체를 평이한 텍스트 형태의 문자열로 바꾼다.

    - `JSONBuilder` 는  `Data` 객체를 JSON 형태의 문자열로 만든다.

    - `XMLBuilder` 객체는 `Data` 객체를 XML 형태의 문자열로 만든다.
    
    <br>

    - `Builder` 패턴은 `Template`, `Facade`, `Strategy` 패턴을 객체의 생성에 적용한 것이라고 볼 수 있다. 

    - `Director` 가 `Facade` 패턴에 관한 것으로 `Builder` **객체의 메서드들의 사용을 단순화** 시켜주고 있다.

    - `Builder` 를 통해서 무언가를 생성해 낼 때, **호출되는 메서드들을 추상메서드로 선언하고, 각 메서드를 구현 클래스에서 구현하고 이를 메서드들의 호출 방식**은 `Director` 클래스에서 정해놓은 것은 `Template` 패턴의 응용에 해당한다.

    - **원하는 상황에 맞는 형식의 문자열로 변환하는 것**은 `Strategy` 패턴의 적용에 해당한다.