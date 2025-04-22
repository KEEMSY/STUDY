# 가변성 (Mutability)  

객체지향 프로그래밍(OOP)은 현실 세계의 개념과 엔티티를 모델링하여 시스템을 구축하는 방법이다.  
현실 세계의 대상은 시간이 지나며 관찰을 통해 이해가 깊어지지만, 본질은 쉽게 변하지 않는다.  
따라서 객체지향 모델도 가능한 한 **불변성**을 유지하고, 필요한 경우에만 조심스럽게 진화하도록 설계해야 한다.  

객체를 과도하게 변경하거나 남용하면, 모델이 불완전해지고 설계 전체에 파급효과가 생긴다.  
이는 시스템의 일관성과 안정성을 해치며, 장기적으로 유지보수를 어렵게 만든다.  


## 객체는 왜 불변해야 하는가?  

- 객체는 생성 시점부터 **완전하고 유효한 상태**여야 한다.  
- 초기부터 엔티티의 본질을 정확히 표현할 수 있어야 하며, 그래야 이후에도 안정적으로 동작할 수 있다.  
- 현실 세계의 대부분의 엔티티는 불변이며, 변화가 있다 해도 **본질의 수정이 아닌 새로운 상태로의 이행**이다.  
- 객체가 변화한다면, 그 변화는 **우연적(incidental)**이어야 하며, **다른 객체들과의 결합 없이 독립적으로** 이루어져야 한다.  

## 모델의 표현과 일관성을 유지하기 위한 3가지 규칙  

### 규칙 1. 객체는 생성 시 완전해야 한다.  
- 유효하지 않은 상태로 객체를 생성해서는 안 된다.  
- 생성과 동시에 의미 있는 역할을 수행할 수 있어야 하며, 이는 객체의 본질을 훼손하지 않기 위함이다.  

### 규칙 2. 세터(setter)는 존재해서는 안 된다.  
- 세터는 외부에서 객체의 내부 상태를 직접 수정하게 만들어 객체의 일관성을 깨뜨린다.  
- 상태 변경은 객체의 **행위를 통해서만** 이루어져야 하며, 해당 행위는 도메인 내에서 의미 있는 동작이어야 한다.  

### 규칙 3. 게터(getter)도 지양해야 한다.  
- 현실 세계의 엔티티는 자신의 속성을 외부에 직접 노출하지 않는다.  
- 단순히 내부 값을 반환하는 동작은 객체의 책임이 아니며, 객체의 **행위 속에 포함되어야** 한다.  
- getXXX() 같은 접근자는 내부 구현에 대한 관심을 외부로 유출시켜 **캡슐화(encapsulation)**를 해치게 된다.  

## 결론  

객체지향에서 진정한 모델링은 데이터를 저장하는 구조체가 아닌, **의미 있는 개념과 행위를 가진 독립적인 객체**를 만드는 것이다.  
객체는 태어날 때부터 의미 있고 완전해야 하며, 그 상태는 가능하면 불변으로 유지되어야 한다.  
변화가 필요한 경우에도 외부에 영향을 최소화하며, **독립적으로 진화**해야 한다.  

불변성은 단순한 기술적 제약이 아니라, **모델의 일관성과 현실 세계의 정확한 표현을 위한 중요한 설계 원칙**이다.  


---
---

## 객체의 본질 변경을 막는다.

객체를 생성한 후 객체의 본질 변경이 현실 세계에서 불가능하다면 필수 속성이 설정된 후에는 변경을 금지해야 한다. 이는 앞서 설명한 세 가지 규칙(완전한 객체 생성, 세터 제거, 게터 지양)을 통해 달성할 수 있다.

- 불변 객체를 사용함으로써, 파급효과를 피하고 참조 투명성을 높일 수 있다.
- 객체는 오직 우연적(incidental)인 방식으로만 변형되어야 하며, 본질적인 부분이 변형되어서는 안된다.

### 불변 객체의 장점

1. **스레드 안전성**: 동시성 환경에서 별도의 동기화 없이 안전하게 사용 가능
2. **방어적 복사 불필요**: 객체가 변경되지 않으므로 복사본을 만들 필요가 없음
3. **디버깅 용이성**: 객체 상태가 중간에 변경되지 않아 오류 추적이 용이함
4. **시간적 결합 감소**: 객체 생성 시점과 사용 시점 사이의 의존성이 줄어듦

> **객체의 본질**
> 객체의 본질을 식별하는 것은 객체가 속한 도메인에 대한 깊은 이해가 필요하다. 만약 특정 동작을 제거해도 객체가 계속 동일한 역할을 수행한다면, 그 동작은 필수적이지 않다고 볼 수 있다.
> 그리고 속성은 동작과 밀접하게 결합되어 있으므로 동일한 원칙이 적용되어야 한다.

### 자바에서의 불변 객체 구현 예시

```java
// 불변 객체로 구현한 Money 클래스
public final class Money {
    private final BigDecimal amount;
    private final Currency currency;

    public Money(BigDecimal amount, Currency currency) {
        if (amount == null) throw new IllegalArgumentException("금액은 null이 될 수 없습니다");
        if (currency == null) throw new IllegalArgumentException("통화는 null이 될 수 없습니다");
        
        this.amount = amount;
        this.currency = currency;
    }

    // 상태를 변경하는 대신 새 객체를 반환하는 연산
    public Money add(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("다른 통화와 연산할 수 없습니다");
        }
        return new Money(this.amount.add(other.amount), this.currency);
    }

    public Money multiply(int multiplier) {
        return new Money(this.amount.multiply(new BigDecimal(multiplier)), this.currency);
    }

    // 필요한 경우 제한적으로 값에 접근할 수 있는 메서드 제공
    public boolean isGreaterThan(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("다른 통화와 비교할 수 없습니다");
        }
        return this.amount.compareTo(other.amount) > 0;
    }

    @Override
    public String toString() {
        return amount.toString() + " " + currency.getSymbol();
    }

    // equals와 hashCode는 반드시 구현
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return amount.equals(money.amount) && currency.equals(money.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }
}
```

이 예시에서 `Money` 클래스는:

1. `final` 클래스로 선언되어 확장을 방지
2. 모든 필드가 `final`로 선언되어 변경 불가
3. 상태를 변경하는 메서드는 원본 객체를 수정하지 않고 새 객체를 반환
4. 생성자에서 유효성 검사를 통해 완전한 상태로 객체 생성
5. 내부 상태를 직접 노출하는 게터 대신 의미 있는 행위 제공
6. 동등성 비교를 위한 `equals`와 `hashCode` 구현

불변 객체는 단순히 변경을 방지하는 것이 아니라, 도메인 모델의 무결성과 일관성을 보장하는 강력한 설계 원칙이다. 특히 값 객체(Value Object)와 같이 식별성보다 값의 의미가 중요한 경우, 불변성은 핵심 속성이 된다.

---
---
## 지연 로딩(지연초기화)을 사용하지 않는다.

지연 초기화는 비용이 많이 드는 객체를 필요한 시점에만 생성하고, 그 전까지는 초기화를 지연시키는 방법이다. 이는 자원 사용을 최적화하고 성능을 개선하기 위해 사용되지만, 불변성 원칙에 위배되며 다양한 문제를 야기할 수 있다.

### 지연 초기화의 문제점

- **객체 생성 시 완전성 위배**: 불변 객체는 생성 시점에 완전한 상태여야 하지만, 지연 초기화는 이를 위반한다.
- **동시성 문제**: 멀티스레드 환경에서 경쟁 상태(race condition)와 같은 문제가 발생할 수 있다.
- **코드 복잡성 증가**: 동시성 문제를 해결하기 위한 락(lock)이나 동기화 코드가 필요해 복잡도가 증가한다.
- **교착상태 위험**: 여러 객체가 상호 의존적으로 지연 초기화를 사용할 경우, 교착상태가 발생할 수 있다.

### 안티 패턴 예시: 지연 초기화를 사용한 싱글턴

```java
// 문제가 있는 지연 초기화 싱글턴 패턴 구현
public class ExpensiveResource {
    private static ExpensiveResource instance;
    
    // 비용이 많이 드는 데이터
    private final HeavyData heavyData;
    
    private ExpensiveResource() {
        // 시간이 오래 걸리는 초기화 작업
        heavyData = loadHeavyData();
    }
    
    // 동기화되지 않은 지연 초기화 - 스레드 안전하지 않음
    public static ExpensiveResource getInstance() {
        if (instance == null) {  // 경쟁 상태 가능성 있음
            instance = new ExpensiveResource();
        }
        return instance;
    }
    
    private HeavyData loadHeavyData() {
        // 비용이 많이 드는 로딩 작업 시뮬레이션
        try {
            Thread.sleep(2000);  // 2초 대기
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return new HeavyData();
    }
    
    public void process() {
        // heavyData를 사용한 처리
    }
    
    private static class HeavyData {
        // 많은 메모리를 사용하는 데이터 구조
    }
}
```

### 개선된 접근 방법: 불변성 유지하기

```java
// 불변성을 유지하는 즉시 초기화 방식
public final class ExpensiveResource {
    // 즉시 초기화 (Eager initialization)
    private static final ExpensiveResource INSTANCE = new ExpensiveResource();
    
    private final HeavyData heavyData;
    
    private ExpensiveResource() {
        heavyData = loadHeavyData();
    }
    
    // 스레드 안전한 인스턴스 접근
    public static ExpensiveResource getInstance() {
        return INSTANCE;
    }
    
    private HeavyData loadHeavyData() {
        // 비용이 많이 드는 로딩 작업
        return new HeavyData();
    }
    
    // 메서드는 불변 상태를 유지
    public Result process(Input input) {
        // heavyData를 사용하여 input을 처리하고 새로운 Result 반환
        return new Result(input, heavyData);
    }
    
    private static class HeavyData {
        // 불변 데이터 구조
    }
    
    public static class Result {
        private final Input input;
        private final HeavyData data;
        
        public Result(Input input, HeavyData data) {
            this.input = input;
            this.data = data;
        }
        
        // 결과에 대한 불변 접근 메서드들...
    }
    
    public static class Input {
        // 입력 데이터...
    }
}
```

### 대안적 해결책

실제로 성능 문제가 있는 경우, 다음과 같은 대안을 고려해볼 수 있다:

1. **프록시 패턴**: 실제 객체 대신 대리 객체를 통해 접근하되, 객체 자체는 완전히 초기화
2. **팩토리 메서드**: 객체 생성을 팩토리에 위임하여 생성 시점을 제어하되, 생성된 객체는 불변으로 유지
3. **의존성 주입**: 필요한 무거운 리소스를 외부에서 주입받아 객체 자체는 항상 완전한 상태 유지

불변성을 유지하면서도 초기화 비용 문제를 해결하는 것이 중요하다. 섣부른 최적화보다는 설계의 일관성과 객체의 완전성을 우선시해야 한다.