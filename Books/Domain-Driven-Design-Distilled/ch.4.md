# **ch.4 컨텍스트 매핑과 전략적 설계**

핵심 도메인을 다른 바운디드 컨텍스트와 통합하는 것을 **컨텍스트 매핑** 이라고 한다.

- 컨텍스트 사이에는 통합뿐만 아니라 팀 간의 다양한 관계도 존재할 수 있다.
- 두 바운디드 컨텍스트 안에 각각의 보편언어가 있다는 것을 생각해보면, 컨텍스트 매핑은 두 언어 사이의 통역을 나타낸다.
- 컨텍스트 매핑에는 여러 좋유릐 컨텍스트 매핑이 있을 수 있다.(팀, 기술 등 모든 관계)

<br><hr>

## **매핑의 종류**

컨택스트 매핑을 통해 다음과 같은 관계와 통합을 표현할 수 있다.

- **파트너십**
- **공유 커널**
- **고객-공급자**
- **준수자**
- **반부패 계층**
- **공개 호스트 서비스**
- **공표된 언어**
- **각자의 길**
- **큰 진흙덩어리**

<br>

> **파트너십**

![partnership](/img/partnership.png)


두 팀 사이에 파트너십 관계에서, 각 팀은 하나의 바운디드 컨텍스트를 책임진다. 두 팀은 일련의 목표에 대한 의존성을 맞추기 위해 파트너십을 구성한다.

- 두 팀이 함께 성공하거나 다같이 실패한다.
- 상호 의존적인 작업이나 여러 일정들을 조율하고, 통합을 적절하게 유지하기 위해 지속적으로 통합에 노력한다.
- 이러한 형태의 동기화는 두 팀 사이에 굵은 매핑 선으로 표시한다.(굵은 선은 필요한 약속의 수준이 꽤 높음을 의미한다.)

<br><br>

> **공유 커널**

![sharedKernel](/img/sharedKernel.png)

두 바운디드 컨텍스트의 교차 지점으로 표시한 공유 커널(Shared Kernel) 은 팀 사이에 작지만 공통인 모델을 공유하는 관계를 나타낸다. 공유 커널은 팀 사이에 열린 의사소통이 가능해야하고, 공유하는 모델에 대한 지속적인 합의가 있어한다.

- 각 팀은 공유하는 모델 요소에 대해 서로 협의해야한다.
- 공유하는 모델의 코드, 빌드를 관리하고 테스트 하는 것은 한 팀에서 맡아 수행한다.
- 관련된 모든 팀들이 각자 저마다의 길을 가는 것 보다 공유 커널이 더 좋은 생각이라고 여긴다면 좋은 결과를 얻을 수 있다.

<br><br>

> **고객-공급자**

![Upstream-Downstream](/img/Upstream-Downstream.png)

고객-공급자는 2개의 바운디드 컨텍스트와 각 팀들의 관계를 나타내는데, 공급자(Upstream; U), 고객(Downstream; D)로 표현한다. 고객-공급자는 팀 사이에 매우 일반적이고 현실적인 관계이며, 같은 조직 내에서도 마찬가지로 적용된다.

- 공급자는 고객이 뭔하는 것을 제공해야 때문에 관계를 주도한다.
- 고객은 다양한 기대를 충족시키기 위해 공급자와 함께 계획한다.
- 고객이 언제 무엇을 받게될지는 공급자가 결정한다.

<br><br>

> **준수자**

![conformist](/img/conformist.png)

준수자(Conformist) 관계는 상류와 하류 팀이 있고, 상류 팀이 하류 팀의 특정 요구에 지원할 동기가 없는 경우에 나타난다.

- 하류 팀이 자기들의 특정 요구에 맞춰 상류 모델의 보편언어를 계속 변화시는 것이 쉽지 않아 상류팀 모델을 그대로 따른다.
- 확실하게 자리잡은 매우 거대하고 복잡한 모델과의 통합이 필요할 때 이러한ㄴ 유형이 나타난다.

<br><br>

> **반부패 계층**

![AnticorruptionLayer](/img/AnticorruptionLayer.png)

반부폐 계층(Anticorruption Layer)은 가장 방어적인 컨텍스트 매핑 관계로, 하류 팀이 그들의 보편언어 모델과 상류 팀의 보편언어 모델 사이에 번역 계층을 만드는 것을 말한다.

- 하류 팀과 상류 팀 사이의 번역계층은 상류 모델로 부터 하류모델을 독립시키고 둘 사이를 번역한다.
- 가능하다면 하류모델과 상류 통합 모델 사이에 반부패 계층을 만들어 통합에 용이한 모델 개념들을 만들고, 원하는 형태의 특정 비즈니스 요구도 맞추며, 외부의 이질적인 개념으로부터 독립성을 유지시키는 것이 좋다.

<br>

> **공개 호스트 서비스**

![OpenHostServic](/img/OpenHostServic.png)

공개 호스트 서비스(Open Host Service)는 일련의 서비스 처럼 바운디드 컨텍스트에 대한 접근을 제공하는 프로토콜이나 인터페이스를 정의한다.

- 프로토콜은 바운디드 컨텍스트와 통합하고자 하는 모두가 용이하게 사용할 수 있도록 "공개" 한다.
- API(애플리케이션 프로그래밍 인터페이스)로 제공하는 서비스는 문서화가 잘되어있어야한다.
- 공개 호스트는 서드파티에게 최상의 통합 경험을 줄 수 잇는 공표된 언어를 제공한다.

<br>

> **공표된 언어**

![publishedLanguage](/img/publishedLanguage.png)

공표된 언어는 이를 사용하는 바운디드 컨텍스트의 규모에 관계없이, 모두 간단한 사용과 번역을 가능하게 하는 잘 문서화된 정보 교환을 말한다.

- 공표 문서는 XML, JSON 또는 Protobuf, Avro 와 같이 최적화된 작성 방식으로 정의할 수 있다.
- 이 결합은 매우 편리하게 두 보편언어 사이에 번역을 제공한다.

<br>
