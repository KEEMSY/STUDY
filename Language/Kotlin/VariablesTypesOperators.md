# Variables, types, and operators

## **변수,Variables**

```java
package com.lannstark.lec01;

import java.util.Arrays;
import java.util.List;

public class Lec01Main {

  public static void main(String[] args) {
    long number1 = 10L; // (1) Long 타입의 변수
    final long number2 = 10L; // (2) final Long 타입의 변수, Primitive type

    Long number3 = 1_000L; // (3) Long 객체의 타입, 레퍼런스 타입, null 가능
    Person person = new Person("김성연"); // (4) 직접만든 Person 클래스의 변수

    final List<Integer> numbers = Arrays.asList(1,2); // 컬렉션 자체는 바꿀 수 없지만, 컬렉션(ArrayList일 경우) 안에 값을 추가할 수 있다.

  }
}
```

- Java 에서 `long`(1) 과 `final long`(2) 의 차이는 해당 변수가 `가변` / `불변`(read-only)이냐에 따라 달려있다.
- Java 에서는 `Type`을 반드시 명시해야한다.

```kotlin
fun main() {
    var number1: Long = 10L // 가변
    val number2 = 10L // 불변 // Type을 작성하지 않아도 된다.

    var number11: Long // 값을 바로 할당하지 않는 경우, 타입을 명시한다.
    val number22: Long
    number22 = 10L // 값 최초 할당

    var number3 = 1_000L
    var number33: Long? // null 을 허용한다.

    var person = Person("김성연")
}
```

- 코틀린 에서는 모든 변수에 수정가능 여부(`var`-가변 / `val`-불변)을 명시해주어야 한다.
- 코틀린에서는 `Type`을 명시적으로 작성하지 않아도, 자동으로 컴파일러가 추론해준다.
  - 하지만 **값을 명시하지 않을 경우 컴파일러가 값을 추론할 수 없어, `Type` 을 명시해야한다.**
  - `val` 은 초기화 되지 않은 변수에 한해, 최초 한번은 값을 넣어줄 수 있다.
- `val` 컬렉션에는 `element` 를 추가할 수 있다.

*TIP. 모든 변수는 val로 만들고 꼭 필요한 경우 var로 변경한다.*

- `Primitive Type` 과 `Reference Type` 을 **구분하지 않아도 된다.**
  - `숫자`, `문자`, `불리언`과 같은 몇몇 타입은 내부적으로 특별한 표현을 갖는다.
  - 이 타입들은 실행시에 Primitive Value 로 표현되자만, 코드에서는 평범한 클래스 처럼 보인다.
  - 프로그래머가 `boxing` / `unboxing` 을 고려하지 않아도 되도록 코틀린이 알아서 처리한다.
  - `Long`(숫자, 문자, 불리언) 으로 되어 있더라도, 상황에 따라 필요하다면, `long`(primitive type)으로 **자동으로 알아서 처리** 한다는 의미이다.

- 기본적으로 `null` 을 할당할 수 없게 설계 되어 있다.
  - `null` 이 들어갈 수 있는지 없는지를 애시당초 표기를 해야한다.
  - `null` 을 허용하기 위해서는 타입? 를 작성한다.
  - 아예 다른 타입으로 간주됨을 주의한다.

- 객체를 인스턴스화 할 때, `new` 를 붙이면 안된다.