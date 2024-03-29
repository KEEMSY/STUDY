# 가능한 단순하게 유지하라, Keep It Short and Simple(KISS)

`KISS` 에 대해 알아보기 전에, 다음 질문에 스스로 답을 해보는 것이 좋다.

- `KISS` 원칙에서 `단순한` 이라는 단어가 가지는 의미는 무엇일까?
- 어떤 종류의 코드를 단순한 코드라고 할 수 있을까?
- 복잡한 코드는 어떤 코드인가?
- 간단한 코드를 작성하려면 어떻게 하면 좋을까?
- `YAGNI` 원칙은 `KISS` 원칙과 어떤 점이 다른가?

<br><hr>

## KISS 원칙 정의와 해석

`KISS` 원칙은 `가능한 단순하게 유지하라` 는 원칙이며, 많은 상황에 적용될 수 있는 포괄적인 설계 원칙이다.

- `Keep It Simple and Stupid`, `Keep It Short and Simple`, `Keep it Simple and Straight forward` 등 다양하게 이야기 될 수 있다.
- 소프트웨어 개발 뿐만아니라, 시스템 설계와 제품설계에서도 많이 사용된다.

<br>

`KISS` 원칙은 코드를 읽고 유지관리 할 수 있도록 해주는 중요한 수단이다. 코드가 매우 간단하기 때문에 읽기 쉬울 뿐만아니라 버그를 찾아내기도 쉽다. 따라서 버그가 있더라도 비교적 쉽게 수정이 가능하다.

- 코드 품질을 측정하는 중요한 기준은 코드의 `가독성`과 `유지 보수성` 이라는 것을 감안할 때, KISS 원칙은 유용하다.

<br>

`KISS` 원칙에서 말하는 코드를 `단순` 하게 유지라는 것은 어떤 종류가 단순한 코드인지 명확하지 않다. 또한 이런 코드를 어떻게 개발해야하는지에 대한 방법도 구체적인 예시가 없다.

- 적은 줄 수의 코드가 더 간단하지 않다.
- 복잡한 코드가 반드시 `KISS` 원칙을 위반하는 것은 아니다.

<br><hr>

## KISS 원칙을 만족하는 코드 작성 방법

- 복잡한 정규표현식, 프로그래밍 언어에서 제공하는 지나치게 높은 레벨의 코드 등 지나치게 복잡한 기술을 사용하여 코드를 구현하지 않는다.
- '바퀴를 다시 발명' 하는 대신 기존의 라이브러리를 사용하는 것을 고려한다.
	- 라이브러리의 기능을 직접 구현하면 버그가 발생할 확률이 높아지고, 유지 관리 비용도 함께 증가한다.
- 과도하게 최적화 하지 않는다. 코드를 최적화 하기 위해 산술 연산 대신 비트 연산을 사용하거나, if-else 대신 복잡한 조건문을 사용하는 것을 최소화 한다.


<br><hr>

## YAGNI 원칙과 KISS 원칙의 차이

`YAGNI`(You Ain't Gonna Need It) 원칙을 소프트웨어 개발에 적용하면, `현재 사용하지 않는 기능을 설계하지 말고 현재 사용되지 않는 코드를 작성하지 않는다.`(`정말 필요할 때까지 그 기능을 만들지 말라`) 를 의미하게 된다.

- `YAGNI` 은 `과도하게 설계하지 말라` 는 의미를 갖으며, `KISS` 원칙과 마찬가지로, 포괄적인 설계 원칙이다.
- 추후에 사용될 코드를 고려하여 미리 작성하지 않는다 라고 이해할 수 있으나, 이것이 코드의 확장성을 고려할 필요가 없다는 뜻은 아니다.
- `KISS` 원칙은 가능한 간단하게 유지하라는 방법 에 관한 것이며, `YAGNI` 원칙은 현재 필요하지 않은 것을 미리 하지 말라는 금지 에 관한 것이다.
<br>

> YAGNI 를 위반하는 경우

- 프로젝트에 현재 필요하지 않은 개발 패키지를 미리 도입하는 경우
- 현재 프로젝트에서는 이미 충분한 기능을 갖고 있음에도 불구하고, 특정 라이브러리를 사용하려는 경우
