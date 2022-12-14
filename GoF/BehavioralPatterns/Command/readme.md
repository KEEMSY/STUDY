# **Command 패턴**
`Command` 패턴은 하나의 명령(기능)을 객체화한 패턴을 말한다.

`Command` 패턴의 특징은 다음과 같다.
- 객체는 전달할 수 있고 보관할 수 있다. 즉 명령(기능)을 전달하고 보관할 수 있게 된다.(데이터가 아닌 행위를 저장할 수 있다.)
- 명령을 전달하는 것도 메서드 인자로 전달하는 것 뿐만아니라 네트워크를 통해 또 다른 컴퓨터에 전달하여 기능을 수행할 수 있다.
- 배치 실행, Undo/Redo, 우선순위가 좊은 명령을 먼저 실행하기 등이 가능해진다.

**기능을 자유롭게 전달**하고, **보관**할 수 있게 되면 기능들을 모아서 한번에 실행하는 배치 실행, 이미 실행된 기능을 되돌리거나, 다시 실행하기가 가능하다.

뿐만아니라 기능들을 모으고 가장 우선순위가 높은 기능을 먼저 실행할 수 있는데 이는 시스템의 **유연성**을 발휘한다. 말할 수 있다.

실제 개발에 적용을 하면, 작은 기능 구현에 집중해서 개발하게 되고, 이 기능들을 조합해서 복합적이고, 더 큰 기능을 자연스럽게 만들어 나가는 느낌을 받을 수 있다.

<br><hr><br>

## **예제 코드**


<br>

### **코드 구조**
![Command.png](/img/Command.png)

<br>

- ### **설명** 

    `Command` 인터페이스는 

    `CommandGroup` 클래스는 `Command` 객체를 여러개 담을 수 있다. 이 클래스는 여러개의 기능을 한번에 실행할 수 있는 배치 기능 구현에 이용될 수 있다.

    각 실행 클래스들은 **오직 자신의 기능을 실행하는 것에만 초점**을 맞춘다.

    

