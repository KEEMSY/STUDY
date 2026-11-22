# MSA (Microservice Architecture)

애플리케이션을 비즈니스 역량 단위의 작은 서비스로 나누고, 각 서비스를 독립적으로 배포하고 운영하는 아키텍처 스타일이다.

이 폴더에서는 MSA를 쓰면 무엇을 얻고 무엇을 잃는지에 초점을 맞춘다. 패턴 이름을 외우기보다, 각 패턴이 어떤 문제를 풀려고 나왔는지를 기록한다.

<br>

## 학습 순서

| 순서 | 주제 | 핵심 질문 | 상태 |
|------|------|-----------|------|
| 01 | [모놀리스와 MSA](./01-monolith-vs-msa.md) | MSA는 언제 쓰고, 언제 쓰지 말아야 하는가? | [x] |
| 02 | [서비스 분해](./02-decomposition.md) | 서비스 경계는 무엇을 기준으로 나누는가? | 진행 중 |
| 03 | [서비스 간 통신](./03-communication.md) | 동기 호출과 비동기 메시징은 어떻게 고르는가? | [ ] |
| 04 | [데이터 관리](./04-data-management.md) | 서비스마다 DB를 따로 두면 어떤 문제가 생기는가? | [ ] |
| 05 | [Saga](./05-saga.md) | 분산 트랜잭션 없이 정합성을 어떻게 맞추는가? | [ ] |
| 06 | [API Gateway](./06-api-gateway.md) | 클라이언트는 여러 서비스를 어떻게 호출하는가? | [ ] |
| 07 | [장애 대응](./07-resilience.md) | 한 서비스의 장애가 퍼지는 것을 어떻게 막는가? | [ ] |
| 08 | [관측성](./08-observability.md) | 여러 서비스를 거친 요청을 어떻게 추적하는가? | [ ] |
| 09 | [마이그레이션](./09-migration.md) | 모놀리스를 어떻게 단계적으로 옮기는가? | [ ] |

<br>

## 노트 작성 방식

주제 하나에 노트 하나를 쓰고 노트 하나에 커밋 하나를 한다.

노트는 [StudyTemplate](../../../Template/StudyTemplate.md)을 바탕으로 하고 `Contents`에 아래 네 가지를 꼭 넣는다.

1. **문제**: 이 패턴이 풀려는 문제
2. **해법과 구조**: 동작 방식과 다이어그램
3. **트레이드오프**: 얻는 것, 잃는 것, 쓰면 안 되는 상황
4. **관련 패턴**: 함께 쓰거나 대신 쓸 수 있는 패턴 링크

<br>

## 관련 문서

- [DDD](../../CodeDesign/DDD/readme.md): 서비스 경계를 정할 때 쓰는 Bounded Context
- [Backend For Frontend](../BackendForFrontend.md): API Gateway의 변형
- [Load Balancing](../LoadBalancing.md)
- [EDA](../EDA/readme.md): 서비스 간 비동기 통신과 데이터 정합성

<br>

## 참고 자료

- Chris Richardson, 『Microservices Patterns』 (Manning, 2018) - 주 교재
- [microservices.io](https://microservices.io/patterns/) - 같은 저자의 패턴 카탈로그
- Sam Newman, 『Building Microservices』 2판 (O'Reilly, 2021)
- Sam Newman, 『Monolith to Microservices』 (O'Reilly, 2019)
