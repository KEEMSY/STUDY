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

fun startsWithA1(str: String?): Boolean { // null 이 들어올 수 있음을 명시한다.
    if(str == null) {
        throw IllegalArgumentException("null 이 들어왔습니다.")
    }
    return str.stratsWith("A")
}

// startsWithA1 을 kotlin 스럽게 바꾼다면?
fun startsWithA11(str: String?): Boolean { // null 이 들어올 수 있음을 명시한다.
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

<br><hr>

## **Kotlin 에서 Type 을 다루는 방법**

```kotlin
val number1 = 3 // Int
val number2 = 3L // Long
val number3 = 3.0f // Float
val number4 = 3.0 // Double
```

기본타입은 다음과 같으며, 코틀린에서는 선언된 기본값을 보고 타입을 추론한다.

- Byte
- Short
- Int
- Long
- Float
- Double
- 부호없는 정수들

<br>

```java
innt number1 = 4;
long number2 = number1; 

System.out.println(number1 + number2); // 계산이 됨: int 타입의 값이 long 타입으로 암시적으로 변경 됨( 더 큰 타입으로 암시적 변경)
```

```kotlin
val number1 = 4
// val number2: Long = number1 // Type missmatch, 컴파일 에러 발생
val number2: Long = number1.toLong()
println(number1 + number2)

val number3 = 3
val number4 = 5
val result = number3 / number4.toDouble() // 실수의 결과를 얻게 함

val number5: Int? = 3 // null 일 수 있음
val number6: Long = number5?.toLong() ? : 0L // null에 대한 처리

```

자바에서 기본 타입간의 변환은 암시적으로 이뤄질 수 있지만, 코틀린 기본 타입간의 변환은 명시적으로 이뤄져야 한다.

- `to변환타입()` 을 사용한다.
- 변수에 `nullable` 하다면 적절한 처리가 필요하다.

<br>

## **타입 캐스팅**

```java
public class Person {

  private final String name;
  private final int age;

  public Person(String name, int age) {
    this.name = name;
    this.age = age;
  }

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }
}
```

```kotlin
/*
 value is Type
 value 가 Type 이면 true
 value 가 Type이 아니라면 false

 value as Type
 value 가 Type 이면 Type 으로 캐스팅
 value 가 Type이 아니라면 예외 발생

 value as? Type
 value 가 Type 이면 Type 으로 캐스팅
 value 가 null 이면 null
 value 가 Type이 아니라면 null
*/

fun printAgeIfPerson(obj: Any) {
    if(obj is Person) { // obj 가 Person 이라면
    // if(obj !is Person)  // obj 가 Person 이 라면
        val person = obj as Person // obs를 Person으로써 간주하여 person 변수에 할당한다. as Person은 생략 가능하다.
        println(person.age)
        // println(obj.age) 가능하며, 이를 스마트 캐스트 라고한다.
    }
}

fun printAgeIfPerson(obj: Any) {
     if(obj !is Person)  // obj 가 Person 이 라면
        val person = obj as Person // obs를 Person으로써 간주하여 person 변수에 할당한다. as Person은 생략 가능하다.
        println(person.age)
    }
}

fun printAgeIfPerson(obj: Any?) {
    if(obj is Person) { // obj 가 Person 이라면
    // if(obj is Person)  // obj 가 Person 이 라면
        val person = obj as? Person // obj 가 nulldl dkslfkaus Person 타입으로 변화 시키고, null 이라면 null 이 된다.
        println(person?.age)
    }
}
```

- 자바의 `instanceof` 는 코틀린의 `is` 에 해당한다.
  - 부정은 `!is` 으로 사용한다.
- 자바의 `(Person)`은 코틀린의 `as Person obj` 에 해당한다.

<br><hr>

## **코틀린의 특이한 타입**

> ### **Any**

- 자바의 `Object` 역할을 한다.(모든 객체의 **최상위 타입**을 의미한다.)
  - 모든 `Primitive Type`의 최상위 타입에도 해당한다.
- `Any` 자체로는 `null` 을 포함할 수 없어 null 을 포함하고 싶다면 Any? 를 사용한다.
- `Any` 에는 `equals` / `hashCode` / `toString` 이 존재한다.

<br>

> ### **Unit**

- 자바의 `void` 와 동일한 역할을 한다.
  - `void` 와는 다르게 `Unit` 은 그 자체로 타입인자로 사용 가능하다.
- 함수형 프로그래밍에서 `Unit` 은 단 하나의 인스턴스만 갖는 타입을 의미한다.
  - 코틀린의 `Unit` 은 **실제 존재하는 타입** 이라는 것을 표현한다.

<br>

> ### **Nothing**

```kotlin
fun fail(message: String): Nothing {
    throw IllegalArgumentException(message)
}
```

- `Nothing` 은 함수가 **정상적으로 끝나지 않았다** 는 사실을 표현하는 역할을 한다.
- **무조건 예외를 반환** 하는 함수 / **무한 루프** 함수 등에서 사용한다.

<br><hr>

## **String interpolation / String indexing**

```kotlin
val person Person("김성연", 100)
val log1 = "사람의 이름은 ${person.name}이고 나이는 ${person.age}세 입니다."

val name = "leo"
val age = 100
val log2 = "이름: $name 나이: $age"
```

- 코틀린에서는 `${변수}` 를 통해 값을 넣는다.
  - **가독성** 및 **일괄변환**, **정규식** 활용 등을 위해 `${변수}` 를 사용하는 것이 좋다.
- `$변수` 로도 사용 가능하다.

<br>

```kotlin
val withoutIndent = 
"""
        ABC
        123
        456
    """.trimIndent()

println(withoutIndent)
```

- `"""(큰따옴표 세개)` 를 적절히 사용하여 문자열을 가공할 때 깔끔한 코딩이 가능하다.

<br>

```java
// 자바에서의 문자열의 특정 문자 가져오기
String str = "ABCDE";
char ch = str.charAt(1);
```

```kotlin
// 코틀린에서의 문자열의 특정 문자 가져오기
val str = "ABCDE"
val ch = str[1]
```

- **문자열에서 문자를 가져올 때** 자바의 배열처럼 `[]` 를 사용한다.

<br><hr>

## **Operator**

### **단항 연산자 / 산술 연산자 / 산술 대입 연산자 모두 Java 와 동일하다.**

- `++`, `--`
- `+`, `-`, `*`, `/`, `%`
- `+=`, `-=`, `/=`, `%=`

### **비교 연산자 는 Java와 동일하나 객체간 비교 시 차이가 존재한다.**
  
- `>`, `<`, `>=`, `<=`

> `동등성(Equality)`: 두 객체의 값이 같은가?

```kotlin
fun main() {
    val money1 = JavaMoney(1_000L)
    val money2 = money1
    val money3 = JavaMoney(1_000L)

    println(money1 === money2) // true
    println(money1 === money3) // false
    println(money1 == money3)  // true // equals 를 호출한다.
}
```

- Java 의 `equals`은 Kotlin 의 `==` 이다.(equals 를 호출한다.)

<br>

> `동일성(Identity)`: 완전히 동일한 객체인가?(주소(래퍼런스)가 같은가?)
- Java 의 `==` 은 Kotlin 의 `===` 이다.
- Kotlin 에서는 객체를 비교할 때, 비교 연산자를 사용하면 자동으로 `compareTo` 를 호출한다.

```java
public class main(String[] args) {
    JavaMoney money1 = new JavaMoney(2_000L);
    JavaMoney money2 = new JavaMoney(1_000L);

    if (money1.compareTo(money2) > 0) {
        System.out.println("Money1이 Money2 보다 금액이 큽니다.");
    }
}
```

```java
public class JavaMoney implements Comparable<JavaMoney> {
    private final long amount;

    public JavaMoney(long amount) {
        this.amount = amount;
    }

    @Override
    public int compareTo(@NotNull JavaMoney o) {
        return Long.compare(this.amount,o.amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JavaMoney javaMoney = (JavaMoney) o;
        return amount == javaMoney.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}

```


```kotlin
fun main() {
    val money1 = JavaMoney(2_000L)
    val money2 = JavaMoney(1_000L)

    if (money1 > money2) { // compareTo 연산을 자동 호출한다.
        println("Money1이 Money2 보다 금액이 큽니다.")
    }
    
}
```

<br>

### **논리 연산자 / 코틀린에만 있는 특이한 연산자**

```kotlin
println(1 in numbers) // numbers 컬렉션 안에 1 이 포함되어 있다.

val str = "ABC"
println(str[2]) // C
```

- 논리 연산자는 Java 와 완전히 동일하다.
- `&&`, `||`, `!`
- Java 처럼 `Lazy 연산`을 수행한다.
- `in`, `!in`: 컬렉션이나 범위에 포함되어있다, 포함되어 있지 않다.
- `a..b`": a 부터 b 까지의 범위 객체를 생성한다.
- `a[i]`: a 에서 특정 Index i 로 값을 가져온다.
- `a[i] = b`: a의 특정 index i에 b를 넣는다.

<br>

### **연산자 오버로딩**

```kotlin
data class Money(
    val amount: Long
) {
    operator fun plus(other: Money): Money {
        return Money(this.amount + other.amount)
    }
}

val money1 = Money(1_000L)
val money2 = Money(2_000L)
println(money1 + money2) // Money(amount=3000)
```

- Kotlin 에서는 객체마다 연산자를 직접 정의할 수 있다.
- 연산자 오버로딩을 어떻게 다룰 수 있는지 공부해두면 좋다.
