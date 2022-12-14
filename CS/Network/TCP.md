## TCP(전송제어 프로토콜 - Transmission Control Protocol)
*TCP는 연결 지향적이며, 데이터 전달을 보증하고, 순서를 보장하는 프로토콜이다*
- **연결지향 - TCP 3 way handshake(가상연결)**
    - TCP 3 way handshake : 통신하기에 앞서, 논리적인 접속을 성립하는 과정
        
        ![3 way handshake](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/7efc2ac9-f518-4c02-bf7b-b91a82cf459f/스크린샷_2022-05-05_오후_10.32.43.png)
        
        1. 클라이언트는 SYN이라는 메세지를 보냄(connet 과정)
        2. 서버에서는 해당 메세지가 왔다는 응답으로 SYN와 ACK를 클라이언트에게 보낸다.
        3. 이에 응답으로 클라이언트는 서버에 ACK를 다시 보낸다.
            
            a. 최근에는 최적화가 잘 되어, 3번과정에서 데이터를 함께 보냄

        4. 이 과정을 통해 논리적 연결이 성립되며, (현 과정이 3번 반복 되었으므로 3 way handshake라고함) 데이터를 전송
        <br><br>

        >### 3 way handshake, 왜 하는 걸까?
        
        3 way handshake를 통해 클라이언트도 서버를 믿을 수 있고, 서버도 클라이언트를 믿을 수 있게 됨.(연결이 되었구나! 인식을 할 수 있음.)
        
        하지만 이는 논리적(개념적)으로만 연결이 된 것이다.(소켓연결된것 X)
        
        - 중간의 수많은 노드들이 연결이 되었는지는 모름
        - 나를 위한 전용 랜선이 보장된 것이 아니다.
    - 연결을 한 다음 메세지를 보낸다. <br><br>

- **데이터 전달 보증**
    - 데이터를 보낼 때, 중간에 누락이 된다면 내가 알아차릴 수 있다.
    <br><br>
    
- **순서를 보장**ßßßßß
    
    ![순서보장](https://s3-us-west-2.ß¸¸¸¸¸amazonaws.com/secure.notion-static.com/8e9ca494-452e-463e-b074-09e5a9a1bf81/스크린샷_2022-05-05_오후_10.42.06.png)
    
    - 1번, 2번, 3번 순서로 와야하지만, 1번, 3번, 2번 순서로 데이터가 올 경우에는 2번부터 다시 보내라고 함