## Dockerfile 문법
```docker
FROM ubuntu:20.04

ENV NAME=KEEMSY \
    FROM=KOREA


MAINTAINER "ahr03003@gmail.com"


RUN apt-get update && \
    apt-get upgrade

RUN apt-get install openssh-server \
    net-tools


COPY . /home


WORKDIR /home 

COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh
ENTRYPOINT ["/entrypoint.sh"]

COPY run.sh /run.sh
RUN chmod +x /run.sh
```


- `FROM <이미지>[:태그]`: 컨테이너 BASE IMAGE 지정
- `MAINTAINER <이름>`: 이미지의 메타데이터에 저작권을 추가한다.
- `LABEL <key>=<value> <key>=<value>`: 컨테이너 정보 저장
- `RUN`: FROM의 베이스 이미지에서 커맨드를 실행
    - `RUN ["커맨드", "파라미터1", "파라미터2"]`

<br>

- `COPY <소스> <컨테이너 내 경로>`: 컨테이너 빌드시 소스(파일, 디렉토리, tar 파일, URL)를 컨테이너 내 경로에 복사
     - `COPY ["소스", ... , "<컨테이너 내 경로>"]`

<br>

- `ADD <소스> <컨테이너 내 경로>`: 컨테이너 빌드시 호스트 파일을 컨테이너로 복사 (tar, url(ftp://..)도 가능)
    - `ADD ["소스", ... , "<컨테이너 내 경로>"]`

<br>

- `WORKDIR /<path>`: RUN, CMD, ENTRYPOINT, COPY, ADD의 작업 디렉토리 지정
- `ARG <이름>[=<디폴트 값>]`: 빌드 할 때 넘길 인자를 정의
    - `--build-arg <변수명>=<값>`

<br>

- `USER <유저명> | <UID>`: 명령(RUN, CMD, ENTRYPOINT) 실행 시 적용할 유저 설정
- `VOLUME["/<path>"]`: 공유 가능한 볼륨을 마운트

- `EXPOSE <port>[<port>...]`: 컨테이너 동작 시 외부에서 사용할 포트 지정(공개 포트 지정)
- `CMD ["실행 바이너리", "파라미터1", "파라미터2"]`: 컨테이너 가동 시 실행될 커맨드를 지정한다.
    - `CMD <커맨드><쉘 형식>`
    - `CMD ["파라미터1", "파라미터2"](ENTRYPOINT의 파라미터)`

<br>

- `ENV <key><value>`: 환경변수 지정
    - 여러개의 환경변수를 지정할 경우 `\`를 사용하자(layer가 하나로 여러개의 변수를 지정할 수 있음)
    KEY2=VALUE2 \
    KEY2=VALUE2
    - <key>=<value> ...
    

<br>

- `ENTRYPOINT ["실행가능한 것", "파라미터1", "파라미터2"]`: 컨테이너 동작시 자동으로 실행될 서비스나 스크립트 지정(명령어 치환 불가), 컨테이너가 실행하는 파일을 지정한다.
    - `ENTRYPOINT 커맨드 파라미터1 파라미터 2(쉘 형식)`
