# Study Log: QueryDSL

## Background Knowledge(배경지식)

나는 `Spring` 을 공부하면서, 영속성 제어를 위해 `JPA` 를 사용했다. `JPA` 에서 제공하는 메서드를 사용함으로써 간단한  CRUD 를` `제어할 수 있었고, 유용했다. 하지만 `JPA` 에서 제공하는 메서드만을 활용하여 개발하는 경우는 드물었다. 복잡한 쿼리를 작성해야 할 경우, `JPA` 를 활용하여 작성하기는 어려웠고, `Native Query` 를 고민해야했다. 

```java
User findbyNameContains(String name);
```

`Native Query` 의 경우, 문자열로 작성을 해야했는데, 이는 가독성 측면과 코드 작성 간 발생하는 문제를 미리 식별할 수 없다는 큰 불편함을 주었다.

> 내가 생각하는 `Native Query` 의 주요 문제점

- 가독성이 떨어진다.
- 문자열로 작성하기 때문에, 오타가 발생하기 쉬우며 이를 찾아내기 어렵다.


```java
@Query(value = "SELECT id, title, user_id FROM article WHERE user_id IN (SELECT id FROM user WHERE level > :level)", nativeQuery = true)
List<Article> findByLevel(String level);
```

나는 `ORM` 을 활용하여 이 문제를 해결하고 싶었다. 그리고 이를 해결할 수 있는 도구가 `QueryDSL` 임을 알게되었다.

<br>

### Preliminary Questions(사전질문)

- `QueryDSL` 을 사용하는 이유는 무엇인가? 기존 도구들과 비교하여 어떤 장단점이 존재하는가?
- `QueryDSL` 을 사용하기 위해서는 어떤 환경 설정이 필요한가?
- 이와 유사한 다른 기술에는 어떤 기술이 존재하는가?

---

## Contents

### QueryDSL 는 무엇이고 사용하는 이유는 뭘까?

`Querydsl` 은 `JPA`, `MongoDB` 및 `Java SQL`을 포함한 여러 백엔드에 대해 유형이 안전한 SQL 유사 쿼리를 생성할 수 있는 프레임워크 이다.  잘 와닿지 않는다면 간략하게 `Querydsl` 은 자바 코드 기반으로 쿼리를 작성하게 해준다”라고 생각해도 좋다.

- [Official Document](http://querydsl.com/)
- [Github](https://github.com/querydsl/querydsl)

일반적으로 JPA 를 사용하여 쿼리를 작성하는 경우, 명세만 잘 정의하면 문제없이 사용할 수 있다.

```java
User findbyNameContains(String name);
```

그러나 실제 작업에서는 간단한 쿼리만 필요하지 않다. 복잡한 쿼리가 필요한 경우 JPA 자체 제공 메서드만 으로는 해결하기 어렵기 때문에, `Native Query(Raw Query)` 의 사용을 고려하게된다.

```java
@Query(value = "SELECT id, title, user_id FROM article WHERE user_id IN (SELECT id FROM user WHERE level > :level)", nativeQuery = true)
List<Article> findByLevel(String level);
```

`Native Query` 의 문제점은 무엇일까? 크게 두가지를 이야기 할 수 있다.

- 가독성이 떨어진다.
- 문자열로 작성하기 때문에, 버그가 발생하기 쉬우면서 이를 찾아내기 어렵다.


이 때 `QueryDSL` 가 해결책이 될 수 있다. `QueryDSL` 을 사용하여 해당 메서드를 작성한다면 다음과 같다.

```java
public List<Article> findByUserLevel(String level) {
    QArticle article = QArticle.article;
    QUser user = QUser.user;

    return queryFactory.selectFrom(article)
        .where(
            article.userId.in(
                JPAExpressions
                    .select(user.id)
                    .from(user)
                    .where(user.level.gt(level))
            )
        )
        .fetch();
}
```

코드의 양은 늘어났지만, 코드의 `가독성` 은 개선되었고, 실행 시점 이전에 잘못된 `버그`를 컴파일 오류를 통해 사전에 방지할 수 있다. 즉, `Native Query` 에서의 불편함을 개선할 수 있는 방법이다.

<br><hr>

## QueryDSL 을 사용하기위한 설정

`QueryDSL` 을 사용하기 위해 설정하는 방법은, `build.gradle` 내 의존성을 추가하고, `QueryDSLConfig` 를 생성함으로써 사용할 수 있다. 그러나` Spring Boot` 의 버전이 2.X 인지, 3.X 인지에 따라 `build.gradle` 설정이 달라짐을 주의 해야 한다.


### 공통 설정 

> `QuerydslConfiguration.java`

```java
@Configuration  
public class QuerydslConfiguration {  
    @PersistenceContext  
    private EntityManager entityManager;  
    @Bean  
    public JPAQueryFactory jpaQueryFactory() {  
        return new JPAQueryFactory(entityManager);  
    }  
}
```

### Spring Boot 2.X 에서의 설정 방법

```groovy
buildscript {  
    ext {  
        queryDslVersion = "5.0.0"  
    }  
}

plugins {  
    id 'org.springframework.boot' version '2.6.8'  
    id 'io.spring.dependency-management' version '1.0.11.RELEASE'  
    id 'java'  
    id "com.ewerk.gradle.plugins.querydsl" version "1.0.10"  
}

dependencies {
	.
	.
	.
	// ---------- QueryDsl START ----------  
	implementation "com.querydsl:querydsl-jpa:${queryDslVersion}"  
	implementation "com.querydsl:querydsl-apt:${queryDslVersion}"
	// ---------- QueryDsl END   ----------  

	.
	.
	.
}


// ---------- QueryDsl START ----------  
def querydslDir = "$buildDir/generated/'querydsl'"  
  
// JPA 사용여부 및 사용 경로 설정  
querydsl {  
    jpa = true  
    querydslSourcesDir = querydslDir  
}  
  
// build 시 사용할 sourceSet 추가 설정  
sourceSets {  
    main.java.srcDir querydslDir  
}  
  
// querydsl 컴파일 시 사용할 옵션 설정  
compileQuerydsl {  
    options.annotationProcessorPath = configurations.querydsl  
}  
  
// querydsl 이 compileClassPath 를 상속하도록 설정  
configurations {  
    compileOnly {  
        extendsFrom annotationProcessor  
    }  
    querydsl.extendsFrom compileClasspath  
}
// ---------- QueryDsl END   ----------  

```

### Spring Boot 3.X 에서의 설정 방법

> `build.gradle`

```groovy
buildscript {  
    ext {  
        springBootVersion = '3.1.5'  
        queryDslVersion = '5.0.0'  
        mysqlConnectorVersion = '8.0.23'  
    }  
  
    repositories {  
        mavenCentral()  
    }  
  
    dependencies {  
        classpath "org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}"  
    }  
}

dependencies {  
	.
	.
	.
        // ---------- QueryDsl START ----------  
        implementation 'com.querydsl:querydsl-jpa:5.0.0:jakarta'  
        annotationProcessor "com.querydsl:querydsl-apt:5.0.0:jakarta"  
        annotationProcessor "jakarta.annotation:jakarta.annotation-api"  
        annotationProcessor "jakarta.persistence:jakarta.persistence-api"  
        // ---------- QueryDsl END   ----------  
        .
        .
        .
}

// ---------- QueryDsl START ----------  
// QueryDsl 빌드 옵션 (선택)
def querydslDir = "$buildDir/generated/querydsl"  
  
sourceSets {  
    main.java.srcDirs += [ querydslDir ]  
}  
  
tasks.withType(JavaCompile) {  
    options.annotationProcessorGeneratedSourcesDirectory = file(querydslDir)  
}  
  
clean.doLast {  
    file(querydslDir).deleteDir()  
}  
// === ⭐ QueryDsl 빌드 옵션 (선택)  END ===
```

---

## Study Reflection

### Answers to Preliminary Questions(사전 질문에 대한 답변)

- _질문 1에 대한 응답_
- _질문 2에 대한 응답_
- _질문 3에 대한 응답_

### Evolving Thoughts

_공부를 하면서 바뀐 내 생각 혹은 느낀점을 정리_
