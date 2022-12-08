# 예시: nginx-pod.yaml
```yaml
apiVersion: v1
kind: Pod
metadata:
  name: nginx
spec:
  containers:
  - name: nginx    # 메인 컨테이너
    image: nginx:latest
  - name: cloner   # 사이드카 컨테이너
    image: user/image:tag
```

<br>

## 설명

### **1. 콜론(:)의 왼쪽은 `키` 이며 오른쪽은 `값`을 의미한다.**

- `apiVersion: v1` 에서 `apiVersion`은 키, `v1`은 값이다.
- `containers:` 의 경우 containers 가 키, 그 밑의 내용들이 값이다.

<br>

### **2. 들여쓰기로 복잡적 데이터 구조를 표현한다.**
- 키 metadata 밑에 name 의 키와 값을 기술한다.
- 키 spec 밑에 containers, volumes 등의 키와 값을 기재한다.

<br>

### **3. 하이픈(-)으로 시작하는 줄은 배열의 요소가 된다. 이어서 하이픈이 없는 줄은 윗줄과 같은 배열의 요소로 취급된다.**
- YAML의 구조
```yaml
containers:
- name: nginx # 첫번째 요소(메인 컨테이너)
  image: nginx:latest
- name: cloner # 두 번째 요소(사이드카 컨테이너
  image: user/image:tag
```
위 YMAL을 프로그램의 변수에 저장하면 [[첫 번째 요소], [두번째 요소]]와 같이 된다.

`[{'image': 'nginx:latest', 'name': 'nginx'}, {'image': 'user/image:tag', 'name': 'cloner'}]`



  ```yaml
  # API 버전
  # apps/v1 은 여러종류의 kind(오브젝트)를 가지고 있다.
  apiVersion: apps/v1

  # 오브젝트 종류 
  kind: Deployment 
  metadata:
  name: {디플로이먼트의이름}
  labels:
      app: {디플로이먼트의레이블}

  spec:
  # 몇개의 파드를 생성할지 결정
  replicas: {생성할파드의갯수} 

  selector:
      matchLabels:
      app: {셀렉터의레이블}

  template:
      metadata:
      labels:
          app: {템플릿의레이블}

      # 템플릿에서 사용할 컨테이너 이미지 지정
      spec:
      containers:
      - name: {컨테이너이름}}
          image: {계정이름}/{이미지이름}
  ```