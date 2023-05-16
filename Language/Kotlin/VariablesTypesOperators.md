# Variables, types, and operators

## **변수,Variables**

> ### **Java**

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

<br>

> ### **Kotlin**

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

<br><hr>

## **Kotlin 에서의 null 을 다루는 방법**

> ### **Java**

```java
public class Lec02Main {

  public static void main(String[] args) {

  }

  public boolean startsWithA1(String str) {
    if (str == null) {
      throw new IllegalArgumentException("null이 들어왔습니다");
    }
    return str.startsWith("A");
  }


  public Boolean startsWithA2(String str) {
    if (str == null) {
      return null;
    }
    return str.startsWith("A");
  }


  public boolean startsWithA3(String str) {
    if (str == null) {
      return false;
    }
    return str.startsWith("A");
  }

}
```

<br>

> ### **Kotlin**

```kotlin
fun main() {
    var str: String? = "ABC"
    // str.length // 불가능
    str?.length // 가능, safe call

    val str1: String? = "ABC"
    str?.length ?: 0 // elvis 연산자
}

fun startsWirthA1(str: String?): Boolean { // null 이 들어올 수 있음을 명시한다.
    if(str == null) {
        throw IllegalArgumentException("null 이 들어왔습니다.")
    }
    return str.stratsWith("A")
}

// startsWirthA1 을 kotlin 스럽게 바꾼다면?
fun startsWirthA11(str: String?): Boolean { // null 이 들어올 수 있음을 명시한다.
    return str?.startsWith("A")ㅋ
      ?: throw IllegalArgumentException("null 이 들어왔습니다.")
}


fun startsWithA2(str: String?): Boolean? { // null 이 들어올 수 있고, 반환값에 null 이 가능함을 명시한다.
    if(str == null) {
        return null
    }
    return str.stratsWith("A")
}

// startsWirthA2 을 kotlin 스럽게 바꾼다면?
fun startsWithA2(str: String?): Boolean? { // null 이 들어올 수 있고, 반환값에 null 이 가능함을 명시한다.
    return str?.stratsWith("A")
}


fun startsWithA3(str: String?): Boolean { 
    if(str == null) {
        return false
    }
    return return str.stratsWith("A")
}

// startsWirthA3 을 kotlin 스럽게 바꾼다면?
fun startsWithA3(str: String?): Boolean {
    return return str?.stratsWith("A")?: false
}

fun startsWithA4(str: String): Boolean {
    return str!!.startsWith("A")  // null일 수도 있지만, 어떠한 경우에도 null이 들어올 수 없다.
}
```

코틀린에서는 `null` 이 가능한 타입을 완전히 다르게 취급하며, `null` 이 가능한 타입만을 위한 기능이 존재한다.

- 한번 `null` 검사를 하면 `non-null` 임을 컴파일러가 알 수 있다.
- `Safe Call`: `null` 이 아니면 실행하고, `null` 이 아니면 실행하지 않는다.(그대로 `null`이 된다.)
- `Elvis Operator`: 앞의 연산결과가 `null` 이면 뒤의 값을 사용한다.

<br>

`nullable type` 이지만, 아무리 생각해도 `null` 이 될 수 없는 경우 `!!` 을 사용한다.

- DB에 처음 넣을 때는 `null` 이지만, 한번 모종의 업데이트 이후에는 절대 `null` 이 될 수 없는 경우 등..
- 아무리 그래도 혹시나 `null` 이 들어온다면 `NPE` 가 발생하기 때문에 정말 `null` 이 아닌것이 확실한 경우에만 `null` 아님 단언을 사용한다.

<br>

### **코틀린에서 자바 코드를 가져다 사용할 경우 해당 타입이 `null` 이 될 수 있는지 없는지 확인하는 방법**

> ### **Java**

```java
package com.lannstark.lec02;

import org.jetbrains.annotations.Nullable;

public class Person {

  private final String name;

  public Person(String name) {
    this.name = name;
  }
  
  @Nullable  // 해당 주석이 없을 경우, 코틀린에서 사용할 경우, runtime 시 Exception 이 발생할 수 있다.
  public String getName() {
    return name;
  }
}
```

> ### **Kotlin**

```kotlin
fun main() {
    val person = Person("공부하는 김성연")
    startsWithA(person.name) // @Nullable 이 있을경우 에러발생
}

fun startsWithA(str: String): Boolean {
    return  str,startsWith("A")
}
```

코틀린에서 자바코드를 가져다 쓸 경우, `null` 에 대한 `Annotation` 정보를 이해한다.

- `javx.annotation` 패키지
- `android.support.annotation` 패키지
- `org.jetbrains.annotation` 패키지

`@Nullable` 이 없다면 코틀린에서는 해당 값이 `nullable` 인지 `non-nullable` 인지 알 수가 없다.

- 플랫폼 타입은 코틀린이 `null` 관련 정보를 알수없는 타입을 말한다.
- 이경우 `runtime` 시 `Exception` 이 발생할 수 있다.
- 따라서 코틀린에서 자바 관련 코드를 사용할 때에는 , `null` 관련 코드를 좀 더 꼼꼼하게 작성하고 확인한다.(`null` 가능성을 확인한다.)
  - 최초 코틀린에서 자바 라이브러리를 가져다 쓴 지점을 랩핑해서 단일 지점으로 만들어 추후 이슈가 발생할 경우 좀 더 쉽게 대응할 수 있도록 한다.