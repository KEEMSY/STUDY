## **컨테이너 생성**
`docker run -d -p [host]:[container] --name [CONTAINER NAME] -v [host path]:[container path] -e RIT_LOG_LEVEL=INFO [IMAGE]`

- `--name` : 컨테이너 이름 지정
- `-p[호스트포트]:[컨테이너 포트]` : 호스트와 컨테이너의 포트를 매핑한다.
- `-v [호스트 디렉토리]:[컨테이너디렉토리]` : 볼륨을 지정한다.
- `-env ` or `-e` : 환경변수를 설정한다.
- `-d` : 백그라운드 실행

<br><hr><br>

## **컨테이너 확인**
### **전체 컨테이너 확인**
`docker ps (-a)`

<br> <br>

### **컨테이너 세부정보 출력**
`docker inspect [CONTAINER NAME or CONTAINER ID]`

<br> <br>

### **컨테이너 내부 확인**
`docker exec -it [CONATINER NAME or CONTIANER ID] bash`

<br> <br>

### **컨테이너 로그 확인**
`docker logs [CONTAINER NAME or CONTAINER ID]`


