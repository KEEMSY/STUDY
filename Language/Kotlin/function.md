# **함수를 다루는 방법**

## **함수 선언 문법**

```java
// Java

public int max(int a, int b) {
    if (a > b) {
        return a;
    } 
    return b;
}
```

```kotlin
// Kotlin

fun max(a: Int, b: Int): Int =
     if (a > b) {
        a
    } else {
        b
    }

// 반환타입 생략 가능(어떤 경우 건, Int 타입을 반환하기 때문에 추론됨) 단 생략하기 위해서는 함수를 작성할 때 중괄호 대신 = 을 써야한다.
// body가 하나의 값으로 간주되는 경우 block 을 없앨 수 있으며, block 이 없다면 반환 타입을 업앨 수도 있다.
// 조건분의 중괄호 생략가능
fun improvedMax(a: Int, b: Int) =  if (a > b) a else b 

```

- `public` 은 접근 지시어로, `public` 은 생략 가능하다.
- `fun` 은 함수를 의미하는 키워드이다.
- 함수는 클래스 안에 있을 수도, 파일 최상단에 존재할 수도 있다.
  - 한 파일 안에 여러 함수들이 존재할 수 있다.
- `body` 가 하나의 값으로 간주되는 경우 `block` 을 없앨 수 있으며, `block` 이 없다면 반환 타입을 없앨 수도 있다.
- 매개변수는 `매개변수: 매개변수 타입` 으로 선언한다.
- 반환타입은 `Unit`(자바의 `void`) 일경우 생략 가능하다.
  - `block {}` 을 사용하는 경우, 반환 타입이 `Unit` 이 아니라면, 반환 타입을 명시적으로 작성해주어야한다.
  - `=` 을 사용할 경우 **타입 추론이 가능** 하다.

<br><hr>

## **Default Parameter**

```java
// Java


public void repeat(String str, int num, boolean useNewLine) {
    for (int i = 1; i <= num; i++) {
        if (useNewLine) {
            System.out.println(str);
        } else {
            System.out.print(str);        }
    }
}

// useNewLine 이 자주 true 로 사용될 경우, 오버로딩을 활용한다.
public void repeat(String str, int num) {
    repeat(str, num, true);
}
```

```kotlin
// Kotlin

fun main() {
    repeat("Hello World", useNewLine=false) // named argument
}

// 기본 값(default)을 지정할 수 있다.
fun repeat(
    str: String,
    num: Int = 3, 
    useNewLine: Boolean = true
) {
    for (i in 1..num) {
        if (useNewLine) {
            println(str)
        } else {
            print(str)
        }
    }
}
```

- 코틀린에서는 `기본값(default)` 값을 지정할 수 있다.
  - 사용 시, 파라미터를 넣어주지 않으면 기본 값을 사용한다.
  - 함수를 **호출하는 쪽** 에서 **매개변수 이름을 직접 지정하여 파라미터를 사용할 수 있다.**(`named argument`)
    - builder 를 직접 만들지 않고 builder 의 장점을 갖을 수 있다.
    - 단, 코틀린에서 자바 함수를 가져다 사용할 경우 `named argument` 를 사용할 수 없다.
- 코틀린에도 자바와 동일하게 `오버로드` 기능이 존재한다.

<br><hr>

## **같은 타입의 여러 파라미터 받기(가변인자)**

```java
// Java

public static void printAll(String... strings) { // 타입... 을 사용하면 가변인자를 사용한더는 의미이다.
    for (String str : strings) {
        System.out.println(str);
    }
}

// 해당 코드를 사용하는 경우
String[] array = new String[]{"A", "B", "C"};
printAll(array);
printAll("A", "B", "C");
```

```kotlin
// Kotlin

fun main() {
    printAll("A", "B", "C")

    val array = arrayOf("A", "B", "C")
    printAll(*array) // 바로 배열을 넣어줄 경우 spread 연산자 * 를 사용한다.
}

fun printALl(vararg strings: String) {
    for (str in strings) {
        println(str)
    }
}
```

`가변인자` 함수를 만들경우 `vararg` 키워드를 사용한다.

- `가변인자` 함수를 배열과 호출할 때는 `*(spread 연산자)` 를 사용해야 한다.
