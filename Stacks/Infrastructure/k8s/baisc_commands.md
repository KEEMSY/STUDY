# Commands


<br>

### **파드 생성하기**
`kubectl create deployment POD_NAME --image=DOCKERHUB_ACCOUNT_NAME/IMAGE_NAME`

- docker hub의 작성된 image 를 통해 deployment object를 만든다.



<br>

`kubectl run POD_NAME --image=IMAEGE_NAME`
- **작동 순서**
    1. kubectl이 쿠버네티스에게 명령을 전달한다.
    2. 노드에 이미지가 없으면 원격 리포지터리(Docker Hub)에서 다운로드 한다.
    3. 노드의 containerd 가 컨테이너를 실행한다.
    4. kubectl이 터미널에 메세지를 표시한다.

<br>

> **create 와 run 의 차이**

*`run` 으로 파드를 생성하면 단일 파드 1개만 생성되고 관리된다. 그리고 `create deployment` 로 파드를 생성하면 `deployment` 라는 관리 그룹 내에서 파드가 생성된다.*

`create` 로 생성하려면 `kubectl create` 에 `deployment` 를 추가해서 실행해야한다. 

`run`으로 생성된 파드는 초코파이 1개로, `create deployment` 로 생성한 파드는 초코파이 상자에 들어있는 초코파이 1개로 묘사할 수 있다.

*`run` 커맨드는 서브 커맨드의 기능이 지나치게 방대해지는 것을 막기위해 파드 자체를 만들때만 사용하도록 한다.*

<br><hr><br>

### **파드 조회하기**
- kubectl get pods: pods 정보 확인
- kubectl get pods -o wide : 생성된 파드의 IP정보 확인
- kubectl get deployments: deployments 정보 확인

<br>

*생성된 파드의 IP 주소를 확인하면 curl 명령을 통해 작동하는지 확인하자.*

명령어 : `curl {IP}`

<br>

### **파드 삭제하기**
`kubectl delete OPJECT_KIND POD_NAME`

예시: `kubectl delete deployment testpod`

디플로이먼트에 속한 이름이 testpod 인 파드를 삭제한다.


<br>

### 레플리카셋으로 파드 수 관리하기
*다수의 파드를 생성할 때 레플리카셋 오브젝트를 사용한다.*

`kubectl scale`

파드를 3개를 만들 레플리카셋에 선언하면 컨트롤러 매니저와 스케줄러가 워커노드에 파드 3개를 만들도록 선언한다. 그러나 레플리카셋은 파드 수를 보장하는 기능만 제공하며, 롤링 업데이트 기능은 없다.(파드 수를 관리할 때는 롤링 업데이트 기능 등이 추가된 디플로이 먼트를 사용하자.)

<br>



<br><hr><hr><br>




> ## **minukube**
<br>

 ### **설치**
- brew install minikube 

<br>


### **확인**
- minikube start --driver={driver_name}: minikube 시작
- minikube status: minikube 의 상태를 확인한다.
- minikube dashboard : Cluster를 웹페이지로 띄운다.
