# CS 기초 체크리스트

시스템 설계와 기술 면접 답변의 기반이 되는 개념들.  
각 항목 옆에 ✅/⬜로 체크하며 진행한다.

<br>

## 운영체제 (OS)

### 프로세스와 스레드
- ⬜ 프로세스 vs 스레드 차이 (메모리 공간, 컨텍스트 스위칭 비용)
- ⬜ 멀티프로세스 vs 멀티스레드 선택 기준
- ⬜ 컨텍스트 스위칭(Context Switching) 과정
- ⬜ PCB(Process Control Block)에 저장되는 정보

**자주 나오는 질문**
> "프로세스와 스레드의 차이를 설명하고, 언제 어떤 것을 선택하나요?"  
> "Java의 스레드 모델과 코루틴의 차이는?"

### 동시성과 동기화
- ⬜ Race Condition 발생 조건과 예방
- ⬜ Mutex vs Semaphore 차이
- ⬜ 데드락(Deadlock) 4가지 조건 + 예방/회피 방법
- ⬜ 낙관적 락(Optimistic Lock) vs 비관적 락(Pessimistic Lock)
- ⬜ Java: synchronized, ReentrantLock, volatile

**자주 나오는 질문**
> "데드락이란 무엇이고, 실제 코드에서 어떻게 방지하나요?"  
> "멀티스레드 환경에서 공유 자원을 어떻게 관리하나요?"

### 메모리 관리
- ⬜ 가상 메모리(Virtual Memory) 개념과 페이징
- ⬜ 스택(Stack) vs 힙(Heap) 메모리 구조
- ⬜ 가비지 컬렉션(GC) 동작 방식 (사용 언어 기준)
- ⬜ 메모리 누수(Memory Leak) 발생 패턴과 탐지

### CPU 스케줄링
- ⬜ FCFS, SJF, Round Robin 알고리즘 개념
- ⬜ 선점형 vs 비선점형 차이

<br>

## 네트워크

### HTTP / HTTPS
- ⬜ HTTP 1.1 vs HTTP 2.0 vs HTTP 3.0 차이
- ⬜ HTTPS 동작 원리 (TLS Handshake)
- ⬜ HTTP 메서드 (GET/POST/PUT/PATCH/DELETE) 의미와 차이
- ⬜ HTTP 상태 코드 분류 (2xx/3xx/4xx/5xx) 및 주요 코드
- ⬜ 쿠키(Cookie) vs 세션(Session) vs JWT 비교
- ⬜ CORS 발생 원인과 해결 방법

**자주 나오는 질문**
> "브라우저에서 URL을 입력하면 어떤 일이 일어나나요?" (전체 흐름 설명)  
> "HTTP vs HTTPS, 왜 HTTPS를 사용해야 하나요?"

### TCP / UDP
- ⬜ TCP 3-way Handshake, 4-way Handshake
- ⬜ TCP 흐름 제어(Flow Control) vs 혼잡 제어(Congestion Control)
- ⬜ TCP vs UDP 선택 기준 (언제 UDP를 쓰는가?)
- ⬜ TIME_WAIT 상태의 의미와 서버에서 발생하는 문제

### DNS
- ⬜ DNS 조회 과정 (재귀적 쿼리 vs 반복적 쿼리)
- ⬜ DNS 캐싱과 TTL

### REST API
- ⬜ REST 6가지 제약 조건
- ⬜ RESTful API 설계 원칙 (URI 설계, 상태코드 활용)
- ⬜ REST vs GraphQL vs gRPC 비교 및 선택 기준

### 로드밸런싱 & 프록시
- ⬜ L4 vs L7 로드밸런서 차이
- ⬜ 포워드 프록시 vs 리버스 프록시

<br>

## 데이터베이스

### 인덱스
- ⬜ B-Tree 인덱스 구조와 동작 원리
- ⬜ 인덱스가 SELECT는 빠르게, INSERT/UPDATE/DELETE는 느리게 하는 이유
- ⬜ 복합 인덱스(Composite Index) 설계 원칙 (왼쪽 접두사 규칙)
- ⬜ Covering Index, Index Range Scan, Full Table Scan
- ⬜ 실행 계획(EXPLAIN) 읽는 법

**자주 나오는 질문**
> "인덱스를 어떻게 설계하나요? 실제로 튜닝한 경험이 있나요?"  
> "인덱스가 있는데 왜 느릴 수 있나요?"

### 트랜잭션
- ⬜ ACID 속성 (Atomicity, Consistency, Isolation, Durability)
- ⬜ 격리 수준 4단계 (Read Uncommitted → Serializable)
- ⬜ 각 격리 수준에서 발생하는 문제 (Dirty Read, Non-Repeatable Read, Phantom Read)
- ⬜ 격리 수준별 성능 트레이드오프

**자주 나오는 질문**
> "트랜잭션 격리 수준을 설명하고, 실제 프로젝트에서 어떤 수준을 사용했나요?"

### 정규화
- ⬜ 1NF, 2NF, 3NF 개념
- ⬜ 정규화 vs 비정규화 (성능 vs 일관성 트레이드오프)
- ⬜ 언제 비정규화를 선택하는가?

### DB 복제 & 샤딩
- ⬜ Master-Slave 복제 동작 방식과 읽기 부하 분산
- ⬜ Replication Lag 문제와 해결 방법
- ⬜ 수직 확장(Scale-Up) vs 수평 확장(Scale-Out)
- ⬜ 샤딩 키 선택 기준
- ⬜ Consistent Hashing 개념

### NoSQL
- ⬜ RDB vs NoSQL 선택 기준
- ⬜ CAP 정리 (Consistency, Availability, Partition Tolerance)
- ⬜ BASE 속성 (Basically Available, Soft State, Eventually Consistent)
- ⬜ MongoDB, Redis, Cassandra 각각의 특성과 사용 사례

<br>

## 운영 & 인프라 (스타트업 필수)

### 캐시
- ⬜ 캐시 히트율(Cache Hit Ratio) 측정과 목표 수준
- ⬜ Cache Stampede(Thunder Herd) 문제와 방지 (Mutex Lock, PER 알고리즘)
- ⬜ Cache Invalidation 전략

### 메시지 큐
- ⬜ At-Least-Once vs At-Most-Once vs Exactly-Once 의미
- ⬜ 멱등성(Idempotency) 구현 방법
- ⬜ Dead Letter Queue(DLQ) 활용

### 마이크로서비스
- ⬜ 모놀리식 vs 마이크로서비스 트레이드오프
- ⬜ 서비스 간 통신: 동기(REST/gRPC) vs 비동기(MQ)
- ⬜ 분산 트랜잭션: Saga 패턴 (Choreography vs Orchestration)
- ⬜ 서킷 브레이커(Circuit Breaker) 패턴

<br>

## 학습 우선순위

1주차에는 **네트워크(HTTP 흐름 + TCP)** + **DB(인덱스 + 트랜잭션)**을 먼저.  
이 두 영역이 면접에서 가장 빈도 높게 나온다.
