## mysqldump
*mysql을 백업할 때 사용 함*
- 대용량 Data 처리이 DB Engine Type에 따라 상당한 속도 차이가 발생할 수 있음.
    - 대용량 처리시 MyISAM을 사용하자.

<br>
<hr>

## COMMANDS
### `mysqldump -u {user} -p {datbase} {table} > {저장될 파일 명}`
*dump 데이터 생성*

command : `mysqldump -uroot -p mydb t1 t2 t3  > mydb_dump.sql `

- DB명=mydb, 테이블명=t1, t2, t3 

<br>
<hr>

### `mysql -u[사용자아이디] -p [디비명] < 덤프파일명`
*dump 데이터로 복구하기*

command : `mysql -uroo -p mydb < mydb_dump.sql`
- DB명이 mydb인 곳에 백업된 데이터를 sql명령으로 복원 