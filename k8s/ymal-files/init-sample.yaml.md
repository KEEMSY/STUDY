# **파드 내에 초기화만을 담당하는 컨테이너 설정**

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: init-sample
spec:
  containers:
  - name: main           # 메인 컨테이너
    image: ubuntu
    command: ["/bin/sh"]
    args: [ "-c", "tail -f /dev/null"]
    volumeMounts:
    - mountPath: /docs   # 공유 볼륨 마운트 경로
      name: data-vol
      readOnly: false

  initContainers:        # 메인 컨테이너 실행 전에 초기화 전용 컨테이너를 기동 
  - name: init
    image: alpine
    ## 공유 볼륨에 디렉터리를 작성하고, 소유를 변경
    command: ["/bin/sh"]
    args: [ "-c", "mkdir /mnt/html; chown 33:33 /mnt/html" ]
    volumeMounts:
    - mountPath: /mnt    # 공유 볼륨 마운트 경로 
      name: data-vol
      readOnly: false

  volumes:               # 파드의 공유 볼륨
  - name: data-vol
    emptyDir: {}
```

초기화만을 수행하는 컨테이너와 요청을 처리하는 컨테이너를 변도로 개발하여 각각 재사용하기 위해 사용한다.
- **사용 예시**
    - 스토리지를 마운트 할 때 '스토리지 안에 새로운 디렉터리를 만들고, 소유자를 변경한 후 데이터를 저장' 하는 것과 같은 초기화 처리 전담.


현재 파일 설명

1. 초기화 전용 컨테이너가공유 볼륨을 '/mnt'에 마운트한다.
2. 디럭테리 '/mnt/html'을 만들고 소유자를 변경한다.
3. 명령어를 실행하고 바로 종료한다.
4. 이 후 메인 컨테이너가 가동하여 공유 볼륨을 마운트 한다.

