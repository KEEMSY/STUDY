# **CHAPTER 10 레이어드 아키텍처 스타일**

`레이어드 아키텍처(layered architecture, n-tiered)`는 가장 흔한 아키텍처 스타일 중 하나이다. **단순**하고 **대중적**이면서 **비용도 적게** 들어 모든 애플리케이션의 **사실상 표준 아키텍처**라고 말할 수 있다.

- 개발자와 아키텍트가 어떤 아키텍처 스타일을 정하지 않았다면, 레이어드 아키텍처는 좋은 선택지가 될 수 있다.
- 하지만, 레이어드 아키텍처는 `묵시적 아키텍처(architecture by implication) 안티패턴`, `우발적 아키텍처(accidental architecture)안티 패턴` 등 몇몇 아키텍처 안티패턴의 범주에 속한다.

<br><hr><hr>

## **토폴로지**

*토폴로지는 시스템의 구성 요소 또는 모듈의 배열과 서로 상호 작용하는 방식을 나타낸다.*

`레어이드` 아키텍처의 내부 컴포넌트는 **수평한** **`레이어`** 들로 구성되며, 각 `레이어`는 애플리케이션에서 주어진 **역할**을 수행한다.

- 레이어의 종류에는 `프리젠테이션(presentation)`, `비즈니스(business)`, `퍼시스턴스(persistence)` , `데이터베이스(database)`의 4개 **표준** **`레이어`** 로 구성한다.
- 규모가 작은 애플리케이션은 3개, 덩치가 크고 복잡한 비즈니스 애플리케이션은 5개 또는 그 이상의 레이어로 구성된다.
- 각 `레이어`는 `관심사의 분리(seperation of concerns)`의 개념을 통해, **아키텍처 내부의 역할 및 책임 모델을 효과적으로 구성**할 수 있다.
- 특정 `레이어`에 소속된 컴포넌트는 **역할 범위가 한정**되며 그 `레이어`에 알맞은 로직만 처리한다.
- 이에 대한 트레이드오프로, **개발자의 기술 역량을 도메인의 (`프리젠테이션` 로직, `퍼시스턴스로직` 등)기술적인 부분에 집중시킬 수 있으나, 전체적인 `민첩성`(변화에 신속하게 반응하는 능력)이 떨어지게 된다.**

<br>

*프리젠테이션 레이어는 고객 데이터를 조회하는 방법은 알 필요도 없고 그럴 이유도 없다. 그저 자신이 받아온 정보를 화면에 보기 좋게 보여주기만 하면된다. 마찬가지로, 비즈니스 레이어는 고객 데이터를 어디서 가져올지, 화면에는 어떻게 보여줄지 전혀 관여하지 않는다. 퍼시스턴스 레이어에서 데이터를 가져와 (값을 계산하거나 취합하는 등) 비즈니스 로직을 수행한 결과 데이터를 프리젠테이션 레이어에 잘 전달하기만 하면 된다.*

<br>

> ### **레이어드 아키텍처는 도메인 분할 아키텍처와는 반대의 기술 분할 아키텍처이다.**

`레이어드` 아키텍처는 컴포넌트를 (고객 같은) 도메인 단위로 묶는 것이 아닌, **아키텍처의 (`프리젠테이션` 혹은 `비즈니스` 같은) `기술 역할`에 따라 묶기** 때문에 `비즈니스` 도메인이 각각 **모든 아키텍처 `레이어`에 분산**된다.

- **고객 도메인**은 `프리젠테이션`, `비즈니스`, `규칙`, `서비스`, `데이터베이스` **모든 레이어에 다 포함**되어 이 도메인에 어떤 변경을 가하기는 쉬운 일이 아니다.
- `레이어드` 아키텍처에 (어울리지 않는)`도메인 주도 설계방`식은 하지 말도록 하자.

<br><hr><hr>

## **레이어 격리**

![layeredArchitecture](/img/layeredArchitecture.png)

`레이어드` 아키텍처의 각 `레이어`는 `폐쇄(closed)` 또는 `개방(open)` **상태**이다.

- `폐쇄 레이어(closed layer)`는 요청이 **상위 `레이어`** 에서 **하위 `레이어`** 로 이동하므로 **중간의 어떤 레이어도 건너 뛸 수 없고 현재 레이어를 거쳐야 바로 다음 레이어로 나아갈 수 있음**을 의미한다.
- `추월차선 리더 패턴(fast-lane reader pattern)`은 **단순 조회 요청**이라면 **불필요한 `레이어`를 건너뛰고 `프리젠테이션` `레이어`가 `데이터베이스`를 직접 엑세스 하는 것**을 말한다.
- 요청이 **`레이어`를 건너뛰기 위해서는 `비즈니스` `레이어`, `퍼시스턴스` `레이어`는 개방이 되어있어야 한다.**

<br>

> ### **레이어 격리**

`레이어 격리`란, **어느 아키텍처 `레이어`에서 변겅이 일어나도 다른 `레이어`에 있는 컴포넌트에 아무런 영향을 끼치지 않아 `레어어` 간 계약은 불변함**을 의미한다.

- 각 `레이어`는 서로 **독립적**으로 작동되며, **다른 `레이어`의 내부 작동 로직은 거의/전혀 알지 못한다.**
- `레이어 격리`를 지원하기 위해서는 **요청의 메인 흐름에 관한 레이어가 반드시 폐쇄 되어 있어야 한다.**
- (잘 정의된 `계약`과 `비즈니스 위임 패턴`;busioness delegate pattern)`레이어`를 `격리`하면 아키텍처의 **모든 레이어를 다른 레이어에 영향을 주지않고 교체할 수 있다.**

<br>

*프레젠테이션 레이어가 퍼시스턴스 레이어에 직접 엑세스 할 수 있으면 퍼시스턴스 레이어에서 변경이 발생할 경우 비즈니스 레이어, 프리젠테이션 레이어 모두 영향을 받게도고, 결국 컴포넌트 간의 레이어 상호 의존도가 높아져 단단히 커플링된 애플리케이션이 만들어 지게 된다.*

<br><hr><hr>

## **레이어 추가**

![serviceLayer](/img/serviceLayer.png)

아키텍처 내부적으로 `폐쇄 레이어`를 통해 **변경을 격리**할 수도 있지만, 어떤 `레이어`는 **개방하는 것이 더 합리적인 경우도 존재**한다.

- `비즈니스` `레이어` 내부의 객체(공통 비즈니스 기능이 구현된 객체)를 `공유`할 때, `서비스` `레이어`(개방 레이어)를 통해 아키텍처 **`레이어` 간 관계와 요청 흐름을 정의할 때 유용**하다.
- 아키텍처 내부의 다양한 `레이어` 액세스 제약에 관한 필수 정보와 지침을 제공할 수 있다.
- 하지만 아키텍처의 어느 `레이어`가 **`개방/폐쇄`되어 있는지(그리고 왜 그런지) 정확히 `문서화`되어 소통하지 않으면 테스트, 유지보수, 배포 작업이 아주 힘든, 단단히 `커플링`되어 금방이라도 깨질듯한 아키텍처가 되어버릴 수 있다.**

<br>

*공통 비즈니스 기능이 구현된 객체에는 날짜, 문자열 유틸리티 클래스, 감사 클래스, 로깅 클래스 등을 이야기 할 수 있다.*

<br><hr><hr>

## **기타 고려 사항**

아키텍처 스타일을 완전히 결정하지 못했을 경우, `레이어드` 아키텍처는 좋은 출발점이 될 수 있다. `마이크로서비스` 아키텍처를 고려 중인데 `마이크로서비스`가 올바른 선택인지 알 수 없을 때도 좋다.

- `재사용`은 **최소한**으로, `객체 계층(hierarchy)`은 **최대한 가볍게 맞추어 적절한 `모듈성`을 유지하여, 미래의 아키텍처 스타일 변환 시 큰 어려움이 없도록 한다.**
- `요청`이 한 `레이어`에서 다른 `레이어`로 `이동`할 때 **각 `레이어`가 아무 비즈니스 로직도 처리하지 않고 그냥 통과 시키는 `싱크홀(architecture sinkhole) 아키텍처`를 조심한다.**
- 처리 중인 요청의 비율(`정상:싱크홀`)이 `8:2`라면 괜찮지만, **싱크홀이 8이라면 해당 도메인에 `레이어드` 아키텍처는 적합하지 않음을 의미**한다.

*싱크홀 패턴의 예시로 유저가 기본 고객 데이터를 조회하는 단순 요청을 하면 프리젠테이션 레이어가 응답하는 아키텍처에서, 프리젠테이션 레이어는 비즈니스레이어에 요청을 전달하고, 비즈니스 레이어는 아무일도 하지않고 규칙 레이어로 넘기고, 규칙 레이어 역시 아무 일도 안하고 다시 퍼시스턴스 레이어에 넘기고, 결국 퍼시스턴스 레이어가 데이터베이스 레이어에 단순 조회 SQL 을 실행하여 고객데이터를 가져오면 취합, 계산, 규칙 적용, 데이터 변환 등 일체의 로직 없이 다시 왔던 길을 거꾸로 가는 경우를 말할 수 있다.*

*싱크홀 아키텍처 패턴은 불필요한 객체 초기화 및 처리를 빈번하게 유발하고 쓸데없이 메모리를 소모하며 성능에도 부정적인 영향을 미친다.*

<br><hr><hr>

## **레이어드 아키텍처를 사용하는 이유**

`레이어드` 아키텍처는 **작고 단순한 애플리케이션이나 웹사이트에 알맞은 아키텍처**이다.

- 처음 구축을 시작할 때, 예산과 일정이 빠듯한 경우 출발점으로 괜찮다.
- 비즈니스 니즈와 요구사항을 분석하는 중이고 어떤 아키텍처 스타일이 최선인지 불확실한 경우에도 나쁘지 않은 선택이다.
- 애플리케이션의 **규모가 커질수록** `유지 보수성`, `민첩성`, `시험성`, `배포성` 같은 아키텍처 특성이 **점점 나빠진다.**
- 따라서 대규모 애플리케이션이나 시스템은 다른 더 모듈러한 아키텍처 스타일이 더 잘맞는다.

<br><hr><hr>

## **정리**

*별의 갯수가 적을수록 해당 특성이 별로 좋지 않음을 의미하며, 별의 갯수가 많을수록 이 아키텍처의 강점임을 의미한다.*

|아키텍처 특성|별점|
|---|---|
|**분할 유형**| 기술|
|**퀀텀 수**|1|
|**배포성**|⭐|
|**탄력성**|⭐|
|**진화성**|⭐|
|**내고장성**|⭐|
|**모듈성**|⭐|
|**전체비용**|⭐⭐⭐⭐⭐|
|**성능**|⭐⭐|
|**신뢰성**|⭐⭐⭐|
|**확장성**|⭐|
|**단순성**|⭐⭐⭐⭐⭐⭐|
|**시험성**|⭐⭐|

*[아키텍처 특성 참고](https://github.com/KEEMSY/STUDY/blob/Book/Books/FundamentalsOfSoftwareArchitecture/ch.4.md)*

<br>

`전체 비용`과 `단순성`은 `레이어드` 아키텍처 스타일의 **주요 강점**이다.

- `레이어드` 아키텍처는 `모놀리식`에 가깝기 때문에 `분산` 아키텍처 스타일에 비해 `복잡도`가 낮고, `구조`가 `단순`하여 **알기 쉬우며 구축 및 유지보수 비용도 적게 든다.**
    
    ***하지만 점점 커지고 더 복잡해질 수록 이런 장점들은 빛을 바랜다.***

<br>

반면 `레이어드` 아키텍처는 `배포성`과 `시험성`이 **매우 떨어진다.**

- `배포`를 하려면 절차가 까다롭고(그만큼 더 고통스럽고) 리스크가 높으며 자주 `배포`할 수 없어 배포성이 떨어진다.

    ***클래스 파일 3줄만 바꿔도 전체 다시 배포해야하며, 데이터 베이스변경, 구성 변동고 수반될 수 있고, 다른 코드도 함께 고쳐야 할 수도 있다.***
- 하지만 컴포넌트 전체를 `모킹(mocking)` 또는 `스터빙(stubbing)` 할 수 있어 **전체 테스트 공수는 덜 들어갈 수 있다.**

<br>

`분산` 아키텍처에서 일반적인 `네트워크 트래픽`, `대역폭`, `레이턴시` 문제는 덜 하므로 `신뢰성`은 보통이다. 하지만 `모놀리식` 배포 특성상 **`시험성`(테스트의 완벽도)와 배포 리스크 측면은 좋다 할 수 없다.**

<br>

`모놀리식` 내부 기능중에 상대적으로 확장가능한 것들도 있으나, 대부분 멀티스레딩, 내부 메시징을 비롯하여 이 아키텍처와는 어울리지 않는 갖가지 병렬 처리 프랙티스와 기법이 동원되어야 한다.

- `레이어드` 아키텍처는 `유저 인터페이스`, `벡엔드 처리`, `데이터베이스` 구조가 `모놀리식`이고 언제나 `단일 시스템 퀀텀`이므로 애플리케이션은 **단일 퀀텀을 기반으로 특정 지점까지만 확장이 가능하다.**
- `병렬 처리`가 거의 안되고, `퐤쇄적인 레이어 구조`와 `싱크홀 아키텍처 안티패턴` 때문에** 고성능 시스템에는 어울리지 않는다.**

<br>

부족한 아키텍처 `모듈성` 때문에 `내고장성`도 좋지않다.

- 어느 한 작은 파트에 `OOM(Out Of Memory, 메모리 부족)`이 발생하면 **애플리케이션 전체적으로 영향을 받고 충돌이 발생**한다.
- 대부분 `모놀리식` 애플리케이션이 겪는 `MTTR(Mean Time To Recovery, 평균 수리시간)` 때문에 소규모 애플리케이션은 2분, 대규모 애플리케이션은 대부분 15분 이상의 가동 시간이 소요되며, 이 만큼 **전체 `가용성`도 영향을 받게 된다.**