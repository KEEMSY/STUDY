# 빈약한 모델을 피하는 방법
## 동적 속성은 사용하지 않는다.
- 동적 속성은 코드를 읽기 어렵게 만들며 범위 정의가 명확하지 않고 오타를 발견하기 어렵게 만든다.
- 잘못된 속성 이름을 사용하기 수비기 때문에 타입 안정성을 악화시킨다.
  - 특성은 대규모 코드베이스에서 디버깅 하기 어려운 런타임 오류로 이어 질 수 있다.
- 클래스나 객체에 이미 정의된 속성과 동일한 이름을 가질 수 있어서 명명 충돌 가능성이 있으며, 예기치 않은 동작을 유발할 수 있다.

# 기본형 사용을 줄여야 하는 이유
## 기본 형타입만 필드로 포함하는 큰 객체 보다는, 작은 객체를 만들고 MAPPER 책임을 부여하여 구체화 한다.
- 핵심 원칙
  - 기본형 타입만 필드로 포함하는 큰 객체보다는, 작은 객체들로 분리하고 각각에 적절한 책임을 부여
  - 의미 있는 객체로 캡슐화하여 도메인 개념을 더 명확하게 표현
- 기본형 사용의 문제점
  - 데이터 유효성 검증이 여러 곳에 분산됨
  - 비즈니스 규칙을 표현하기 어려움
  - 코드 중복이 발생하기 쉬움
  - 도메인 의미를 명확히 전달하기 어려움
- 값 객체 사용의 이점
  - 유효성 검증이 각 값 객체에 캡슐화됨
  - 도메인 개념을 코드로 명확하게 표현
  - 타입 안전성 향상
  - 관련 기능 확장이 용이
- 적용 시 고려사항
  - 과도한 분리는 피하고 적절한 크기의 객체로 설계
  - 도메인에서 의미 있는 개념을 가진 데이터는 값 객체로 분리
  - 재사용 가능한 검증 로직은 값 객체에 포함
- 작은 객체를 찾는 것은 매우 어려운 작업이며, 좋은 작업을 수행하고 과도한 설계를 피하려면 경험이 필요하다.
  - 매핑 방법과 시기를 선택하는 데는 정답이 없다.

```java
// 기본형 타입을 사용하는 경우
public class Person {  
    private final String name;  
    private final String email;  

    public Person(String name, String email) {  
        // 각 필드마다 검증 로직 필요  
        if (name == null || name.trim().isEmpty()) {  
            throw new IllegalArgumentException("이름은 필수입니다.");  
        }  
        if (!email.contains("@")) {  
            throw new IllegalArgumentException("유효하지 않은 이메일입니다.");  
        }  
        
        this.name = name;  
        this.email = email;  
    }  
}  
```

```java
// 값 객체 사용
public class Name {  
    private final String value;  

    public Name(String value) {  
        if (value == null || value.trim().isEmpty()) {  
            throw new IllegalArgumentException("이름은 필수입니다.");  
        }  
        this.value = value;  
    }  
}  

public class Email {  
    private final String value;  

    public Email(String value) {  
        if (!value.contains("@")) {  
            throw new IllegalArgumentException("유효하지 않은 이메일입니다.");  
        }  
        this.value = value;  
    }  
}  

public class Person {  
    private final Name name;  
    private final Email email;  

    public Person(Name name, Email email) {  
        this.name = name;  
        this.email = email;  
    }  
}  
```
