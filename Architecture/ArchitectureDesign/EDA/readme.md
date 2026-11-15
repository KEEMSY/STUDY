# EDA (Event-Driven Architecture)

서비스가 서로를 직접 호출하지 않고 상태 변화를 이벤트로 발행하고 구독해서 협력하는 아키텍처 스타일이다.

MSA에서 서비스를 나누면 서비스 간 결합과 데이터 정합성 문제가 생긴다. EDA는 이 문제를 비동기 이벤트로 푸는 방법이다. 이 폴더에서는 이벤트를 쓰면 무엇이 느슨해지고 대신 무엇이 어려워지는지를 기록한다.

<br>

## 학습 순서

| 순서 | 주제 | 핵심 질문 | 상태 |
|------|------|-----------|------|
| 01 | [이벤트 기초](./01-event-basics.md) | Event, Command, Message는 어떻게 다른가? | [ ] |
| 02 | [이벤트 활용 패턴](./02-event-patterns.md) | 이벤트는 어떤 목적으로 쓰이는가? (Fowler의 4가지 분류) | [ ] |
| 03 | [메시지 브로커](./03-broker.md) | Pub/Sub와 Queue는 무엇이 다르고, Kafka는 어떻게 동작하는가? | [ ] |
| 04 | [전달 보장](./04-delivery-semantics.md) | 메시지가 중복되거나 순서가 바뀌면 어떻게 대응하는가? | [ ] |
| 05 | [Transactional Outbox](./05-outbox.md) | DB 저장과 이벤트 발행을 어떻게 원자적으로 묶는가? | [ ] |
| 06 | [Event Sourcing](./06-event-sourcing.md) | 상태 대신 이벤트를 저장하면 무엇이 달라지는가? | [ ] |
| 07 | [CQRS](./07-cqrs.md) | 쓰기 모델과 읽기 모델을 왜 나누는가? | [ ] |
| 08 | [스키마 진화](./08-schema-evolution.md) | 이벤트 형식이 바뀌면 기존 구독자는 어떻게 되는가? | [ ] |

<br>

## 노트 작성 방식

[MSA 노트 작성 방식](../MSA/readme.md#노트-작성-방식)과 같다. 주제 하나에 노트 하나를 쓰고 노트 하나에 커밋 하나를 한다.

<br>

## 관련 문서

- [MSA](../MSA/readme.md): 서비스 간 통신(03), 데이터 관리(04), Saga(05)와 이어진다
- [Pipes and Filters](../PipesAndFiltersArchitecture.md): 데이터 흐름을 단계로 나누는 또 다른 스타일
- [Observer 패턴](../../CodeDesign/GoF/BehavioralPatterns/Observer/): 객체 수준의 발행/구독

<br>

## 참고 자료

- Martin Fowler, [What do you mean by "Event-Driven"?](https://martinfowler.com/articles/201701/event-driven.html) (2017)
- Chris Richardson, 『Microservices Patterns』 3장(프로세스 간 통신), 4장(Saga), 6장(Event Sourcing), 7장(CQRS)
- Martin Kleppmann, 『Designing Data-Intensive Applications』 11장(스트림 처리)
- Ben Stopford, 『Designing Event-Driven Systems』 (O'Reilly, 2018)
