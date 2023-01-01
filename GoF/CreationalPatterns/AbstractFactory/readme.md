# **Abstract Factory 패턴**
![FactorySummary.png.png](/img/FactorySummary.png)
> ### **요약**

|Abstract Factory|내용|
|---|---|
|**목적**|목적|
|**설명**|설명|
|**방법**|방법|
|**장점**|장점|
|**단점**|단점|
||

***팩토리 패턴과 팩토리 메서드 패턴의 차이는 추상화이며, 팩토리 메서드 패턴과 추상 팩토리 패턴의 차이는 추상화된 그룹을 형성하고 관리한다는 점이다.***

`Abstract Factory` 패턴은 먼저 만들어야할 컴포넌트들을 추상적으로 정하고 어떤 구체적인 상황이 주어지면 각 컴포넌트들을 구체적으로 만드는 패턴을 말한다.

이 패턴은 **어떤 특정한 상황에 대하여, 그 상황에 맞는 컴포넌트들을 생성해내는 패턴**이다. 만약 **새로운 상황이 생겼을 경우 설계를 확장하기 유용**하다.(별도의 개발문서를 보지 않아도, 소스코드를 통해 자연스럽고 명확히 파악할 수 있다.)

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



    

