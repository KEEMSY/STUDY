## `vi`: 파일 편집기
<hr>

### `insert mode` : 텍스트를 입력할 수 있는 상태
진입 방법
  - `i` : 현재 위치에서 insert mode 진입
  - `a` : 현재 위치에서 커서를 한 칸 앞으로 이동 후 insert mode 진입
  - `A` : 현재 위치에서 가장 마지막 텍스트로 이동 한 후 insert mode 진입
  - `o` : 현재 위치에서 한칸 개행 한 후 insert mode 진입
  - `esc` : insert mode 나가기
<br>

### `command mode` : 특수한 command를 입력할 수 있는 상태
- commend mode 명령어
    - `u` : undo (ctrl + z와 동일)
    - `ctrl + r` : redo
    - `gg` : 커서를 가장 처음으로 옮김
    - `G` : 커서를 마지막 줄로 옮김
    - `dd` : line 잘라내기
    - `yy` : line 복사
    - `p` : 붙여넣기
    - `:se nu` : 라인 줄 표시
    - `:숫자` : 숫자 라인으로 이동
    - `v` :  블록 선택
    > **검색방법** <br>
        - `/word` : word라는 단어를 검색 <br>
        - `/\cword` : 대소문자를 구분하지 않고 word라는 단어를 검색 <br>
        - `n` : 다음 단어 검색 <br>
        - `N` : 이전 단어 검색 <br>
<br>

### `저장`
- `:w` : 저장
- `:q` : 나가기
- `:wq` : 저장 하고 나가기
- `:q!` : 강제로 나감