# **comparing the difference between the relational database and NoSQL**

`관계형 데이터베이스`와 `NoSQL` 데이터베이스는 **데이터 저장 및 검색에 대한 접근 방식** 이 다른 두 가지 유형의 데이터베이스이다.

<br>

 ## **Relational Database**

**관계형 모델**을 기반으로 하며 테이블을 사용하여 데이터를 저장한다. 

- 테이블은 **데이터의 속성을 나타내는 열**과 데이터의 **인스턴스를 나타내는 행**으로 구성된다. 
- 관계형 데이터베이스는 **스키마** 를 사용하여 **데이터 구조**를 정의하고 **제약 조건** 을 통해 데이터 무결성을 강화한다.
- `SQL`은 관계형 데이터베이스의 데이터를 쿼리하고 조작하는 데 사용된다.

<br>

## **NoSQL**

`NoSQL` 데이터베이스는 **대량의 비정형** 또는 **반정형 데이터**를 처리하도록 설계되었다. 

- **문서 기반**, **키-값**, **그래프 기반**및 **열 계열**과 같은 **다양한 데이터 모델**을 사용하여 데이터를 저장한다. 
- **스키마** 를 적용하지 **않**으므로 데이터 **저장** 및 **검색**에 더 많은 `유연성`을 제공한다.
- 종종 관계형 데이터베이스보다 높은 `확장성`과 `가용성`을 제공할 수 있다.


## **차이점**

궁극적으로 관계형 데이터베이스와 NoSQL 데이터베이스 간의 **선택은 애플리케이션과 저장되는 데이터의 특정 요구 사항에 따라 달라진다.**

- 관계형 데이터베이스는 일반적으로 **관계가 많은 복잡한 쿼리 및 데이터** 에 더 적합한 반면, NoSQL 데이터베이스는 **대량의 비정형 또는 반정형 데이터**를 처리하는 데 더 적합하다.
- 관계형 데이터베이스는 데이터 모델링과 **스키마**가 더 엄격한 반면 NoSQL 데이터베이스는 더 **유연** 하다.
- 관계형 데이터베이스는 더 오래 사용되었으며 도구와 기술의 **생태계가 더 확립**된 반면, NoSQL 데이터베이스는 **비교적 새로운 기술**이며 도구와 기술 집합이 더 **제한적** 이다.
- 관계형 데이터베이스는 일반적으로 **ACID 트랜잭션** 을 더 잘 지원하는 반면, NoSQL 데이터베이스는 종종 **엄격한 데이터 일관성보다 `확장성`과 `가용성`을 우선시한다.**(결국 모든 노드에서 일관되게 되는 `최종 일관성`에 의존한다.)

<br>

NoSQL 데이터베이스는 **분산 시스템** 을 확장하고 처리하는 데 사용할 수 있는 **데이터 복제** 및 **파티셔닝** 과 같은 기능을 아키텍처 및 철학의 기본 부분으로 설계한 반면 기존 관계형 데이터베이스에서는 추가 기능 또는 애드온으로 추가되는 경우가 많다. **NoSQL 데이터베이스는 분산 시스템을 확장하고 처리하는 접근 방식이 더 `유연`하고 `역동적`인 반면, 관계형 데이터베이스는 동일한 수준의 `확장성` 과 `가용성` 을 달성하기 위해 더 많은 계획과 인프라가 필요할 수 있다.**