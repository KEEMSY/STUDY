## MySQL 설치(Docker)
*docker hub에서 원하는 버전의 mysql image를 다운로드*

command : `docker pull mysql:{version}`

## 기본 설정
*mysql.cnf 파일 설정을 통하여 기본 설정 값이 설정 됨*
### 환경 변수 확인(mysql cosole 접속)
- `SHOW VARIABLES` or `SHOW GLOBAL VARIABLES`


<br> <hr>

## Master - Slave 설정
*DB에 대한 트래픽을 분산하기 위해 사용 함*
- `master`에게는 데이터 동시성이 아주 높게 요구되는 트랜잭션을 담당하고, 
- `slave`에게는 읽기와 같은 데이터 동시성이 꼭 보장될 필요가 없는 경우에 읽기 전용으로 사용함
- `slave`를 만듬으로써, master가 장애가 났을 경우 데이터에 대한 복구를 빠르게 할 수 있다.
<br>


> Replication의 동작 원리

![원리](./img/mysql_replication원리.png)

1. 클라이언트가 Commit을 누르면 Master 서버에 존재하는 Binary log에 변경사항을 모두 기록한다.
2. Master Thread는 비동기적으로(복사되는시간을 기다리지 않음) Binary log를 읽어 Slave 서버로 전송한다.
3. Slave의 I/O Threadsms Master로 받은 변경 데이터들을 Relay log에 기록한다.
4. Slave의 SQL Thread는 Replay log의 기록들을 읽어 자신의 스토리지 엔진에 최종 적용한다.
<br>


> Master, Slave 생성 시, 주의사항

1. `my.cnf`파일 설정
    - `server_id` 값은 master와 slave가 달라야한다.
    - slave 서버는 master 서버의 DB와 버전이 같거나 더 높아야 한다.
2. `master 서버`에서의 `권한` 설정
    - **master**
        ```mysql
        create user '{USER}'@'{SLAVE_IP}' identified by '{PASSWORD}';
        grant all privileges on *.* to '{USER}'@'{SLAVE_IP}' with grant option;
        flush privileges;
        ```
    - **slave**
        ```mysql
        stop slave;
        change master to master_host = {MASTER_IP}, master_port = 3306, master_user = '{USER}', master_password = '{PASSWORD}', master_log_pos = {POSITION_NUMBER}, master_log_file = 'FILE_NAME';
        ```
        - `master_log_pos`과 `master_log_file`은 master서버의 mysql에서 `show master status;`를 통해 확인 할 수 있다.
        - 커맨드 입력 이 후 slave 서버의 mysql에서 `show slave status\G;`를 입력 했을 때,
        - `Slave_IO_Running: Yes, Slave_SQL_Running: Yes`가 나온다면 연결 성공
        - master 서버의 mysql에서 `show processlist\G;`를 통해 slave의 접속 정보를 확인 할 수 있다. 
            - `master has sent all binlog to slave; waiting for more updates`의 메세지가 보인다면 정상적으로 마스터로 접속하고 있음을 확인 할 수 있다.


## 사용자 추가, 삭제 / 권한 부여
- MySQL은 사용자 이름, 비밀번호, 접속 호스트로 사용자를 인증한다.
- MySQL은 로그인을 시도하는 위치가 어디인가 하는 것도 인증의 일부로 간주한다.
- MySQL에서 사용자 계정을 추가하고 권한을 추가하거나 제거하는데 GRANT 와 REVOKE 명령어 사용을 권장한다.
> 기본적으로 확인해야하는 사항들

- `use {DATABASE}` : 사용할 데이터 베이스 선택 <br>
- `selct user, host, password from user` : user 테이블 확인 <br>
-  `flush privileges;` : 변경된 내용을 메모리에 반영(권한 적용) <br>

### 사용자 추가
`create user '{USER}'@'{HOST}' identified by '{PASSWORD}`; 
### 사용자 삭제
`drop user {USER}@'{HOST}';`

### 권한 부여
- `create user '{USER}'@'{HOST}' identified by '{PASSWORD};'`
- `grant all privileges on {DB}.{TABLE} to '{USER}'@'{HOST}' with grant option;'`


