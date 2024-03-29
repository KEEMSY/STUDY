# Study Log: DTO(데이터 전송 객체)

## Background Knowledge(배경지식)

나는 웹 애플리케이션 서버를 개발하는 백엔드 개발자이다. 현재 업무에서 사용하는 언어로는 파이썬과 파이썬 기반의 프레임워크(Flask, FastAPI)를 사용하고있다. (지극히 개인적인 경험을 기반으로)파이썬 진영에서는 Data Transfer Object(이하 DTO)를 사용하는 경우를 보지 못했고, 나는 DTO 의 존재를 전혀 모르고 있었다. 그리고 시간이 지나, 애플리케이션 아키텍처 및 코드 설계에 대한 공부를 하면서 DTO의 존재를 알게되었다.

Python과 Flask 및 FastAPI와 같은 프레임워크를 사용하여 웹 애플리케이션 서버를 제작하는 백엔드 개발자로서 내 경험에는 Python 환경 내에서 DTO(데이터 전송 개체)를 활용하는 사례는 아직까지 들어보지 못했다. 그러나 DTO 에 대한 이야기는 애플리케이션 아키텍처 및 코드 설계에 대한 공부 중에 빈번하게 들어볼 수 있었다.

- DTO 는 (애플리케이션 내 존재하는)계층 간 통신을 위해 사용한다.
- DTO 를 활용하여, 코드의 결합도를 낮춰 코드의 유지보수성, 확장성 그리고 유연성 등의 아키텍처의 품질특성을 향상시킬 수 있다.

이에 더해 최근 공부하고 있는 기술스택(주로 Java, Spring)와 관련하여 자주 접하게 되는 DTO 에 대하여, 이번 공부를 통해 DTO에 대한 내용을 정리하고 Python 기반 프로젝트의 맥락에서 DTO의 실제 적용을 고려해보는 것을 목표로 한다.

### Preliminary Questions(사전질문)

- 애플리케이션 개발에서 DTO의 역할과 이점은 무엇인가?
- DTO가 특히 유용한 시나리오는 무엇일까? DTO 사용과 관련된 잠재적인 문제는 없을까?
- DTO 라는 용어를 명시적으로 사용하지 않더라도 Flask 또는 FastAPI 등의 파이썬 프레임워크에서 구조화된 데이터 전송과 관련된 Python 관련 패턴이나 사례가 있을까?

---
## Contents

### 애플리케이션 개발에서 DTO

Data Transfer Object(이하 DTO)는 마틴 파울러의 책 P of EAA(Patterns of Enterprise Application Architecture)에서 처음 언급이 되었다.  DTO 는  데이터 전송 객체 역할을 하며,  마틴 파울러는 애플리케이션의 서로 다른 계층이나 구성 요소 간에 데이터를 전송해야 하는 시나리오에서 DTO 의 중요성을 강조한다.

- 계층 간 결합도 감소: DTO 는 데이터 전송에만 초점을 맞춰 계층 간의 결합을 줄이는 데 중추적인 역할을 한다. 이러한 결합 감소로 인해 API 사양 및 스키마 생성을 포함한 개발 유연성이 향상될 수 있다.
- 효율적인 데이터 교환: DTO 는 특히 네트워크 통신 시나리오에서 데이터 교환을 위한 효율적인 메커니즘을 제공한다. 이는 필요한 데이터만 포함함으로써 달성되며, 보다 간소화되고 최적화된 교환 프로세스에 기여할 수 있다.

<br>

> 계층 간의 결합도를 줄일 수 있다.

DIP(역전 원칙)를 준수함으로써 DTO는 상호 작용하는 계층 간의 종속성을 효과적으로 관리한다. 뿐만 아니라, DTO 는 비즈니스 논리를 처리하지 않으며 결합을 최소화하는 역할을 강조한다는 점에 유의하는 것이 중요하다.

<br>

> 데이터 교환(네트워크 통신)에 효율적인 메커니즘을 제공한다.

DTO 는 필요한 데이터만을 갖고 있음으로서, 사용하지 않는 불필요한 데이터로 인한 데이터 교환의 손실을 줄일 수 있다. 예를들어 User 의 프로필을 조회하는 경우, User 정보와 Order 을 보여준다고 한다면 User 와 Order 두번을 호출하는 것이 아닌, UserProfileDTO 내 해당 정보를 모두 갖고있음으로써 최적화 할 수 있다.

필수 데이터만 포함하는 DTO는 사용되지 않거나 불필요한 정보로 인해 발생하는 데이터 교환 손실을 완화한다. 예를 들어 관련된 주문이 포함된 사용자 프로필을 검색할 때 UserProfileDTO에 모든 관련 정보를 통합하면 User 및 Order 엔드포인트를 별도로 호출할 필요가 없어 프로세스를 최적화 할 수 있다.  


<br>

---

## DTO 가 특히 유용한 시나리오 와 발생할 수 있는 문제점

DTO 는 다양한 상황에서 사용될 수 있다. 데이터 전달 객체로서, 애플리케이션 내 외부 통신 과정에서 사용될 수 있다.

- 내부: 애플리케이션 계층간의 상호작용, API 버전 관리, 데이터 노출 수준 조절
- 외부: 네트워크 통신 간 정의한 API Spec

  <br>
### DTO 가 유용한 시나리오 

**1. 애플리케이션 계층 간의 상호작용**

DTO 는 애플리케이션 에 존재하느 계층 간 상호작용에서 사용할 수 있다. 계층 간 통신에서 DTO 를 사용함으로써 다음의 장점을 얻을 수 있다.

- 계층 간 느슨한 결합을 만들어 다른 계층 모듈(혹은 클래스)를 사용하더라도 사용하는 모듈(혹은 클래스)의 영향을 줄일 수 있다.
- 필요한 데이터를 캡슐화 하여 도메인 엔터티의 내부 복잡성을 노출하지 않고도 원할한 사용을 할 수 있게 도와준다.

<br>

**2. API 버전 관리**

1. 의 응용으로, DTO 를 API 버전 관리로 사용할 수 있다. 애플리케이션 간의 상호작용을 외부와의 상호작용에 적용하고, 필요한 관심사 만을 노출하고 이를 API 스펙으로 정함으로써 이를 API 버전 관리에 사용할 수 있다. 

- 자주 변경되는 엔터티의 변경이 API 스펙에 직접적인 영향을 주지 않아 안정적인 인터페이스를 만들 수 있다.(애플리케이션 안정성 증진)

