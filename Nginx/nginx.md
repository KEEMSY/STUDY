## 역할
 *client PC에서 동작 할 정적 Web Page를 생성하여 Client PC에 전달한다.*
1. **정적 파일을 처리하는 HTTP 서버**로서의 역할
    1. 웹 서버의 역할은 HTML, CSS, JavaScript, 이미지와 같은 정보를 웹 브라우저에 전송하는 역할을 한다.
    
2. **응용프로그램 서버에 요청을 보내는** 리버스 프록시 역할
    1. 클라이언트는 가짜 서버에 요청(request)하면 프록시 서버(=Nginx)가 배후 서버(reverse server=응용프로그램서버; WAS)로 부터 데이터를 가져오는 역할을 한다. 
    2. client가 서버에 직접적으로 접근하는것을 막아 줄 수 있다.

> **리버스 프록시(Nginx)를 두는 이유**

 요청(request)에 대한 버퍼링이 존재하기 때문이다. 클라이언트가 직접 App서버에 직접 요청하는 경우, 프로세스 1개가 응답 대기 상태가되어야만 한다. 따라서 프록시 서버를 둠으로써 요청을 배분 하는 역할을 한다.

3. 로드밸런싱을 활용하여 트래픽을 분산 시킬 수 있다.
    1. active/stanby로 구성할 경우 좀 더 안정적으로 배포 환경을 구축할 수 있다.
4. SSL(https)기능을 사용하여 데이터를 안전하게 전달 할 수 있다.
5. 컨텐츠를 캐싱하여 동일한 요청에 대해 더 빠른 속도로 처리 할 수 있게 해준다.
<br>
<hr>

## 기본 설정 `nginx.conf`
### 파일 위치
http 세팅 : `/etc/nginx/nginx.conf` <br>
server 세팅 : `/etc/nginx/conf.d/default.conf` <br>
>nginx 설정 파일 
- `/etc/nginx` : nginx 설정파일 디렉토리
- `/etc/nginx/nginx.conf` : 메인 설정 파일(접속자 수, 동작 프로세스 수 
- `/etc/nginx/conf.d` : nginx.conf에서 불러들일 수 있는 파일 저장
- `/etc/nginx/fastcgi.conf` : FastCGI 환경 설정 파일
- `/etc/nginx/sites-available/` : 비활성화된 사이트 파일
- `/etc/nginx/sites-enabled/` : 활성화된 사이트 파일


<br>
<hr>

## 명령어
- `service nginx status` : nginx 상태 확인
- `service nginx start` : nginx 시작
- `service nginx restart`
    - `service nginx reload` : 이상이 없으면 재 시작(restart 보다 이것을 사용하자!)
