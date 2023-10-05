# 의존성 역전 원칙, Dependency Inversion Princicple(DIP)

의존성 역전원칙을 이야기 하기 전에, 다음의 질문에 답을 해보는 것이 좋다.

- `의존성 역전`이 뜻하는 것은 **어떤 대상 사이의 역전**인가? 그리고 **어떤 의존이 역전**되는 것인가? 그리고 여기서 말하는 **역전은 무엇을 의미**하는가?
- `제어 반전(Inversion of Control)` 과 `의존성 주입(Dependency Injection)` 의 두가지 다른 개념은 의존 역전과 같은 개념에 속하는가? 만약 그렇지 않다면 그 차이는 무엇인가?
- `Spring` 의 `IoC` 는 앞에서 언급한 세 가지 개념과 어떤 관련이 있는가?

<br><hr>

## 의존성 역전 원칙

`의존성 역전 원칙`의 정의는 다음과 같다.

- `상위 모듈`은 `하위 모듈`에 의존하지 않아야 하며, `추상화`에 의존해야 한다. 또한 `추상화`가 `세부 사항`에 의존하는 것이 아니라, `세부 사항`이 `추상화`에 의존해야 한다.
	- 상위모듈: 호출자
	- 하위 모듈: 수신사

<br>

`의존성 역전 원칙`은 `제어반전(Inversion of Control, Ioc)` 과 유사하게 프레임워크의 설계를 사용하도록 하는데 주로 사용된다.

- Java 웹 애플리케이션을 실행할 때 사용하는 Tomcat(상위모듈)와 우리가 작성한 웹 애플리케이션코드(하위모듈) 은 서로 직접적인 의존성이 없으며, 둘 다 동일한 추상인 서블릿 사양에 의존한다.
- 서블릿 사양은 이에 의존하는 특정 Tomcat 컨테이너나 애플리케이션 구현 세부사항에 의존하지 않는다.

<br><hr>

## 제어 반전(Inversion of Control, Ioc)

**제어**는 프로그램의 **실행 흐름을 제어하는 것**을 의미한다.

- **역전이 되는 대상**은 프레임워크를 사용하기 전에 **직접 작성했던 전체 프로그램 흐름의 실행을 제어하는 코드**이다.
- 전체 프로그램의 실행 흐름은 프레임워크에 의해 제어되고, **흐름의 제어는 프로그래머에서 프레임워크로 역전**된다.

<br>

`제어 반전`을 구현하는 방법은 다양하게 존재하며, `제어 반전`은 특정한 기술이 아닌, 일반적으로 프레임워크를 사용할 때 만나게 되는 보편적인 설계 사상에 가깝다.

- `템플릿 디자인 패턴`을 사용하는 방법
- `의존성 주입`을 사용하는 방법


<br><hr>

## 의존성 주입(Depenecy Injection, DI)

`의존성 주입`은 제어 반전과 달리 특정한 프로그래밍 기술을 의미한다. 

- `의존성 주입`을 한 문장으로 요약하면, `new ` 예약어를 사용하여 클래스 내부에 종속되는 클래스 객체를 생성하는 대신, 외부에서 종속 클래스의 객체를 생성한 후 생성자, 함수의 매개변수 등을 통해 클래스에 주입하는 것을 의미한다.
- 의존성 주입은테스트 가능한 코드를 작성하는 효과적인 수단이다.

<br>

```java
public class Notification {
	private MessageSender messageSender;

	public Notification(MessageSender messageSender) {
		this.messageSender = messageSender; // new 를 사용하지 않고, 의존성 주입
	}

	public void sendMessage(String cellphone, String message) {
		this.messageSender.send(cellphone, message);
	}
}

// Notification 사용
MessageSender messageSender = new SmsSender();
Notification notification = new Notification(messageSender)
```

```java
public interface MessageSender {
	void send(String cellphone, String message);
}

// 문자 메시지 발송 클래스
public class SmsSender implements MessageSender {
	@Override
	public void send(String cellphone, String message) {
	// ...
	}
}

// 내부 문자 발송 클래스
public class InboxSender implements MessageSender {
	@Override
	public void send(String cellphone, String message) {
	// ...
	}
}
```

- 예제 코드에서는 클래스 내부에서 `new `예약어를 통해 `MessageSender` 객체를 직접 생성할 필요가 없게 되었지만, 여전히 객체 생성, 조합, 의존성 주입 등의 코드 논리는 프로그래머가 직접 작성해야만 한다.

<br><hr>

## 의존성 주입 프레임 워크

실제 개발 시에는 수십, 수백 개의 클래스가 필요할 수 있으며, 이에 따라 클래스 객체의 생성과 의존성 주입은 매우 복잡해진다.

- 프로그래머가 직접 코드를 작성하는 방식으로 진행하는 경우, 오류가 발생하기 쉽고, 개발 리소스도 많이 든다.
- 객체의 생성과 의존성 주입은 비즈니스 논리에 속하지 않는다.

<br>

여기서 **"객체의 생성과 의존성 주입은 비즈니스 논리에 속하지 않는다."** 에 주목을 해야한다. 이 말은 즉, 불필요한 리소스 낭비를 도구를 통해 줄일 수 있다는 의미이기도 하다.

- 프레임 워크에 의해 자동으로 완성되는 코드로 추상화 하는 것이 가능하다. 그리고 이것을 `의존성 주입 프레임 워크`라고 한다.

<br>

`의존성 주입 프레임워크`를 사용하면, 생성해야하는 모든클래스의 객체와 클래스 간의 의존성을 간단히 구성할 수 있으며, 이를 통해 프레임워크가 객체를 생성하고, **객체의 라이프 사이클을 관리하고, 의존성을 주입할 수 있게 된다.**

- 대표적으로는, `Spring` 의 `IoC`, `DI` 를 이야기 할 수 있으며, Spring 프레임워크는 의존성 주입 프레임 워크이다.

<br>

> **Spring IoC (Inversion of Control)**

`Spring IoC` 는 `제어의 역전`을 의미한다. 기존에는 개발자가 객체의 생성과 생명주기를 직접 관리했다면, `Spring IoC Container`가 이를 대신 수행한다. 개발자는 더 이상 객체의 생성, 초기화, 소멸과 같은 일을 처리할 필요가 없고, 스프링 컨테이너가 객체의 생명주기와 의존성을 관리하게된다.

`Spring` 에서는 주로 Bean 이라고 불리는 객체들을 `Spring Container` 에 등록하여 사용하는데, `Spring IoC Container` 는 이러한 `Bean` 을 생성하고, 필요에 따라 주입하며, 소멸한다. 이렇게 함으로써 객체 간의 결합도를 낮추고 코드의 유지보수성을 높이는 효과를 얻을 수 있다.

<br>

>  **Spring DI (Dependency Injection)** 

Spring DI는 의존성 주입을 의미한다. 객체가 다른 객체에 의존할 때, 이 의존성을 Spring Container 가 주입해주는 개념을 말한다. Spring 은 주로 생성자 주입, 세터 주입, 필드 주입 등을 통해 의존성을 주입한다.
