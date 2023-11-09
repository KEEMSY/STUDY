## Background Knowledge(배경지식)

>API Note

**Optional is primarily intended for use as a method return type where there is a clear need to represent "no result," and where using null is likely to cause errors. A variable whose type is Optional should never itself be null; it should always point to an Optional instance.**   
  
**메소드가 반환할 결과 값이 '없음'을 명백하게 표현할 필요가 있고,** **null** **을 반환하면 에러가 발생할 가능성이 높은 상황에서 메소드의 반환 타입으로** **Optional** **을 사용하자는 것이** **Optional** **을 만든 주된 목적이다.**   
**Optional** **타입의 변수의 값은 절대** **null** **이어서는 안 되며, 항상** **Optional** **인스턴스를 가리켜야 한다.**

<br>

<img width="811" alt="Optional 을 사용한 예시" src="https://github.com/KEEMSY/STUDY/assets/96563125/1a1a5f21-d87a-4c5a-9938-b7adc6944b64">

나는 `Optional` 을 프로젝트를 개발하면서 사용하고 있다. `Optional` 은 값이 존재 하지 않을 수 있는 상황(null) 대신 사용되어, `코드 안정성` 및 `코드 복잡도` 를 줄이고 `코드 가독성`(null 확인 코드)을 증진 시킬 수 있는 효과적인 방법이며, `래퍼 클래스` 이다.

- Optional은 Java 8부터 도입된 클래스이며, Optional로 객체를 감싸서 사용한다면 null 체크를 직접하지 않아도 된다.
- null을 다룰 때 발생하는 NullPointerException을 방지하며, 명시적으로 해당 변수가 null 일 수 있다는 가능성을 표현할 수 있다.

<br>

> Optional 을 사용하기 전의 코드

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

> Optional 을 사용할 경우

```java
import java.util.Optional;

public class UserService {
    private UserRepo userRepo;

    public Optional<User> getUser(String telephone) {
        // 사용자가 없을 경우 Optional.empty() 반환
        // 혹은 Optional.ofNullable()을 사용하여 null일 때도 처리 가능
        return Optional.ofNullable(userRepo.getUserByTelephone(telephone));
    }
}

// getUser() 사용
Optional<User> userOptional = userService.getUser("1891771****");
userOptional.ifPresent(user -> {
    String email = user.getEmail();
    email.ifPresent(e -> {
        String escapedEmail = e.replaceAll("@", "#");
        // ...
    });
});

```


`Optional` 의 사용 목적은 `NPE` 를 방지하고, `값이 존재하지 않을 수 있음` 을 분명하게 표현할 수 있어, 개발자에게 의도를 분명하게 전달할 수 있다. 그러나 `Optional` 을 알지 못하거나 올바른 사용법을 알지 못한다면 오히려 역효과가 발생할 수 있다.

- Optional.of() 을 호출할 때, 값이 없다면, 에러가 발생할 수 있다.
- 값이 존재하는지 확인하는 코드가 추가되어, 코드 가독성에 영향을 줄 수 있다.

<br>

나는 개발 간 `Optional` 을 사용하면서, `단일 객체` 에 대한 빈 객체의 가능성을 명확하게 전달하는데 있어서는 유용하다고 생각했으나 `컬렉션` 에 대해서는 `Optional 이 필요한 것인가?` 하는 생각이 들었다.

<br>

> 문제의 테스트

```java
@Test  
@DisplayName("존재하지 않는 memberId 를 조회 시, CreditHistory 미존재 확인")  
void findByMemberId_ShouldReturnOptionalEmpty() {  
    // given  
    MemberId unknownMemberId = new MemberId(UUID.randomUUID());  
  
    // when  
    Optional<List<CreditHistory>> foundCreditHistoryList = creditHistoryPersistenceAdapter  
            .findByMemberId(unknownMemberId);  
  
    // then  
    assertThat(foundCreditHistoryList).isEmpty();  
}

/* 
# 테스트 결과
Expecting an empty Optional but was containing value: []
java.lang.AssertionError: 
Expecting an empty Optional but was containing value: []
*/
```

- foundCreditHistory 는 항상 isPresent() 가 참 이다.
- foundCreditHistory 는 Optional[[]] 으로 isEmpty 가 거짓 이다.

<br>

### Preliminary Questions(사전질문)

- **Optional 은 필수적인가?**
- **올바른 Optional 의 사용은 무엇인가?**

---

## Contents

### Optional 은 필수적인가?

`Optional` 을 올바르게 사용하기 위해서는 `Optional 을 사용하는 목적` 에 대해서 다시 한번 생각해보면 좋을 것이라고 생각한다. 앞서 이야기 했듯, **Optional 의 사용 목적은 객체가 있을 수도 있고 없을 수도 있는 상황(null 가능성) 에 대한 코드 안정성을 보장하기 위함**이다. 

- null 로 인한 문제는 null 이 가능함을 분명하게 표현하지 못하여(인지하지 못하여) 발생한다. 그리고 Optional 을 활용하면 null 일 수 있음을  분명하게 표현할 수 있다.

<br>

그렇다면, `Optional` 을 활용해야만 `null` 일 수 있음을 표현할 수 있을까? 이에 대한 답은, **언제 null 일 수 있는가?** 를 생각해보아야한다. 일반적으로 `null 은 값이 존재하지 않음을 표현`할 때 사용한다.

1. 데이터베이스에서 검색 결과가 없을 때, 네트워크 요청이 실패했을 때, 파일이 존재하지 않을 때 등의 경우에 null 을 반환할 수 있다.
2. 특정 로직의 예외처리 의 방법으로 null 을 반환 할 수 있다.

<br>

`null` 을 사용하는 경우 중 첫번째 경우는 합리적이다. 조회 함수 또는 요청에 대한 데이터가 없는 경우 결과 값이 없어 `null` 을 반환하는 것은 합리적이다. 그러나 두번째 경우에 해당하는 `예외처리`(예상하는 결과가 아닌 경우)는 잘못된 케이스 되었다고 생각한다. 그리고 이 경우에 `NPE` 가 발생할 가능성이 높다. 즉, `예외처리를 위해 null 을 사용할 때 NPE 발생으로 인한 코드 안정성 문제가 발생한다` 고 볼 수 있다.

다시 본론으로 돌아와 `null 안정성을 위해 사용하는 Optional 은 필수적인가?` 라는 질문에 대한 답을 해본다면, 내 생각은 그렇지 않다. 문제가 되는 `null 이 예외처리를 애 대한 null 안정성을 위해 Optional 을 사용한다는 것은 옳지 않다.` 그리고 `null 을 반환해야하는 것이 합리적인 경우 null 을 처리하는 것이 더 올바른 방향` 이라고 생각한다.

- 호출자 에서 발생하는 NPE 를 방지하기 위해 Optional 을 사용한다는 것은 바람직하지 않다 생각한다.(애초에 잘못되었다.)
- 호출되는 코드에서 값이 없을경우에 대한 처리 로직을 포함해야한다.

<br>

`Optional` 을 사용함으로써 `null` 일 수 있음을 분명하게 표현하는 것은 장점일 수 있으나, `NPE` 가 왜 문제가 되는지를 고민해보았을 때 문제를 급하게 해결하기 위한 반창고에 불과한다 생각한다. 

<br>

### 그럼에도 Optional 은 유용하다. 어떻게 Optional 을 사용하는 것이 올바르게 사용할 수있을까?

내가 생각한 경우 외에도 분명 `Optional` 이 유용한 상황은 존재한다. 하지만 유용한 것을 올바르게 사용해야 유용한 것이므로, 올바른 `Optional` 사용이 되어야 한다.  올바른 사용위한 고민은 마찬가지로 `Optional` 을 사용하는 목적 에 대한 고민을 통해 해결할 수 있다고 생각한다. `Optional 은 null 을 반환함으로써 발생할 수 있는 코드 안정성의 문제를 해결하기 위해 탄생`하게 되었음을 잊지 말자.

- null 안정성을 보장하면서, null 일 수 있는 순간에 Optional 을 활용한 코드를 작성할 수 있다.

<br>

> Optional 을 사용하기 좋은 상황

내가 작성 시점(23-11-09)에서 생각하는 `Optional` 을 사용하기 좋은 상황은 다음과 같다.

1. 메소드가 어떤 조건에 따라 값을 반환하지 못할 수 있는 경우

```java
public Optional<String> findUsernameById(int userId) {
    // username 을 찾는 로직
    if (userExists(userId)) {
        return Optional.of(getUsername(userId));
    } else {
        return Optional.empty();
    }
}

```

- 적절한 반환 값이 존재하지 않을 때 값이 존재하지 않음을 분명하게 표현할 수 있으며, 해당 메서드를 사용하는 곳에서 ifPresent() 를 통해 적절한 처리를 할 수 있다.

<br>

2. 컬렉션에서 원하는 요소를 찾지 못할 수 있는 경우

```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
Optional<String> result = names.stream().filter(name -> name.startsWith("D")).findFirst();

```

- 컬렉션 내의 원하는 조건의 값의 존재 유무를 알 수 없을 때, 활용할 수 있다.

<br>

> Optional 을 올바르게 사용하는 방법

1. `Optional` 변수에 절대로 `null`을 할당하지 않는다.
2. `Optional.get()` 호출 전에 `Optional` 객체가 값을 가지고 있음을 확실히 한다.
3. 값이 없는 경우, `Optional.orElse()`를 통해 이미 생성된 기본 값(객체)를 제공한다.
4. 값이 없는 경우, `Optional.orElseThrow()`를 통해 명시적으로 예외를 던진다.
5. Optional.of() 와 Optional.ofNullable() 을 혼동하지 않는다.
6. 값이 있는 경우에 이를 사용하고 없는 경우에 아무 동작도 하지 않는다면, `Optional.ifPresent()`를 활용한다.
7. `Optional`을 빈 컬렉션이나 배열을 반환하는 데 사용하지 않는다.
8. `Optional`을 필드의 타입으로 사용하지 않는다.

*출처: [https://dev-coco.tistory.com/178](https://dev-coco.tistory.com/178) [슬기로운 개발생활:티스토리]*

---

## Study Reflection

### Answers to Preliminary Questions(사전 질문에 대한 답변)

> Optional 은 필수적인가?

내 생각은 그렇지 않다. `null` 안정성을 위해 도입된 `Optional`(**반환 타입을 위해 설계된 타입**) 은, `Optional` 이 없이도 `null` 안정성을 보장할 수 있다고 생각하기 때문이다. 

- 개발 과정에서 보다 분명한 처리를 통해 가능하다고 생각한다.

<br>

하지만 `Optional` 이 불필요하다는 생각은 아니다. `Optional 을 통해 분명하게 null 이 가능한 객체임을 표현` 할 수 있기 때문에, 이 부분에서 오는 장점이 분명히 존재한다. 뿐만아니라, 기존에 `null` 이 가능한 경우에는 작성할 수 없던 코드에서 `Optional` 을 통해 `null` 일 수 있음을 명시함으로써 코드 로직을 좀 더 유연하면서 명확하게 작성할 수 있다.

하지만 `Optional` 에 대한 진입 장벽 및 올바른 사용을 하지 않는다면 이 장점은 단점이될 것이므로, `Optional` 의 올바른 사용 방법을 잘 숙지해야한다고 생각한다.

<br>

> 올바른 Optional 의 사용은 무엇인가?

`Optional` 을 올바르게 사용하기 위해서는 `Optional` 이 왜 사용(도입)하게 되었는지 그 목적을 분명하게 인지함으로써 도달 할 수 있다고 생각한다.

- Optional 은 null 을 반환함으로써 인한 코드 안정성의 문제(**반환 타입을 위해 설계된 타입**)를 해결하기 위해 도입되었다.

<br>

아직은 `Optional` 을 올바르게 사용하기 위한 방법을 스스로 정의하지는 못했다. 이를 공부하기 위해 다른 블로그의 내용을 참고하여 공부하였고, 이를 통해 내가 잘못 사용했음을 알 수 있었다.

- 값이 존재하지 않을 때, 기본 값 혹은 에러를 설정하지 않았다.
- 빈 컬렉션을 Optional 으로 감싸 사용했다.
- 값이 존재하지 않을경우에 대한 처리로직을 따로 추가(isPresent())하여 사용했다.

<br>

### Evolving Thoughts

이번 공부를 통해, 기존 알고있던 `Optional` 에 대한 더 깊은 이해 뿐만아니라 개인적 관심사인 `코드 가독성` 측면까지 고민하면서 적용하는 방법을 고민해볼 수 있었다. 뿐만아니라 이전 내 자신의 지식 습득에 대한 잘못된 부분을 인지할 수 있었다. 나는 긍정적인 측면(장점)을 위주로 생각하고 적용했으며, 올바른 사용에 대한 고민이 부족했다.

앞으로 사용하게될 다양한 기술들에 대해서는, 지금과 같은 생각과 고민을 거쳐 올바른 사용을 할 수 있도록 노력해야겠다.

- 적용하려는 기술의 목적은 무엇인가?
- 현 상황에 필수적인가? 그렇게 생각한이유는 무엇인가?
- 올바른 사용방법은 무엇인가?
