# 개방 폐쇄 원칙, Open-Closed Principle(OCP)


개방 폐쇄 원칙은 확장할 때는 개방, 수정할 때는 폐쇄 원칙으로 불린다. 이는 SOLID 원칙 중에서도 가장 이해하기 어렵고, 마스터하기 어려우면서도 가장 유용한 원칙이다.

개방 폐쇄 원칙을 이해하기 어려운 이유는 코드를 변경할 때 그 결과를 확장으로 보아야하는지, 수정으로 보아야하는지 명확하게 구분하기 어렵기 때문이다.

개방 폐쇄 원칙이 숙달하기 어려운 이유는 '확장할 때는 개방, 수정할 때는 폐쇄' 라는 개념을 어떻게 달성할 것인지, 높은 확장성을 추가하면서 코드의 가독성에 영향을 미치지 않도록 이 원칙을 프로젝트에 어떻게 유연하게 적용할 것인지와 같은 문제는 이해하기 어려운 문제에 속하기 때문이다.

하지만 개방 폐쇄 원칙은 확장성이 코드 품질의 중요한 척도 이기 때문에 가장 유용하다. 

- 디자인 패턴 22개 중 대부분은 코드 확장성 문제를 해결하기 위해 고안되었으며, 이 패턴들의 중요한 설계 원칙이 OCP 이다.

<br><hr>

## 확장할 때는 개방, 수정할 때는 폐쇄

OCP 를 정확하게 이야기한다면, 모듈, 클래스, 함수와 같은 소프트웨어의 단위들은 확장을 위해 개방되어야 하지만 수정을 위해서는 폐쇄되어야 한다.

- 새로운 기능을 추가할 때, 기존의 모듈, 클래스, 함수를 수정하기 보다는 기존의 코드를 기반으로 모듈, 클래스, 함수 등을 추가하는 방식으로 코드를 확장해야 한다.

<br>

> Alert 클래스 예시

```java
public class Alert
	private AlertRule rule;
	private Notification notification;

	public Alert(AlertRul rule, Notification notification) {
		this.rule = rule;
		this.notification = notification;
	}

	public void check(String api, long requestCount, long errorCount, long duration) {
		long tps = requestCount / duration;
		if (tps > rule.getMatchedRule(api).getMaxTps()) {
			notification.notify(NotificationEmergencyLevel.URGENCY, "...");
		}

		if (errorCount > rul.getMatchedRule(api).getMaxErrorCount()) {
			notification.notify(NotificationEmergencyLevel.SEVER, "...");
		}
	}
```

- 비즈니스 논리는 주로 check() 에 집중되어 있다.
- 인터페이스의 초당 트랜잭션 수가 미리 설정한 최댓값을 초과하거나 인터페이스 요청 오류 수가 최대 허용치를 초과하는 경우 경고가 발생하며, 이를 해당 인터페이스 담당자 또는 팀에 알리게 된다.

<br>


> '초당 인터페이스 요청 횟수가 미리 설정된 최댓값을 초과할 경우, 경고 알람이 설정되며 통지가 발송된다.' 라는 새로운 경고 알림 규칙 추가

<br>

### 코드 자체를 수정하여 문제를 해결하기

코드 자체를 수정하는 방법으로 문제를 해결하고 하는 경우, 수정해야 할 부분을 크게 두 가지 부분으로 볼 수 있다.

- check() 함수의 입력 매개변수를 수정하여, 인터페이스 요청 타임아웃 수치를 나타내는 새로운 timeoutCount 를 추가한다.
- check() 함수에 새로운 경고 알림 논리를 추가한다.

<br>

```java
public class Alert
	// ...AlertRule/Notification 클래스의 속성과 구조, 기능 구현 생략..

	// 변경 1. 매개변수 timeoutCount 추가
	public void check(String api, long requestCount, long errorCount, long timeoutCount, long duration) {
		long tps = requestCount / duration;
		if (tps > rule.getMatchedRule(api).getMaxTps()) {
			notification.notify(NotificationEmergencyLevel.URGENCY, "...");
		}

		if (errorCount > rul.getMatchedRule(api).getMaxErrorCount()) {
			notification.notify(NotificationEmergencyLevel.SEVER, "...");
		}

		// 변경 2. 인터페이스 요청 타임아웃 처리 추가
		long timeoutTps = timeoutCount / duration;
		if (timeoutTps > rule.getMatchedRule(api).getMaxTimeoutTps()) {
			notification.notify(NotificationEmergencyLevel.URGENCY, "...")
		}	
	}
```

이렇게 수정할 경우, 두가지 문제가 뒤따른다.

- 인터페이스 자체를 수정하면 인터페이스를 호출하는 코드도 모두 그에 따라 수정된다.
- check() 함수가 수정되면 해당 함수에 대한 단위테스트 역시 수정된다.(단위테스트 또한 유지보수 해야한다.)

<br>

### 개방 폐쇄 원칙을 준수하여 문제를 해결하기

새로운 경고 알림을 추가하기 전, `Alert` 클래스의 코드를 리팩토링하여 확장성을 높여야 한다.

- `check()` 함수의 여러 입력 매개변수를 `ApiStatInfo` 클래스로 캡슐화한다.
- 핸들러를 도입하여 if 판단 논리를 각 핸들러로 분배한다.

<br>

```java
public class Alert {
	private List<AlertHandler> alertHandlers = new ArrayList<>();

	public void addAlertHandler(AlertHandler alertHandler) {
		thist.alertHandler.add(alertHandler);
	}

	public void check(APicStatInfo apiStatInfo) {
		for (AlertHandler handler : alertHandlers) {
			handler.check(apiStatInfo);
		}
	}
}
```

```java
public class ApiStatInfo { // 생성자, getter, setter 메서드 생략
	private String api;
	private long requestCount;
	private long errorCount;
	private long duration;
}
```

```java
public abstract class AlertHandler {
	protected AlertRule rule;
	protected Notification notification;
	
	public AlertHandler(AlertRule rule, Notification notification) {
		this.rule = rule;
		this.notification = notification;	
	}

	public abstract void check(ApiStatInfo apiStatInfo);
}

public class TpsAlertHandler extends AlertHandler {
	public TpsAlertHandler(AlertRule rule, Notification notification) {
		super(rule, notification)
	}

	@Override
	public void check(ApiStatInfo apiStatInfo) {
		long tps = apiStatInfo.getRequestCount() / apiStatInfo.getDuration();
		if (tps > rul.getMatchedRule(apiStatInfo.getApi()).getMaxTps()) {
			notification.notify(NotificationEmergencyLevel.URGENCY, "...");
		}
	}
}

public class ErrorAlertHandler extends AlertHandler {
	public ErrorAlertHandler(AlertRule rule, Notification notification) {
		super(rule, notification);
	}

	@Override
	public void check(ApiStatInfo apiStatInfo) {
		if (apiStatInfo.getErrorCount() > rul.getMatchedRule(apiStatInfo.getApi()).getMaxErrorCount()) {
		notification.notify(NotificationEmergencyLevel.SEVER, "...");
		}
	}
}

```

- `ApiStatInfo` 클래스에 새로운 `timeoutCount` 속성을 추가한다.
- 새로운 핸들러인 `TimeoutAlertHandler` 클래스를 추가한다.
- `ApplicationContext` 클래스의 `initializeBeans()` 메서드에 `alert` 객체를 대상으로 `TimeoutAlertHandler` 를 등록한다.
- `Alert` 클래스를 사용할 때 `check()` 함수의 입력 매개변수 `apiStatInfo` 객체에 대한 `timeoutCount` 속성 값을 설정한다.

<br>

### Alert 클래스의 구체적인 사용 예시

```java
public class ApiStatInfo { // 생성자, getter, setter 메서드 생략
	private String api;
	private long requestCount;
	private long errorCount;
	private long duration;
	private long timeoutCount // 변경 1. timeoutCount 속성 추가
}
```

```java
// 변경 2. TimeoutAlertHandler 추가
public class TimeoutAlertHandler extends AlertHandler { /*코드 생략*/ }
```

```java
public class ApplicationContext {
	private AlertRule alertRule;
	private Notification notification;
	private Alert alert;

	public void initializeBeans() {
		alertRule = new AlertRule(/* 매개변수 생략 */); // 초기화 코드 생략
		notification = new Notification(/* 매개변수 생략 */); // 초기화 코드 생략
		alert = new Alert();
		alert.addAlertHandler(new TpsAlertHandler(alertRule, notification));
		alert.addAlertHandler(new ErrorAlertHandler(alertRule, notification));
		// 변경 3. alert 객체에 TimeoutAlertHandler 등록
		alert.addAlertHandler(new TimeoutAlertHandler(alertRule, notification)); 
	}

	public Alert getAlert() { return alert; }

	// 빈약한 도메인 기반의 싱글턴
	private static final ApplicationContext instance = new ApplicationContext();
	private ApplicationContext() {
		initializeBeans();
	}

	public static ApplicationContext getInstance() {
		return instance;
	}
}
```

```java
public class Demo {
	public static void main(String[] args) {
		ApiStatInfo apiStatInfo = new Api StatInfo();
		// ... apiStatInfo 데이터 값 설정 코드 생략
		// 변경 4. timeoutCount 값 설정
		apiStatInfo.setTimeoutCount(289);
		ApplicationContext.getInstance().getAlert().check(apiStatInfo);
	}
}
```

이와 같이 리팩토링 및 사용된다면 코드는 더 유연하고 확장하기 쉽다.

- 새 경고 알림을 추가하기 위해 기존의 `check()` 메서드를 변경할 필요없이, 확장 메서드에 기반한 새로운 핸들러 클래스를 생성하기만 하면된다.
- 기존의 클래스에 대한 단위테스트를 매번 수정할 필요 없이, 새로 추가된 핸들러 클래스에 대한 테스트를 추가하면 된다.

<br><hr>

## 코드를 수정하는 것은 개방 폐쇄원칙을 위반한다?

수정인것인가, 확장인 것인가를 구분하는 것은 어려운 일이다.

- 클래스 입장에서는 수정이 될 수 있지만, 속성이나 메서드 입장에서는 확장으로 받아들여질 수 있다.

<br>
그러나 코드를 수정하는 작업이 `OCP` 를 위반하는지 고민이 될 때에는, 본래의 `OCP` 의 목적을 상기해보는 것이 좋다.

- `OCP` 의 기본 목적은 기존의 코드의 수정하지 않고, 새로운 기능울 추가하는 것이다. 즉, 코드의 수정이 기존에 작성되었던 코드와 단위테스트를 깨트리지 않는 한, 이는 개방 폐쇄원칙을 위반하지 않았다고 이야기 할 수 있다.

<br>
우리는 새로운 기능을 추가할 때 소프트웨어 단위에 해당하는 모듈, 클래스, 메서드의 코드를 전혀 수정하지 않는 것은 불가능하다는 것을 인지해야한다.

- 수정을 아예 안하는 것이 아니라, 코드의 핵심 부분이나, 복잡한 부분, 공통 코드나 기반 코드가 OCP를 충족하는 방향으로 노력해야한다.

<br><hr>

## 확장할 때는 개방, 수정할 때는 폐쇄를 달성하는 방법

개방 폐쇄 원칙은 코드가 `확장`하기 쉬운지를 판단하는 표준이 된다.

- 추후 변경되는 요구 사항에 대응할 때 코드가 확장할때는 개방, 수정할때는 폐쇄 될 수 있다면, 해당 코드의 확장성은 매우 뛰어나다고 할 수 있다.

<br>

`확장 가능한 코드` 를 작성하기 위해서는 `확장`, `추상화`, `캡슐화` 에 대해 인식하고 있는 것이 매우 중요하며, 이는 개발 기술 자체보다 훨씬 더 중요할 수 있다.

- 코드를 작성할 때 현재 코드에 앞으로 요구 사항이 추가될 가능성이 있는지 판단하는데 더 많은 시간을 할애해야한다.
- 코드의 변경가능한 부분과 변경 할 수 없는 부분을 잘 식별해야한다.
- 변수 부분을 `캡슐화` 한다.
- 상위 시스템에서 `추상 인터페이스` 를 기반으로 새로운 구현을 확장하여 기존의 구현을 대체할 수 있어야 하며, 상위 시스템의 코드를 수정할 필요가 없어야 한다.

<br><hr>

## 프로젝트에 개방 폐쇄 원칙을 유연하게 적용하는 방법

OCP 기반의 높은 확장성을 지원하는 코드를 작성하는 방법의 핵심은 확장 포인트를 미리 준비해두는 것이다.

- 확장포인트를 준비하기 위해서는 비즈니스에 대한 충분한 이해가 필요하다.
- 해당 모듈들이 어떻게 사용될지, 어떤 요구 사항이 있을지 이해하는 것이 필수적이다.

<br>

하지만 비즈니스와 시스템에 대해 충분히 알고 있더라도 모든 확장 포인트를 미리 준비하는 것은 불가능하다. 

- 모든 확장 포인트를 알고 있더라도, 추후 요구될 가능성이 거의 없는 사항들까지 미리 준비하는 것은 '`과도한 설계`' 라고 할 수 있다.

<br>

일반적으로는 단기간 내에 진행할 수 있는 확장, 코드 구조 변경에 미치는 영향이 비교적 큰 확장, 구현 비용이 많이 들지 않는 확장에 대해 확장 포인트를 준비하도록 하는 것이 좋다.

- 향후 지원해야하는지 불확실한 요구사항이면서 오히려 코드 개발에 부하를 주는 경우, 해당 작업이 실제로 필요할 때 리팩토링 하는 것이 좋다.
- OCP 는 공짜가 아니다. 코드의 `확장성` 은 종종 코드의 `가독성` 을 떨어뜨린다는 것을 명심해야한다.
- 일반적인 개발상황에서는 코드의 `확장성` 과 `가독성` 사이에서 적절한 균형이 필요하다.(하지만 (구조의 복잡도 증가를 고려한) `가독성` < `확장성` 임을 고려하자.)
