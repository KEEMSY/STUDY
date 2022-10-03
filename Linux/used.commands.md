# 명령어 모음

## 리눅스 기본
- `ls 0lh /var/log`: `/var/log/` 디렉터리에 있는 내용을 사람이 잃기 좋은 형태로 보여준다.
- `cd`: 사용자 홈 디렉토리로 이동한다.
- `cp file1 newdir`: `file`을 `newdir` 디렉터리에 복사한다.
- `mv file? /some/other/directory/`: 파일명이 `file`이거나 `file` 뒤에 문자가 하나 더 있는 파일 모두를 대상 디렉터리로 이동한다.
- `rm -r *`: 현재 디렉터리의 파일과 하위 디렉터리 모두를 삭제한다.(사용할 때 주의한다.)
- `man sudo`: `sudo` 명령 사용에 관련된 메뉴얼 문서를 보여준다.

<br><hr><br>

## 원격연결
- `dpkg -s openssh-client`: `APT` 기반의 소프트 웨어 패키지 상태를 검사한다.
- `systemctl status ssh`: 시스템 프로세스(systemd)의 상태를 검사한다.
- `systemctl start ssh`: 서비스를 시작한다.
- `ip addr`: 컴퓨터에 있는 네트워크 인터페이스를 모두 나열한다.
- `ssh keygen`: `SSH` 키 쌍을 새로 생성한다.
- `cat .ssh/id_rsa.pub | ssh ubuntu@IP "cat > .ssh/authorized_keys"`: 원격 컴퓨터에 로컬 컴퓨터의 `SSH` 공개 키를 복사해 추가한다.
- `ssh-copy-id -i .ssh/id_rsa.pub ubuntu@IP`: 암호화 키를 안전하게 복사한다.(권장표준)
- `ssh -i .ssh/mykey.pemubuntu@IP`: 세션에 사용할 키 쌍을 지정한다.
- `scp myfile ubuntu@IP:/home/ubuntu/myfile`: 로컬 파일을 원격 컴퓨터에 안전하게 복사한다.
- `ssh -X ubuntu@IP`: 그래픽이 활성화된 세션을 열어 원격 컴퓨터에 로그인한다.
- `ps -ef | grep init`: 현재 실행중인 프로세스를 모두 나열한 후, `init` 문자열을 담은 줄만 출력한다.
- `pstree -p`: 현재 실행되는 모든 시스템 프로세스를 트리 모양으로 시각화 하여 출력한다.

<br><hr><br>

## 아카이브 관리
- `df -h`: 현재 활성화된 파티션을 사람이 읽기 쉬운 형식으로 보여준다.
- `tar czvf archivename.tar.gz /home/myuser/Videos/*.mp4`: 특정 디렉터리에 있는 비디오 파일들의 압축된 아카이브를 생성한다.
- `split -b 1G archivename.tar.gz archivename.tar.gz.part`: 큰 파일을 지정한 크기의 작은 파일 여러개로 분할한다.
- `find /var/www/ -iname "*.mp4" -exec tar -rvf videos.tar {} \`: 지정한 기준에 맞는 파일들을  찾아 tar 명령에 전달해 아카이브에 추가한다.
- `chmod o-r /bin/zcat`: 나머지 사용자의 읽기 권한을 제거한다.
- `dd if=/dev/sda2 of=/home/username/partition2.img`: `sda2` 파티션의 이미지를 생성해 홈 디렉터리에 저장한다.
- `dd if=/dev/urandom of=/dev/sda1`: 파티션을 무작위 글자로 덮어써 이전 데이터를 알아볼 수 없게한다.

<br><hr><br>

## 관리 자동화
- `#!/bin/bash('#!' 를 쉬뱅이라고 부름)`: 어느 쉘 인터프리터를 이용해 스크립트를 실행할지 리눅스에게 알린다.
- `||(이중 파이프)`: 스크립트에서 '만약 실패하면'으로 해석한다. 따라서 왼쪽 명령이 만약 실패하면 오른쪽 명령이 실행된다.
- `&&(이중 앰퍼샌드)`: 스크립트에서 '만약 성공하면'으로 해석된다. 따라서 왼쪽 명령이 성공하면 오른쪽 명령이 실행된다.
- `test -f /etc/filename`: 지정한 파일이나 디렉터리가 존재하는지 검사한다.
- `chmod +x upgrade.sh`: 스크립트 파일을 실행 할 수 있게 권한을 부여한다.
- `21 5 * * 1 root apt update && apt upgrade`: 매주 월요일 아침 5시 21분에 apt 명령 2개를 실행한다.
- `NOW=$(date +"%m_%d_%y")`: 스크립트에 이 코드가 있으면 현재 날짜를 변수에 할당한다.
- `systemctl start stie-backup.timer`: systemd 타이머를 활성화 하는 명령이다.

<br><hr><br>


## 네트워크 연결
- `hostname OpenVPN-Server`: 어느 서버에 로그인하는지 명령 프롬프트로 쉽게 확인 할 수 있다.
- `cp -r /usr/share/easy-rsa/ /etc/openvpn`: `easy-rsa` 스크립트와 환경 설정 파일을 `OpenVPN` 환경 설정 디렉터리로 복사한다.
- `./build-key-server server`: `server`: 라는 이름의 RSA 키 쌍을 생성한다.
- `./pkitool client`: 공개 키 기반 구조에서 사용할 클라이언트 키 집합을 생성한다.
- `openvpn --tls-client --config /etc/openvpn/client.conf`: `client.conf` 파일에 설정된 내용을 이용해 `OpenVPN` 리눅스 클라이언트를 실행한다.
- `iptables -A FORWARD -i eth1 -o eh2 -m state --state NEW, ESTABLISHED, RELATED -j ACCEPT`: eth1과 eth2 네트워크 인터페이스 간의 데이터 전송을 허용한다.
- `man shorewall-rules`: `Shorewall` 이 사용하는 `rules` 파일을 보여준다.

<br><hr><br>

## 시스템모니터링
- `journalctl -n 20`: 저널에서 최근 로그 항목 20개를 출력한다.
- `journalctl --since 15:50:00 --until 15:52:00`: `since` 에서 `util` 까지의 이벤트만 출력한다.
- `systemd-tmpfiles --create --prefix /var/log/journal`: 시스템을 부팅할 때 마다 저널이 지워지지 않도록 영구 저장소에 보관하게 한다.
- `cat /var/log/auth.log | grep -B 1 -A 1 failure`: 로그에서 `failure` 가 기록된 로그 항목과 그 앞뒤 항목을 하나씩 출력하게 한다.
- `cat /var/log/mysql/error.log | awk '$3 ~/[Warning]/'`: `MySQL` 오류 로그에서 `Warning` 으로 분류된 항목의 개수를 보여준다.

<br><hr><br>

## 시스템 성능 확인
- `uptime`:1, 5, 15분 동안 평균 CPU 부하를 보여준다.
- `cat /proc/cpuinfo | grep processor`: CPU 개수를 반환한다.
- `top`: 실행 중인 프로세스들의 상태를 실시간으로 보여준다.
- `killall yes`: `yes` 명령으로 실행된 프로세스를 모두 종료한다.
- `nice --15 /var/scripts/mybackup.sh`: `mybackup.sh` 가 시스템 리소스를 사용하는 우선순위를 높인다.
- `free -h`: 전체 램과 가용한 램의 크기를 보여준다.
- `df -i`: 각 파일 시스템의 전체 `inode` 와 가용가능한 `inode` 를 보여준다.
- `find . -xdev -type f | cut -d "/" -f 2 | sort | uniq -c | sort -n`: 현재 디렉터리에서 하위 디렉터리별로 저장된 파일의 개수를 보여준다.
- `apt autoremove`: 사용되지 않는 예전 커널 헤더들을 삭제한다.
- `nethogs eth0 eth0`: 인터페이스를 통해 네트워크에 연결된 프로세스와 전송 데이터를 보여준다.
- `tc qdisc add dev eth0 root netem delay 100ms`: `eth0` 인터페이스를 통해 전송되는 트래픽을 100ms 지연한다.
- `nmon -f -s 30 -c 120`: `nmon` 이 30초 단위로 1시간 동안 조사한 일련의 데이터를 파일에 저장한다.

<br><hr><br>
