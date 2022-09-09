## 설치
> ubuntu 20.04
- `sudo curl -L \ 
"https://github.com/docker/compose/releases/download/1.28.5/dockercompose-$(uname -s)-$(uname -m)" \ 
-o /usr/local/bin/docker-compose`
    
    - 결과적으로 "/usr/local/bin/" 경로에 "docker-compose" 라는 이름의 파일로 다운된다.
    - 최신 버전을 설치하고 싶다면 위 명령어에 보이는 1.28.5 라는 버전 숫자를 바꿔주면 됨
- `sudo chmod +x /usr/local/bin/docker-compose  `
    - chmod 를 통해서 실행이 가능하게 세팅

<br>
<hr>

## `OPTIONS`
### `-f` 
*다른 이름이나 경로의 파일을 Docker Compose 설정 파일로 설정하고 싶다면 `-f` 옵션으로 명시한다.*

command : `docker-compose -f docker-compose-local.yml up`

<br>
<hr>

### `up`
*docker compose 에 정의되어 있는 모든 서비스 컨테이너를 한번에 생성하고 실행하기 위해서 사용함*
- `-d` 옵션과 함께 사용하여 백그라운드에서 컨테이너를 띄우는 경우가 많다.
    

command : `docker-compose up -d`

<br>
<hr>

### `down`
*docker compose에 정의되어 있는 모든 서비스 컨테이너를 한번에 정지시키고 삭제힘(`docker-compose up`이랑 정반대의 동작을 함.)* 

command : `docker-compose down`

<br>
<hr>

### `start`
*내려가 있는 특정 서비스 컨테이너를 올릴 때 사용함.(`docker-compose up`을 사용해도 내려간 서비스를 알아서 올려줌)*

command : `docker-compose start {name}`

<br>
<hr>

### `stop`
*돌아가고 있는 특정 서비스 컨테이너를 정지 시키기 위해서 사용함(`docker-compose start`와 정반대의 동작을 함)*
command : docekr-compose stop {name}

<br>
<hr>

### `ps`
*docker compose에 정의되어 있는 모든 서비스 컨테이너 목록을 조회할 때 사용함.*
command : `docker-compose ps`


<br>
<hr>

### `logs`
*서비스 컨테이너의 로그를 확인하고 싶을 때 사용한다.*
- `-f`
command : `docker-compose logs -f {name}`

<br>
<hr>

### `exec`
*실행 중인 서비스 컨테이너를 대상으로 특정 명령어를 날릴 때 사용 함*

command : `docker-compose exec {} {} {} {}`

<br>
<hr>

### `run`
*컨테이너의 특정 명령어를 일회성으로 실행할 때 사용함.*
command : `docker-compose run {} {}`

<br>
<hr>

### `config`
*docker compose 설정 파일을 확인 할 때 사용함.*
- `-f` 옵션으로 여러 개의 설정파일을 사용할 때, 최종적으로 어떻게 설정이 적용되는지 확인해 볼 때 유용하다.
command : `docker-compose config`