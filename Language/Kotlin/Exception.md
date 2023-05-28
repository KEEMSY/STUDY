# **예외를 다루는 방법**

## **try catch finally**


```java
// Java

priavte int parseIntOrThrow(@NotNull String str) {
    try {
        return Integer.parseInt(str);
    } catch (NumberFormatException e) {
        throw new IllegalArgumentException(String.format("주어진 %s 는 숫자가 아닙니다."), str);
    }
}

priavte int parseIntOrThrow2(@NotNull String str) {
    try {
        return Integer.parseInt(str);
    } catch (NumberFormatException e) {
        return null;
    }
}
```

```kotlin
// Kotlin

fun parseIntOrThrow(str: String): Int {
    try {
        return str.toInt()
    } catch (e: NumberFormatException) {
        throw IllegalArgumentException("주어진 ${str} 는 숫자가 아닙니다.")
    }
}

fun parseIntOrThrow2(str: String): Int? {
    return try {
        str.toInt()
    } catch (e: NumberFormatException) {
        null
    }
}
```

코틀린에서 `try-catch-finally` 의 문법은 자바와 완전히 동일하다.

- 기본 타입간의 형변환은 `toType()` 을 사용한다.
- 타입이 뒤에 위치하며, `new` 를 사용하지 않는다.
- `포매팅` 이 간결하다.
- `try-catch-finally` 는 하나의 `Expression` 으로 간주된다.

<br><hr>

## **Checked Exception 과 Uncheckecd Exception**

```java
// Java

public class JavaFilePrinter {
    public void readFile() throws IOException {
        File currentFile = new File(".");
        File file = new File(currentFile.getAbsolutePath() + "/a.txt)");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        System.out.println(reader.readLine());
        reader.close();
    }
    
}
```

```kotlin
// Kotlin

class FilePrinter {
    val currentFile = File(".")
    val file = File(currentFile.absolutePath + "a.txt")
    val reader = BufferedReader(FileReader(file))
    println(reader.readLine())
    reader.close()
}
```

코틀린에서는 `Checked Exception` 과 `Unchecked Exception` 을 구분하지 않는다.

- 모두 `Unchecked Exception` 에 해당한다.(throws 를 볼일이 거의 없다.)

<br><hr>

## **try with resources**

```java
// Java

public void readFile(String path) throw IOException {
    // try 와 괄호가 생겨 괄호 안에 외부 자원을 만들어주고 try가 끝나면 자동으로 외부 자원을 닫아준다.
    try (BufferedReader reader = new BufferedReader(new FileReader(path))) { 
        System.out.println(reader.readLine());
    }
}
```

```kotlin
// Kotlin

fun readFile(path: String) {
    BufferedReader(FileReader(path)).use { reader ->
        println(reader.readLine())
    }
}
```

코틀린에는 `try with resources` 구문이 **없**고, `uss` 를 사용한다.

- `use` 는 `inline 확장함수` 를 이야기한다.