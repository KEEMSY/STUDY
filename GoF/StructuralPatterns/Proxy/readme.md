# **Proxy 패턴**
`Proxy` 의 뜻은 "대리인"을 의미한다.

`Proxy` 패턴은 **어떤 작업의 실행을 대리인을 통해 간접적으로 실행하도록 하는 패턴**을 말한다.

Proxy 패턴은 어떤 요청에 대해서 그 결과를 캐시처럼 저장해두고 새로운 요청이 이전에 온 요청과 동일하다면 캐시에 저장된 결과를 바로 전달해준다. 이를 통해 속도 향상은 물론 CPU 등과 같은 자원을 절약할 수 있다.  




<br><hr><br>

## **예제 코드**


<br>

### **코드 구조**
![Proxy.png](/img/Proxy.png)

<br>

- ### **설명** 

    `BufferDisplay` 클래스가 `Proxy` 로 **대리인에 해당**한다. 
    어떤 문자열을 출력할 때 `ScreenDisplay` 객체를 바로 사용해도되지만 `BufferDisplay` 클래스를 대신 사용해도 된다.

    `ScreenDisplay` 객체는 하나씩 처리하여 오래걸리지만
    `BufferDisplay` 클래스는 하니씩 처리하면 시간이 오래걸리는 기능을 처리해야할 데이터를 모아서 한번에 처리하도록 하여, 성능을 향상시킨 것이다.

    <br>
    
    `Display` 인터페이스를 두고, `BufferDisplay` 와 `ScreenDisplay` 를 구현하고 있으며 `BufferDisplay` 와 `ScreenDisplay` 를 하나의 타입으로 처리할 수 있다.

    `Display` 인터페이스를 통해 `BufferDisplay` 나 `ScreenDisplay` 를 파악해야 할 때 상위 클래스(인터페이스)인 `Display` 를 파악함으로써 훨씬 빠르고 명확하게 이해 할 수 있다.

    뿐만아니라 시스템이 `BufferDisplay` 나 `ScreenDisplay` 와 직접적으로 관계를 맺지 않고 오직 `Display` 라는 한개의 인터페이스와 관계를 맺게 함으로써 시스템의 구조가 단순하고 명확해 질 수 있다.

    즉, 결합도를 줄여 기능 확장성이 높아지고 유지보수성이 증가한다.
    인터페이스를 두고 이를 구현하는 클래스를 두게 되면, 여러 개발자 간의 협업이 원활해지게 된다.