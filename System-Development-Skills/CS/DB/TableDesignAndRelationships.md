# **테이블 설계와 릴레이션**

애플리케이션을 만들 때 중요한 것은 **어떤 데이터 항목이 필요한가?** 를 제대로 파악하는 것이다.

- 항목이 부족하면 나중에 추가할 필요도 있지만, 이는 대부분 애플리케이션 개선이 필요하며, 가동 후에 실시하는 것은 쉽지않다.
- 어떤 항목이 필요한지를 적절히 결정하는데에는 **"데이터를 어떻게 사용하느냐"** **"업무 요구사항"** 이 명확해야 한다.
- 하지만 기능 추가가 자주 발생하는 애플리케이션에서 **데이터 항목을 완벽히 미리 밝혀내는 것은 불가능**하기 때문에 **나중에 손쉽게 추가할 수 있는 디자인**도 중요하다.

<br>

테이블 설계 시 직원과 조직을 다루는 테이블은 반드시 정해진 포맷이 있는 것은 아니다.

- 애플리케이션은 사용자를 위해 있는 것이며, 그 사용자의 요구에 따라 어떻게든 달라진다.
- 요구사항은 **"사용자로부터의 의견 청취"** 를 통해 모아지는 것이 일반적이다.

<br><hr><hr>

## **데이터 모델링**

데이터는 보유하는 방법에 따라 일관성이 무너질 수 있다.

- **"어떠한 시나리오에서 일관성이 무너질 위험성이 있는지"** 라는 관점에서라도 반드시 데이터 모델링에 대해 숙지해야 한다.

<br>

> ### **전통적인 모델링**

직원 테이블을 만든다.

emp_id|emp_name|emp_roman|emp_email|dept_name|dept_tel
|---|---|---|---|---|---|

- 사원번호(emp_id, primary key)
- 사원이름(emp_name)
- 사원 로마자(emp_roman)
- 메일 주소(emp_email)
- 부서 이름(dept_name)
- 부서 전화번호(dept_tel)

<br>

테이블 설계에서는 행을 고유하게 식별하기 위한 **"식별자(기본키 ,primary key)"** 라고 한다.

- **기본 키가 없다면** 어떤 레코드에 문제가 있는지를 체크할 경우에 해당 레코드가 **"단지 하나뿐"** 이라고 보증 할 수 없다.

<br>

현재 상태로 테이블을 사용하면, 같은 값을 가지는(중복) 데이터가 생기므로, **"테이블 관계"** 를 도입한다.

emp_id|emp_name|emp_roman|emp_email|`dept_name`|dept_tel
|---|---|---|---|---|---|
|1|김성연|Leo|ahr03003@gamil.com|개발팀|123-456-7890|

|`dept_name`|dept_tel
|---|---|
|개발팀|123-456-7890|

*부서 정보를 다른 테이블에 갖게 하여1:N 의 관련성을 맺는다.*

- 레코드가 중복된 정보를 갖게 되는 것은 모든 부분을 미처 다 바꾸지 못하는 실수가 발생할 여지를 만든다.
- 따라서 **"직원은 직원"**, **"부서는 부서"** 별로 별도의 테이블을 생성한다.

<br>

|`dept_name`|dept_tel
|---|---|
|개발팀|123-456-7890|

- dept 테이블에서 부서 이름(dept_name)은 키가 되고, 이로 인해 부서 전화번호(dept_tel)가 고유의 값으로 결정된다.
- 따라서, 같은 부서에 소속되어 있는 여러 직원이 부서 전화번호(dept_tel)를 중복해서 등록하지 않아도 된다.

<br>

emp_id|emp_name|emp_roman|emp_email|`dept_id`
|---|---|---|---|---|
|1|김성연|Leo|ahr03003@gamil.com|100|

|`dept_id`|dept_name|dept_tel
|---|---|---|
|100|개발팀|123-456-7890|

- 일반적으로 기본 키의 값은 한번 정해지면 쉽게 변경되지 않아야 하므로, 변경되지 않는 ID 값을 도입하고 기본키로 하는 것이 정석이다.
- dept 테이블을 통해 부서번호(dept_id)가 동일하면 부서 이름(dept_name) 또는 부서 전화번호(dept_tel)이 동일함이 보장된다.

<br><hr><hr>

## **참조 무결성 제약**

**참조 무결성 제약(Referential Intergrity, 외래키 제약)** 은 소속된 부서 코드(데이터)가 잘못되지 않았음을 감지할 수 있다.

- **다른 테이블의 기본 키를 참조**하는 한 테이블의 필드를 말한다.
- 참조 무결성 제약 조건의 주요 목적은 **유효하지 않거나 일관성이 없는 데이터 생성을 방지**하는 것이다.
- 참조 무결성 제약은 존재하지 않는 부서 번호(dept_id)를 입력할 때 그 값이 dept 테이블에 존지하는지에 대한 여부를 자동으로 체크해준다.
- dept 테이블에 존재하지 않는 부서번호(dept_id) 가 존재하는 일은 "일어나서는 안되는 일" 인데 외래키는 이를 자동으로 관리해준다.

<br>

하지만 입력을 존재하는 다른 값으로 잘못 입력(개발팀(100)를 인사팀(101)) 할 경우 그대로 저장되므로 **"Check Digit"** 을 도입하여 **잘못된 값을 검출하는 애플리케이션 측면의 대처**를 마련해야 한다.

- 체크 처리의 기본형은 **"해당 ID(식별자)가 관련 테이블에 존재하는지에 대한 여부를 참조 확인"** 하는 것이다.
- 이 참조는 매우 고속으로 끝나야 하므로, 대상 열(칼럼)에는 **인덱스**를 필수적으로 걸어준다.

<br><hr><hr>

## **정규화 이론**

*정규화 원칙을 따르면 데이터 중복성이 줄어들고 데이터 무결성이 향상되어 보다 효율적이고 효과적인 데이터 관리 및 쿼리가 가능하다.*

<br>

> ### **제 1 정규형**

emp_id|emp_name|emp_roman|emp_email|`dept_name`|dept_tel
|---|---|---|---|---|---|
|1|김성연|Leo|ahr03003@gamil.com|개발팀, 인프라팀|123-456-7890|

**제 1 정규형이 아닌 테이블** 은 테이블 구성에서 **중복 또는 반복, 복합값 등을 포함한 구조** 를 말한다.

- 중복한 열 값의 일부를 수정하거나 해서 그것을 자동으로 감지할 수 없다.
- 같은 이름의 조직임에도 주소가 다른 모순된 상태가 발생할 수 있다.

<br>

첫 번째 정규 형식에서는 테이블의 **모든 데이터가 원자적이거나 분할할 수 없어야 한다.**

- 각 열에는 하나의 값만 포함되어야 하며 해당 값은 고유해야한다.

<br>

> ### **제 2 정규형**

emp_id(PK)|dept_id(PK)|dept_name|created|
|---|---|---|---|
|1|100|개발팀|2023-04-04|

*제 2 정규형을 위반하는 테이블*

**제 2 정규형이 아닌 테이블** 은 **기본 키가 여러 열로 구성되어 있고 그 중 일부 열의 값을 의에 의해서만 결정되는 열이 있는 경우** 를 말한다.

*키가 아닌 속성이 전체 키가 아닌 기본 키의 일부에만 의존하는 테이블을 말한다.*

- 등록일(created) 은 emp_id, dept_id 의 두 가지 사항을 모르면 모르면 특정할 수 없지만, 부서 이름(dept_name)은 dept_id 만 정해지면 확인할 수 있기 때문에 이 테이블에 속하는 것이 적절치 않다.
- 동일 dept_id 인데도 부서명이 서로 다른 레코드가 등록될 가능성이 있다.(충돌)

<br>

두 번째 정규 형식에서는 **테이블의 키가 아닌 각 열이 기본 키에 완전히 종속**되어야 한다.

- 키가 아닌 각 열은 동일한 테이블의 다른 키가 아닌 열이 아니라 기본 키와 관련되어야 한다.

<br>

> ### **제 3 정규형**

emp_id(PK)|dept_id|dept_name|
|---|---|---|
|1|100|개발팀|

*제 3 정규형을 위반하는 테이블*

**제 3 정규형이 아닌 테이블** 은 **데이터 중복 및 불일치로** 인해 **데이터 이상** 및 **잠재적인 데이터 무결성 문제** 로 이어질 수 있다.

- 같은 부서 번호(dept_id) 임에도 불구하고 다른 dept_name 을 등록할 수 있다.

<br>

세 번째 정규 형식에서는 **테이블의 키가 아닌 각 열이 키가 아닌 다른 열이 아닌 기본 키에 직접 종속**되어야 한다.

- 키가 아닌 각 열이 동일한 테이블의 다른 키가 아닌 열과 독립적이어야 한다.
- 제 3 졍규형을 따를 경우 키가 아닌 모든 속성은 해당 테이블의 기본 키에만 기능적으로 종속되며 전이적 종속성은 없다.

*전이 종속성은  non-key 속성이 기본키(primary key)가 아닌 non-key속성에 종속될 때 전이 종속성이 발생한다 이야기한다. 그리고 이것은 속성값 사이에 종속성 체인이 있음을 의미한다.*


<br><hr><hr>

## **역정규화(비정규화, Denormalization)**

비정규화는 쿼리 성능을 향상시키거나 애플리케이션 개발을 단순화하기 위해 의도적으로 데이터베이스에 중복성을 도입하는 것과 관련된 데이터베이스 설계 기술을 말한다.

- 정규화된 테이블은 대규모 데이터 세트를 처리할 때 특히 복잡한 조인과 느린 쿼리 성능을 초래할 수 있다.
- 비정규화는 빠른 데이터 엑세스가 필요한 애플리케이션에서 중복성을 다시 도입하여 사용된다.

<br>

> ### **장점**

- 데이터의 빠른 접근이 가능하다.
- 간단한 쿼리를 통해 여러 테이블의 내용을 조회할 수 있다.

<br>

> ### **단점**

- 데이터 스토리지 요구사항이 증가한다.
- 데이터 일관성 감소 및 업데이트 시 이상 위험이 증가한다.

<br>

> ### **방법**

1. 중복열을 추가한다.

    중복 열을 테이블에 추가하여 비용이 많이 드는 조인이 필요한 쿼리를 단순화하고 더 빠르게 실행한다.

    두 테이블간의 조인이 많이 발생할 때, 한 테이블에 다른 테이블의 열을 추가하여 조인을 없애고 쿼리 성능을 증가시킨다.

2. 여러 테이블에서 데이터를 복제한다.

    여러 테이블에서 데이터를 복제하는 것이 쿼리 성능 향상에 도움이 될 수 있다.

    두 테이블의 데이터를 자주 쿼리하는 경우 두테이블을 단일 테이블로 결합하여 쿼리 성능을 개선할 수 있다. 

    하지만 데이터 스토리지 요구사항을 높일 수 있고, 데이터 일관성 문제가 발생할 수 있다.

3. 여러 테이블을 단일 테이블로 결합한다.

    애플리케이션이 여러 테이블의 데이터를 자주 쿼리하는 경우 단일 테이블로 결합허여 조인을 없앤다.