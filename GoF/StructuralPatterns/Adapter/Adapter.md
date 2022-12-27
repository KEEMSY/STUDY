# **Adapter 패턴**
어떤 클래스를 사용할 떄 클래스에 대한 코드를 변경할 수 없는 상황에서도 Adapter을 통해 사용할 수 있도록 하는 패턴

<br><hr><br>

## **예제 코드**


<br>

### **코드 구조**
![Adapter.png](/img/Adapter.png)

<br>

- ### **설명** 

    `User`는  `Animal`을 사용한다.  `Animal` 클래스를 구현한 클래스는 `Dog`, `Cat` 이 존재한다.

    이 때 코드를 수정할 수 없는 `Tiger` 클래스를 `Animal` 클래스처럼 사용하기 위해 `TigetAdapter` 클래스를 생성한다.

    `TigetAdapter` 는 `Animal` 클래스를 구현하고, `Tiger` 클래스를 필드로 갖게 한다.

    이렇게 하여 `Tiger` 클래스를 변경하지 않아도, `TigerAdapter` 클래스를 통해 `Animal` 클래스처럼 사용이 가능하다.
