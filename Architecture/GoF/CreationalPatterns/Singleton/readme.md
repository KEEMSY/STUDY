# **Singleton 패턴**

![SigletonExplaination2.png](/img/SigletonExplaination2.png)

<br>

> ### **요약**

|Singleton|내용|
|---|---|
|**목적**|객체 생성을 1개로 제한하여, 해당 객체의 중복된 생성으로 인한 자원의 손실을 방지한다.|
|**설명**|하나의 클래스 타입에 대하여 자원 공유를 위해 오직 1개의 객체만이 생성되도록 보장하는 패턴을 말한다. 그리고 생성된 객체는 공유되어 어디서든 접근할 수 있다.|
|**방법**|생성자를 제한하고, 참조체 및 생성 메서드를 만든다.(정적프로터피와 정적 메서드를 사용한다.)|
|**장점**|중복된 객체생성으로 인한 자원의 손실을 방지할 수 있다.|
|**단점**|책임을 2가지를 갖는 객체이므로 객체 지향원칙에 어긋나며, 안티패턴으로 분류 되기도 한다.|
||

**하나의 클래스** 타입에 대해서 오직 **하나의 객체만**이 생성되도록 보장해주는 패턴이다.

`Singleton` 패턴이 적용된 클래스의 객체는 **다른 클래스들에서 접근**할 수는 있지만 **생성은 할 수 없다.**(`Singleton` 패턴은 **객체 생성을 제한**한다.)

- new 키워드를 통해 객체를 생성할 때, 서로다른 A, B 객체가 생성되는 것이 아니라 동일한 객체 1개만 유지함을 의미한다.

<br>

> **Singleton 패턴이 유용한 상황**

- 공유 자원 접근
- 복수의 시스템이 하나의 자원에 접근할 때
- 유일한 객체가 필요할 때
- 값의 캐시가 필요할 때

`Singleton` 은 객체의 관계 속에서 상호 작용하기 위한 값을 저장하고 전달하는데, **값을 전달하기 위해**서는 **공용 객체가 필요**하다.
`Singleton` 패턴은 **new 키워드를 이용해 객체를 생성하는 방법을 원천적으로 금지**한다. 대신 **객체를 생성할 수 있는 메서드를 추가**하며, new 키워드 대신 **생성 메서드 호출만으로 객체를 생성** 할 수 있다.

**내부의 참조체를 통해 자신의 객체를 보관**한다.(내부적으로 **중복 생성을 방지하는 로직(`Flyweight` 패턴)이 존재**한다.
참조체를 통해 **자신의 객체가 생성되었는지 판단**한다.

`Singleton` 패턴을 적용하면 참조제를 통해 하나의 객체만 갖도록 보증하지만, **상속과 복수 객체를 생성할 수 있는 객체지향의 장점을 잃게 된다.**(*이는 접근 권한을 수정하여 어느 수준 보완 가능하다.*)

<br>

> **Singleton 패턴을 이해하기 위해서는 객체의 접근 권한 속성을 이해해야한다.**

- **new 키워드로 객체를 생성하지 못하도록** 해야한다.(생성자의 접근 권한을 변경(`public->private`)한다.)
- 객체를 생성하는 또 다른 방법인 **`clone` 또한 `private` 설정**을 통해 접근을 제한한다.
- **확장**을 위해 접근 권한을 `protected` 로 변경하면 상속을 통한 확장이 가능하다.
  - 객체 생성은 **상속받은 클래스의 내부 정적 메서드를 호출**해야한다.

<br>

>**Singleton이 안티패턴으로 분류되는 이유**

특수한 환경에서 **단일 객체 생성을 보장하지 못하는 경우**(멀티스레드 조건)에서 **객체 생성이 동시 요청되는 경우 경합성이 발생**한다.

- `경합조건`: 동일한 메모리나 자원을 동시에 접근하는 것을 말한다.(2개 이상의 스레드가 동일한 자원을 사용할 경우 충돌이 발생한다.)
- `경합성`: 메서드의메서드의 `원자성(atomic)`의 결여로 2개의 객체가 만들어지는 오류가 발생한다.

이 문제(경합성문제 및 멀티 스레드 환경)을 해결하기 위해 **늦은 바인딩**을 사용한다.

- `Sigleton` 은 **정적 호출을 통해 생성 호출하기 전에는 객체를 만들지 않는다.**
- 최초의 생성 호출이 발생할 때 객체를 생성하고 생성된 객체는 내부 참조체에 저장된다.(객체를 생성하여 메모리(자원)에 할당한다.)
- `늦은 초기화(lazy initialization)`: 늦은 바인딩을 통해 객체 생성을 동적으로 처리하는 것을 말한다.
- 경합성과 늦은 초기화 문제를 좀 더 보완하기 위해 **시스템 부팅 시 필요한 Sigleton 의 객체를 미리 생성**한다.(Sigleton 으로 처리 할 부분을 부팅시 미리 처리하면 참조체를 구별하는 if 문의 오작동을 방지할 수 있다.)
- 불필요한 객체를 모두 생성하여 메모리에 상주시키는 것은 **메모리 낭비의 문제**와 매번 공유 자원의 객체에 접근할 때마다 **자원 중복 여부를 체크**하는 여전한 문제점이 존재한다.
- `Sigleton` 으로 생성한 자원은 프로그램이 종료 될 때까지 메모리에 상주한다.

<br><hr>

## 싱글턴 패턴 구현

싱글턴을 구현하는 방법은 다양하며, 크게 5가지 방법을 이야기 할 수 있다.

- 즉시 초기화(Eager Initialization)
- 늦은 초기화(Lazy Initialization)
- 이중 잠금(Double-Checked Locking)
- 홀더에 의한 초기화
- 열거형 사용

> 싱글턴을 개발하기 전, 고려하면 좋을 사항

1. 생성자는 new 예약어를 통한 인스턴스 생성을 피하기 위해 private 접근 권한을 갖게 한다.
2. 객체가 생성될 때 스레드 안정성을 보장하는지 확인한다.
3. 지연 로딩을 지원하는지 확인한다.
4. getInstance() 함수의 성능은 충분한지 확인한다.

<br>

> 즉시 초기화, Eager Initialization

```java
public class IdGenerator {
	private AtomicLong id = new AtomicLong(0);
	private static final IdGenerator instance = new IdGenerator();
	
	private IdGenerator() {}
	public static IdGenerator getInstance() {
		return instance;
	}

	public long getId() {
		return id.incrementAndGet();
	}
}
```

싱글턴 인스턴스는 클래스가 메모리에 적재될 때 이미 생성되어 초기화가 완료되어 인스턴스 생성 프로세스는 스레드 안전한 상태를 보장한다.

- 실행 시간 오류를 피하고, 초기화 과정으로 인한 성능 문제를 피할 수 있다.
- Lazy Loading 을 지원하지 않으며, 인스턴스는 사용되는 시점이 아니라 미리 생성된다.

*Fail-Fast 설계 원칙에 따르는 방법이다.*

<br>

> 늦은 초기화(Lazy Initialization)

```java
public class IdGenerator {
	private AtomicLong id = new AtomicLong(0);
	private static IdGenerator instance;
	
	private IdGenerator() {}
	public static synchronized IdGenerator getInstance() {
		if (instance == null) {
			instance = new IdGenerator();
		}
		return instance;
	}

	public long getId() {
		return id.incrementAndGet();
	}
}
```

늦은 초기화는 즉시 초기화 방식과는 달리, 지연 적재를 지원하여, 인스턴스의 생성과 초기화가 실제로 사용되기 전까지 일어나지 않는다.

- 시간이 오래걸리는 초기화 작업이 인스턴스가 사용되기 직전에 이루어진다면 시스템 성능에 영향을 줄 수 있다.
- getInstance() 함수에 추가적인 잠금 synchronized 로 인해 함수의 동시성이 1로 바뀌어 낮은 동시성으로 인한 병목 현상이 발생 할 수 있다.

<br>

> 이중 잠금(Double-Checked Locking)

```java
public class IdGenerator {
	private AtomicLong id = new AtomicLong(0);
	private static volatile IdGenerator instance;
	
	private IdGenerator() {}
	
	public static synchronized IdGenerator getInstance() {
		if (instance == null) {
			synchronized(IdGenerator.class) { // 클래스 레벨의 잠금 처리
				if (instance == null) {
					instance = new IdGenerator();
				}
			}
		}
		return instance;
	}

	public long getId() {
		return id.incrementAndGet();
	}
}
```

이중 잠금 방식은 지연 적재와 높은 동시성을 모두 지원하는 싱글턴 구현 방식이다.

- getInstance()  함수가 호출 될 때 잠금이 발생하지 않는다.
- instance 멤버 변수의 경우, 명령어 재정렬 문제가 발생할 수 있어, volatile 키워드를 사용하여, 해당 문제를 해결한다.

<br>

> 홀더에 의한 초기화(Initialization on demand holder idiom)

```java
public class IdGenerator {
	private AtomicLong id = new AtomicLong(0);
	
	private IdGenerator() {}

	private static class SingletonHoler {
		private static final IdGenerator instance = new IdGenerator();
	}
	
	public static IdGenerator getInstance() {
		return SingletonHolder.instance;
	}

	public long getId() {
		return id.incrementAndGet();
	}
}
```

- 정적 내부 클래스로 SingletonHolder 를 사용함으로써, 인스턴스의 유일성과 생성 프로세스의 스레드 안정성이 JVM에 의해 보장된다.
- 홀더에 의한 초기화 방식은 스레드 안정성을 보장할 뿐만아니라, 지연 적재도 가능한 방법이다.

<br>

> 열거(Enum)

```java
public enum IdGenerator {
	INSTANCE;
	private AtomicLong id = new AtomiocLong(0);

	public long getId() {
		return id.incrementAndGet();
	}
}
```

Java 의 **열거 형이 가지는 특성**을 이용하여 인스턴스 생성 시, 스레드 안정성과 인스턴스의 유일성을 보장한다.
- **스레드 안전성(Thread Safety):** Enum은 JVM에 의해 클래스 로딩 시점에 초기화되며, 그 이후로는 다시 초기화되지 않는다. 따라서 여러 스레드에서 동시에 접근하더라도 안전하게 초기화될 수 있다..
- **인스턴스의 유일성(Instance Uniqueness):** Enum은 상수를 나타내는데, 각 상수는 해당 enum 타입의 유일한 인스턴스이다. 따라서 enum을 사용하면 인스턴스의 유일성이 보장된다.


<br><br><hr>

## 싱글턴 패턴의 단점

싱글턴 패턴은 일반적으로 사용되긴하지만, 다양한 단점이 존재하여 문제가 발생하는 경우가 많으며, 이에 따라 안티 패턴으로 인식되기도 한다. 따라서 가급적이면 사용하지 않는 것이 좋다.

- 클래스 간의 의존성을 감춘다.
- 코드의 확장성에 영향을 준다.
- 테스트 용이성에 영향을 미친다.
- 매개변수가 있는 생성자를 지원하지 않는다.

<br>

> 클래스 간의 의존성을 감춘다.

코드 가독성 측면에서 클래스 간의 의존성이 확실히 들어나는 것은 매우 중요하다. 그리고 클래스 간의 의존성은 다음과 같은 방법을 통해 식별할 수있다.

- 생성자를 통해 선언된 클래스 간의 의존성을 확인한다.
- 함수의 매개변수 전달을 통해 의존성을 확인할 수 있다.

<Br>

그러나 싱글턴 클래스는 명시적으로 생성할 필요가 없고 매개변수 전달에 의존할 필요도 없으며 함수에서 직접 호출할 수 있음에도 의존성이 전혀 드러나지 않는다.

- 각 함수의 코드 구현 자체를 깊게 확인해야만 해당 클래스가 어떤 싱글턴 클래스에 의존하는지 알 수 있다.

<br>

> 코드의 확장성에 영향을 미친다.

싱글턴 클래스는 하나의 인스턴스만 생성할 수 있다. 하지만 나중에 두개 이상의 인스턴스가 필요하게 될 경우, 코드를 전면적으로 수정해야 한다.

- 데이터베이스 연결 풀이 싱글턴 클래스로 설계되어 있다면, 문제가 될 수 있다.


<br>

> 코드의 테스트 용이성에 영향을 미친다.

싱글턴 클래스는 전역적인 상태를 갖는다. 이는 독립적인 환경에서 이뤄져야하는 테스트와는 달리, 테스트간 싱글턴 클래스가 공유되어 예측할 수 없는 동작이 발생할 수 있다.

<br>

> 매개변수가 있는 생성자를 지원하지 않는다.

싱글턴 패턴은 매개변수가 있는 생성자를 지원하지 않아 연결 풀이 있는 싱글턴 객체를 생성할 때, 매개변수를 통해 연결 풀의 크기를 지정할 수 없다. 하지만 다음과 같은 방법을 통해 해당 문제를 해결할 수 있다.

1. **init() 메서드를 통해매개변수를 전달한다.**

```java
public class Singleton {
	private static Singleton instance = null;
	private final int paramA;
	private final int paramB;

	private Singleton(int paramA, int paramB) {
		this.paramA = paramA;
		this.paramB = paramB;
	}

	public static Singleton getInstance() {
		if (instance == null) {
			throw new RuntimeException("Run init() first.");
		}
		return instance;
	}

	public synchronized static Singleton init(int paramA, int paramB) {
		if (instance != null) {
			throw new RuntimeException("Singleton has been created!");
		}
		instance = new Singleton(paramA, paramB);
		return instance;
	}
}

// init() 함수를 호출하고 getInstance() 함수를 통해 객체를 가져옴
Singleton.init(10, 50);
Singleton singleton = Singleton.getInstance();
```

<br>

2. **매개변수를 getInstance() 함수에 넣는다.**

```java
public class Singleton {
	private static Singleton instance = null;
	private final int paramA;
	private final int paramB;

	private Singleton(int paramA, int paramB) {
		this.paramA = paramA;
		this.paramB = paramB;
	}

	public synchronized static Singleton getInstance(int paramA, int paramB) {
		if (instance == null) {
			instance = new Singleton(paramA, paramB);
		}
		return instance;
	}
}

Singleton singleton = Singleton.getInstance(10, 50);

// 문제의 경우
Singleton singleton1 = Singleton.getInstance(10, 50);
Singleton singleton2 = Singleton.getInstance(10, 50);
```

getIntance() 함수가 두번 호출 될 경우, 두번째의 매개변수는 효과가 없을 뿐만 아니라 빋드 프로세스도 이를 알려주지 않아 잘못된 결과를 가져온다.

<br>

3. **매개변수를 전역변수에 넣는다.**

```java
public class Config {
	public static final int PARAM_A = 123;
	public static final int PARAM_B = 456;
}

public class Singleton {
	private static Singleton instance = null;
	private final int paramA;
	private final int paramB;

	private Singleton() {
		this.paramA = Config.PARAM_A;
		this.paramB = Config.PARAM_B;
	}

	public synchronized static Singleton getInstance() {
		if (instance == null) {
			instance = new Singleton();
		}
		return instance;
	}
}
```

이 방법은 가장 명확하고 오류가 발생할 여지가 적은 방법이다.


<br><hr><br>

## **예제 코드**

<br>

### **코드 구조**

![singleton.png](/img/singleton.png)

<br>

- ### **설명**

    `singleton` 표시를 통해 해당 객체(`King`)이 Singletone 패턴이 적용됬음을 인지한다.
