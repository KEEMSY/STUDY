# BASIC
## 실행 상태 확인
`docker ps`
- option
  `-a` : 멈춰있는 컨테이너까지 보여줌

## docker hub 로그인
`docker login`

## docker hub 로그아웃
`docker logout`

<br>
<hr>

# CONTAINER

## 컨테이너 실행
`docker run [OPTION] [IMAGE_NAME] [COMMAND]`
<br><br>

## 컨테이너 중지
`docker stop [CONTAINER ID]`
- 애플리케이션이 마지막으로 끝이 날 때, 정리해야하는 것이 있으면 정리할 시간을 보장해줌 <br> <br>

`docker kill`
- `docker stop`와는 달리, 바로 삭제됨
<br><br>

## 컨테이너 삭제
`docker rm [CONTAINER ID]`
- 실행중인 컨테이너는 삭제 할 수 없으므로, `docker stop` or `docker kill`을 수행한 뒤에 삭제 할 수 있다.
<br><br>


## 컨테이너 되살리기
`docker restart [CONTAINER ID]`
- 되살리고 나면 잘 살아 났는지 꼭 `docker ps` 를 통해 확인할 것  
<br><br>

## 컨테이너 확인하기
`docker exec [OPTION] [CONTAINER ID or CONTAINER NAME] [COMMAND]`

- `docker exec [CONTAINER ID] ls`
  - 컨테이너 내부에 있는 파일 목록과 폴더 구조를 보여줌
- `docekr exec -it [CONTAINER ID] sh`
  - 컨테이너 안에서 쉘 스크립트를 실행한다.(나가는건 exit)
  >
  > - `-i` : interactive 옵션으로 쉘스크립트에 사용되는 input값을 localhost에서 입력한 것을 컨테이너에서도 쓰겠다는 의미
  >  - `-t` : `-tty`옵션으로 터미널 환경을 만들어서 쉘 스크립트를 쓸 수 있는 환경을 위해 필요한 옵션 
* `cat` : 여기 있는 파일을 이 화면으로 보여주겠다.


<hr>

# IMAGE
## 로컬에 있는 이미지 확인
`docker images`

## 이미지 빌드
`docker built -t [DOCKER ID]/[IMAGE NAME]:[TAG]`

## 이미지 업로드

## 이미지 가져오기
`docekr pull [REPOSITORY NAME]:[TAG]`
- pull하는 이미지가 로컬에 없을 경우, 도커레지스트리에 등록된 이미지를 가져옴 

## 이미지 삭제
`docker rmi [IMAGE ID]`