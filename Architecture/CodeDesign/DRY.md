# 중복 코드를 작성하지 말라, Don't Repeat Yourself(DRY)

`DRY`(Don't Repeat Yourself) 원칙은 흔히 중복 코드를 작성하지 말라 는 의미로 사용된다. 하지만 `DRY` 원칙에서 말하는 중복은 프로젝트에 여러개의 중복된 코드가 `DRY` 원칙에 위배한다 말하는 것은 아니다.

- 코드 자체는 `DRY` 원칙의 위배 대상에 포함되지 않는다.

<br>

> 개발에서 이야기 할 수있는 중복

- 코드 논리의 중복
- 기능적(의미론적) 중복
- 코드 실행의 중복

<br><hr>

## 코드 논리의 중복

```java
public void authenticate(String username, String password) {
	if (!isValidUserName(username)) {
		// InvalidUsernameException 발생
	}
	if (!isValidPassword(password)) {
		// InvalidPasswordException 발생
	}
	// .. 일부 코드 생략
}
```

```java
private boolean isValidUserName(String username) {
	if (StringUtils.isBlank(username)) {
		return false;
	}
	
	int length = username.length();
	if (length < 4 || length > 64) {
		return false;
	}

	if (!StringUtils.isAllowerCase(username)) {
		return false;
	}

	for (int i = 0; i < length; i++;) {
		char c = username.charAt(i);
		if (!(c >= 'a' && c <= 'z') || (c >= '0' && c <= '9') || c == '.') {
			return false;
		}
	}

	return true;
}
```

```java
private boolean isValidPassword(String password) {
	if (StringUtils.isBlank(password)) {
		return false;
	}
	
	int length = password.length();
	if (length < 4 || length > 64) {
		return false;
	}

	if (!StringUtils.isAllowerCase(password)) {
		return false;
	}

	for (int i = 0; i < length; i++;) {
		char c = password.charAt(i);
		if (!(c >= 'a' && c <= 'z') || (c >= '0' && c <= '9') || c == '.') {
			return false;
		}
	}

	return true;
}
```

이 코드에서 `isValidUserName()` 와 `isValidPassword()` 에는 동일한 코드 논리가 많이 포함되어 있다. 하지만 이것은 `DRY` 원칙을 위반하지 않는다.

- 두 함수는 코드 논리가 중복되지만, 의미적으로는 사용자 이름을 확인하는 일과 암호를 확인하는 일로 서로 전혀 다른 일을 하는 함수이다.
- 반복적인 코드 논리가 자주 나타난다면, 보다 세분화된 기능을 `추상화` 하여 해결 할 수 있다.
	- 특정 문자만 포함하는지 확인하는 논리를 별도로 분리 및 `캡슐화` 한다.
- 논리는 동일하지만 의미가 다른 것은 `DRY` 원칙에 위배되지 않는다고 말할 수 있다.


<br><hr>

## 기능적(의미론적) 중복

같은 기능을 하는 기능이 중복 되어 구현되어 있다면, 이것은 `DRY` 원칙에 위배된다.

- 코드 논리가 중복되지 않지만 **의미적인 중복(기능의 중복)** 은 `DRY` 원칙에 **위배**된다.
- 같은 기능을 하는 다른 코드로 인해, 코드의 `가독성`과 `유지보수성`이 떨어지게 된다.

<br>

```java
public boolean isValidIp(String ipAddress) {
	if (StringUtils.isBlank(ipAddress)) return false;
	String reges = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d[1-9])\\."
		+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
		+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
		+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
	return ipAddress.matches(regex);
}
```

```java
public boolean checkIfIpValid(String ipAddress) {
	if (StringUtils.isBlank(ipAddress)) return false;
	String[] ipUnits = StringUtils.split(ipAddress, '.');
	if ( ipUnits.length != 4) {
		return false;
	}
	for (int i = 0; i < 4; i++;) {
		int ipUnitIntvalue;
		try {
			ipUnitIntValue = Integer.parseInt(ipUnits[i]);
		} catch (NumberFormatException e) {
			return false;
		}
		if (ipUnitIntValue < 0 || ipUnitIntValue > 255) {
			return false;
		}
		if (i == 0 && ipUnitIntValue == 0) {
			return false;
		}
		return true;
	}
}
```

- `isValidIp()` 와 `checkIfIpValid()` 함수는 이름과 코드 논리도 다르지만, 모두 IP 주소가 적합한지 판단하는데 사용되며, 이는 기능(의미적) 중복에 해당한다.

<br> <hr>

## 코드 실행의 중복

코드의 논리적 중복이나 의미적 중복이 없지만 코드에 실행 중복은 `DRY` 원칙에 위배 된다.

<br>

```java
public class UserService {
	private UserRepo userRepo; // 의존성 주입 또는 제어 반전

	public User login(String email, String password) {
		boolean existed = userRepo.checkIfUserExisted(email. password); // 문제가 되는 부분1
		if (!existed) {
			// AuthenticationFailureException 예외 발생
		}
		User user = userRepo.getUserByEmail(email); // 문제가 되는 부분2
		return user;
	}
}
```

```java
public class UserRepo {
	public boolean checkIfUserExisted(String email, String password) {
		if (!EmailValidation.validate(email)) { // 논리의 중복
			// InvalidEmailException 발생
		}
		if (!PasswordValidation.validate(password)) {
			// InvalidPasswordException 예외 발생
		}
		// 사용자 정보에서 해당 email, password 가 존재하는지 확인하는 코드 생략 
	}

	public User getUserByEmail(String email) {
		if (!EmailValidation.validate(email)) { // 논리의 중복
			// InvalidEmailException 발생
		}
		// .. 주어진 이메일에 해당하는 사용자가 있는지 확인하는 코드 생략...
	}
}
```

- `login()` 에서 `checkIfUserExisted()` 를 호출 할 때와 `getUserByEmail()` 를 호출할 때, **이메일 확인 논리가 두 번 실행** 된다.

<br>

> DRY 를 준수하도록 리팩토링

```java
public class UserService {
	private UserRepo userRepo; // 의존성 주입 또는 제어 반전

	public User login(String email, String password) {
		if (!EmailValidation.validate(email)) { 
			// InvalidEmailException 발생
		}
		if (!PasswordValidation.validate(password)) {
			// InvalidPasswordException 예외 발생
		}

		User user = userRepo.getUserByEmail(email);
		if (user == null || !password.equals(user.getPassword()) {
			// AuthenticationFailureException 예외 발생
		}
		return user;
	}
}
```

```java
public class UserRepo {
	public boolean checkIfUserExisted(String email, String password) {
		// 사용자 정보에서 해당 email, password 가 존재하는지 확인하는 코드 생략        
	}
	
	public User getUserByEmail(String email) {
		// 주어진 email 에 해당하는 사용자가 있는지 확인하는 코드 생략
	}
}
```

이메일을 확인하기 위해 데이터베이스에 접근했다가 다시 사용자 정보를 확인하기 위해 데이터 베이스에 접근할 필요가 없다.

- 이메일 확인 논리를 `UserRepo` 클래스에서 `UserService` 클래스 로 옮긴다.
