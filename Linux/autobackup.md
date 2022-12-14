# 관리 자동화
## 요약
- 리눅스는 사용자 계정과 인증 정보를 etc 디렉토리에 있는 passwd, group, shadow, gshadow라는 4개의 텍스트 파일에 저장한다.
- 실행 가능한 스크립트를 `/etc/cron.*/` 디렉터리 중 하나에 복사하면 해당 주기마다 자동으로 실행된다.
- anacrontab 파일에 명령을 추가하면 절대 시각이 아니라 시스템 부팅을 기준으로 한 상대 시각에 따라 명령을 실행한다.
- `systemd` 타이머는 절대 시각을 기반으로 설정하거나 하드웨어의 상태 변화에 반응하게 설정 할 수 있다.
- `syslog`, `root` 등의 시스템 계정은 원격에서 로그인 할 수 없도록 한다.
- 보안 계힉에 오프사이트 백업을 추가하면 데이터 신뢰성이 한 층 더 강화된다.

<br><hr><br>

## 핵심용어
- `종료코드(exit code)`: 모든 리눅스 명령은 실행이 완료되었을 때 이 코드를 반환한다. 0은 성공적으로 실행되었음을 나타내고, 그 외의 값들은 어떤 오류가 발생했음을 알려준다.


<br><hr><br>


## 세부사항

<br>

> **스크립트(script)**

*Bash 등의 쉘 스크립트 인터리프트가 이해하는 명령을 담은 일반 텍스트 파일*
```shell
#!/bin/sh

cd /var/backups || exit 0
for FILE in passwd group shadow gshadow; do
    test -f /etc/$FILE          || continue
    cmp -s $FILE.bak /etc/$FILE && continue
    cp -p /etc/$FILE $FILE.bak && chomod 600 $FILE.bak

done
```

- **요약**
    - 스크립트는 마지막 백업 이후 업데이트된 특정 환경 설정 파일의 사본을 만들도록 설계되어있다.
    - `etc` 디렉터리에 지정한 이름의 파일이 있고 이 파일의 내용이 `/var/backups` 디렉터리에 있는 비슷한 이름의 파일과 내용이 다르면, `etc` 디렉터리에 있는 파일을 `/var/backups/` 디렉터리에 복사하고 적절한 이름으로 바꾼 후 권한을 제한한다.

    <br>

    - `/etc/shadow`: `sudo` 권한을 이용하면 시스템 사용자들의 암호화된 패스워드를 볼 수 있다.
    - `/etc/group`:  현재 시스템과 사용자 그룹에 대한 기본 정보를 갖고 있다. 이 파일을 수정하여 그룹 구성을 관리 할 수 있다.
    - `/etc/gshadow`: 암호화된 그룹 패스워드가 들어있다.(그룹에 속하지 않은 사용자가 피룡에 따라 그룹 리소스에 접근 할 수 있다.)

<br>


- `||`: 만약 실패하면을 의미한다.(`/var/backup`에 이동하는데 실패하면 스크립트를 빠져나와라.) 
    - 종료코드(exit code)는 리눅스 명령이 완료된 후 결괏값으로 전달된다. 0은 성공을 의미하고 나머지 숫자는 어떤 종류의 오류가 발생했는지를 알려준다.

<br>

- `for`
    - 4개의 문자열(passwd, group, shadow, gshadow)을 한번에 하나씩 차례대로 FILE 변수에 할당한다. 
    - 한번 할당 할 때마다 do와 done 사이의 있는 코드 블록을 실행한다.
        - `test -f /etc/$FILE          || continue`
            - `etc`디렉터리 안에 변수 `$FILE`의 현재 값에 매칭되는 이름과 같은 이름의 파일이 있는지 검사한다. `etc`에 해당 이름을 가진 파일이 없으면 `$FILE` 변수에 다음 값(다음 파일명)을 할당하고 루프 블록을 다시 실행한다.
        
        <br>

        - `cmp -s $FILE.bak /etc/$FILE && continue`
            - `etc` 디렉터리에 해당 파일이 있으면 스크립트는 현재 디렉토리(`/var/backups/`)에 파일명이 같고 파일명 뒤에 `.bak` 확장자가 붙은 파일과 내용을 비교(`cmp`) 한다.
            - 비교 연산이 성공하면(`&&`) 쉘 스트립트는 `$FILE` 에 다음 값을 할당하고 루프블록을 다시 실행한다. 
            - 비교 연산이 실패하면 두 파일의 내용이 다른 것이며, 다음 줄로 이동한다.

        <br>

        - `cp -p /etc/$FILE $FILE.bak && chomod 600 $FILE.bak`
            - `페이로드(payload)`를 실행한다.(`etc` 디렉터리에 있는 현재 버전의 파일을 `/var/backups/`디렉터리에 복사하고 원래 이름에 `.bak` 확장자가 붙은 새로운 이름으로 바꾸고 나서 비인가 사용자가 읽지 못하게 파일의 접근 권한을 강화한다.(이 작업은 기존에 있던 동일한 이름의 파일을 덮어쓴다.))

            - `-p`: 원본 파일의 기존 소유권과 타임 스탬프를 유지하게 한다.

<br>

>**[**부족**]crontab**
- `crontab -l`: 스캐줄링 된 내용 확인 
    - `no crontab for <USER>`: 아무것도 스케줄링이 되어있지 않음

<br>

- `crontab -e`: crontab 수정


<br>

>**[**부족**]anacron**

*지정된 시간에 컴퓨터가 켜져 있음을 보장할 수 없을 경우 `anacrontab`에 스케줄을 추가한다.*
- `anacrontab` 은 절대적인 시각이 아니라 시스템이 부팅된 후 부터 상대적인 시각을 기준으로 작동한다. 
```shell
# /etc/anacrontab: configuration file for anacron

# See anacron(8) and anacrontab(5) for details.

SHELL=/bin/sh
PATH=/usr/local/sbin:/usr/local/bin/:/sbin//bin:/usr/sbin/:/usr/bin 
HOME=/root
LOGNAME=root

# These replace cron's entries
1   5   cron.daily      run-parts -- report /etc/cron.daily
7   10  cron.weekly     run-parts --report /etc/cron.weekly
@monthly        15  cron.manthly    run-parts --report /etc/cron.monthly
```
- `PATH=/usr/local/sbin:/usr/local/bin/:/sbin//bin:/usr/sbin/:/usr/bin `
    - `PATH` 디렉터리 변수로 이 디렉터리의 파일들은 전체 경로를 지정하지 않고도 참조 할 수 있다.

<br>

- `1   5   cron.daily      run-parts -- report /etc/cron.daily`
    - 하루에 한번 `/etc/cron.daily` 디렉터리의 모든 스크립트를 실행한다. 
