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

### 소주제 1

_관련된 세부 정보_

## 소주제 2

_관련된 세부 정보_

---

## Study Reflection

### Answers to Preliminary Questions(사전 질문에 대한 답변)

- _질문 1에 대한 응답_
- _질문 2에 대한 응답_
- _질문 3에 대한 응답_

### Evolving Thoughts

_공부를 하면서 바뀐 내 생각 혹은 느낀점을 정리_
