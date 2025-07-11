개발을 진행하면서 고민하고 답을 내려보면 좋을 질문들을 정리한다.

- 질문 내용은 카테고리에 속한다면, 중복될 수 있음

### 소프트웨어 아키텍처 및 디자인

- 계층화와 하위 모듈화 방법은 무엇인가?
- 클래스 간의 상호작용을 설계하는 방법은 무엇인가?
- 상속이나 연관을 사용하는 것이 옳은가?
- 인터페이스나 추상 클래스를 사용하는 것이 옳은가?
- 결합도가 높은 코드와 낮은 코드는 무엇인가?
- 디커플링을 달성하는 방법은 무엇인가?
- 가독성을 유지하면서 확장성을 향상하기 위해 디자인 패턴을 도입하는 방법은 무엇인가?
- 마감 시장을 정하고 선행 설계를 해야하는가?
- 오래걸리더라도 위험을 줄이는 설계를 해야하는가?
- 작은 부분부터 구현하는가? 아니면 여러 영역을 함께 시작하는가?
- 최소한으로 아키텍처에 큰 영향을 주는 주요 요구사항은 무엇인가?
- 개발하는 소프트웨어는 누구에게 의미가 있는가?
- 이해관계자가 진정 원하는 바는 무엇인가?(어떤 아키텍처 특성이 중요한가?)
- 아키텍처에서 관계에 대해 무엇을 알고 있는가?(또는 무엇을 모르고 있는가?)
- 개발하고자 하는 기능을 도입하여 이루고자하는 것은 무엇인가?
- 프로젝트에 관한 정보를 충분히 알고 있는가?
- 주어진 시스템의 아키텍처 설계를 얼마나 수행하고 결과물로 만들어야 하는가?
- 소프트웨어 아키텍처로 가장 잘 해결되는 리스크는 무엇인가?
- 아키텍처 설계 원칙을 어떻게 적용해서 설계 문제를 해결하는가?

### 클래스 및 객체 지향 설계

- 클래스를 어떻게 나누는 것이 좋은가?
- 각 클래스에는 어떤 속성과 메서드가 있는가?
- 싱글턴 패턴이나 정적 메서드를 사용하는 것이 옳은가?
- 객체를 생성할 때 팩터리 패턴을 사용하는 것이 옳은가?
- 요구 사항 분석은 어떻게 해야하는가?
- 각각의 클래스는 어떻게 상호 작용해야 하는가?
- 어떤 기능이 확장될 가능성이 있는가?
- 기능을 확장할 때 어느 클래스를 수정해야하는가?
- 수정할 필요가 없는 것은 어느 클래스인가?

### 코드 품질 및 가독성

- 결합도가 높은 코드와 낮은 코드는 무엇인가?
- 가독성을 유지하면서 확장성을 향상하기 위해 디자인 패턴을 도입하는 방법은 무엇인가?
- 어떤 코드가 높은 가독성을 가지는 코드인가?
- 어떤 코드를 간결한 코드라고 할 수 있을까?
- 고품질의 코드는 어떤 코드일까?

### 디자인 패턴 및 확장성

- 결합도가 높은 코드와 낮은 코드는 무엇인가?
- 디커플링을 달성하는 방법은 무엇인가?
- 가독성을 유지하면서 확장성을 향상하기 위해 디자인 패턴을 도입하는 방법은 무엇인가?
- 디자인 패턴을 적용하는 목적은 무엇인가?
- 프록시 패턴과 데코레이터 패턴의 차이점은 무엇인가?
- 빌더 패턴을 사용하면서 발생할 수 있는 문제점에는 어떤 것들이 존재하는가? 문제점들이 존재하는데 빌더패턴을 사용하는 이유는 무엇일까?
- DI 컨테이너와 팩터리 패턴의 차이점은 무엇일까?
- 싱글턴 패턴은 안티패턴으로도 불리는데, 안티패턴인가? 혹은 유용한 패턴인가?
- 확장성을 고려한 코드(설계)와 범용성을 갖춘 코드(설계)의 차이는 무엇일까?

### 유지 보수 및 유연성

- 유지 보수는 정확히 어떤 것을 의미하는 것인가?
- 어떤 상황에서 코드가 매우 유연하다고 말할 수 있을까?
- 어떤 종류의 코드가 확장과 유지 관리에 용이한가?
- 가독성, 확장성, 유지 보수 사이의 관계는 무엇인가?

### 실패 시나리오

- 만약에 심각한 버그가 배포된다면 어떻게 할까? 얼마나 빨리 롤백하거나 수정할 수 있을까? 또는 그 시간을 단축할 수 있을까?
- 만약에 데이터베이스 서버에 장애가 발생한다면 어떻게 할까? 다른 장비로 대체하여 장애를 극복하고 손실된 데이터를 복구하려면 어떻게 해야 할까?
- 만약에 서버 과부하가 발생하면 어떻게 할까? 증가한 트래픽을 처리할 수 있게 확장할 방법은 무엇일까? 아니면 일부 요청에라도 올바르게 응답할 수 있도록 부하를 없애려면 어떻게 해야할까?
- 만약에 대체스 환경이나 스테이징 환경에 문제가 있으면 어떻게 할까? 어떻게 새로운 환경을 구성할 수 있을까?
- 만약에 고객이 긴급한 문제를 보고하면 어떻게 할까? 고객지원 팀이 엔지니어링 팀에게 이를 알리는데 얼마나 걸릴까? 엔지니어링 팀이 수정하는데는 얼마나 걸릴까?
- 만약에 관리자나 다른 이해관계자가 비정기적인 리뷰 회의에서 제품 계획에 이의를 제기하면 어떻게 될까? 어떤 질문이 나올 수 있으며 어떻게 답해야 할까?
- 만약에 핵심 팀원이 아프거나 사고를 당하거나 퇴사하면 어떻게 할까? 팀이 계속 정상적으로 작동하려면 어떻게 인수인계를 받아야 할까?
- 만약에 사용자들이 논란의 여지가 있는 새로운 기능을 반대하면 어떻게할까? 어떤 입장을 취할 것이며 얼마나 빠르게 대응할 수 있는가?
- 만약에 프로젝트가 약속한 기한을 지키지 못하면 어떻게 할까? 어떻게하면 지연을 빨리 예측하고 복구하고 대응할 수 있을까?

### 비즈니스

- 이해관계자는 누구인가? 그들은 무엇을 얻길 원하는가?
- 사용자는 누구인가? 그들이 어떤 일을 끝내고 싶어 하는가?(소프트웨어로 달성하는 작업이 아닌 실제로 완수하고 싶은 일)
- 최악의 경우는 무엇인가? (때로는 실패를 생각함으로써 비즈니스 목표를 찾을 수 있다.)
- 개발하고자 하는 기능은 나의 욕심에서 시작되었는가? 혹은 고객의 목소리에서 시작되었는가?

### 의사결정

- 결정을 못 해서 더 나아갈 수 없는가?
- 더는 기다릴 수 없이 당장 결정해야만 해결되는 문제인가?
- 의사결정이 더 많은 선택지나 기회를 만드는가?
- 결정을 미루면 위험이 매우 커지는가?
- 결정을 내릴 때의 영향력을 충분히 숙지하고 있는가?
- 지금 왜 결정을 내려야만 하는지 논리적 근거가 명확한가?
- 잘못되었을 경우 되돌릴 만한 시간은 있는가? 실수를 감당할 수 있는가?
- 의사결정 과정에서 팀원들과 심도있는 회의를 진행했는가?
- 팀에서 겨정을 내리기 어려웠던 사항은 무엇이었는가?
- 여건이 불확실한 상황에서 어떤 결정이 내려졌는가?
- 돌이킬 수 없는 이유로 특정한 방법을 선택할 수밖에 없지는 않았는가?
- 질문읜 구체적인가?(일반적인 질문으로는 일반적인 통찰력만 얻을 수 있다. 질문이 구체적일수록 통찰력도 더 실행가능한 수준으로 얻을 수 있다.)
- 걱정하고 있는게 있는가?('만약에...' 라는 가정으로 생각해보는 것은 재미난 것은 아니지만 실제로 일어나는 엔지니어링 문제는 이런 생각이 자라나서 발생한 결과이다.)
- 그 문제는 문제인가?
- (문제가 맞다면) 그 문제는 해결해야 할 문제인가?
- (해결해야할 문제가 맞다면) 그 문제는 언제까지 해결해야할 문제인가?
- (해결해야할 일정이 정해졌다면) 그 문제는 꼭 프로그래밍으로 풀 수 밖에 없는 문제인가?
- (프로그래밍으로 풀어야 하는 문제라면) 그 문제를 풀기 위한 가장 적정한 기술은 무엇인가?

### 다이어그램

- 해당 다이어그램이 어떤 점을 유추할 때 도움이 되는가?
- 다이어그램의 핵심적인 패턴은 무엇인가? 패턴이 숨겨져 있는가?
- 다이어그램이 내포하는 메타모델은 무엇인가? 다이어그램 없이도 자체로 이해할 수 있는가?
- 다이어그램은 완성본인가? 더 단순화해도 여전히 이해할 수 있는가?
- 모듈, 컴포넌트와 커넥터, 할당 구조에 대한 뷰가 하나 이상 있는가?


### 팀 온보딩

> 온보딩 기간에 가장 중요한 것은 앞으로 함께 일할 살마들들 만나고, 회사의 비즈니스가 어떻게 운영되는지 파악하고, 최대한 많이 배우면서 회사 내부에서 신뢰를 얻는 것이다.

비즈니스는 어떻게 작동하는가?
- 돈은 어디서 나오고 어디로 가는가?
- 회사에 얼마나 자금이 있는가?
- 회사의 가치를 크게 증시가키려면 내년에 무엇을 성취해야 하는가?
- 내가 엔지니어링 팀과 비즈니스를 운영하는 사람들 사이에 어떤 지식 격차가 있는가?

회사의 문화는 무엇으로 정의되는가?
- 회사의 진정한 가치는 무엇인가?
- 최근에 회사가 내린 몇가지 중요한 결정은 무엇인가?
- 의사결정은 실제로 어떻게 내려지는가?
- 어떤 사람이 좋은 평가를 받으며 그 이유는 무엇인가?
- 누구의 역할이 성장했고, 누가 정체되었는가?

동료 및 이해관계자와 어떻게 건강한 관계를 구축할 수 있는가?
- 동료들은 엔지니어링 팀으로부터 무엇을 필요로 하는가?
- 동료들이 성공할 수 있도록 어떻게 도울 수 있는가?
- 이해 관계자들이 정의하는 성공이란 무엇인가?
- 갈등이 처음 생기기 전에 어떻게 협력적 관계를 구축할 수 있는가?

엔지니어링 팀이 올바르게 일을 수행하고 있는가?
- 아이디어는 어떻게 완성되는가?
- 직업은 어떻게 할당되는가?
- 비상 상황이 발생하면 누가 어떤 방식으로 처리하는가?

기술 품질이 높은가?
- 도구들이 팀의 일상 업무를 얼마나 잘 지원하고 있는가?
- 주요 기술적 행동과 속성은 무엇인가?
- 기술적 한계로 인해 수행할 수 없는 프로젝트는 무엇인가?

엔지니어링 팀은 사기가 높고 포용적인가?
- 팀 내에서 어떤 사람이 성공하고 있는가?
- 누가 성공을 거두지 못하고 그 이유는 무엇인가?
적극적인 포용 노력은 무엇이녀 누가 그런 노력을 주도하는가?
- 업무는 가치있게 평가받고 있는가?
- 팀을 활력있게 만드는 것은 무엇인가?
- 팀의 에너지를 빼앗는 요소는 무엇인가?

진행 중인 업무의 속도는 장기적으로 지속 가능한가?
- 팀원들이 장기적으로 일에 참여하고 활력을 유지하기 위해 무엇이 필요한가?
- 초기에는 잠시 제한을 풀었지만, 문제를 해결한 후에는 다시 넘지 말아야 할 선이 있는가?

현재 진행되고 있는 일이 효과적인과 확장 가능한가?
- 사람들이 기존 프로세스에 대해 어떻게 느끼고 있는가?
- 무엇이 잘되고 있고 무엇이 잘 안되고 있는가?

엔지니어링 작업 속도의 내부/외부 측정 기준은 무엇인가?
- 엔지니어링 작업의 속도를 측정하는 기준은 비즈니스 성과(수익성장)나 비즈니스에 미치는 여러 요소 측면에서 설정하는 것이 중요하다.
- 이 기준을 정의해준다면 비즈니스의 모든 요소들이 어떻게 맞물려 돌아가는지를 폭넓게 생각하는데 도움이 된다.

업무 프로세스는 어떻게 구성되어 있는가?
- 업무 수행에 방해가 되는 요소는 무엇인가?
- 조직 구조를 개편해야하는가?

### 전략 설정

- 현금 흐름 목표는 무엇인가?
- 각 부분(영업 및 마케팅, 연구 및 개발, 일반 및 관리)의 투자 논리는 무엇인가?
- 인수 합병(M&A)의 목적은 무엇인가사
- 사업부는 어떻게 구성되어 있는가? 사업부 간에 서로를 어떻게 지원하고 비용은 어떻게 배분되나요?
- 제품의 사용자는 누구이며, 그들의 요구는 무엇인가요? 이러한 사용자들의 우선순위는 어떻게 정하고 있나요?
- 다른 부서들은 향후 1년 동안 성공을 어떻게 평가할 것인가요?
- 현재의 유통 매커니즘은 무엇이며, 이를 어떻게 변경하려고 하나요?
- 가장 중요한 경쟁 위협은 무엇인가요?
- 현재 전략에서 작동하지 않는 부분은 무엇인가요?

정책 지침 관련
- 조직의 우선순위에 따른 자원 배분은 어떻게 이루어지고, 그 이유는 무엇인가?
- 모든 팀이 준수해야 할 기본 규칙은 무엇이고, 왜 중요한가?
- 엔지니어링 조직 안에서 의사결정은 어떻게 이루어지고, 왜 그렇게 하는가?
