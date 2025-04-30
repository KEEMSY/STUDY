# 빈약한 모델을 피하는 방법

## 동적 속성은 사용하지 않는다.

- 동적 속성(dynamic property)은 코드를 읽기 어렵게 만들고, 속성의 범위와 역할이 명확하지 않게 된다.
- 오타가 발생해도 컴파일 타임에 발견하기 어렵고, 런타임 오류로 이어질 수 있다.
- 잘못된 속성 이름을 사용하기 쉬워 타입 안정성이 크게 저하된다.
- 대규모 코드베이스에서는 이러한 특성 때문에 디버깅이 매우 어려워진다.
- 클래스나 객체에 이미 정의된 속성과 동일한 이름을 동적으로 추가할 수 있어, 명명 충돌(name collision) 및 예기치 않은 동작을 유발할 수 있다.

### 보완 설명 및 예시

동적 속성은 주로 JavaScript, Python 등 동적 타입 언어에서 쉽게 사용할 수 있지만, 다음과 같은 문제를 일으킨다.

#### 나쁜 예: 동적 속성 사용

```python
class User:
    pass

user = User()
user.name = "Alice"  # 동적으로 속성 추가
user.age = 30        # 동적으로 속성 추가

# 오타 발생 시 런타임까지 오류를 알 수 없음
user.nmae = "Bob"    # 'name'이 아니라 'nmae', 오류가 발생하지 않음
```

#### 좋은 예: 명시적 속성 정의

```python
class User:
    def __init__(self, name: str, age: int):
        self.name = name
        self.age = age

user = User("Alice", 30)
# 오타 발생 시 IDE나 린터, 타입 검사에서 바로 오류를 확인할 수 있음
```

### 결론

- 동적 속성 사용을 지양하고, 클래스나 객체의 속성은 명확하게 정의해야 한다.
- 명확한 속성 정의는 코드의 가독성, 유지보수성, 타입 안정성, 디버깅 효율성을 모두 높여준다.
- 특히 대규모 프로젝트에서는 동적 속성 사용이 예기치 않은 버그와 유지보수 비용 증가로 이어질 수 있으므로 반드시 피해야 한다.

---
---

## 빈 생성자를 사용하지 않는다.

객체를 생성할 때는 모든 필수 인자를 전달하여, 하나의 완전한 단일 생성자만을 사용해야 한다.

- 인수 없이 생성된 객체(빈 생성자)는 대체로 변경 가능하고, 예측할 수 없으며, 일관성이 없는 상태가 되기 쉽다.
- 불완전한 객체는 다양한 잠재적 문제(예: 객체의 동작이 예기치 않게 변경됨)를 유발할 수 있다.
- 모든 객체는 생성 시점에 그 본질이 유효하고 완전해야 한다.
- 나이나 물리적 위치와 같은 우발적 속성은 변할 수 있지만, 객체의 본질(불변 속성)은 생성 시점에 반드시 정의되어야 하며, 이후에도 일관성을 유지해야 한다.

### 보완 설명 및 예시

#### 나쁜 예: 빈 생성자 사용

```java
public class User {
    private String name;
    private int age;

    public User() {
        // 아무 값도 초기화하지 않음
    }

    // setter로 값 할당
    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
}
```
- 이 경우, User 객체는 불완전한 상태로 생성될 수 있고, 일관성 없는 객체가 될 위험이 있다.

#### 좋은 예: 필수 인자를 받는 생성자 사용

```java
public class User {
    private final String name;
    private final int age;

    public User(String name, int age) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("이름은 필수입니다.");
        }
        if (age < 0) {
            throw new IllegalArgumentException("나이는 0 이상이어야 합니다.");
        }
        this.name = name;
        this.age = age;
    }
}
```
- 객체는 생성 시점에 항상 완전하고 유효한 상태를 보장한다.

### 결론

- 빈 생성자 사용을 지양하고, 객체의 본질을 정의하는 모든 필수 속성은 생성자에서 반드시 초기화해야 한다.
- 이는 객체의 일관성, 안정성, 유지보수성을 크게 높여준다.

---
---

## 게터(Getter)를 제거한다.

get 접두사를 사용하는 접근자 메서드는 정보 은닉과 캡슐화 원칙을 위배하게 된다. 이는 객체의 내부 구현을 외부에 노출시켜 유연한 설계를 방해한다.

### 게터 사용의 문제점

1. **캡슐화 위반**
   - 객체의 내부 상태를 직접 노출
   - 구현 세부사항이 외부로 유출
   - 객체의 자율성 저하

2. **결합도 증가**
   - 객체 간 강한 결합 발생
   - 변경의 어려움
   - 유지보수성 저하

3. **책임의 분산**
   - 객체가 자신의 상태를 책임지지 못함
   - 비즈니스 로직이 객체 외부로 유출
   - 응집도 저하

### 코드 예시

#### 나쁜 예: 게터 사용

```java
class Order {
    private List<OrderItem> items;
    private BigDecimal totalAmount;

    // 게터를 통해 내부 상태 노출
    public List<OrderItem> getItems() {
        return items;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
}

// 클라이언트 코드
class OrderProcessor {
    public void process(Order order) {
        // 객체 외부에서 상태를 직접 조작
        List<OrderItem> items = order.getItems();
        BigDecimal total = order.getTotalAmount();
        
        // 비즈니스 로직이 객체 외부에 존재
        if (total.compareTo(new BigDecimal("10000")) > 0) {
            applyDiscount(items);
        }
    }
}
```

#### 좋은 예: 행위 중심 설계

```java
class Order {
    private List<OrderItem> items;
    private BigDecimal totalAmount;

    // 객체가 자신의 상태를 책임지는 행위 제공
    public void applyDiscountIfEligible() {
        if (isEligibleForDiscount()) {
            applyDiscount();
        }
    }

    private boolean isEligibleForDiscount() {
        return totalAmount.compareTo(new BigDecimal("10000")) > 0;
    }

    private void applyDiscount() {
        items.forEach(OrderItem::applyDiscount);
        recalculateTotalAmount();
    }

    // 필요한 경우 상태 확인을 위한 서술적 메서드 제공
    public boolean hasItems() {
        return !items.isEmpty();
    }

    public boolean exceedsAmount(BigDecimal threshold) {
        return totalAmount.compareTo(threshold) > 0;
    }
}

// 클라이언트 코드
class OrderProcessor {
    public void process(Order order) {
        // 객체에 행위를 요청
        order.applyDiscountIfEligible();
    }
}
```

### 개선 방안

1. **Tell, Don't Ask 원칙 적용**
   - 객체의 상태를 묻지 말고, 행위를 요청하라
   - 객체가 자신의 상태를 스스로 관리하도록 함

2. **행위 중심 인터페이스**
   - 상태 접근자 대신 의미 있는 행위 제공
   - 비즈니스 로직을 객체 내부로 이동

3. **의도를 드러내는 메서드**
   - 단순한 상태 접근이 아닌 의도가 드러나는 메서드 이름 사용
   - 도메인 용어를 활용한 메서드 명명

4. **불변성 활용**
   - 가능한 한 객체를 불변으로 설계
   - 상태 변경이 필요한 경우 새로운 객체 반환

### 결론

게터를 제거하고 객체가 자신의 상태를 스스로 관리하도록 설계하면 다음과 같은 장점을 얻을 수 있다.
- 캡슐화가 향상됨
- 객체의 자율성이 증가
- 코드의 유지보수성이 개선됨
- 도메인 모델이 더 풍부해짐
