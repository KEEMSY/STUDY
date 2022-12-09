```yaml
apiVersion: v1
kind: Pod
metadata:
    name:webapl
spec:
    containers:
    - name: webapl
      image: user/image:tag # (1) 핸들러가 구현된 이미지
      livenessProbe: #(2) 활성 프로브에 대한 핸들러 설정
        httpGet:
            path: /healthz
            port: 3000
        initialDelaySeconds: 3 # 처음으로 검사하기 전의 대기 시간
        periodSeconds: 5 # 검사 간격
    readinessProbe:    # (3) 준비 상태 프로브에 대한 핸들러 살장
        httpGet:
            path: /ready
            port: 3000
        initialDelaySeconds: 15
        periodSeconds: 6
```

- (1)의 이미지 애플리케이션에는 프로브에 대응하는 핸들러가 구현되어 있다.(Node.js 로 작성됨)
- 기본설정으로 활성 프로브가 연속해서 3번 실패하면 kubelet이 컨테이너를 강제 종룟하고 재기동한다.(컨테이너가 재시작되면 컨테이너에 있던 정보들은 별도로 저장하지 않은 이상 지워진다.)

<br>

- ### **프로브 대응 핸들러 기술예시**
    - `httpGet`: HTTP 핸들러
        - `path` / `port`: 핸들러의 경로 / HTTP 서버의 포트 번호

        ```yaml
        readinessProbe:
            httpGet:
                path: /ready
                port: 3000
        ```


    <br>

    - `tcpSocket`: TCP 포트 접속 핸들러
        - `port`: 감시 대상의 포트 번호

        ```yaml
        readinessProbe:
            tcpSocket:
                port: 80
        ```

    <br>

    - `exec`: 컨테이너 내의 커맨드 실행 핸들러
        - `command`: 컽네이너의 커맨드를 배열로 기술

        ```yaml
        livenessProbe:
            exec:
                command:
                    - cat
                    - /tml/healthy
        ```


    <br>

    - `initialDelaySeconds`: 프로브 검사 시작 전 대기 시간
    - `periodSeconds`: 검사 간격
    