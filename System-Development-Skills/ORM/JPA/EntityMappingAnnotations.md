# JPA Annotations: 객체와 테이블 매핑

### `@Entity`
- JPA 에서 테이블에 매핑할 클래스에 붙인다. 이 어노테이션이 붙은 클래스는 엔티티로 불린다.
- privaet 혹은 protected 인 기본 생성자(default constructor)를 반드시 작성해야한다.
- final class, enum, interface, inner class 에는 사용이 불가능하다. (final 필드 또한 사용불가)
  
### `@Table`
- 엔티티와 매핑할 테이블 이름을 지정한다.
- 속성 값:
  - `name`: 테이블의 이름
  - `schema`: 데이터베이스의 스키마
  - `catalog`: 테이블의 데이터베이스 카탈로그
  - `uniqueConstraints`: 테이블 열에 고유한 제약 조건을 지정하는데 사용

### `@Id`
- 엔티티의 유니크한 식별을 위해 사용되는 기본 키로 사용되는 필드에 사용한다.
- @GeneratedValue 어노테이션을 통해 다양한 기본키 생성 전략을 사용할 수 있다.

> 기본키 매핑 전략
1. **직접 할당하는 방법**: Manually setting the primary key.
2. **IDENTITY**: 기본키 생성을 데이터베이스에 위임한다. 이 경우, MySQL에서 AUTO_INCREMENT 를 지정해 둔 경우 사용한다. IDENTITY 전략 사용시 즉시 DB반영된다.(쓰기지연 X)
3. **SEQUENCE**: 데이터베이스 시퀀스를 사용하여 고유 값을 생성한다.
4. **TABLE**: 데이터베이스 테이블을 사용하여 고유성을 보장한다. SEQUENCE 와 비슷하게 기본키를 생성한다.

### `@GeneratedValue`
- 영속성 컨텍스트에 엔티티를 저장하려면 반드시 식별자가 필요하기 때문에, 기본 키 생성 전략이 필요하며, @GeneratedValue 는 기본키를 매핑 하는 전략을 나타낸다.
- Strategies: `IDENTITY`, `SEQUENCE`, `TABLE`, `AUTO`.

### `@Column`
- 엔티티 필드를 데이터베이스 열에 매핑한다.
- 속성 값
  - `name`: 데이터베이스의 열 이름을 지정한다.
  - `nullable`: 열이 null일 수 있는지 여부를 나타낸다.
  - `length`: 열 길이(주로 문자열 데이터 유형에 사용됨).
  - `unique`: 열 값이 고유해야 하는지 여부를 나타낸다.

### `@Enumerated`
- 필드를 열거형 유형으로 유지하도록 지정한다.  열거형(문자열 또는 일반형;STRING or ORDINAL)을 지정할 수 있다.

### `@Temporal`
- 데이터베이스에서 사용되는 SQL 유형(DATE, TIME, or TIMESTAMP)을 지정하기 위해 java.util.Date 및 java.util.Calendar 클래스와 함께 사용된다.

### `@Lob`
- 필드가 CLOB(Character Large Object) 또는 BLOB(Binary Large Object)와 같은 큰 객체로 유지되어야 함을 나타낸다.

### `@Transient`
- 영속성 프레임워크에서 무시 할 필드를 의미한다. 데이터베이스에서 지속되지 않을 필드를 표시한다.

### `@Access`
- 필드 액세스(AccessType.FIELD) 또는 속성 액세스(AccessType.PROPERTY)를 통해 JPA가 엔티티의 값에 액세스하는 방법을 지정한다.
  - `FILED`: 기본값으로, 직접 필드에 접근한다.
  - `PROPERTY`: getter를 통해 접근한다.

### `@Embedded` and `@Embeddable`
- 클래스를 다른 엔티티의 일부로 임베드하는 데 사용된다.
  - `@Embedded`: 필드에 선언
  - `@Embeddable`: 클래스에 선언  

### `@AttributeOverride` and `@AttributeOverrides`
- 임베드 가능한 클래스에 정의된 열 매핑을 재정의할 수 있다.
- 임베더블 클래스가 여러 엔티티에서 사용되는 경우 속성을 재정의하는 데 유용하다.

<br><hr>

## Examples

### Basic Entity Mapping
```java
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Role role;
    // Constructors, Getters, and Setters
}
```

### Embeddable Object Example
```java
@Embeddable
public class Address {
    private String street;
    private String city;
    private String state;
    private String zipCode;
    // Constructors, Getters, and Setters
}

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Embedded
    private Address address;
    // Constructors, Getters, and Setters
}
```

### Overriding Embedded Attributes
```java
@Entity
@Table(name = "suppliers")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "street", column = @Column(name = "supplier_street")),
        @AttributeOverride(name = "city", column = @Column(name = "supplier_city"))
    })
    private Address address;
    // Constructors, Getters, and Setters
}
```

### Association Override

```java
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @AssociationOverride(
        name = "employees",
        joinTable = @JoinTable(
            name = "dept_employees",
            joinColumns = @JoinColumn(name = "department_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
        )
    )
    @OneToMany
    private List<Employee> employees;
    // Constructors, Getters, and Setters
}
```
