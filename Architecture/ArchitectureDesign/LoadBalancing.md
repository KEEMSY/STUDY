# 로드밸런싱, LoadBalancing

로드밸런싱이란, **전송자로부터 오는 각 요청을 시스템 내 한 워커에 디스패칭** 하는 기능을 말한다. 이를 활용한다면, 사실상 무한대의 컴퓨터 자원을 활용할 수 있는 클라우드 환경에서 손쉽게 `확장성`을 가진 애플리케이션을 만들 수 있다.

- 로드밸런싱을 사용한다면 매일 수십억 개 이상의 `요청`을 처리할 수 있다.
- `PB` 규모의 `데이터`를 처리할 수 있다.
- 클라우드 환경에서 `비용` 효율적인 아키텍처를 구축할 수 있다.


<br>

> 로드밸런싱이 없을 경우

수만 명의 고객이 사용하는 애플리케이션을 `단일 클라우드 서버` 에서 배포하는 상황일 경우 다음과 같은 문제점이 발생할 수 있다.

- 수신 트래픽이 단일 서버의 CPU 메모리 혹은 네트워크 용량을 넘어설 경우 애플리케이션이 `충돌`하거나 `성능`이 저하될 수 있다.

<br>

이 문제를 해결하기 위해서 수직확장을 고려할 수 있으나 `수직확장`은 문제해결을 늦출 뿐, 근본적인 해결책이 되지 못한다.

<br><hr>

## 로드밸런싱 패턴에 대하여

<img width="1386" alt="로드밸런싱패턴" src="https://github.com/KEEMSY/STUDY/assets/96563125/7ac68f31-d8c6-4535-9475-52d2cc18e14f">


로드밸런싱 패턴은 `소스 데이터` 나 시스템에 전송되는 `네트워크 요청` 과 `워커` 사이에 `디스패처`를 배치하는 아키텍처 패턴이다.

- 디스패처는 각 클라이언트로부터의 요청을 가용 워커 중 하나의 워커로 `라우팅` 한다.
- 클라이언트의 수가 증가하거나 요청 전송 증가 속도가 빨라지면, 더 많은 워커를 추가하여 로드를 분산한다.

<br>
이러한 로드밸런싱은 애플리케이션의 `조건`에 따라 다르게 구현될 수 있으며 다음의 조건을 따져 상황에 알맞는 로드밸런싱 `알고리즘`을 사용해야한다.

- Statelessness Vs Statefulness 
- 클라이언트와의 서비스의 연결 및 세션의 지속시간


<br>

### 라우팅 알고리즘

> 라운드로빈, Round-Robin

<img width="1384" alt="스크린샷 2023-10-29 오후 5 31 33" src="https://github.com/KEEMSY/STUDY/assets/96563125/75e697a6-a23d-4ea9-bc1e-c78b9b10709e">

`라운드로빈` 알고리즘은 로드밸런서는 각각의 수신 요청을 연속적으로 다음 워커 인스턴스에 라우팅하는 알고리즘이다.

- 기본 설정 알고리즘이다.
- 스테이스리스(stateless) 애플리케이션에 적절하다.(클라이언트로 부터 수신한 요청을 각 대상 애플리케이션 서버에서 격리하여 처리한다는 것을 가정한다.)

<br>

> 고정세션(세션 선호도), Sticky Sesstion

<img width="1384" alt="스크린샷 2023-10-29 오후 5 40 42" src="https://github.com/KEEMSY/STUDY/assets/96563125/41422bd0-2fba-40d5-aa13-aed9b40507ea">

`고정세션` 알고리즘은 로드밸런서가 서버가 안정적일 경우 특정 클라이언트로 받은 트래픽을 동일한 서버로 전송한다.

- 특정 서버에 긴 세션이 너무 많이 몰리는 경우, 특정 서버에 문제가 발생할 수 있어, 비교적 짧은 세션에서 사용한다.(짧은 세션에서 효과적이다.)
- 인증이 필요한 애플리케이션, 대용량 파일을 여러 요청으로 나누어 업로드 하는 경우 사용한다.

<br>

> 최소연결, Least Connections

<img width="1382" alt="스크린샷 2023-10-29 오후 5 44 50" src="https://github.com/KEEMSY/STUDY/assets/96563125/6f361675-a694-4306-ae04-417f1c5f4b29">

`최소 연결` 알고리즘은 로드밸런서가 개방된 연결 중 가장 적은 요청을 처리하는 서버로 새로운 요청을 라우팅한다.

- SQL, LDAP 등 장시간 연결을 요구하는 작업에서 특히 유용하다.

<br><hr>

## 로드밸런싱 사용 사례

> FE 에서 BE 로의 HTTP 요청 밸런싱 - N tier 아키텍처

<img width="1385" alt="fe-be 아키텍처" src="https://github.com/KEEMSY/STUDY/assets/96563125/8f99e136-83c1-45b8-98a6-b98b0a452214">

클라이언트가 보내는 모든 요청은 FE에서 BE로 직접적으로 전달되지 않고, 로드밸런싱 디스패처로 전송되며, 디스패처가 각 요청을 각 애플리케이션인스턴스에 라우팅 한다.

- FE: 최종 사용자의 장치에 설치된 모바일 앱 또는 웹프라우저에서 실행되는 클라이언트(JS)
- BE: 클라우드 인프라에서 실행하는 애플리케이션 인스턴스 세트

<br>

> FE 에서 BE 로의 HTTP 요청 밸런싱 - MSA 아키텍처

<img width="1384" alt="msa 아키텍처" src="https://github.com/KEEMSY/STUDY/assets/96563125/baaf54dc-bb44-4fc6-886a-7f14de8f6d9f">

각 인스턴스 그룹의 모든 통신은 또 다른 디스패처를 거쳐 이뤄진다.

- 서비스의 트래픽과 워크로드에 따라 각 마이크로서비스들을 완전히 독립적으로 확장할 수 있다.
- 트래픽에 따라 사용하는 인스턴스를 서비스로 적용시켜 비용을 절약할 수 있다.