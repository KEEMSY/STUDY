# **클래스를 다루는 방법**

## **클래스와 프로퍼티**

```kotlin
// Kotlin 
class Person constructor(name: String, age: Int{
    
    val name = name
    var age = age
}

// 코틀린 스럽게 작성하기
class Person(val name: String, var age: Int)  // body에 아무 내용도 없다면 생략 가능하다.

```

- 코틀린에서는 필드만 만들면, `getter`, `setter` 를 자동으로 만들어 준다.
- 클래스의 필드 선언과 생성자를 동시에 선언할 수 있다.
  - `.필드` 를 통해 getter 와 setter 를 바로 호출한다.(Java 클래스에 대해서도 `.필드`를 통해 getter 와 setter를 사용한다.)

*프로퍼티 = 필드 + getter + setter 를 의미한다.*

<br><hr>

## **생성자와 init**

```kotlin
fun main() {
    Person() // 두번째 부생성자 호출
}

class Person(val name: String, var age: Int) { // 주 생성자(primary constructor) 부분: 반드시 존재해야 한다. 단, 주 생성자에 파라미터가 하나도 없으면 생략 가능하다.
    
    init { 
        if (age < 0) {
            throw IllegalArgumentException("나이는 ${age} 일 수 없습니다.")
        }
        println("순서 1, 초기화 블록")
    }

    constructor(name: String): this(name, 1) { // 2번째, 3번째 생성자는 contructor 를 사용해야 한다. this 를 통해 다른 생성자를 호출한다. 
        println("순서 2, 부생성자1")
    }

    constructor(): this("keemsy") {
        println("순서 3, 부생성자 2")
    }
}

// 코틀린 스럽게 작성하기: default parameter 사용하기

class Person(
    val name: String = "keemsy",
    var age: Int = 1,
) {
    
    init {
        if (age < 0) {
            throw IllegalArgumentException("나이는 ${age} 일 수 없습니다.")
        }
    }
}

```

- `init(초기화)`블록은 생성자가 호출되는 시점에 호출된다.
  
  클래스가 초기화 되는 시점에 한번 호출되는 블록을 말한다.

  클래스가 생성되는 시점에 검증로직을 추가하고 싶을 경우 사용하면 된다.

- 생성자를 추가하고 싶은 경우 `constructor(파라미터)` 를 통해 생성자를 추가할 수 있다.

  `constructor` 는 `부 생성자(secondary constructor)`로, 생성자를 추가적으로 만들고 싶을 때 만드므로, 있을수도 있고 없을 수도 있다.

  또한 부 생성자는 최종적으로 주 생성자를 `this` 로 호출해야 하며, `body` 를 가질 수 있다.

- `default parameter` 를 사용하는 것이 좋다. 하지만, Converting 와 같은 경우 부생성자를 사용할 수 있으나, `정적 팩토리 메소드`를 사용하는 것이 더 좋다.

<br><hr>

## **커스텀 getter, setter**

```kotlin
// 성인을 확인하는 함수

  fun isAdult(): Boolean {
    return this.age >= 20
  }

// 코틀린 스럽게 작성하기: 커스텀 getter
  val isAdult(): Boolean 
    get() = this.age >= 20

  val isAdult(): Boolean 
    get{
      return this.age >= 20
    }

// name 을 get 할때 무조건 대문자로 바꿀 경우
class Person(
    name: String = "keemsy", // 커스텀 getter 를 사용할 것이기 때문에 기존의 val 을 제거한다.
    var age: Int = 1,
) {
    
    // 커스텀 getter 정의, backing field 를 활용
    val name = name
      get() = field.uppercase()

    // backing field 를 사용하기 보다는 새로운 커스텀 getter(프로퍼티)를 만들어 사용하는 경우가 더 많음
    val uppercaseName: String
      get() = this.name.uppercase()


    init {
        if (age < 0) {
            throw IllegalArgumentException("나이는 ${age} 일 수 없습니다.")
        }
    }

    val isAdult(): Boolean 
      get() = this.age >= 20

}

```

- `커스텀 getter`: Person 객체에 새로운 `프로퍼티`를 보여주는 것
  
  자기 자신을 변형할 수도 있다.(`val name 부분 참고`)

    - `주 생성자` 에서 받은 `name` 을 `불변 프로퍼티(val)` `name` 에 바로 대입한다.
    - `name` 에 대한 `커스텀 getter` 를 만들 때 `field` 를 사용한다.
      - `(backing)field` 는 `무한루프`를 막기 위한 예약어로, 자기 자신을 가리킨다.

- `커스텀 setter`

  ```kotlin
    name: String // val 제거
  )
    var name = name 
      set(value) {
          field = value.uppercase()
      }
  ```

  `setter` 자체를 `지양`하기 때문에 커스텀 setter 사용을 거의 하지 않는다.

- 모두 동일한 기능이며, 표현방법만 다르다.(디컴파일을 통해 확인 가능)
- `객체의 속성`이라면 `custom getter` 를, 그렇지 않다면 함수를 사용하는 것이 좋다.


<br><hr>

## **정리**

```java
// 기준 Java 클래스
public class JavaPerson {

  private final String name;  // final 필드는 한번 정해지면 다시 바꿀 수 없다.
  private int age;

  public JavaPerson(String name, int age) {
    if (age <= 0) {
      throw new IllegalArgumentException(String.format("나이는 %s일 수 없습니다", age));
    }
    this.name = name;
    this.age = age;
  }

  public JavaPerson(String name) {
    this(name, 1);
  }

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public boolean isAdult() {
    return this.age >= 20;
  }
}
```

```kotlin
class Person(
    val name: String = "keemsy", // 커스텀 getter(혹은 setter) 를 사용할 경우  val 을 제거한다.
    var age: Int = 1,
) {
    
    // 커스텀 getter 정의를 사용할 때에는 backing field 를 활용한다.
    val name = name
      get() = field.uppercase()

    // 커스텀 setter 를 사용할 경우 var 을 선언하는 것을 잊지 말자.
    var name = name 
      set(value) {
          field = value.uppercase()
      }

    // backing field 를 사용하기 보다는 새로운 커스텀 getter(프로퍼티)를 만들어 사용하는 경우가 더 많다.
    val uppercaseName: String
      get() = this.name.uppercase()


    init {
        if (age < 0) {
            throw IllegalArgumentException("나이는 ${age} 일 수 없습니다.")
        }
    }

    // 커스텀 getter 활용
    val isAdult(): Boolean 
      get() = this.age >= 20

}
```

- 코틀린에서는 필드를 만들면 `getter` 와 (필요에 따라) `setter` 가 자동으로 생긴다.
  - 이 때문에 `프로퍼티`라고 부른다.
- 코틀린에서는 `주생성자`가 필수이다.
- 코틀린에서는 `constructor` 키워드를 통해 `부생성자` 를 추가로 만들 수 있다.
  - 단 `default parameter` 나 `정적 팩토리`를 사용하는 것이 더 좋다.
- `커스텀 getter` 와 `커스텀 setter` 를 만들 수 있다.
  - 커스텀 getter, setter 에서는 `무한루프` 를 막기 위해 `field` 키워드를 사용한다.
    - 이를 `backing field` 라고 한다.
