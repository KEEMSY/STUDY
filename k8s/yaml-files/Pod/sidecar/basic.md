# **사이드카 패턴**
*사이드카 패턴은 하나의 파드 안에서 여러 개의 컨테이너를 담아서 동시에 실행시키는 패턴을 말한다.*

<br>

![sidecar](/img/sidecar.png)

웹 서버 컨테이너와 최신 컨텐츠를 깃헙에서 **다운받는 컨테이너가 하나의 파드에 묶여 있는 조합 패턴**을 말한다. 

<br>

사이드카 패턴을 활용한 웹 서버와 contents-cloner를 조합하면 콘텐츠 개발자가 git push 만으로 웹서버의 콘텐츠를 업데이트 할 수 있다.

공유 저장소를 사용하는 대신 각 노드의 저장 공간을사용하기에 성능 이점 또한 챙겨 갈 수 있다.

<br><hr><br>

## **준비물**
1. **매니페스트**
2. **쉘파일**
3. **도커파일**


<br><hr><br>

## **장점**
- 컨테이너의 재사용성과 생산성이 높아진다.
- 컨테이너를 재활용하여 단기간에 큰 성과를 낼 수 있다.

<br>

## **단점**
- 파드를 종료할 경우 종료가 완료될 때까지 시간이 오래걸린다.
    - 시간이 오래걸리면 롤링 업데이트와 같은 쿠버네티스의 핵심 기능을 사용할 때 문제의 소지가 된다.

    <br>

    - 스니펫(SIGTERM 시그널에 대한 종료 함수)


<br><hr><br>