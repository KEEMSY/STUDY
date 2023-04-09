# K8S Basic
*쿠버네티스는 여러 머신을 위한 docker-compose와 유사하다.*

k8s는 자동 배포, 스케일링 조정 및 로드밸런싱, 관리를 한곳에서 할 수 있는 오픈 시스템이다.(컨테이너 조정 및 배포를 위한 시스템이다.)




<br><hr><hr><br>

## **구성요소**

<br>

<br>
    
![API 서버와 컨트롤러 매니저](/img/apiserverWithControllermanager.png)

<br>

*API 서버와 컨트롤러 매니저는 단순히 파드가 생성되는 것을 감시하는 것이 아니라 디플로이먼트처럼 레플리카 셋을 포함하는 오브젝트 생성을 감시한다.*

> ### **마스터 노드**
- **kubectl**
    
    쿠버네티스 클러스터에 명령을 내리는 역할을 한다. 다른 구성요소들과 다르게 바로 실행되는 명령 형태인 바이너리(binary)로 배포되기 때문에 마스터 노드에 있을 필요는 없다. 하지만 통상적으로 주로 API 서버와 통신하므로 API 서버가 위치한 마스터 노드에 구성하는 경우가 존재한다.

    <br>

- **API 서버**

    쿠버네티스 클러스터의 중심 역할을 하는 통로이다. 회사에 비유하자면 모든 직원과 상황을 관리하고 목표를 설정하는 관리자에 해당한다.


    <br>

- **etcd**(etc + distributed(퍼뜨렸다.))

    구성 요소들의 상태 값이 모두 저장되는 곳으로, 회사의 관리자가 모든 보고 내용을 기록하는 노트라고 생각하면 된다. 
    
    etcd의 정보만 백업되어 있다면 긴급한 장애 상황에서도 쿠버네티스 클러스터를 복구 할 수 있다.

    또한 etcd는 분산 저장이 가능한 key-value 저장소 이므로, 복제해 여러 곳에 저장해두면 하나의 etcd에서 장애가 나더라도 시스템의 가용성을 확보할 수 있다.

    <br>

- **컨트롤러 매니저**

    쿠버네티스 클러스터의 오브젝트 상태를 관리한다.

    워커 노드에서 통신이 되지 앟는 경우, 상태 체크와 복구는 컨트롤러 매니저에 속한 노드 컨트롤러에서 이루어진다.

    레플리카셋 컨트롤러는 레플리카셋에 요청받은 파드 개수대로 파드를 생성한다. 

    <br>

- **스케줄러**

    노드의 상태와 자원, 레이블, 요구 조건등을 고려해 파드를 어떤 워커 노드에 생성할 것인지를 결정하고 할당한다. 스케줄러라는 일므에 걸맞게 파드를 조건에 맞는 워커 노드에 지정하고, 파드가 워커 노드에 할당되는 일정을 관리하는 역할을 담당한다.

<br><hr><hr><br>

## **개념**

- ### **오브젝트(Object)**

    파드와 디플로이먼트를 개별 속성을 포함해 부르는 단위를 말한다. 

    기본 오브젝트에는 다음 네가지가 존재한다.

    1. **파드(Pod)**
    
        쿠버네티스에서 실행되는 최소 단위, 즉 웹 서비스를 구동하는데 필요한 최소 단위를 말한다.(서버역할 담당)
        
        독립적인 공간과 사용 가능한 IP(클러스터 네트워크상의 IP)를 가지고 있다. 파드의 IP 주소는 k8s 클러스터 외부에서 직접 접속할 수 없다. 
        
        파드의 IP 주소는 기동시에 할당되고 종료시에 회수되어 다른 파드에 재할당 된다.(롤아웃이나 롤백, 스케일에 의해 파드가 종료되고 새롭게 만들어질 때 새로운 IP 주소가 할당된다.) 단 재가동 시(컨테이너의 프로세스가 이상 종료한 경우 파드가 컨테이너를 재시작하는경우)에는 파드의 IP가 바뀌지 않는다. 
        
        
        하나의 파드는 1개 이상의 컨테이너를 갖고 있기 때문에 여러 기능을 묶어 하나의 목적으로 사용할 수도 있다. 그러나 범용으로 사용할 때는 대부분 1개의 파드에 1개의 컨테이너를 적용한다.

        파드에는 컨테이너에 있는 애플리케이션의 정상 동작을 확인하는 활성프로브, 초기화가 완료되어 응답이 가능한 상태가 되었음을 확인하는 준비 상태 프로브 를 설정 할 수 있다. 

        그리고 컨테이너는 종료 요청 시그널(SIGTERM)에 대한 종료 처리를 수행하도록 구현해야 한다.

        <br>

        ![kubectl get po](/img/kubectl-get-po.png)

        - NAME: 파드의 오브젝트 명
        - READY: 가동 완료 수. 분자는 파드 내의 컨테이너가 가동된 수, 분모는 파드 내의 적의한 컨테이너의 총 수
        - STATUS: 파드의 상태. CrashLoopBackOff 는 컨터이너가 재시작을 반복하며 다음 재시작 전에 대기하고 있는 상태를 의미한다. 컨테이너를 가동할 때 리눅스의 프로세스 관리로 인해 CPU 부하가 많이 발생하기 때문에 계속된 반복에 의한 CPU 과부하를 막기 위해 일정 간격을 두고 재시작을 실행한다.
        - RESTARTS: 파드 안의 컨테이너의 재시작 횟수(파드의 IP주소 변경 X)
        - AGE: 파드 오브젝트가 만들어진 후 경과 시간
        
        <br>


        >**단독(알몸) 파드 구성**
        
        ![single-pod](/img/single-pod.png)
        - 비정상으로 종료해도 가동하지 않음
        - 컨테이너 종료 후, 삭제가 필요
        - 수평 스케일이 안됨

        <br>

        >**서버 타입의 파드 제어**

        ![servertype-pod-control](/img/servertype-pod-control.png)
        - 요구를 계속 대기하면서 종료하지 않는 타입
        - 수평 스케일
        - 비정상 종료 시 기동

        <br>

        >**배치 처리 타입 파드 제어**

        ![batchtype-pod-control](/img/batchtype-pod-control.png)
        - 처리가 정상 종료 시 완료함
        - 처리가 실패하면 재시도
        - 병렬 처리 수를 설정해서 시간 단축

        <br>


    <br>

    2. **네임스페이스(Namespace)**: 쿠버네티스 클러스터에서 사용되는 리소스들을 구분해 관리하는 그룹을 말한다. 특별히 지정하지 않으면 기본으로 할당되는 defaul, 쿠버네티스 시스템에서 사용되는 kube-system, 온프레미스에서 쿠버네티스를 사용할 경우 외부에서 쿠버네티스 클러스터 내부로 접속하게 도와주는 컨테이너들이 속해 있는 metallb-system이 존재한다.

    <br>

    3. **볼륨(Volume)**: 파드가 생성될 때 파드에서 사용할 수 있는 디렉터리를 제공한다. 기본적으로 파드는 영속되는 개념이 아니라 제공되는 디렉터리도 임시로 사용한다. 하지만 파드가 사라지더라도 저장과 보존이 가능한 디렉터리를 볼륨 오브젝트를 통해 생성하고 사용할 수 있다.

    <br>

    4. **서비스(Service)**: 파드는 클러스터 내에서 유동적이기 때문에 접속 정보가 고정적일 수 없다. 따라서 파드 접속을 안정적으로 유지하도록 서비스를 통해 내/외부로 연결된다. 그래서 서비스는 새로 파드가 생성될 때 부여되는 새로운 IP를 기존에 제공하던 기능과 연결해 준다. 쉽게 말해, 쿠버네티스 외부에서 쿠버네티스 내부로 접속할 때 내부가 어떤 구조로 돼 있는지, 파드가 살았는지 죽었는지 신경쓰지 않아도 이를 논리적으로 연결하는 것이 서비스 이다. 기존인프라에서 게이트웨이와 비슷한 역할을 한다. 

    
    <br>
    
    ![기본오브젝트](/img/standardobject.png)

    <br>

- ### **디플로이먼트(Deployment)**

    ![servertype-pod-control](/img/servertype-pod-control.png)

    <br>

    쿠버네티스에서 **가장 많이 사용되는 오브젝트**이며, 디플로이먼트 오브젝트는 **파드에 기반**을 두고 있으며, **레플리카셋 오브젝트를 합쳐 놓은 형테**(*주로 상태를 참조한다. 직접 레플리카셋 조작 하는 경우는 거의 없음.*)이다.

    디플로이먼트의 주된 역할은 파드의 개수를 관리하는 것이다. 파드(서버)의 개수를 관리하는 것은 시스템의 처리 능력, 서비스를 중단하지 않는 가용성, 그리고 비용측면에서 매우 중요하다.

    디플로이먼트는 요청한 개수만큼 파드를 가동하여, 장애 등의 이유로 파드의 개수가 줄어들면 새롭게 파드를 만들어 가동한다. 그리고 애플리케이션의 버전을 업그레이드 할 경우 새로운 버전의 파드로 조금씩 바꾸는 기능도 제공한다.

    <br>

    > **디플로이먼트 생성하고 삭제하기(1개의 파드 생성)**
    
    ![kubctl get deploy](/img/kubectl-get-deploy.png)

    ```shell
    # 디플로이먼트 생성
    1) kubectl create deployment {파드이름} --image={계정이름}/{이미지이름}

    2) kubectl apply -f <YAML_파일명>

    # 생성된 디플로이먼트 확인
    kubectl get deploy

    - NAME: 디플로이먼트의 오브젝트 명
    - DESIRED: 희망 파드의 개수, 디플로이먼트를 만들 때 설정한 파드 수
    - CURRENT: 현재 실행 중인 파드의 개수
    - UP-TO-DATE: 최근에 업데이트 된 파드의 개수. 즉, 컨트롤러에 의해 조정된 파드 수
    - AVAILABLE: 사용 가ㅣ능한 파드 개수. 즉 정상적으로 가동되어 서비스 가능한 파드 수
    - AGE: 오브젝트가 만들어진 후 경과 시간

    # 디플로이먼트 삭제
    kubectl delete deployment {파드이름}
    kubectl delete -f <YAML_파일명>
    ```

    <br>

    > **레플리카셋으로 파드 수 관리하기**

    *다수의 파드를 만들 때, 레플리카셋 오브젝트를 사용하여 효율적으로 파드를 생성한다.*

    `create` 에서는 relpicas 옵션을 사용할 수 없으며, `scale` 은 이미 만들어진 디플로이먼트에서만 사용할 수 있다.

    파드의 개수를 늘리는 중 k8s 클러스터의 자원(CPU와 메모리)가 부족해지면 노드를 추가하여 자원이 생길 때까지 파드생성이 보류된다.(메인 메모리로 파드를 배치할 수 없으면 파드는 생성되지 않는다.)

    *파드의 개수를 늘리기 전에 클러스터의 가용 자원을 확인하여 노드의 증설을 검토하자.*

    ```shell
    # 배포된 파드의 상태를 확인한다.
    kubectl get pods

    # scale 명령을 통해 파드 수를 증가시킨다. --replicas=3 는 파드의 수를 3개로 맞춘다는 것을 의미한다.
    # 파드의 수를 늘릴 오브젝트를 명시하지 않으면, 리소스를 확인할 수 없다는 에러가 발생한다.
    # Error from server (NotFound): the server could not find the requesetd resource
    kubectl scale pod deployment {파드이름} --replicas=3

    # scale 명령을 통해 생성된 파드를 확인하면 생성시간(AGE)이 짧은 것을 확인 할 수 있다.
    kubectl get pods

    # 생성된 파드 및 이전의 파드 모두 정상적으로 워커 노드에 적용되고 IP가 부여됬는지 확인한다.
    kubectl get pods -o wide

    # 오브젝트(현재 디플로이먼트)에 속한 모든 파드 삭제하기
    kubectl delete {파드이름}
    ```

    <br>

    >**스펙을 지정해 오브젝트 생성하기(다수의 파드 생성)**

    디플로이 먼트를 생성하면서 한번에 다수의 파드를 생성하기 위해 오브젝트 스펙(spec)을 작성한다.
    스펙은 YMAL 문법으로 작성한다.

    ![파일구조](/img/filestructure.png)

    <br>

    ```ymal
    apiVersion: apps/v1 # apps/v1 은 여러종류의 kind(오브젝트)를 가지고 있다.

    # 오브젝트 종류 
    kind: Deployment 
    metadata: 
    name: {디플로이먼트의이름} #네임스페이스 내에서 유일한 이름 값이여야함
    labels:
        app: {디플로이먼트의레이블}

    spec: # 디플로이먼트의 사양 참조
        replicas: {생성할파드의갯수} 
        selector:
            matchLabels: # 컨트롤러와 파드를 대응시키는 라벨
            app: {셀렉터의레이블} # <- 파드에 해당 라벨이 있어야함

        template: # 파드 템플릿
            metadata:
            labels:
                app: {템플릿의레이블} # 파드의 라벨로 컨트롤러의 matchLabels와 일치해야한다.
            spec: # 파드 템플릿의 사양 참조
                containers: # 컨테이너의 사양을 배열로 기술한다.
                - name: {컨테이너이름}}
                    image: {계정이름}/{이미지이름}
    ```    

    - **디플로이먼트 API(Deployment v1 apps)**
        - apiVersion: 오브젝트를 포함하는 API의 버전을 의미한다. 일반적으로 알파(alpha)와 베타(beta)버전은 안정적이라 보지않지만 그 만큼 풍부한 기능을 갖고 있다.
        - metadata: name에 오브젝트 이름을 설정한다.
        - kind: Deployment 를 설정한다.
        - spec: 이곳에 디플로이먼트의 사양을 기술한다.

        *더 많은 내용은 https://kubernetes.io/docs/reference/generated/kubernetes-api/v1.14/#deployment-v1-apps 를 참고한다.*

    <br>

    - **디플로이먼트 사양(Deploymentspec v1 apps)** 
        - replicas: 파드의 템플릿을 사용하여 가동할 파드의 개수를 지정한다. 디플로이먼트는 이 값을 유지하도록 동작한다.
        - selector: 디플로이먼트 제어하의 레플리카셋과 파드를 대응 시키기 위해 matchLabels의 라벨이 사용된다.(이 라벨과 파드 템플릿의 레이블과 일치하지 않으면 kubectl create/apply 시 에러가 발생한다.)
        - template: 파드 템플릿

        *각 항목의 자세한 내용은 https://kubernetes.io/docs/reference/generated/kuberetes-api/v1.14/#deploymentspec-v1-apps 를 참고한다.*

    <br>
    
    ![deploymentVsPod](/img/deploymentVsPod.png)

    <br>

    - **파드 템플릿(PodTemplateSpec v1 core)**
        - metadata: 이 라벨의 내용은 상기의 셀렉터가 지정하는 라벨과 일치해야한다.
        - containers: 파드 컨테이너의 사양

    *사용가능한 API 버전 확인하기: kubectl api-versions*

    <br>
    

    [추가적인 파드의 사양 확인](https://kubernetes.io/docs/reference/generated/kubernetes-api/v1.14/#podspec-v1-core)

- **컨테이너 설정(Container v1 core)**
    - image: 이미지의 리포지터리명과 태그
    - name: 컨테이너를 여러 개 기술할 경우 필수 항목
    - livenessProbe: 컨테이너 애플리케이션이 정상적으로 동작 중인지 검사하는 프로브
    - readinessProbe: 컨테이너 애플리케이션이 사용자의 요청을 받을 준비가 되었는지 검사하는 프로브
    - ports: 외부로부터 요청을 전달받기 위한 포트 목록
    - resources: CPU와 메모리 요구량과 상한치
    - volumeMounts: 파드에 정의한 볼륨을 컨테이너의 파일 시스템에 마운트하는 설정. 복수 개 기술 가능
    - command: 컨테이너 가동 시실행할 커맨드. args가 인자로 적용
    - args: command의 실행 인자
    - env: 컨테이너 내에 환경 변수를 설정


        [추가적인 컨테이너 설정](https://kubernetes.io/docs/referrence/generated/kubernetes-api/v1.19/#container-v1-core)

    <br>

    > **apply 로 오브젝트를 생성하고 관리하기**

    run 을 통해 파드를 간단하게 생성할 수 있지만, run 은 단일파드만 생성이 가능하다.
    
    create는 디플로이먼트를 생성하면 변경사항(파일에서 수정)을 바로 적용할 수 없다. 이런경우 apply를 통해 오브젝트를 관리하면 편리하다.

    ```shell
    kubectl apply -f 파일경로/파일이름.yaml
    ```

    ```shell
    |    구분    |  run  | create | apply |
    +-----------+-------+--------+-------+
    | 명령 실행   | 제한적  | 가능함   | 안 됨  |
    +-----------+-------+---------+------+
    | 파일 실행   | 안 됨  |  가능함   | 가능함 |
    +-----------+-------+---------+------+
    | 변경 가능   | 안 됨  |  안 됨   | 가능함 |
    +-----------+-------+---------+------+
    | 실행 편의성  |매우 좋음| 매우좋음  | 좋음  | 
    +-----------+-------+---------+------+ 
    | 기능유지    | 제한적임| 지원됨    | 다양하게 지원됨
    +-----------+-------+---------+------+
    ```

<br>

- ### **데몬셋(DaemonSet)**

<br>

- ### **컨피그맵(ConfigMap)**

<br>

- ### **레플리카셋(ReplicaSet)**

<br>

- ### **PV(Persistent Volume)**

<br>

- ### **PVC(PersistentVolumeClaim)**

<br>

- ### **스테이트풀셋(StatefulSet)**

<br>

- 등등..

<br><hr><hr><br>

## **파드의 컨테이너 자동 복구**

*쿠버네티스의 거의 모든 부분은 자동 복구 되도록 설계되어 있다.*

파드의 자동 복구 기술은 셀프힐링(Self-Healing) 이라고 하는데, 제대로 작동하지 않는 컨테이너를 다시 시작하거나 교체해 파드가 정상적으로 작동하게 하는 것을 말한다.

- 동작을 보증한다.
- 명령한 replicas의 수를 보증한다.(삭제할 경우 선언한 개수를 맞춘다., 이는 AGE를 통해 확인할 수 있다.)

<br>

`kubectl run`(생성 시) `--restart=OnFailure` 옵션 지정 시, **잡 컨트롤러**의 제어 하에 파드가 가동된다.

***잡 컨트롤러**는 파드가 비정상 종료하면 재시작하며 파드가 정상 종료 할 때까지 지정한 횟수만큼 재시작 한다.*


<br><hr><hr><br>

## **파드의 헬스체크**
*health-check.yaml.md 참고*

파드의 컨테이너에는 애플리케이션이 정상적으로 기동 중인지 확인하는 기능(헬스체크)을 설정할 수 있어, 이상이 감지되면 컨테이너를 강제 종료하고 재시작 할 수 있다.

쿠버네티스에서는 노드에 상주하는 `kubelet`이 컨테이너의 헬스체크를 담당한다.

  ![k8s-health-check](/img/k8s-health-check.png)


- ### **활성 프로브(Liveness Probe)**

    컨테이너의 애플리케이션이 정상적으로 실행 중인 것을 검사한다. 검사에 실패하면 파드상의 컨테이너를 강제로 종료하고 재시작 한다.

    이 기능을 사용하기 위해서는 매니페스트에 명시적으로 설정해야 한다.

<br>

- ### **준비 상태 프로브(Readiness Probe)**

    컨테이너의 애플리케이션이 요청을 받을 준비가 되었는지 아닌지를 검사한다. 검사에 실패하면 서비스에 의한 요청 트래픽 정송을 중지한다.

    파드가 기동하고 나서 준비가 될 때까지 요청이 전송되지 않기 위해 사용한다.

    이 기능을 사용하기 위해서는 매니페스트에 명시적으로 설정해야 한다.

<br>


- ### **프로브 대응 핸들러 종류**
    - `exec`: 컨테이너 내 커맨드를 실행한다.Exit 코드 0으로 종료하면 진단결과는 성공으로 간주되며, 그 외의 값은 실패로 간주한다.
    - `tcpSocket`: 지정한 TCP 포트번호로 연결할 수 있다면, 진단 결과는 성공으로 간주한다.
    - `httpGet`: 지정한 포트와 경로로 HTTP GET 요청이 정기적으로 실행된다. HTTP 상태 코드가 200이상 400 미만이면 성공으로 간주되고, 그 외에는 실패로 간주한다. 지정 포트가 열려 있지 않은 경우도 실패로 간주한다.


<br><hr><hr><br>

## **롤아웃(컨테이너 업데이트)**

쿠버네티스에서의 롤아웃은 컨테이너의 업데이트를 의미한다. 롤아웃을 하기 위한 절차는 다음과 같다.

1. **새로운 이미지 빌드 및 리포지토리 등록**
2. **새로운 이미지를 매니페스트의 Image에 기재**
3. **`kubectl apply -f` 를 통한 적용**

<br>

일정 개수만큼 정지하고 새로운 파드를 가동하는 것을 받복시킬 수 있다. 

하지만 새로운 버번과 이전 버전의 컨테이너가 동시에 돌아가는 것이 간으하려면 롤아웃 기능만으로는 불가능하다.(애플리케이션의 설계, 테이블 설계, 캐시 이용 등을 함께 고려)

롤아웃에 의해 종료되는 파드에는 종료 요청 시그널 SIGTERM이 전송된다.(만약 컨테이너에서 애플리케이션이 이 시그널을 받아 종료처리하지 않으면 30초 후에 SIGKILL에 의해 강제 종료된다.)
- 종료 요청 시그널을 받은 컨테이너는 더 이상 클라이언트로부터 새로운 요청을 전달받지 않게 되며, 해당 컨테이너는 시그널을 받고 종료 처리를 한 후 메인 프로세스를 종료시킨다.
- 컨테이너의 메모리나 파일에 보존된 데이터는 파드가 삭제되면서 잃어버리게 되므로 애플리케이션은 상태가 없어야한다.(`stateless`)(웹 애플리케이션의 경우 세션 정보는 외부 캐시에 보존해야 한다.)
    

<br>

![rollout.png](/img/rollout.png)

<br>

- **`25% max unavailable, 25% max surge`**

    최대 25%의 파드를 정지할 수 있고, 최대 25% 만큼 초콰할 수 있음을 의미한다.

    10개의 레플리카를 기준, 최소 파드 수는 `10-10*0.25=7.5(반올림하여8)`이 되고,
    
    초과 파드 수는 `10+10*0.25=12.5(반올림하여 13)`이 된다.

    즉, 최소 8개의 파드를 유지하고, 최대 13개의 파드가 되도록 롤아웃이 점진적으로 진행된다.


<br><hr><hr><br>

## 롤백(롤아웃 전에 사용하던 컨테이너로 되돌리기)

`kubectl rollout undo deployment <디플로이먼트 이름>`

신기능 출시 후, 문제가 발견된 경우, 간단하게 출시 이전 버전으로 돌이키는 방법을 말한다.

하지만 데이터베이스 등에 적재된 데이터까지 롤백되는 것은 아니다.(데이터리커버리는 별도로 구현한다.)