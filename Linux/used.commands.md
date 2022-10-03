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

