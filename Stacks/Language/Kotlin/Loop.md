# **반복문을 다루는 방법**

코틀린의 반복문은 자바와 거의 유사하다.

## **for**

```java
// Java

// for-each(향상된 for 문)
List<Long> numbers = Arrays.asList(1L, 2L, 3L);
for (long number: numbers) {
    System.out.println(number);
}

// 전통적인 for 문
for(int i = 1; i  <= 3; i++) {
    System.out.println(i)
}

for(int i = 3; i  >= 1; i--) {
    System.out.println(i)
}

```

```kotlin
// Kotlin

// for-each
fun forEach() {
    val numbers = listOf(1L, 2L, 3L)
    for (number in numbers) {
        println(number)
    }
}


// 전통적인 for 문
fun traditionalFor() {
    for(i in 1..3) {
        println(i)
    }
}

fun traditionalFor2() {
    for(i in 3 downTo 1) {
        println(i)
    }
}

fun traditionalFor3() {
    for(i in 1..5 step 2) {
        println(i)
    }
}

```

`..`: 범위를 만들어 내는 연산자

- 컬렉션을 만드는 방법에 차이가 존재한다.
- 콜론(`:`) 대신 `in` 을 사용한다.
  - 자바와 동일하게 `Iterable` 이 구현된 타입이라면 모두 들어갈 수 있다.
- `..` 을 통해 범위를 나타낸다.
- 값이 감소하는 경우, `downTo` 를 통해 표햔한다.
- 값이 `n` 씩 올라가는 경우 `step n` 을 사용한다.

<br>

코클린에서 `전통적인 for` 문은 등차수열을 이용한다.

- `IntProgression`(등차수열) 을 `IntRange` 가 구현한 형태이다.
- `1..3`: 1에서 시작하고 3으로 끝나는 등차수열을 만들어라
  - 시작 값: 1
  - 끝 값: 3
  - 공차: 1
- `downTo` 와 `step` 은 **(중위)합수** 이다.

    *중위 함수는 `변수.함수이름(argument)` 대신 `변수 함수이름 argument` 으로 호출 할 수 있게 하는 함수를 말한다.*

<br> <hr>

## **while**

```java
// Java

int i = 1;
while (i <= 3) {
    System.out.println(i);
    i++;
}
```

```kotlin
// Kotlin

var i = 1;
while (i <= 3) {    
    println(i)
    i++
}
```

`while` 문은 자바와 동일하다.

- `do-while` 또한 동일하다.
