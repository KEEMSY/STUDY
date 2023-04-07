# **Interpreter 패턴**
`Interpreter` 패턴은 문법에 맞춰 작성된 스크립트를 해석하여, 해석된 구문을 정해진 규칙대로 실행하는 패턴이다.

- `context`: 스크립트를 구성하는 구문을 정해진 문법에 맞춰 해석된 것을 말한다.
- `Expression(표현)`: 단어 하나하나에 대한 구문을 말한다.


![Script.png](/img/Script.png)

- 스크립트를 응용하여 다양하고 매우 길고 복잡한 스크립트를 만들 수 있다.

<br><hr><br>

## **예제 코드**


<br>

### **코드 구조**
![Interpreter.png.png](/img/Interpreter.png)

<br>

- ### **설명** 

    `Context` 는 스크립트에서 구문을 뽑아내는 기능을 담당한다.

    `Expression` 은 스크립트를 구성하는 각 구문을 처리하는 인터페이스 이다.

    ``BeginExpression` 은  BEGIN 구문을 처리하는 클래스이다.

    `CommandListExpression` 은 여러개의 `CommandExpression` 클래스를 가질 수 있는 클래스이다.

    `CommandExpression` 이 실제 쓸 수 있는 명령(LOOP, BACK, RIGHT, FRONT, LEFT 등)에 대한 구문이며, 추상클래스이다.
