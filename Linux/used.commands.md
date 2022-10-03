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
