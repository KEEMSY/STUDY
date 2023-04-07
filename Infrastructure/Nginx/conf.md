## Nginx.conf(전체파일)
``` 

user  nginx;
worker_processes  auto;

error_log  /var/log/nginx/error.log notice;
pid        /var/run/nginx.pid;


events {
    worker_connections  1024;
    # multi_accept on; (디폴트값 : off)
}


http {
    sendfile        on;
    #tcp_nopush     on;
    default_type  application/octet-stream;

    
    ##
	# Logging Settings
	##
	
    access_log  /var/log/nginx/access.log  main;
	log_not_found off;
	error_log /var/log/nginx/error.log warn;
	log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
                      '$status $body_bytes_sent "$http_referer" '
                      '"$http_user_agent" "$http_x_forwarded_for"';


    keepalive_timeout  65;

    #gzip  on;

    include /etc/nginx/conf.d/*.conf;
    include       /etc/nginx/mime.types;

}
```
<hr>

### `최상단(Core 모듈)`
```shell
user  nginx;
worker_processes  auto;

error_log  /var/log/nginx/error.log notice;
pid        /var/run/nginx.pid;
```
- `user` : Nginx프로세스가 실행되는 권한
- `worker_processes` : Nginx 프로세스 실행 가능 수
    - 실질적인 웹서버 역할을 한다.
    - `auto`도 무방하나, 명시적으로 서버에 장착되어 있는 코어 수 만큼 할당하는 것이 보통이며, 더 높게 설정 가능하다.
- `pid` : Nginx의 마스터 프로세스 ID 정보 저장
<br><br>



### `events` : Nginx의 특징인 비동기 처리방식에 대한 옵션 설정
```shell
events {
    worker_connections  1024;
    # multi_accept on; (디폴트값 : off)
}
```
- `worker_connections` : 하나의 프로세스가 처리할 수 있는 커넥션의 수
    - 최대 접속자 수 = worker_connections * worker_processes


<br><br>

### `http` : 웹 서버 동작에 대한 설정
```shell
http {
    sendfile on;
	tcp_nopush on;
	tcp_nodelay on;
	keepalive_timeout 10; #기본값:75
	types_hash_max_size 2048;
	server_tokens off;
	
	server_names_hash_bucket_size 64; #기본값:32
	server_names_hash_max_size 2048; #기본값:512
	# server_name_in_redirect off;

    default_type application/octet-stream;

    
    ##
	# Logging Settings
	##
	
    access_log  /var/log/nginx/access.log  main;
	log_not_found off;
	error_log /var/log/nginx/error.log warn;
	log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
                      '$status $body_bytes_sent "$http_referer" '
                      '"$http_user_agent" "$http_x_forwarded_for"';


    keepalive_timeout  65;

    #gzip  on;

    include /etc/nginx/conf.d/*.conf;
    include       /etc/nginx/mime.types;

}
```
- `keepalive_timeout` 
    - 접속 시 커넥션을 몇 초동안 유지할지에 대한 설정 값으로, 이 값이 높아지면 불필요한 커넥션(접속)을 유지하기 때문에 낮은 값 또는 0을 권장한다.
- `Logging Settings`
    - 중요한 오류이이외에는 로그로 남기지 않기 위게 설정하여 최대한 로그로 인해 디스크 엑세스를 하지 않도록 해야한다.
    - `log_not_found` : 파일을 찾을 수 없을 떄 에러 로그를 사용하지 않겠다.


<hr>

## 추가 설정
### `vi /etc/logrotate.d/nginx`
- 기본 옵션은 그대로 둔 채, 아래에 코드를 추가한다.
```shell
/var/log/nginx/*/*.log {
        daily
        dateext
        dateyesterday
        missingok
        rotate 30
        notifempty
        create 0640 www-data adm
        sharedscripts
        prerotate
                if [ -d /etc/logrotate.d/httpd-prerotate ]; then \
                        run-parts /etc/logrotate.d/httpd-prerotate; \
                fi \
        endscript
        postrotate
                invoke-rc.d nginx rotate >/dev/null 2>&1
        endscript
}
```
- Nginx 가상호스트에서 로그 경로를 /var/log/nginx/도메인 으로 설정했기 때문에 여러 가상호스트를 위한 디렉토리가 존재하여 모든 디렉토리에 적용될 수 있고록 * 처리가 되었다. 그리고 지난 로그파일에 압축을 적용하지 않아 언제든지 열어보기 쉽게 되어있고, 최대 로그 저장기간은 30일이다.

<br> <br>

### Crontab 설정
- logrotatesms crontab에 하루 한번만 실행되도록 설정되있고, 이 설정은 `/etc/cron.daily/logrotate`에 등록되어 있다. 

- `/etc/cron`
    - 로그 파일 뒤의 날짜와 로그 내용이 정확하게 딱 맞아 떨어지기 위한 설정을 한다.
    ```shell
    # /etc/crontab: system-wide crontab
    # Unlike any other crontab you don't have to run the `crontab'
    # command to install the new version when you edit this file
    # and files in /etc/cron.d. These files also have username fields,
    # that none of the other crontabs do.

    SHELL=/bin/sh
    PATH=/usr/local/sbin:/usr/local/bin:/sbin:/bin:/usr/sbin:/usr/bin


    # 확인 부분: 앞의 설정된 숫자(분/시)가 00 0 으로 수정(아닐경우)
    # m h dom mon dow user  command
    00 *    * * *   root    cd / && run-parts --report /etc/cron.hourly
    00 0    * * *   root    test -x /usr/sbin/anacron || ( cd / && run-parts --report /etc/cron.daily )
    00 0    * * 7   root    test -x /usr/sbin/anacron || ( cd / && run-parts --report /etc/cron.weekly )
    00 0    1 * *   root    test -x /usr/sbin/anacron || ( cd / && run-parts --report /etc/cron.monthly )
    ```