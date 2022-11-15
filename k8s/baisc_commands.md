# Commands



<br>

### **파드 생성하기**
- kubectl create deployment {name } --image={docker_hub/image_name}: docker hub의 작성된 image 를 통해 deployment object를 만든다.

- kubectl run {POD NAME} --image={IMAEGE NAME}

<br>

> **create 와 run 의 차이**

*run 으로 파드를 생성하면 단일 파드 1개만 생성되고 관리된다. 그리고 create deployment 로 파드를 생성하면 deployment 라는 관리 그룹 내에서 파드가 생성된다.*

create 로 생성하려면 kubectl create 에 deployment 를 추가해서 실행해야한다. 

run으로 생성된 파드는 초코파이 1개로, create deployment 로 생성한 파드는 초코파이 상자에 들어있는 초코파이 1개로 묘사할 수 있다.

<br><hr><br>

### **파드 조회하기**
- kubectl get pods: pods 정보 확인
- kubectl get pods -o wide : 생성된 파드의 IP정보 확인
- kubectl get deployments: deployments 정보 확인

<br>

*생성된 파드의 IP 주소를 확인하면 curl 명령을 통해 작동하는지 확인하자.*

명령어 : `curl {IP}`

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
