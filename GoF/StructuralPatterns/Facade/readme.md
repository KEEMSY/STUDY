# **Facade 패턴**
`Facade` 패턴은 어떤 기능을 처리하기 위해 **여러 객체들 사이의 복잡한 메서드 사용을 감춰 단순화 시켜주는 패턴** 을 말한다.

<br>

`Facade` 패턴을 이용하면, 개발자는 어떤 기능을 수행하기 위해 여러개의 클래스를 파악하지 않고도 `Facade` 에 해당하는 클래스만을 이해하고, 사용하면 된다.

<br>

다른 개발자에게 제공하는 라이브러리나 패키지 형태로 코드를 제공할 때, `Facade` 패턴을 적용하면 `Facade` 에 해당하는 클래스만을 공개하고 그 외 나머지 클래스는 비공개로 처리해도 된다. 이는 해당 라이브러리를 사용하는 개발자 입장에서 부담을 많이 줄여 줄 수 있다.


<br>

> **Facade 패턴이 적용 된 예시: 데이터를 조회하여 출력할 때**

![FacadeExample.png](/img/FacadeExample.png)

<br><hr><br>

## **예제 코드**

<br>

### **코드 구조**
![FacadeStructure.png](/img/FacadeStructure.png)

<br>

- ### **설명** 

    - `Facade` 클래스는  `DBMS` 와 `Cache`, `Row`, `Message` 와 관계를 맺고 있다.

    <br>

    - `Facade` 클래스는 `Row` 와 `Message` 클래스를 사용하고, `DBMS` 와 `Cache` 는 필드로 사용한다.