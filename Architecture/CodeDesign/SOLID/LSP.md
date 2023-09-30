# 리스코프 치환 원칙, Liskov Substitution Principle(LSP)

리스코프는 이에 대하여  "`만약 S 가 T 의 하위 유형인 경우, T 유형의 객체는 프로그램을 중단하지 않고도 S 유형의 객체로 대체 될 수 있다.`" 를 말했다.

- 로버트 마틴은 "`기본 클래스를 참조 포인터를 사용하는 함수는 특별히 인지하지 않고도 파생 클래스의 객체를 사용할 수 있어야 한다.`" 라고 하였다.

리스코프와 마틴의 설명을 조합하여 다음과 같이 정의 할 수 있다.

- 하위 유형 또는 파생 클래스의 객체는 프로그램 내에서 상위 클래스가 나타내는 모든 상황에서 대체 가능하며, 프로그램이 원래 가지는 논리적인 동작이 변경되지 않으며, 정확성도 유지된다

혹은 `계약에 따른 설계(design by contract)` 으로 설명할 수 있다.

- 하위 클래스를 설계할 때에는 **상위 클래스의 동작 규칙**을 따라야한다.
- 상위 클래스는 함수의 동작 규칙을 정의하고 하위 클래스는 함수의 내부 구현 논리를 변경할 수 있지만 함수의  원래 **동작 규칙**은 변경할 수 없다.
	- 동작 규칙: 함수가 구현하기 위해 선언한 것, 입력, 출력, 예외에 대한 규칙, 주석에 나열된 모든 특수 사례 설명 이 포함된다.

> **예시 코드**

```java
public class Transporter {
	private httpClient httpClient;
	
	public Transporter(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public Response sendRequest(Request request) {
		// ...httpClient 를 통해 발송 요청하는 코드 생략...
	}
}
```

```java
public class SecurityTransporter extends Transporter {
	private String appId;
	private String appToken;

	public SecurityTransporter(HttpClient httpClient, String appId, String appToken) {
		supper(httpClient);
		this.appId = appId;
		this.appToken = appToken;
	}

	@Override
	public Response sendRequest(Request request) {
		if (StringUtils.inNotBlank(appId) && StringUtils.isNotBlank(appToken)) {
			request.addPayload("app-id", appdId);
			request.addPayload("app-token", apptToken);
		}
		return super.sendRequest(request);
	}
}

public class Demo {
	public void demoFunction(Transporter transporter) {
		Request request = new Request();
		// request 객체에 값을 설정하는 코드 생략
		Response response = transporter.sendRequest(request);
		// 일부 코드 생략
	}
}
```

```java
// 리스코프 치환원칙
Demo demo = new Demo();
demo.demofunction(new SecurityTransporter(/*매개 변수 생략*/))
```
<br><hr>

## 리스코프 치환 원칙과 다형성의 차이점

`리스코프 치환원칙` 과 `다형성`은 보기에는 비슷해 보이지만, 실제로는 완전히 다른 의미를 담고 있다.

```java
// 수정 전
public class Securitytransporter extends Transporter {
	// ...일부 코드 생략...
	@Override
	public Response sendRequest(Request request) {
		if (StringUtils.inNotBlank(appId) && StringUtils.isNotBlank(appToken)) {
			request.addPayload("app-id", appdId);
			request.addPayload("app-token", apptToken);
		}
		return super.sendRequest(request);
	}
}
```

```java
public class Securitytransporter extends Transporter {
	// ...일부 코드 생략...
	@Override
	public Response sendRequest(Request request) {
		if (StringUtils.inBlank(appId) || StringUtils.isBlank(appToken)) {
			throw new NoAuthorizationRunTimeException(...);
		}
		request.addPayload("app-id", appdId);
		request.addPayload("app-token", apptToken);
		return super.sendRequest(request);
	}
}
```

수정된 코드에서는 상위 클래스 `Transporter` 클래스의 객체가 `demoFunction()` 메서드로 전달 되는 경우에는 어떤 예외도 발생시키지 않지만, 하위 클래스인 `SecurityTransporter` 클래스의 객체가 `demoFunction()` 메서드로 전달되면 예외를 발생시킬 수 있다.

- 하위 클래스가 상위 클래스의 `demoFunction()` 메서드를 대체하면서 **전체 프로그램의 논리적 동작이 변경**되었다.
- 이것은 설계 관점에서 리스코프 치환원칙을 따르지 않는다.

<br>

`다형성` 은 **코드를 구현하는 방식**에 해당하지만, `리스코프 치환 원칙`은 **상속 관계에서 하위 클래스의 설계 방식**을 설명하는 설계원칙에 해당한다.

- **상위 클래스를 대체할 때 프로그램의 원래 논리적 동작이 변경되지 않고** 프로그램의 **정확성**이 손상되지 않도록 해야한다.


<br><hr>

## 리스코프 치환 원칙을 위반하는 안티패턴

리스코프 치환 원칙을 위반하는 예시를 다음과 같이 이야기 할 수 있다.

- 하위 클래스가 구현하려는 상위 클래스에서 **선언한 기능을 위반** 하는 경우
- 하위 클래스가 입력, 출력 및 예외에 대한 **상위 클래스의 계약을 위반** 하는 경우
- 하위 클래스가 상위 클래스의 **주석에 나열된 특별 지침을 위반** 하는 경우

<br>

> 하위 클래스가 구현하려는 상위 클래스에서 선언한 기능을 위반하는 경우

상위 클래스가 주문 정렬을 위한 `sortOrdersbyAmount()` 함수를 정의하여 **금액에 따라 작은 것부터 큰 것 순서대로 주문을 정렬**하게 구성되었을 때, 하위 클래스에서 **생성 날짜에 따라 주문을 정렬**하도록  `sortOrdersbyAmount()` 함수를 재정의 한 경우 하위 클래스의 설계는 리스코프 치환 원칙을 위반하게 된다.

<br>

> 하위 클래스가 입력, 출력 및 예외에 대한 상위 클래스의 계약을 위반하는 경우

상위 클래스에서 작업 시 **오류가 발생하면 null 을 반환** 하며, **값을 얻을 수 없을 경우에는 빈 컬렉션을 반환**하지만, 하위클래스에서 이 함수를 재정의 하면서 구성이 변경되어, **작업시 오류가 발생하면 null 대신에 예외를 발생**시키고, **값을 얻을 수 없을 때는 null 을 반환** 한다면 하위 클래스의 설계는 리스코프 치환 원칙을 위반하는 것이다.

<br>

**상위 클래스에서 입력 시 모든 정수를 받아들일 수** 있지만, 하위 클래스에서 이 함수를 재정의하면서 **양의 정수만 받아들일 수** 있으며, **음의 정수를 입력 받을 경우 예외를 발생** 시킨다면 하위 클래스의 유효성 검사가 상위 클래스의 유효성 검사보다 훨씬 엄격하게 변경된 것이며, 이 하위 클래스의 설계는 리스코프 치환 원칙을 위반한다.

<br>

상위 클래스에서 발생시키는 예외가 `ArgumentNullException` 예외 하나 뿐이라면 하위 클래스에서 이 함수를 재정의 하더라도 여전히 `ArgumentNullException` 만 발생 시킬 수 있다. 그렇지 않다면 하위 클래스의 설계는 리스코프 치환 원칙을 위배한다.

<br>

> 하위 클래스가 상위 클래스의 주석에 나열된 특별 지침을 위반하는 경우

상위 클래스에서 예금을 인출하는` withdraw()` 함수가 정의되어 있으며, **주석으로 사용자의 출금 금액이 계정 잔액을 초과해서는 안된다고 명시**되어 있는경우 VIP 계정에 대한 처리를 담당하는 하위 클래스에서 재정의된 `withdraw() `함수가 **당좌 인출 기능**을 지원하는 경우 이는 리스코프 치환 원칙을 위반한다.

- 상위클래스의 **주석을 수정**하는 방법이 가장 쉽게 하위클래스가 리스코프 치환 원칙을 만족하게 할 수 있다.

<br><hr>

## 리스코프 치환 원칙을 위반하는지 판단하는 방법

하위 클래스의 설계 와 구현이 리스코프 치환 원칙을 위반하는지 여부를 판단하기 위한 방법으로, 상위 클래스의 `단위 테스트`를 통해 하위 클래스의 코드를 확인하는 방법 이 있다.

- 일부 **단위 테스트**가 통과하지 못한다면, 하위 클래스의 설계와 구현이 상위 클래스의 계약을 완전히 준수하지 않고 하위 클래스가 리스코프 치환 원칙을 위반함을 의미하게 된다.

<br>

리스코프 치환 원칙을 준수하는 코드 작성을 위해, 코드를 작성하기 전에 리스코프 치환 원리의 `목적`을 생각해보자

- 목적: 다형성을 보장하면서 부모 클래스와 자식 클래스 간의 관계를 안정적으로 유지한다.
