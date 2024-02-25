# 코드를 제어하는 방법

## **제어문(if) 및 Expression**

```java
// Java

public class Main {
    public static void main(String[] args) {
    }

    private void validateScoreIsNotNegative(int score) {
        if(score < 0) {
            throw new IllegalArgumentException(String.format("%s는 0보다 작을 수 없습니다."), score);
        }
    }

    private String getPassOrFail(int score) {
        if(score >= 50) {
            return "P";
        } else {
            return "F";
        }
    }
    
    // 3항 연산자는 하나의 값으로 취급한다. -> Expression 이면서 Statement
    String grade = score >= 50 ? "P" : "F";

}
```

```kotlin
// Kotlin

fun validateScoreIsNotNegative(score: Int) {
    if(score < 0) {
        throw IllegalArgumentException("${score}는 0보다 작을 수 없습니다.")
    }
}

fun validateScore(score: Int) {
    if(score !in 0..100) {
        throw IllegalArgumentException("score 의 범위는 0부터 100입니다.")
    }
}

// if-else: Expression 에 해당하여 바로 return 할 수 있다.

fun getPassOrFail(score: Int): String {
    return if (score >= 50) {
        "P"
    } else {
        "F"
    }
}
```

`if (조건) {}`

- 함수에서는 `Unit`(Java 의 `void`) 가 생략된다.
- 예외를 던질 때 `new` 를 붙이지 않는다.
- **자바** 에서 `if-else` 는 `StateMent` 이지만, **코틀린** 에서는 `Expression` 이다.

    ```kotlin
    val score = 30 + 40 // Expression 이면서 Statement

    // Expression 이기 때문에 바로 return 이 가능하다.
    fun getPassOrFail(score: Int): String {
        return if (score >= 50) {
            "P"
        } else {
            "F"
        }
    }
    ```

  - 따라서 코클린에서는 `3항 연산자` 가 존재하지 않는다.

    *Statement 는 프로그램의 문장으로 간주되며 하나의 갑승로 도출되지 않는다. 하지만 Expression 은 하나의 값으로 도출된다.(문장이다.) 따라서 Statement 중에 하나의 값으로 도출되는 문장들이 Expression 이다.*

- 어떠한 값이 특정 범위에 포함되어 있는지, 포함되어 있지 않은지를 확인할 때 간단한게 사용할 수 있다.

    ```kotlin
    // 자바에서도 동일하다.
    if(0 <= score && score <= 100) {}

    // 코틀린 문법을 활용
    if(score in 0..100) {}
    ```

<br><hr>

## **switch, when**

```java
// Java

private String getGradeWithSwitch(int score) {
    switch (score / 10) {
        case 9:
            return "A";
        case 8:
            return "B";
        case 7:
            return "C";
        default:
            return "D";
    }
}

private boolean startWithA(Object obj) {
    if (obj isinstanceof String) {
        return ((String) obj).startsWith("A");
    } else {
        return false;
    }
}

private void judgeNumber(int number) {
    if (number == 1 || number == 0 || number == -1) {
        System.out.println("어디서 많이 본 숫자 입니다.");
    } else {
        System.out.println("-1, 0, 1 이 아닙니다.")
    }
}

private void judgeNumber2(int number) {
    if (number == 0) {
        System.out.println("주어진 숫자는 0 입니다.");
        return ;
    } 

    if (number % 2 == 0) {
        System.out.println("주어진 숫자는 짝수 입니다.");
    }
    System.out.println("주어진 숫자는 홀수 입니다.");
}

```

```kotlin
// Kotlin

/*
when (값) {  // 값이 없을 수 도 있음
    조건부 -> 어떠한 구문 // 조건부에는 어떠한 Expression 이라도 들어갈 수 있으며, 여러개의 조건을 동시에 검사 할 수 있다.
    조건부 -> 어떠한 구문
    else -> 어떠한 구문
}
*/

// when 또한 expression에 속한다.
fun getGradeWithSwitch(score: Int): String {
    return when (score / 10) {
        9 -> "A"
        8 -> "B"
        7 -> "C"
        else -> "D"
    }
}

// 코틀린의 when 은 다양한 조건을 가지고 분기를 칠 수 있다.

fun getGradeWithSwitch(score: Int): String {
    return when (score) {
        in 90..99 -> "A"
        in 80..89 -> "B"
        in 79..79 -> "C"
        else -> "D"
    }
}

// 조건부에는 어떠한 Expression 이라면 들어올 수 있다.
fun startsWithA(obj: Any): Boolean {
    return when (obj) {
        is String -> obj.startsWith("A")  // 스마트 캐스팅
        else -> false
    } 
}

// 조건부에서 여러 조건을 동시에 검사 할 수 있다.
fun judgeNumber(number: Int) {
    when (number) {
        1, 0, -1 -> println("어디서 많이 본 숫자 입니다.")
        else -> println("-1, 0, 1 이 아닙니다.")
    }
}

fun judgeNumber2(number: Int) {
    when {
        number == 0 -> println("주어진 숫자는 0 입니다.")
        number % 2 -> println("주어진 숫자는 짝수 입니다.")
        else -> println("주어진 숫자는 홀수 입니다.")
    }
}

```

- 자바의 switch 가 사라지고 when 이 생겼다.
- 자바에서의 case 대신 `->`를 이용한다.
- 코틀린의 when 은 특정한 값일 경우에 분기를 나눌 수 도 있지만, 이외에도 다양한 조건으로 분기를 나눌 수 있다.
  - `Enum` class 혹은 `Sealed` Class 와 함께 사용할 경우, 더욱 더 좋다.
