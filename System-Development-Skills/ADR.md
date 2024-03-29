# **아키텍처 결정 기록(Architecture Decision Record;ADR)**

소프트웨어 구축에서 **의사결정**은 매우 중요하다. 소프트웨어의 **품질**과 추진하는 비즈니스의 **결과**는 이러한 **결정의 품질**에 달려있기 때문이다. 따라서, `ADR` 을 사용하여 시스템 설계와 구축에 내린 주요 결정을 기록한다.

- 어떤 결정은 당시에는 최선이었겠지만 기술, 사람, 상황이 변하면 좋은 결정이 아니게 될 수 있다.(또는 애초부터 좋은 결정이 아닐수도있다.)
- 어떤 경우든 중요한 결정을 기록하여 시간이 지남에 따라 재평가하고 개선할 수 있는 방법이 필요하다.
- `ADR`은 명확한 결정을 내릴 수 있는 좋은 방법이 될 수 있다.

<br><hr><hr>

## **ADR의 구성**

좋은 결정은 다음과 같은 **네 가지** 중요 요소를 포함한다.

- [콘텍스트](#콘텍스트)
- [대안](#대안)
- [선택](#선택)
- [영향](#영향)

<br>

> ### **콘텍스트**

결정 기록은 다음과 같은 상황에 대한 요약을 제공하여 결정의 근거와 기록의 업데이트가 필요한 이유를 이해할 수 있도록 한다.

- 목표는 무엇인가?
- 해결해야 하는 문제는 무엇인가?
- 제약은 무엇인가?

<br>

> ### **대안**

대안은 결정이 내려진 시점의 상황과 '선택 공간'을 이해하는데 도움이 된다.

- 결정을 내릴 수 있는 선택지가 없다면 좋은 결정이 아니다.
- 좋은 결정은 선택지가 무엇인지 이해하는데 도움되어야 한다.

<br>

> ### **선택**

결정의 핵심은 선택이다.

- 모든 결정 기록은 선택한 사항을 문서화 해야한다.

<br>

> ### **영향**

결정에는 그에 따른 결과가 있으며 중요한 내용은 결정 기록에 문서화되어야 한다. 기록을 할 땐, 다음 항목을 고려하자.

- 장단점은 무엇인가?
- 우리의 선택은 우리가 일하는 방식이나 내려야 할 다른 결정에 어떤 영향을 미치는가?

<br><hr><hr>

## **ADR의 예시**

결정기록은 원하는 방식으로 진행한다. 형식과 도구보다는 내용이 더 중요하다.

<br>

> **경량 아키텍처 결정 기록(Lightweight Architecture Decision Record;LADR)**

`LADR` 형식은 의사 결정 기록을 문서화하는 간결항 방법이다.

- LADR은 소스코드를 관리하느 것과 같은 방식으로 텍스트 파일에 작성된 의사결정 기록을 관리할 수 있다.
- **`상태`**: 결정이 위치한 생명 주기 단계를 작성한다. 동의가 필요한 새로운 결정 초안을 작성하는 경우에는 `제안됨` 상태를, 기존의 결정을 변경하려는 경우 `검토 중` 상태를 , 이미 결정이 내려졌다면 `승인됨` 상태로 설정한다.
- **`콘텍스트`**: 결정을 내리기 위한 문제, 제약, 배경을 설명한다. 중요한 결정을 기록해야하는 배경과 이유를 작성한다. 또는 생각하는 내용을 자유롭게 작성해도 무방하다.

```markdown
# OPM1: 결정을 추적하기 위해 ADR을 사용한다.

## 상태 
승인됨

## 콘텍스트
마이크로 서비스 아키텍처는 복잡하기 때문에 많은 결정이 필요하다.
결정에 대한 결과를 재평가하기 위해 중요한 결정을 추적할 수 있는 방법도 필요하다.
우리는 새로운 소프트웨어를 설치하지 않아도 되는 가벼운 텍스트 기반 솔루션을 원한다.

## 결정
우리는 마이클 나이가드의 경량 아키텍처 결정 기록(LADR)을 사용하기로 결정했다.
LADR은 텍스트 기반이며 우리의 요구사항을 충족시킬 수 있을 만큼 가볍다.
우리는 각 LADR 기록을 자체 텍스트 파일에 보관하고 파일을 코드처럼 관리한다.

또한 다음과 같은 사항의 도입을 고려했다.
* 프로젝트 관리 도구 사용(새로운 도구 설치를 원하지 않았기 때문에 선택하지 않음)
* 비공식 또는 '입소문(word of mouth' 정보 보관(신뢰할 수 없기 때문에 선택하지 않음)

## 결과
* 주요 결정을 위한 결정 기록을 작성한다.
* 결정 기록 파일을 관리하기 위한 소스 코드 관리 솔루션이 필요하다
```
