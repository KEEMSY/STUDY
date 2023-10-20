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
