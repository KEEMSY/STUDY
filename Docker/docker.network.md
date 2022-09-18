# **NETWORK**

## **이점**
- 네트워크연결을 통해 외부 노출 없이 컨테이너 간 통신이 가능하다.

<br>

> 일종의 hosts 파일 내 alias 처리로 컨테이너 이름을 통한 통신도 가능해진다.
- 예시 : a - b 컨테이너가 묶여 있을 경우 a 컨테이너에 접근 후 쉘을 통해 ping b를 입력 시 b가 a쉘을 통해 응답하게 된다. <br>
*상기 기능을 통해 nginx 설정 파일 내에서 프록시 설정 시 연결된 네트워크를 입력하여 설정 할 수 있다.*

<br><hr><br>

## **설정 방법**

### 네트워크 생성
`docker network create [NETWORK NAME]`

<br><br>

### 도커 실행 및 연결
`docker run --network [NETWORK NAME] ...(추가 설정)...`

<br><br>


### 실행중인 컨테이너 연결
`docker network connect [NETWORK NAME] [CONTAINER NAME]`

<br><br>


### docker-compose 내 network 설정 
```docker
networks:
  [network]:
    external: true
  [network2]:
    external: true
...생략
services:
...생략
    networks:
      - [network]
      - [network2]

```