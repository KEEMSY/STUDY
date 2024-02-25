# 리눅스 작업환경 구축하기
## 요약
- `Virtual Box` 같은 하이퍼바이저들은 가상 OS가 하드웨어 리소스에 안전하게 접근 할 수 있는 환경을 제공하지만, 경량 컨테이너 들은 호스트의 소프트웨어 커널을 공유한다.
- `APT`나 `RPM(Yum)`같은 리눅스 패키지 관리자는 원격저장소의 상태를 미러링한 인덱스를 주기적으로 업데이트 함으로써 온라인 저장소에서 소프트웨어를 가져와 설치하고 관리한다.
- `Virtual Box`에서 `VM`을 실행하려면 가상 하드웨어 환경을 정의하고 OS이미지를 내려받아 VM에 OS를 설치해야한다.
- `LXC` 컨테이너는 미리 정의된 템플릿을 기반으로 생성한다.
- `LXC` 데이터는 호스트 파일 시스템에 저장되므로 컨테이너를 관리하기 쉽다.

<br><hr><br>

## 핵심용어
- `가상화(virtualization)`는 여러 프로세스 간에 연산, 저장 공간, 네트워킹 리소스를 논리적으로 공유함으로써 마치 물리적으로 독립된 컴퓨터 환경처럼 각 프로세스를 관리 할 수 있게 해준다.
- `하이퍼바이저(hypervisor)`는 시스템 리소스를 게스트 계층에 제공하고자 호스트 컴퓨터에서 실행되는 소프트웨어로, 완전한 컴퓨터 구조를 갖춘 게스트 VM을 실행하고 관리한다.
- `컨테이너(container)`는 완전한 컴퓨터 구조 대신에 호스트 컴퓨터의 핵심 OS 커널 위에서 실행되는 VM으로, 단기적인 요구에 맞춰 간단히 실행하고 종료할 수 있다.
- `소프트웨어 저장소(software repository)`는 디지털 리소스를 저장할 수 있는 곳으로, 소프트웨어 패키지를 관리하고 배포하는 데 특히 유용하다.

<br><hr><br>

## 세부사항
<br>

> **하이퍼바이저(hypervisor)**

호스트 시스템 하드웨어를 몇 단계로 제어해 각 게스트 OS에 필요한 리소스를 제공한다.

![VM계층 이미지]()

<br>

> **컨테이너(container)**

초경량 가상 서버로, 완전히 독립적인 OS를 실행하는 대신 호스트 OS의 커널을 공유한다. 컨테이너는 텍스트 스크립트로 구축할 수 있으며 몇 초만에 생성하고 실행해서 네트워크를 통해 쉽고 안정적으로 공유 할 수 있다. 현재 가장 유명한 기술은 도커(Docker)인데 이는 리눅스 컨테이너(LXC)에서 영감을 받아 탄생한 기술이다.

![Container계층 이미지]()

- **LXC 준비**
    - **ubuntu**
         ```shell
            apt update
            apt install lxc
        ```
    <br>

    - **CentOS**
        ```shell
            yum install epel-release
            yum install lxc lxc-templates libcap-devel \
            libcgroup busybox wget bridge-utils lxc-extra libvirt
        ```
    <br>

    - 컨테이너 생성
        ```shell
        lxc-create -n [NAME] -t [TEMPLATE]
        ```
        - Templates은 `/usr/share/lxc/templates/`에서 사용 할 수있는 템플릿을 확인 할 수 있다. 

        - 예시 코드 `lxc-create -n myContainer -t ubuntu`



<br>

> **패키지 관리자**
- `APT` : 데비안, 우분투, 민트, 칼리 리눅스
- `RPM` : 레드행 엔터프라이즈, CentOS
- `YaSY` : 수세 리눅스, 오픈수세


<br><hr><br>

## 명령어
<br>

> `apt search [PACKAGE_NAME]`, `apt show [PACKAGE_NAME]`
- `apt search [PACKAGE_NAME]`: 사용 할 수있는 패키지를 직접 검색 
- `apt show [PACKAGE_NAME]` : 찾아볼 패키지에 관한 자세한 정보 표시