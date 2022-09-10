## docker-compose.yaml
```yaml
version: "3"
services:
  db-master:
    build: 
      context: ./
      dockerfile: master/Dockerfile
    restart: always
    environment:
      MYSQL_DATABASE: 'db'
      MYSQL_USER: 'master'
      MYSQL_PASSWORD: 'test'
      MYSQL_ROOT_PASSWORD: 'password'
    ports:
      - '3306:3306'
    # Where our data will be persisted
    volumes:
      - my-db-master:/var/lib/mysql
      - my-db-master:/var/lib/mysql-files
    networks:
      - net-mysql
  
  db-slave:
    build: 
      context: ./
      dockerfile: slave/Dockerfile
    restart: always
    environment:
      MYSQL_DATABASE: 'db'
      MYSQL_USER: 'slave'
      MYSQL_PASSWORD: 'test'
      MYSQL_ROOT_PASSWORD: 'password'
    ports:
      - '3307:3306'
    # Where our data will be persisted
    volumes:
      - my-db-slave:/var/lib/mysql
      - my-db-slave:/var/lib/mysql-files
    networks:
      - net-mysql
  
# Names our volume
volumes:
  my-db-master:
  my-db-slave: 

networks: 
  net-mysql:
    driver: bridge
```
### 설명
- `master`는 로컬의 3306 포트에, `slave`는 로컬의 3307번으로 연결한다.
- mysql의 DATABASED의 이름은 `db`로 설정한다.
- mysql의 `유저`는 master(slave)로 생성하며, `비밀번호`는 test 로 생성한다.

<br> <br>

> **Container 내부 설정**

*`master`와 `slave`간 통신을 위한 `내부 IP 주소`(docker network로 지정된 IP)주소를 알아야 한다.*

-> `docker inspect {container name or container id}` 를 통해 확인한다

   *Master IP 확인 후, `mysql.md` 파일의 **Master, Slave 생성 시, 주의사항** 참고 하여 작성*