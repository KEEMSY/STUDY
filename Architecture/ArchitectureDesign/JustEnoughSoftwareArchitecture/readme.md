# 적정 소프트웨어 아키텍처 - 리스크 주도 접근법
> George H. Fairbanks 저 | 원제: Just Enough Software Architecture: A Risk-Driven Approach (2010)  
> 360페이지 | Carnegie Mellon University 소프트웨어 공학 박사

## 책의 핵심 철학

소프트웨어 아키텍처 작업의 양은 **프로젝트의 리스크 수준에 비례**해야 한다.

> "리스크가 작을 때는 세밀한 설계가 필요 없고, 리스크가 성공을 위협할 때는 허술한 설계를 해서는 안 된다."

이것이 '적정(Just Enough)' 아키텍처의 의미이다. 과잉 설계(over-engineering)와 과소 설계(sloppy design) 사이의 균형을 리스크를 기준으로 찾는다.

<br>

## 세 가지 핵심 아이디어

### 1. 리스크 주도 모델 (Risk-Driven Model)
아키텍처 작업량을 리스크 기반으로 결정하는 3단계 프로세스.  
→ [자세히 보기](./risk-driven-model.md)

### 2. 소프트웨어 아키텍처 개념 체계
- **뼈대(Skeleton)**: 시스템의 핵심 구조를 이루는 아키텍처적 결정
- **품질 속성(Quality Attributes)**: 기능과 직교하는 비기능 요구사항 (성능, 보안, 유지보수성 등)
- **제약(Constraints)**: 아키텍처 결정에 영향을 주는 외부/내부 제한 조건

### 3. 아키텍처 모델 카탈로그 (Partitioned Model Catalog)
20개 이상의 아키텍처 스타일과 패턴을 수록한 카탈로그.  
→ [뷰타입 보기](./architecture-viewtypes.md)

<br>

## 책의 구성

| 파트 | 내용 |
|------|------|
| 파트 1 | 리스크 주도 모델 - 아키텍처를 언제, 얼마나 해야 하는가 |
| 파트 2 | 소프트웨어 아키텍처 개념 - 모델, 뷰, 뷰타입 |
| 파트 3 | 아키텍처 모델 카탈로그 - 실전 기법들 |

**3장**: 리스크 주도 모델 (핵심 장, PDF 샘플 공개)  
**4장**: Home Media Player 케이스 스터디 - 팀 커뮤케이션, COTS 컴포넌트 통합, 메타데이터 일관성 문제 적용 예시

<br>

## 주요 개념 용어

### 설계 의도 갭 (Design Intent Gap)
아키텍처 결정 사항이 설계 단계에서 코드로 구현되는 과정에서 소실되는 현상.
- 설계 문서에는 "왜 이렇게 결정했는지"가 있지만, 코드에는 "무엇을 했는지"만 남는다.
- 아키텍처 지식이 팀 전체에 전달되지 않으면 발생한다.

**해결책 - 아키텍처적 코딩 스타일 (Architecturally-Evident Coding Style)**:  
아키텍처적 의도가 코드 자체에서 읽히도록 코드를 작성하는 스타일.  
코드를 보는 것만으로도 아키텍처 결정의 의도를 파악할 수 있게 한다.

### 아키텍처 호이스팅 (Architecture Hoisting)
아키텍처적 제약 조건을 시스템 구조 자체에 내재화(encoding)하는 기법.
- 개발자가 실수로 아키텍처 원칙을 위반하기 어렵게 만든다.
- 예: 레이어 의존성을 컴파일 타임에 강제하는 구조

### 절차적 지식 vs 선언적 지식
- **절차적 지식**: 어떻게 하는지(How) - 기법의 적용 방법
- **선언적 지식**: 왜 하는지(Why) - 결정 이유와 원리 이해
- 이 책은 선언적 지식을 강조하여 개발자가 상황에 맞는 판단을 내릴 수 있도록 한다.

<br>

## 다른 방법론과의 비교

| 방법론 | 특징 | 리스크 주도 모델과의 차이 |
|--------|------|--------------------------|
| **TOGAF** | 기업 아키텍처 프레임워크, 조직/거버넌스 중심 | 관리/조직 측면을 다루지 않음. 순수 기술 설계에 집중 |
| **arc42** | 문서 템플릿 기반, 12개 구조화된 섹션 | 문서 채우기가 목적이 아닌 리스크 완화가 목적 |
| **C4 Model** | 4단계 다이어그램(Context/Container/Component/Code) | 이 책이 Simon Brown의 C4 모델에 영향을 줌. 뷰 개념이 유사 |
| **RUP** | 프로세스 중심, 단계별 산출물 규정 | 프로세스와 무관(process agnostic). 관리 측면 제외 |
| **애자일** | 적응적 개발, 설계 최소화 | 리스크 주도 모델은 애자일 환경에서도 적용 가능 |

**핵심 차별점**: 이 책은 프로세스나 조직 관리가 아닌, **기술적 리스크 완화**를 위한 공학적 접근에 집중한다.

<br>

## 케이스 스터디 예시 목록
- **Library System**: 모듈 뷰, 런타임 뷰 적용 예시
- **Yinzer System**: 소셜 네트워크 아키텍처
- **Eclipse IDE Plugin**: 컴포넌트 통합 아키텍처
- **Telephone Switch**: 고신뢰성 시스템 설계
- **Home Media Player**: 4장 전체 케이스 스터디
- **Web Startup Scenario**: 확장성 중심 아키텍처

<br>

## 참고 자료
- 저자 공식 사이트: https://www.georgefairbanks.com/book/
- 리스크 주도 모델 PDF 샘플(3장): https://www.georgefairbanks.com/assets/jesa/Risk-Driven-Model.pdf
- 뷰타입 레퍼런스: https://www.georgefairbanks.com/modeling/viewtype/
- 애자일 적용 아티클: https://www.methodsandtools.com/archive/agilesoftwarearchitecture.php
