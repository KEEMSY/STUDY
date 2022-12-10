# **Mediator 패턴**
`Mediator`는 **중재자** 를 의미하며,
**복잡한 관계간의 객체가 발생할 경우 중재자를 두어 복잡한 관계를 단순화** 시키는 패턴을 `Mediator` 패턴이라 말한다.


<br><hr><br>

## **예제 코드**


<br>

### **코드 구조**
![Mediator.png](/img/Mediator.png)

<br>

- ### **설명** 


    `SmartHome`는 `Mediator` 인터페이스를 구현하고 있으며, 이 객체는 여러개의 `Participant` 객체를 가지고 있다.(`Door`, `Window`, `HeatBoiler`, `CoolAircon` 객체들이 해당한다.)

    `Participant` 는 중재를 통한 중재에 참여한다는 의미이다. 자신을 중재해줄 `Mediator` 인터페이스를 참조하고 있으며, **어떤 상태 변화가 발생하면 참조하고 있는 `Mediator` 인터페이스 객체에게 상태변경이 발생했음을 알린다.**

    `Door`, `Window`, `HeatBoiler`, `CoolAircon` 는 자신의 상태가 변경되게 되면 `Mediator` 객체에게 상태변경이 발생했다고 알리게 된다.

    이 객체들은 **서로 관계를 갖지 않고 오직 `SmartHome` 객체를 통해서만 관계를 맺는다.**
    
    참여자들간의 관계는 자주 변경되는데, 이렇게 자주 변경되는 것들이 한 곳에 집중되어 있는 것이 유지보수 하기에 매우 좋다. 또 시스템인 단순하고 응결해 진다는 장점이 있다.
    
