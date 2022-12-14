# **Abstract Factory 패턴**

![FactorySummary.png.png](/img/FactorySummary.png)

<br>

![abstractFactorySubExplaination.png.png](/img/abstractFactorySubExplaination.png)

> ### **요약**

|Abstract Factory|내용|
|---|---|
|**목적**|복수의 객체 생성을 담당하는 군집을 관리하기 위해 사용하며, 문제가 다양할 경우 새로운 객체를 생성해 그룹을 추가한다. |
|**설명**| `Abstract Factory` 패턴은 유사한 객체의 생성 과정을 중심으로 제품군을 설계한다. 이는 객체의 생성을 그룹화 하고 그룹화된 객체를 결합하여 실행함을 의미한다. 다수의 독립적 그룹들은 서로 호환성을 가지며 동일한 인터페이스에 의해 호출된다. 팩토리 메서드 패턴을 포함하며 팩토리 부분을 추상화하여 그룹으로 확장하며, 생성된 그룹을 통해 전체를 쉽게 변경할 수 있다.|
|**방법**|하위 클래스는 조건문 없이 직접 객체를 생성하는 메서드로 구현한다.|
|**장점**|추상화와 상속을 극대화 하여 다형성을의 특징을 응용한다. 이를 통해 그룹을 만들 수 있다. 또한 공통된 인터페이스로 인해 코드를 일관적으로 유지하고 실제 구현을 다르게 실행 시킬 수 있다.|
|**단점**|새로운 종류의 군을 추가하는 것이 쉽지 않다. 계층적 구조로 인해 관리할 그룹이 많아지며, 계층의 크기가 커질수록 복잡한 문제가 발생한다.|
||

***팩토리 패턴과 팩토리 메서드 패턴의 차이는 추상화이며, 팩토리 메서드 패턴(하나의 하위 클래스만 가질 수 있다.)과 추상 팩토리 패턴의 차이는 추상화된 그룹을 형성하고 관리한다는 점이다.***

`Abstract Factory` 패턴은 먼저 만들어야할 컴포넌트들을 추상적으로 정하고 어떤 구체적인 상황이 주어지면 각 컴포넌트들을 구체적으로 만드는 패턴을 말한다.

추상 팩토리는 추상화의 다형성을 이용하여 객체 생성군을 형성하고 추상화와 다형성을 이용하여 집합 단위의 객체 생성을 관리할 수 있다.

이 패턴은 **어떤 특정한 상황에 대하여, 그 상황에 맞는 컴포넌트들을 생성해내는 패턴**이다. 만약 **새로운 상황이 생겼을 경우 설계를 확장하기 유용**하다.(별도의 개발문서를 보지 않아도, 소스코드를 통해 자연스럽고 명확히 파악할 수 있다.)

-  객체 생성을 담당하는 클래스와 이를 호출하는 클라이언트 클래스를 분리할 수 있어, 목적이 변경될 경우 필요한 객체를 생성하는 그룹을 교체할 수 있다.

<br>

> ***Abstract Factory = 추상적인 것들을 만드는 공장***

`Abstract Factory` 패턴이 적용된 API 를 전달받아 사용하는 입장의 개발자는 모든상황에 대한 구체적인 컴포넌트 파악할 필요없이 추상 컴포넌트(`Button`, `CheckBox`, `TextEdit`)들을 파악하고 이해하면 된다.

새로운 환경이 추가되어 구체적인 `Factory` 클래스와 컴포넌트 클래스를 추가하는 경우, **기존에 작성된 소스코드는 전혀 변경할 필요가 없이 오직 새로운 팩토리와 구체적인 컴포넌트에 대한 클래스만 추가하면 된다.**

![AbstractFactoryExample.png.png](/img/AbstractFactoryExample.png)

<br><hr><br>

## **예제 코드**

<br>

### **코드 구조**

![AbstractFactory.png.png](/img/AbstractFactory.png)

<br>

- ### **설명**

    `ComponetFactory` 는 추상적인 컴포넌트들을 만들어 내는 추상 클래스이다. 만들어낼 추상적인 클래스들은 `Button`, `CheckBox`, `TextEdit` 클래스 이다.

    `WindowsFactory`, `LinuxFactory` 클래스는 구체적으로 만드는 클래스이다. `ComponentFactory를` 상속받아 구현하며, **생성해야 할 컴포넌트가 무엇인지 명확히** 한다.

    각각의 `@@Button`, `@@CheckBox`, `@@TextEdit` 클래스는 생성해야 할 컴포넌트에 해당한다.
