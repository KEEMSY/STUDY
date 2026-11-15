# 시스템 설계 학습 로드맵

3년차 서버 개발자 면접에서 **가장 변별력 있는 영역**이다.  
"동작하는 시스템"이 아닌 "확장 가능하고 트레이드오프를 아는 시스템"을 설계할 수 있는지를 본다.

<br>

## 시스템 설계 면접의 구조 (45분 기준)

```
[5분]  요구사항 명확화
[10분] 개략적 설계 (High-Level Design)
[20분] 상세 설계 (Deep Dive)
[10분] 트레이드오프 토론, 추가 질문
```

면접관은 **정답보다 사고 과정**을 본다. 말하면서 설계하는 연습이 필수.

<br>

## 설계 접근 프레임워크

### 1단계: 요구사항 명확화
먼저 질문한다. 가정하지 않는다.

**기능 요구사항**
- 핵심 기능이 무엇인가? (쓰기/읽기/검색 중 무엇이 메인?)
- 사용자가 할 수 있는 것과 할 수 없는 것은?

**비기능 요구사항 (이것이 설계 방향을 결정)**
- 규모: DAU, 초당 요청(QPS), 데이터 크기
- 일관성 vs 가용성 중 무엇이 더 중요한가?
- 지연시간(latency) 요구사항이 있는가?
- 읽기 / 쓰기 비율은?

### 2단계: 개략적 설계
클라이언트 → API → 서버 → DB의 기본 흐름을 먼저 그린다.
- API 설계 (주요 엔드포인트 2~3개)
- 데이터 모델 (핵심 테이블/스키마)
- 기본 아키텍처 다이어그램

### 3단계: 상세 설계
병목 지점을 식별하고 해결한다.

**자주 등장하는 병목과 해결책**
| 병목 | 해결책 |
|------|--------|
| 읽기 트래픽 과부하 | 캐시(Redis), CDN, 읽기 복제본 |
| 쓰기 트래픽 과부하 | 메시지 큐, 비동기 처리, 샤딩 |
| 단일 서버 장애 | 로드밸런서, 다중화 |
| DB 용량 한계 | 샤딩, 파티셔닝 |
| 검색 성능 | Elasticsearch, 역인덱스 |

### 4단계: 트레이드오프 설명
"왜 다른 방법이 아닌 이 방법을 선택했는가"를 항상 설명할 수 있어야 한다.

<br>

## 반드시 알아야 할 핵심 컴포넌트

### 캐시 (Cache)
- **언제 쓰나**: 읽기가 쓰기보다 훨씬 많을 때, DB 부하 분산
- **Redis 기본**: String, Hash, List, Set, Sorted Set
- **캐시 전략**: Cache-Aside(가장 일반적), Write-Through, Write-Behind
- **핵심 이슈**: Cache Stampede, Cache Invalidation, Cold Start
- **TTL 설계**: 데이터 변경 빈도에 따라 다르게 설정

### 메시지 큐 (Message Queue)
- **언제 쓰나**: 비동기 처리, 서비스 간 결합도 낮추기, 트래픽 버퍼링
- **Kafka vs RabbitMQ**: Kafka는 대용량 로그/스트리밍, RabbitMQ는 복잡한 라우팅
- **핵심 개념**: Producer/Consumer, Topic/Partition, Consumer Group, At-Least-Once
- **주의**: 멱등성(idempotency) 보장 필수

### 데이터베이스 선택
| 상황 | 선택 |
|------|------|
| 트랜잭션, 복잡한 쿼리 | RDB (PostgreSQL, MySQL) |
| 대용량 비정형 데이터 | MongoDB, DynamoDB |
| 시계열 데이터 | InfluxDB, TimescaleDB |
| 검색 | Elasticsearch |
| 세션/캐시 | Redis |

### DB 확장 전략
- **복제(Replication)**: 읽기 부하 분산, Master-Slave 구조
- **샤딩(Sharding)**: 쓰기 부하 분산, 수평 분할
  - Hash Sharding vs Range Sharding
  - 핫스팟 문제와 해결 (Consistent Hashing)
- **파티셔닝**: 테이블 수준의 데이터 분할

### 로드밸런서
- **L4(TCP)** vs **L7(HTTP)** 차이
- 알고리즘: Round Robin, Least Connection, IP Hash
- 헬스 체크와 장애 감지

### API 설계
- RESTful 원칙, HTTP 메서드/상태코드
- Pagination: Cursor 기반(추천) vs Offset 기반
- Rate Limiting: Token Bucket, Leaky Bucket
- API Gateway 역할

<br>

## 연습 문제 순서 (난이도 순)

### 초급 — 기본 패턴 익히기
1. **URL 단축기** (TinyURL)
   - 핵심: 해시 충돌 처리, 리다이렉션, 캐시
2. **파일 업로드 서비스**
   - 핵심: 오브젝트 스토리지, CDN, 청크 업로드

### 중급 — 복합 패턴
3. **알림(Notification) 시스템**
   - 핵심: 메시지 큐, Push/SMS/Email 다중 채널, 재시도
4. **피드(Feed) 시스템** (인스타그램/트위터 타임라인)
   - 핵심: Fan-out on Write vs Fan-out on Read, 셀럽 사용자 처리
5. **검색 자동완성**
   - 핵심: Trie, 캐시, 랭킹

### 고급 — 분산 시스템
6. **채팅 시스템** (카카오톡)
   - 핵심: WebSocket, 메시지 순서 보장, 읽음 처리
7. **결제 시스템**
   - 핵심: 멱등성, 분산 트랜잭션, 이중 결제 방지
8. **유튜브/넷플릭스**
   - 핵심: 동영상 인코딩 파이프라인, CDN, 적응형 비트레이트

<br>

## 자주 나오는 면접 질문

**설계 문제**
- "DAU 1000만명의 SNS 피드 시스템을 설계하라"
- "실시간 채팅 서비스를 설계하라"
- "이커머스 주문/결제 시스템을 설계하라"

**개념 질문 (3년차)**
- "캐시 일관성을 어떻게 보장하나요?"
- "DB 샤딩 키를 어떻게 결정하나요?"
- "마이크로서비스에서 트랜잭션을 어떻게 처리하나요?" (Saga 패턴)
- "CAP 정리를 설명하고, 실제 시스템 선택에 어떻게 적용했나요?"

<br>

## 추천 학습 자료

- 가상 면접 사례로 배우는 대규모 시스템 설계 기초 (Alex Xu)
- Designing Data-Intensive Applications (Martin Kleppmann)
- ByteByteGo (유튜브/뉴스레터)
