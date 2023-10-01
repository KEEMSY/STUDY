# 인터페이스 분리 원칙, Interface Segregation Principle(ISP)

로버트 마틴은 `인터페이스 분리 원칙`을 다움과 같이 정의하였다.

- 클라이언트는 필요하지 않은 인터페이스를 사용하도록 강요되어서는 안된다.
- `클라이언트`: 인터페이스 호출자 혹은 사용자

<br>

인터페이스 라는 용어는 소프트웨어 개발의 여러가지 상황에서 사용될 수 있다. 

- API 혹은 기능의 집합
- 단일 API 또는 기능
- 객체지향 프로그래밍에서의 인터페이스

<br><hr>

## API 혹은 기능의 집합으로서의 인터페이스

```java
public interface UserService {
	boolean register(String cellphone, String password);
	boolean login(String cellphone, String password);
	UserInfo getUserInfoById(long id);
	UserInfo getUserInfoByCellphone(String cellphone);
}

public class userServiceImpl implements UserService {
	// 구현 코드 생략...
}
```

- 서비스 사용자 시스템은 등록, 로그인, 사용자 ㅈ어보 획득과 같은 사용자 관련 API 집합을 제공한다.

<br>

현재 관리 시스템에서 사용자 삭제 기능이 추가되어야한다 할 때, 다음과 같은 사항들을 고려해야한다.

- 사용자 삭제는 신중하게 해야하는 작업이므로, 관련 인터페이스의 사용 범위는 관리 시스템으로 제한되어야 한다.
	- `UserService` 을 사용하는 모든 시스템에서 사용자 삭제 인터페이스를 호출 할 수 없어야 한다.
	- 아키텍처 설계 수준에서 인터페이스 인증을 통해 호출을 제한하는 것을 구현할 수 있다.
	- 코드 설계 수준에서 **인터페이스를 분리** 하여 구현할 수 있다.

<br>

```java
public interface UserService {
	boolean register(String cellphone, String password);
	boolean login(String cellphone, String password);
	UserInfo getUserInfoById(long id);
	UserInfo getUserInfoByCellphone(String cellphone);
}

public interface RestrictedUserService {
	boolean deleteUserByCellphone(String cellphone);
	boolean deleteUserById(long id);
}

public class userServiceImpl implements UserService, RestrictedUserService {
	// 구현 코드 생략...
}
```

- `RestrictedUserService` 를 통해 필요로 하는 부분에서만 해당 기능을 제공할 수 있다.
- 인터페이스 또는 기능의 일부가 호출자 중 일부에게만 사용되거나 전혀 사용되지 않는 다면 불필요한 항목을 강요하는 대신, 인터페이스나 해당 부분을 분리하여 해당 호출자에게 별도로 제공한다.
- 사용하지 않는 인터페이스나 기능에 대하여 접근할 수 없도록 해야한다.

<br><hr>

## 단일 API 나 기능으로서의 인터페이스

```java
public class Statics {
	private Long max;
	private Long min;
	private Long average;
	private Long sum;
	private Long percentile99;
	private Long percentile999;
	// getter, setter 메서드 생략
}

public Statics count(Collection<Long> dataSet) {
	Statistics statistics = new Statistics();
	// 계산 코드 생략
	return statistics;
}
```

-  API 나 기능은 가능한 단순해야 하며 하나의 기능에 여러 다른 기능 논리를 구현하지 않아야 한다.
	- `count()` 메서드는 최댓값, 최소값, 평균값과 같은 여러 다른 통계 함수를 포함하기 때문에 단일하지 않다.
	- 인터페이스 분리 원칙에 따라 `count()` 여러 개의 작은 단위 메서드로 분할해야 하며, 각각의 메서드는 다른 함수를 포함하지 않는 통계 기능을 제공해야 한다.

<br>

>다른 함수를 포함하지 않는 독립적인 통계 기능을 제공하는 각각의 단위 메서드로 리팩토링

```java
public Long max(Collection<Long> dataSet) { /*..*/ };
public Long min(Collection<Long> dataSet) { /*..*/ };
public Long average(Collection<Long> dataSet) { /*..*/ };
// 일부 통계 메서드 생략
```

- 전체 정보가 아닌 `min`, `max`, `average` 의 세가지 통계만 주로 사용하는 경우, 불필요한 작업이 실행되지 않는다.

<br>

인터페이스는 단일 책임이 있는지 여부를 확인할 수 있는 방법을 제공한다.

- 호출자가 인터페이스의 일부 또는 그 기능의 일부만 사용하는 경우 해당 인터페이스 설계는 단일 책임 원칙을 충족하지 않는다.
- 인터페이스 분리 원칙은 단일 책임 원칙과 다소 유사하다. 


<br><hr>

## 객체지향 프로그래밍에서의 인터페이스

```java
public class RedisConfig {
	private ConfigSource configSource; // 설정 센터(Zookeeper 등)
	private String address;
	private int timeout;
	private int maxTotal;
	// maxWaitMills, maxIdle, minIdle 등 일부 설정 생략

	public RedisConfig(ConfigSource configSource) {
		this.configSource = configSource;
	}
	
	// get(), init() 메서드 등 생략

	public void update() {
		// configSource 에서 address, timeout, maxTotal 을 읽어온다.
	}
}

public class KafkaConfig { ... }
public class MysqlConfig { ... }
```

- 각 시스템은 IP 주소, 포트, 엑세스 제한 시간과 같은 설정 정보에 대응한다.
- 프로젝트의 다른 모듈에서도 이 정보를 사용할 수 있도록 `RedisConfig`, `MysqlConfig`, `KafkaConfig` 클래스를 구현하여 설정 정보를 메모리에 올렸다고 가정한다.

<br>
### 인터페이스 분리 원칙 준수

> **Redis, Kafka 의 설정 정보를 핫 업데이트 지원 요구사항 발생**
- `핫업데이트`: 설정 센터에서 설정 정보가 변경되면 시스템을 다시 시작하지 않고도 최선 설정 정보를 메모리에 다시 올릴 수 있는 설정

<br>

```java
public interface Update {
	void update()
}

public class RedisConfig implements Update {
	// 일부 속성과 메서드 생략
	@Override
	public void update() { ... }
}

public class KafkaConfig implements Update {
	// 일부 속성과 메서드 생략
	@Override
	public void update() { ... }
}

public class MysqlConfig { ... } // 기존과 동일
```

```java
public class ScheduleUpdater {
	private final ScheduleExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	private long initialDelayInSeconds;
	private long periodInSeconds;
	private Updater updater;

	public ScheduleUpdater(Updater updater, long initialDelayInSeconds, long periodInSeconds) {
		this.updater = updater;
		this.initialDelayInSeconds = initialDelayInSeconds;
		this.periodInSeconds = periodInSeconds;
	}

	public void run() {
		executor.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				updater.update();
			}
		}, this.initialDelayInSeconds, this.periodInSeconds, TimeUnit.SECONDS);
	}
}
```

- `RedisConfig`, `KafkaConfig` 클래스의 `update()`메서드에서 실행 간격 정보인 `periodSeconds` 속성을 호출하여 일정 시간마다 반복하여 설정 정보를 업데이트 한다.

<br>

> **모니터링에 대한 요구사항 추가: MySQL 과 Redis 설정 정보를 편리하게 볼 수 있게 한다.**

```java
public interface Vivewr {
	String outputInPlainText();
	Map<String, String> output();
}

public class RedisConfig implements Updater, Viewer {
	// 일부 속성과 메서드 생략
	@Override
	public void update() { ... }

	@Override
	public String outputInPlainText() { ... }

	@Override
	public Map<String, String> output() { ... }
}

public class KafkaConfig implements Update {
	// 일부 속성과 메서드 생략
	@Override
	public void update() { ... }
}

public class MysqlConfig implements Viewer {
	// 일부 속성과 메서드 생략
	@Override
	public String outputInPlainText() { ... }

	@Override
	public Map<String, String> output() { ... }
}
```

```java
public class SimpleHttpServer {
	private String host;
	private int port;
	private Map<String, List<Viewr>> viewers = new HashMap<>();

	public SimpleHttpServer(String host, int port) { ... }

	public void addViewers(String urlDirectory, Viewer viewer) {
		if (!viewers.containsKey(urlDirectory)) {
			viewers.put(urlDirectory, new ArrayList<Viewer>());
		}
		this.viewers.get(urlDirectory).add(viewers);
	}

	pbulic void run() { ... }
}

```
- `http://127.0.0.1:2389/config` 처럼 HTTP 처럼 설정 정보를 출력할 수 있다.


```java
public class Application {
	ConfigSource configSource = new ZookeeperConfigSource();
	public static final RedisConfig redisConfig = new RedisConfig(configSource);
	public static final KafkaConfig kafkaConfig = new KafkaConfig(configSource);
	public static final MysqlConfig mysqlConfig = new MysqlConfig(configSource);

	public static void main(String[] args) {
		ScheduleUpdater redisConfigUpdater 
			 = new ScheduledUpdater(redisConfig, 300, 300);
		redisConfigUpdater.run();

		ScheduleUpdater kafkaConfigUpdater
			= new ScheduleUpdater(kafkaConfig, 60, 60);
		kafkaConfigUpdater.run();

		SimpleHttpServer simpleHttpServer = new SimpleHttpServer("127.0.0.1", 2389);
		simpleHttpServer.addViewer("/config", redisConfig);
		simpleHttpServer.addViewer("/config", mysqlConfig);
		simpleHttpServer.run();
	}

}

```

- 각각 클래스들은 인터페이스 분리원칙을 준수하였다고 이야기 할 수 있다.
	- `ScheduleUpdater` 는 불필요한 `Viewer` 인터페이스에 의존하지 않으며, `SimpleHttpServer` 또한 불필요한 `Updater` 인터페이스에 의존하지 않는다.
- 설계가 `유연` 하고, `확장성` 이 높고` 재사용` 하기 쉽다.(단일책임 == 응집도 증가, 재사용성 증가)


<br><hr>

## 인터페이스 분리 원칙 미준수 시

```java
public interface Config { // 신규 요구사항 추가 시, 공통 인터페이스 내에 추가됨
	void update();
	String outputInPlainText();
	Map<String, String> output();
}

public class RedisConfig implements Config {
	// Config 인터페이스 update(사용), ouputInPlainText(사용), output(사용) 을 구현해야함
}

public class KafkaConfig implements Config {
	// Config 인터페이스 update(사용), ouputInPlainText(미사용), output(미사용) 을 구현해야함
}

public class MysqlConfig implements Config {
	// Config 인터페이스 update(미사용), ouputInPlainText(사용), output(사용) 을 구현해야함
}
```

- `Config` 인터페이스는 서로관련이 없는 두 종류의 인터페이스가 포함되어 있다.
	- `update()` 와 `outputInPlainText()`, `output()` 은 서로 전혀 관계가 없다.
	- 인터페이스를 분리하지 않는다면, 새 인터페이스가 `Config` 에 추가 될 때마다, 모든 구현 클래스에도 해당 인터페이스를 구현해야한다.
