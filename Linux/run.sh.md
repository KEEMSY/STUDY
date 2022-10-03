## 각 컨테이너 실행 시 필요한 process를 실행한다.
```shell

# 공통 부분
#! /bin/bash
service ssh start

# 컨테이너 별 설정

"nginx"
service nginx start
nginx

"redis"
redis-server /redis.conf
redis

"celery"
/usr/local/bin/celery multi start worker1 \
-A [NAME] --pidfile=/var/run/celery/%N.pid\
--logfile=/var/log/celery/%N.log \
--loglevel=INFO \
--time-limit=300 \ 
--concurrency=8
celery

```

