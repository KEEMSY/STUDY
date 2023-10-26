# 코드 품질 문제를 개선하는 방법, CodeQualityEnhancement

우리는 개발을 하면서 `코드 품질 문제`를 항상 고려해야한다. 

- 코드 품질 평가 기준으로 `가독성`, `확장성`, `유연성`, `간결성`, `재사용성`, `테스트 용이성` 등을 이야기 할 수 있다.

<br>

> 코드 품질에 대한 확인 리스트

- 모듈의 구분이 명확하고 코드 구조가 높은 응집도, 낮은 결합도를 충족하는가?
- 코드가 고전적인 설계원칙(SOLID, DRY, YAGNI, LoD 등)을 준수하는가?
- 디자인 패턴이 제대로 적용되었고, 과도하게 설계되지는 않았는가?
- 코드를 확장하기 쉬운가?
- 코드를 재사용할 수 있는가? 혹시 '바퀴를 재발명' 하고 있지 않은가?
- 코드는 테스트하기 쉬우며, 단위 테스트가 정상 상황과 비정상 상황을 포괄적으로 다루고 있는가?
- 코드가 적절한 명명, 적적할 주석, 균일한 코드 스타일과 같은 코딩 규칙을 준수하고 있는가?

<br>

> 코드 구현에 대한 확인 리스트

- 의도했던 비즈니스 요구사항을 코드가 모두 구현했는가?
- 코드의 논리가 올바르고 발생할 가능성이 있는 다양한 예외를 처리하고 있는가?
- 로그 출력이 적절한가?
- 인터페이스가 사용하기 쉽고, 멱등성, 트랜잭션 등을 지원하는가?
- 코드에 스레드 안전성 문제가 있는가?
- 코드의 특성상 최적화의 여지가 있는가?
- 코드에 보안 허점이 있는가? 입출력의 검증은 합리적인가?
<br>

> 예시코드

```java
public class IdGenerator {
	private static fianl Logger logger = LoggerFactory.getLogger(IdGenerator.class);

	public static String generate() {
		String id = "";
		try {
			String hostName = InetAddress.getLocalHost();
			String[] tokens = hostName.split("\\.");
			if (token,length > 0) {
				hostName = tokens[tokens.length - 1];
			}
			char[] randomChars = new char[8];
			int count = 0;
			Random random = new Random();
			while (count < 8) {
				int randomAscii = random,nextInt(122);
				if (randomAscii >= 48 && randomAscii <= 57) {
					randomChars[count] = (char)('0' + (randomAscii - 48));
					count ++
				} eles if ( randomAscii >= 65 && randomAscii <= 90) {
					randomChars[count] = (char)('A' + (randomAscii - 65));
					count ++
				} eles if ( randomAscii >= 97 && randomAscii <= 122) {
					randomChars[count] = (char)('a' + (randomAscii - 97));
					count ++
				}
			}
			id = String.format("%s-%d-%s", hostName,
						System.currentTimeMills(), new String(randomChars));
		} catch (UnknownHostException e) {
			logger.warn("Failed to get the hostName.", e);
		}
		return id;
	}

}
```


- 이 코드는 비교적 단순하며, `IdGenerator` 클래스로만 구성되어 있으므로 별도의 모듈 분리가 필요하지 않으며, `SOLID`, `DRY`, `KISS`, `YAGNI`, `LoD` 와 같은 `설계원칙`에 위배되지 않는다.
- `IdGenerator` 클래스는 인터페이스가 아닌 `구현 클래스로 설계`되었고, 호출자가 인터페이스 대신 구현에 직접 의존해야 하기 때문에, `구현이 아닌 인터페이스 기반의 프로그래밍 설계사상을 위반`한다.
	- 이 설계 방식이 문제가 되는 것은 아니지만 ID 생성 알고리즘이 변경될 경우, IdGenerator 를 직접 수정할 경우 문제가 된다.
	- 만약 프로젝트에서 두 개 이상의 ID 생성 알고리즘이 필요한 경우 이 알고리즘을 공용 인터페이스로 추상화 해야한다.
- `IdGenerator` 클래스의 `generate()` 함수는 `정적 함수`로써, `테스트 용이성`에 영향을 미친다.
- `IdGenerator` 클래스는 하나의 함수만 포함하고 있으며, 코드의 크기가 크지는 않지만, `가독성`은 좋지 않다.
	- 코드에서 임의의 문자열을 생성하는 부분은 주석이 없고 생성 알고리즘을 이해하기 어려울 뿐만 아니라 `매직 넘버`가 많이 사용되고 있다.
- 코드에서는 `hostName`을 가져오는 부분에서 `hostName` 이 `빈값`인 케이스에 대한 처리가 없다.
	- `예외처리` 또한 경고 로그뿐이 되지 않는다.
- `generate()` 메서드는 `공유변수`를 포함하지 않아, `Thread Safe` 이다.
- `randomAscii` 의 범위는 0부터 122 이지만, 실제로 사용하는 값은 0~9, a~z, A~Z 의 세 구간이기 때문에, 문자열 생성 `알고리즘을 최적화` 할 필요가 있다.
- `generate()` 함수의 `while 루프`에서 사용되는 if 문 코드는 비슷하며, 구현이 복잡하여, `단순화` 할 필요가 있다.


<br><hr>

## 가독성 향상을 위한 리팩터링

예시 코드의 `가독성`과 관련한 문제와 이에 대한 개선안을 이야기한다면 다음과 같다.

- `hostName`  변수는 두 가지 다른 용도로 사용되고 있다. 이를 중복하여 사용하지 않도록 개선한다.
- 코드의 복잡도를 높이는 로직을 `헬퍼 메서드`로 추출한다.
	- `hostName 을 얻기위한 코드`를 추출한다.(`getLastFieldOfHostName()`)
	- `임의 문자열 생성 코드`를 추출한다.(`generateRandomAlphanumeric()`)
- 코드에 57, 90, 97, 122 와 같은 `매직 넘버`를 제거한다.
- `generate()` 함수에서의 `if 문의 논리 반복`을 줄이고 `구현을 단순화`한다.
- `IdGenerator` 클래스는 구현체이므로, 이름을 바꾼 뒤, `인터페이스`를 `추상화`한다.

<br>

> 가독성 리팩터링 예시

```java
// 인터페이스 정의
public interface IdGenerator {
	String generate();
}

public class LogTraceIdGenerator implements IdGenerator {
	private static fianl Logger logger = LoggerFactory.getLogger(IdGenerator.class);

	@Override
	public String generate() {
		String substrOfHostName = getgetLastFieldOfHostName();
		long currentTimeMills = System.currentTimeMills();
		String randomString = generateRandomAlphanumeric();
		String id = String.format("%s-%d-$s",
			substrOfHostName, currentTimeMills, randomString);
		return id;
	}
	// 헬퍼 메서드 추가1
	private String getgetLastFieldOfHostName() {
		String substrOfHostName = null;
		try {
			String hostName = InetAddress.getLocalHost().getHostName();
			String[] tokens = hostName.split("\\.");
			substrOfHostName = tokens[tokens.length - 1];
			return substrOfHostName;
		} catch (UnknownHostException e) {
			logger.warn("Failed to get the host name.", e);
		}
		return substrOfHostName;
	}

	// 헬퍼 메서드 추가2
	private String generateRandomAlphanumeric(int length) {
		char[] randomChars = new char[length];
		int count = 0;
		Random random = new Random();
		while (count < 0) {
			int maxAscii = 'z';
			int randomAscii = random.nextInt(maxAscii);
			boolean isDigit = randomAscii >= '0' && randomAscii <= '9';
			boolean isUppercase = randomAscii >= 'A' && randomAscii <= 'Z';
			boolean isLowercase = randomAscii >= 'a' && randomAscii <= 'z';
			if (isDigit || isUppercase || isLowercase) {
				randomChars[count] = (char) (randomAscii);
				++count;
			}
		} 
		return new String(randomChars)
	}
}
```

<br><hr>

## 코드 테스트 용이성 향상을 위한 리팩터링

**최초 Id 생성기 예제 코드**는 `테스트 용이성` 측면에서 두가지 문제점이 있다.

1. `generate()` 함수는 `정적 함수(staic)`로 정의되어있다.
2. `generate()` 함수의 코드 구현은 호스트 이름과 같은 실행 환경, 시간 함수, 임의 문자열 생성 알고리즘에 따라 달라진다.

<br>

하지만 첫번째 문제는 가독성 리팩터링(이하 첫번째 리팩터링)을 통해 해결된 상태이다. 따라서 첫번째 리팩터링을 기준으로 두번째 문제를 해결한다.

- `getgetLastFieldOfHostName()` 함수에서 복잡한 코드로 구현된 문자열 추출 코드를 분리하여, `getLastSubstrSplitedByDot()` 함수로 정의한다. 분리된 `getgetLastFieldOfHostName()` 함수는 간단하므로 단위테스트를 작성하지 않으며, `getLastSubstrSplitedByDot()` 함수를 테스트 한다.
- `private` 함수는 객체를 통해 호출할 수 없기 때문에 테스트 용이성이 떨어진다. 따라서, `generateRandomAlphanumeric()` 와 `getLastSubstrSplittedByDot()` 함수의 접근 권한을 `private` 에서 `protected` 로 변경하고, `@VisibleForeTesting` 어노테이션을 사용한다.

*`@VisibleForeTesting` 은 `Google Guava` 에서 사용되는 `어노테이션` 으로 실질적인 효과를 전혀 갖지 않고, `식별자` 로서의 역할만 담당 한다.*

<br>

> 테스트 용이성 향상을 위한 리팩터링 예시

```java
public class LogTraceIdGenerator implements IdGenerator {
	private static fianl Logger logger = LoggerFactory.getLogger(IdGenerator.class);

	@Override
	public String generate() {
		String substrOfHostName = getgetLastFieldOfHostName();
		long currentTimeMills = System.currentTimeMills();
		String randomString = generateRandomAlphanumeric();
		String id = String.format("%s-%d-$s",
			substrOfHostName, currentTimeMills, randomString);
		return id;
	}

	private String getgetLastFieldOfHostName() {
		String substrOfHostName = null;
		try {
			String hostName = InetAddress.getLocalHost().getHostName();
			substrOfHostName = getLastSubstrSplittedByDot(hostName);
		} catch (UnknownHostException e) {
			logger.warn("Failed to get the host name.", e);
		}
		return substrOfHostName;
	}

	// 헬퍼 메서드 추가, protected 접근자 설정, @VisibleForeTesting 어노테이션 추가
	@VisibleForeTesting
	protected String getLastSubstrSplittedByDot(String hostName) {
		String[] tokens = hostName.split("\\.");
		String substrOfHostName = tokens[tokens.length -1];
		return substrOfHostName
	}

	// protected 접근자 설정, @VisibleForeTesting 어노테이션 추가
	@VisibleForeTesting
	protected String generateRandomAlphanumeric(int length) {
		char[] randomChars = new char[length];
		int count = 0;
		Random random = new Random();
		while (count < 0) {
			int maxAscii = 'z';
			int randomAscii = random.nextInt(maxAscii);
			boolean isDigit = randomAscii >= '0' && randomAscii <= '9';
			boolean isUppercase = randomAscii >= 'A' && randomAscii <= 'Z';
			boolean isLowercase = randomAscii >= 'a' && randomAscii <= 'z';
			if (isDigit || isUppercase || isLowercase) {
				randomChars[count] = (char) (randomAscii);
				++count;
			}
		} 
		return new String(randomChars)
	}
}
```

- 로그를 출력하는 `Logger` 클래스의 경우 `static final` 로 정의되어 클래스 내부에 생성되지만, **데이터를 읽어와 변화시키지 않으며, 비즈니스 논리에 얽혀 있지 않기 때문** 에 코드의 정확성에 영향을 미치지 않으므로 `Mock` 또는 `의존성 주입`이 **필요하지 않다**.

<br><hr>

## 예외 처리를 위한 리팩터링

함수의 실행 결과는 두 가지 범주로 나눌 수 있다.

- `예상된 결과` 로서 정상 조건에서 함수가 출력하는 결과
- `예기치 않은 결과` 로서 정상적이지 않는 조건 또는 오류 상황에서 출력하는 결과

<br>

이에 따라 반환 값 을 고려할 때, 정상적인 상황에서는 함수가 반환하는 데이터 유형이 비교적 명확하나, 예외가 발생한 경우에는 함수가 반환하는 데이터의 형식이 더 유연하다.

- `예외`, `오류코드`, `null`, `-1` 와 같이 `정의된 값` 혹은 `빈 객체` 나 `빈 컬렉션` 을 반환할 수도 있다.

<br>

> 오류를 반환하는 방법: 오류코드 반환

`Java`, `Python` 와 같은 언어는 주로 `예외`를 사용하여 함수의 오류를 처리하기 때문에 오류코드를 거의 사용하지 않는다. 반면 `C 언어` 같은 경우 예외 처리 구문 메커니즘이 없어 `오류코드` 반환을 통해 함수의 오류를 처리한다.

<br>

> 오류를 반환하는 방법: null 반환

`null` 은 `값이 존재하지 않음` 을 표현할 때 사용하기도 하나, null 에 대한 처리를 잊어버린다면, `NullPointerException` 에러가 발생할 수 있다.

- null 을 반환할 가능성이 있는 함수를 많이 정의하게 된다면, 코드의 많은 부분이 `null 에 대한 처리` 로 구성되어 코드가 `복잡` 해 질 수 있다.
- null 을 처리하는 코드가 비즈니스 논리 코드와 결합될 경우, 코드의 `가독성`이 떨어질 수 있다.

<br>

```java
public class UserService {
	private UserRepo userRepo; 
	
	public User getUser(String telephone) {
		// 사용자가 없을 경우 null 반환
		return null;
	}
}

// getUser() 사용
User user = userService.getUser("1891771****");
if (user != null) { // null 판단을 하지 않으면 NPE 예외 발생 가능
	String email = user.getEmail();
	if (email != null) { // null 판단을 하지 않으면 NPE 예외 발생 가능
		String excapedEmail = email.replaceAll("@", "#");
	}
}
```

<br>

> 오류를 반환하는 방법: 비어있는 객체 반환

null 을 반환하는 것은 언급한 단점 때문에 null 을 반환하기 보다는 `빈 문자열` 혹은 `빈 컬렉션` 을 반환하여, `null 판단 로직`을 피하기도한다.

<br>

```java
public class UserService {
	private UserRepo userRepo; 
	
	public User getUsersByPrefix(String telephonePrefix) {
		// 사용자가 없을 경우 빈 컬렉션 반환
		return Collections.emptyList();
	}
}

// getUsers() 사용
List<User> users = userService.getUser("189");
for (User user: users) { // null 판단 불필요
	// .. 로직
}

// null 대신 빈 문자열 사용
public String retrieveUppercasaeLetters(String text) {
	// text에 대문자가 없을 경우 빈 문자열 반환
	return "";
}

// retrieveUppercasaeLetters() 사용
String uppercaseLetters = retrieveUppercasaeLetters("glfvprosha");
int length = uppercaseLetters.lenghth(); // null 판단 불필요
System.out.println("Contains" + length + " upper case letters.");
```