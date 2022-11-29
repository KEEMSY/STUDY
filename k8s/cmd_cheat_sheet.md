# `kubectl <(1) 커맨드> <(2) 리소스 타입> [이름] [(3) 옵션]`

kubectl의 커맨드 기본 구조는 다음 세부분으로 구성된다.

1. (1) 커맨드로 동작을 지정
2. (2) 리소스 타입과 이름을 통해 대상이 되는 오브젝트를 지정
3. (3) 옵션을 지정

<br><hr><hr><br>

## **첫번째 파라미터: (1) 커맨드**

<br>

> ### **get**

`get`은 지정한 오브젝트의 목록을 한 줄에 하나씩 출력한다.

- kubectl get -f <매니페스트 | 디렉터리>
- kubectl get <리소프_타입>
- kubectl get <리소스_타입> <이름>
- kubectl get <리소스_타입> <이름>

<br><hr><br>

> ### **describe**

`describe`은 get 보다도 자세한 정보를 출력한다.

- kubectl describe -f <매니페스트 | 디렉터리>
- kubectl describe <리소프_타입>
- kubectl describe <리소스_타입> <이름>
- kubectl describe <리소스_타입> <이름>

<br><hr><br>

> ### **apply**

매니페스트에 기술된 오브젝트가 존재하지 않으면 생성하고, 존재하면 변경한다.

- kubectl apply -f <매니페스트>


<br><hr><br>

> ### **create**

매니페스트에 기술된 오브젝트를 생성한다.(이미 있는 경우 에러를 반환한다.)

- kubectl create -f <파일명>

<br><hr><br>

> ### **delete**

매니페스트에 기술된 오브젝트를 삭제한다.

- kubectl delete -f <파일명>
- kubectle delete <리소스_타입> <이름>

<br><hr><br>

> ### **config**

접속 대상이 되는 콘텍스트(k8s 클러스터, 네임스페이스, 유저)의 목록을 출력하거나 선택한다.

- kubectl config get-contexts
- kubectle config use-context<콘텍스트명>

<br><hr><br>

> ### **exec**

컨테이너에 대화형으로 커맨드를 실행한다. 파드 내에 컨테이너가 여러 개 있는 경우 [-c]로 컨테이너 명을 지정한다. (컨테이너 명은 `kubectl get describe <파드명>`으로 확인 가능하다.)

- kubectl exec -it <파드명> [-c 컨테이너 명] <커맨드>


<br><hr><br>

> ### **run**

파드를 실행한다.

- kubectl runm <이름> --image=<이미지 명>

<br><hr><br>

> ### **logs**

컨테이너의 로그를 표시한다.

- kubectl logs <파드명> [-c 컨테이너 명]



<br><hr><hr><br>

## **두번째 파라미터: (2) 리소스 타입**
작성된 리소스 타입은 다음과 같다.
- 파드 관련
- 서비스 관련
- 컨트롤러 관련
- 볼륨 관련
- k8s 클러스터의 구성 관련
- 컨피그맵과 시크릿 관련 
- 네임스페이스 관련
- 역할 기반 엑세스 제어(RBAC)관련
- 보안 관련
- 자원 관리 관련

<br>

> ### **파드 관련 리소스 타입**
- pod(po)

    이름 : 파드

    컨테이너의 최소 기동 단위로, 기동 시 파드 네트워크상의 IP주소를 할당 받으며, 한 개 이상의 컨테이너를 내포한다.
    
    https://kubernetes.io/docs/concepts/workloads/pods/

    <br>

- poddisruptionbudget(pdb)

    이름: 파드 정지 허용 수

    파드의 개수가 지정한 개수 이하가 되지 않도록 디플로이먼트, 스테이트 풀셋, 레플리카셋, 레플리케이션 컨트롤러의 동작을 제어한다.

    https://kubernetes.io/docs/concepts/workloads/pods/disruptions/



<br><hr><br>

> ### **서비스 관련 리소스 타입**
- service(svc)

    이름: 서비스 

    파드를 클라이언트에 공개

    https://kubernetes.io/docs/concepts/services-networking/service/

    <br>

- endpoints(ep)

    이름: 엔드포인트 

    서비스를 제공하는 파드의 IP 주소와 포트를 관리한다.

    https://kubernetes.io/docs/concepts/services-networking/connect-applications-service/#creating-a-service

    <br>

- ingress(ing)

    이름: 인그레스 

    서비스 공개, TLS 암호, 세션 유지, URL 매핑 기능을 제공한다.

    https://kubernetes.io/docs/concepts/services-networking/ingress/

    <br>


<br><hr><br>

> ### **컨트롤러 관련 리소스 타입**
- deployment(deploy)

    이름: 디플로이먼트 

    파드의 레플리카 수, 자기 회복, 롤아웃, 롤백 등을 제어하는 컨트롤러.

    https://kubernetes.io/docs/concepts/workloads/controllers/deployment/

    <br>

- replicas(rs)

    이름: 레플리카셋

    파드의 레플리카 수를 제어하는 컨트롤러로, 디플로이먼트와 연계하여 동작한다.

    https://kubernetes.io/docs/concepts/workloads/controllers/relicaset/

    <br>

- statefules(sts)

    이름: 스테이트풀셋 

    퍼시스턴트 데이터를 보유하는 파드를 제어하는 컨트롤러이다. 퍼시스턴트 불륭과 파드를 하나씩 쌍으로 묶어 각 이름을 동일한 일련 번호를 부여하여 관리한다.

    https://kubernetes.io/docs/concepts/workloads/controllers/staefulset/

    <br>


- job

    이름: 잡

    배치 처리를 수행하는 파드를 관리하는 컨트롤러

    https://kubernetes.io/docs/concepts/workloads/controllers/jobs-run-to-comletion/

    <br>

- cronjob

    이름: 크론잡

    정기적으로 실행되는 배치 처리를 관리하는 컨트롤러.

    https://kubernetes.io/docs/concepts/workloads/controllers/cron-jobs/

    <br>

- daemonset(ds)

    이름: 데몬셋

    모든 노드에 파드를 배치하는 컨트롤러

    https://kubernetes.io/docs/concepts/workloads/controllers/daemonset/

    <br>

- replicationcontroller(rc)

    이름: 레플리케이션 컨트롤러

    파드의 레플리카 수를 제어하는 컨트롤러. 레플리카셋의 이전 버전이다.

    https://kubernetes.io/docs/concepts/workloads/controllers/replicationcontroller/

    <br>

- horizontalpodautoscaler

    이름: Horizontal Pod Autoscaler

    워크로드에 따라 파드 수를 조절하는 컨트롤러.

    https://kubernetes.io/docs/concepts/workloads/controllers/horizontal-pod-autoscale/

    <br>

<br><hr><br>

> ### **볼륨 관련 리소스 타입**
- persistentvolume(pv)

    이름: 퍼시스턴트 볼륨 

    로우 레벨 스토리지 관리

    https://kubernetes.io/docs/concepts/storage/persistent-volumes/

    <br>

- persistentvolumeclaim(pvc)

    이름: 퍼시스턴트 볼륨 클레임

    스토리지 클래스와 용량을 지정해서 논리 볼륨의 프로비저닝을 요구한다.

    https://kubernetes.io/docs/concepts/storage/persistent-volumes/#persistentvolumeclaims

    <br>

- storageclass(sc)

    이름: 스토리지 클래스

    퍼시스턴트 데이터를 보유하는 파드를 제어하는 컨트롤러이다. 퍼시스턴트 불륭과 파드를 하나씩 쌍으로 묶어 각 이름을 동일한 일련 번호를 부여하여 관리한다.

    https://kubernetes.io/docs/concepts/storage/storage-classes/

    <br>

<br><hr><br>

> ### **클러스터의 구성 관련 리소스 타입**
- node(no)

    이름: 노드

    k8s 클러스터의 워크로드를 실행하는 서버

    https://kubernetes.io/docs/concepts/architecture/nodes/

    <br>

- apiservice

    이름: API 서비스

    마스터가 지원하는 API 서비스를 관리한다.

    https://kubernetes.io/docs/concepts/overview/kubernetes-api/

    <br>

- componetstatuses(cs)

    이름: 컴포넌트 상태

    scheduler, controller-manager, etcd-O 에 헬스 체크 결과를 보고한다.

    https://kubernetes.io/docs/concepts/overview/componets/

    <br>

- controllerrevision

    이름: 컨트롤러 리비전

    컨트롤러의 리비전 관리를 담당한다.

    https://kubernetes.io/docs/tasks/manage-daemon/rollback-daemon-set/

    <br>

- event

    이름: 이벤트

    k8s 클러스터에서 발생한 이벤트를 기록하고 표시하기 위한 컨트롤러이다.

    https://kubernetes.io/docs/tasks/debug-application-cluster/debug-application-introspection/#example-debugging-pending-pods
    
    <br>

<br><hr><br>

> ### **컨피그맵과 시크릿 관련 리소스 타입**
- configmap(cm)

    이름: 컨피그맵

    설정 파일을 저장한다.

    https://kubernetes.io/docs/tasks/configure-pod-container/configure-pod-configmap/

    <br>

- secrete

    이름: 시크릿

    패스워드 등 비밀성이 필요한 정보를 저장한다/

    https://kubernetes.io/docs/concepts/configureation/secret/
    
    <br>

<br><hr><br>

> ### **네임스페이스 관련 리소스 타입**
- namespace(ns)

    이름: 네임스페이스

    k8s 클러스터를 논리적으로 분할해서 사용한다.

    https://kubernetes.io/docs/concepts/overview/working-with-objects/namespaces/

    <br>

> ### **역할 기반 액세스 제어(RBAC) 관련 리소스 타입**
- serviceaccount(sa)

    이름: 서비스 어카운트

    서비스 어카운트는 파드에서 실행되는 프로세스를 위한 어카운트이다. 접근 권한을 식별하기 위해 사용한다.

    https://kubernetes.io/docs/reference/access-authn-authz/service-accounts-admin/

    <br>

- role

    이름: 롤

    일련의 권한을 기술하여 롤을 정의한다. 롤의 유효 범위는 네임스페이스로 한정된다.

    https://kubernetes.io/docs/reference/access-authn-authz/rbac/

    <br>

- rolebinding

    이름: 롤바인딩

    서비스 어카운트와 롤을 바인딩 한다.

    https://kubernetes.io/docs/reference/access-authn-authz/rbac/#default-roles-and-role-bindings

    <br>

- clusterrole

    이름: 클러스터 롤

    k8s 클러스터 전체에 유효한 클러스터 롤과 서비스 어카운트를 매핑한다.

    https://kubernetes.io/docs/reference/access-authn-authz/rbac/#api-overview

    <br>

- clusterrolebinding

    이름: 클러스터 롤 바인딩

    k8s 전체에 유효한 클러스터 롤과 서비스 어카운트를 매핑한다.

    https://kubernetes.io/docs/reference/access-authn-authz/rbac/#api-overview

    <br>

<br><hr><br>

> ### **보안관련 리소스 타입**
- certificatesigningrequest(csr)

    이름: 루트 인증서 서명 요구

    인증 기관(CA)에 인증서 서명 요구 작성

    https://kubernetes.io/docs/reference/access-authn-authz/rbac/#api-overview

    <br>

- networkpolicies(netpol)

    이름: 네트워크 폴리시

    네임스페이스 사이의 네트워크 접근 제어

    https://kubernetes.io/docs/concepts/services-networking/network-policies/
    
    <br>

- podsecuritypolicies(psp)

    이름: 파드 시큐리티 폴리시

    파드 시큐리티 관련 항목의 기본 값

    https://kubernetes.io/docs/concepts/policy/pod-security-policy/
    
    <br>

<br><hr><br>

> ### **자원 관련 리소스 타입**
- limitrange(limits)

    이름: limit range

    네임스페이스 내 컨테이너의 CPU와 메모리 요구값과 상한값의 기본 값 설정

    https://kubernetes.io/docs/tasks/administer-cluster.manage-resources/memory-default-namespace

    <br>

- resourcequota(quota)

    이름: resource quota

    네임스페이스별 CPU와 메모리 요구량, 상한값 설정

    https://kubernetes.io/docs/concepts/policy/resource-quotas/
    
    <br>

<br><hr><hr><br>

## **옵션**

- `-n 네임스페이스 명`

    조작 대상을 지정한 네임스페이로 한정

    <br>

- `--all namespaces -A(v1.14부터)`

    모든 네임스페이스의 오브젝트를 대상으로 한다

    <br>

- `-o=yaml`

    YMAL 포맷으로 API 오브젝트를 표시한다.

    <br>

- `-o=wide`

    추가 정보를 표시한다.(파드의 IP 주소, 배치된 노드 이름 등)

    <br>

- `-o=json`

    JSON 형식으로 API오브젝트를 표시한다.

    <br>

- `-o=custom-columns=<spec>`

    항목을 지정해서 목록을 표시한다.

    <br>

- `-o=custom-columns-file=<file>`

    템플릿 파일로 출력할 컬럼을 지정한다.

    <br>

- `-o=jsonpath=<template>`

    jsonpath에 일치하는 목록 표시
    https://kubernetes.io/docs/reference/kubectl/jsonpath

    <br>

- `-o=jsonpath-file=<filename>`

    jsonpath 형식의 템플릿 파일로 출력할 내용 지정

    <br>

참고: https://kubernetes.io/docs/reference/kubectl/overview/#output-options

<br><hr><hr><br>

## **kubectl 커맨드 예시**

<br>

> ### **클러스터의 네임스페이스 수정**

여러개의 k8s 클러스터를 바꿔가면서 조작할 때 유용한 커맨드가 `config` 이다. 이 커맨드를 통해 온프레미스와 클라우드의 k8s 클러스터를 바꿔가며 조작할 수 있다.

<br>

```shell
# kubeconfig 파일 설정 표시
kubectl config view

# 복수의 kubeconfig가 있는 경우, 환경 변수 KUBECONFIG에 설정해서 병합 가능
KUBECONFIG=~/.kube/config:~/.kube/kubeconfig2 kubectl config view

# 컨텍스트 목록 표시. 콘텍스트 쿄체
kubectl config get-contexts
kubectl config use-context my-cluster-name

# 선택중인 컨텍스트 표시
kubectl config cuurent-context

# 네임 스페이스를 지정하여 콘텍스트 작성
# 사전에 '--user=<유저명>', '--cluster=<k8s 클러스터명>' 이 지정되있어야 함.
kubectl config set-context production-c3 --namespace=production --cluster=c3 --user=admin-c3

# 콘텍스트 변경(네임스페이스 변경)
kubectl config use-context production-c3
```

<br><hr><br>

> ### **오브젝트 작성**

`YAML` 형식이나 `JSON` 형식의 매니페스트로부터 오브젝트를 작성한다. 작성한 코드의 `create` 는 `apply`로 바꿔도 무방하다.

`create`를 사용하는 경우 이미 해당 오브젝트명이 존재하면 이상 종료로 처리된다.

`apply`는 기존에 존재하는 오브젝트에 적용할 변화가 없다면 unchanged 라고 표시되어 정상 종료로 취급된다.

따라서, 오브젝트를 목적 상태로 만들기 위해서는 `apply`가 적합하며, apply를 적극 활용하자.


```shell
# 매니페스트 파일로부터 오브젝트 생성
kubectl create -f my_manifest.yaml

# 복수의 ㅁ내니페스트 파일을 지정하면서 작성
kubectl create -f my_manifest1.yaml -f my_manifest2.yaml

# 복수의 매니페스트가 있는 디렉토리로부터 오브젝트를 생성
kubectl create -f <manifest_directory>

# URL로 부터 오브젝트 작성
kubectl create -f https://temp.com/temp_dir/my_manifest.yaml
```

<br>

> ### **오브젝트 삭제**
한번 만들어진 오브젝트는 지워질 때 까지 계속 존재한다. 따라서 불필요한 오브젝트는 삭제하도록 하자.

매니페스트나 매니페스트가 담긴 디렉터리 일므을 지정해서 제거하는 기능을 이용하면 삭제 시 편리하다.

```shell
# 매니페스트 파일로부터 삭제
kubectl delete -f my_manifest.yaml 

# 파드 이름으로 삭제
kubectl delete po nginx 

# 디플로이 먼트 이름으로 삭제
kubectl delete deploy web-deploy

# 서비스 이름으로 삭제
kubectl delete service webservice

# 디렉터리 내 매니페스트로 삭제
kubectl delete -f <directory>
```

<br>

### **오브젝트 표시**
오브젝트를 표시할 때에는 get 혹은 describe 를 사용한다.

get 은 하나의 오브젝트를 한 줄에 표시하고,

describe는 상세한 정보를 표시한다.

여러 리소스 타입을 콤마(',')로 이어서 입력하면 한번에 여러 리소스를 표시 할 수 있다.

```shell
# 서비스 리스트 표시
kubectl get service
kubectl get svc # 단축형



# 파드의 리스트 표시
kubectl get pods
kubectl get po 

# 모든 네임스페이스의 파드 표시
kubectl get po --all-namespaces 

# 네임스페이스 test의 파드 표시
kubectl get po -n test

# 파드의 IP 주소나 할당된 노드를 표시
kubectl get -o wide

# node1의 파드를 표시
kubectl get po --field-selector=spec.nameNode=node1

# 라벨 app=web인 파드를 표시
kubectl get po --show-labels --selector=app=web



# 파드의 상세정보 표시
kubectl describe po # 모든 파드의 상세 정보를 표시

# 파드 이름으로 지정한 파드의 상세 정보 표시
kubectl describe po <파드명>



# 디플로이먼트 표시
kubectl get deployment
kubectl get deploy # 단축형

# 디플로이먼트와 파드를 표시
kubectl get deploy,po
```

<br>

> ### **오브젝트 변경**
실행중인 오브젝트 사양을 수정한다. 변화가 적용될 때 서비스를 멈추지 않고 제어할 수 있다.

```shell
# 매니페스트에 의한 변경 적용
kubectl apply -f new_my_manifest.yaml
kubectl replace -f new_my_manifest.yaml

# 실행중인 컨트롤러의 매니페스트 편집
kubectl edit deploy/web-deploy
KUBE_EDITOR='nano' kubectl edit deploy/web-deploy

# 파드의 라벨 추가
kubectl label pods web-deploy-1234566789 mark=1
```

<br>

> ### **패치 적용**
매니페스트의 일부 값을 커맨드로 적용한다.

```shell
# node1에 대한 스케줄 정지
kubectl patch node node1 -p '{"spec":{"unschedulable":true}}'
```

<br>