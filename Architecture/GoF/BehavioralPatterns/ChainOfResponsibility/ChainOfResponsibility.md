# **Chain of responsibility 패턴**
`Chain of responsibility` 패턴은 **책임의 연결**을 의미한다.
**책임**이란 **무언가를 처리하는 기능(클래스)**을 말한다. 이 **기능이 대한 구현는 클래스**로 이루어 진다.

- 여러 개의 책임들을 동적으로 연결해서 순차적으로 실행하는 패턴이다.
    책임을 연결한다는 것은 여러개의 책임을 동적으로 실행중에 연결한다는 의미이다.


- 기능을 클래스 별로 분리하여 구현하도록 유도하므로 클래스의 코드가 최적화된다.

    기능을 책임이라는 기능으로 분리, 즉, 기능을 클래스 별로 분리해서 구현함으로써, 기능에대한 클래스에 대한 독립성이 보장되고 이로인해 최적화된 코드를 작성할 수 있게된다.

<br>

이 패턴을 통해 **새로운 기능을 추가하게 되면 기존의 다른 클래스의 코드는 전혀 변경하지 않고 새로운 코드작성만으로 기능을 추가** 할 수 있게 된다.

이는 최적화된 설계 구조에서 나타나는 매우 좋은 모습이라 할 수 있다.

![ChinOfResponsibilityExam.png](/img/ChinOfResponsibilityExam.png)



<br><hr><br>

## **예제 코드**


<br>

### **코드 구조**
![ChinOfResponsibility.png](/img/ChinOfResponsibility.png)

<br>

- ### **설명** 

    `Handler` 클래스는 책임에 대한 부모클래스이고, 이 클래스를 상속받아 구체적인 책임을 구현하게 된다.

    `Handler` 는 자신과 동일한 객체를 필드로 갖는다.
    `Handler` 이름의 의미는 무언가를 처리한다를 의미힌다.
    <br>
    
    `ProtocolHandler` 클래스는 URL에서 **프로토콜만을 쳐리**하고,
    `DomainHandler` 클래스는 URL에서 **도메인만을 처리**하며,
    `PortHandler` 클래스는 URL에서 **포트만을 처리**한다.

  
   
    