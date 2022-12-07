# **Composite 패턴**

단일체와 집합체로 표현되는 여러 상황을 하나의 개념으로 처리해서 상황을 단순하게 만들어지는 패턴을 Composite 패턴이라고 한다.

***비유** : 구슬(단일체), 상자(집합체, 상자 및 여러 개의 구슬을 담을 수 있음)*


<br><hr><br>

## **예제 코드**


<br>

### **코드 구조**
![Composite.png](/img/Composite.png)

<br>

- ### **설명** 

    `Unit` 은 `Folder` 와 `File` 을 동일한 개념으로 처리하기 위해서  존재하며 `Folder` 와 `File` 는 `Unit` 을 구현한다.
    
    `Folder`는 **상자**를 의미하며 `Folder` 안에는 한 개 이상의 `File` 이 존재 할 수 있다. 또 `Folder` 안에는 또다른 `Folder` 가 존재할 수 있다.

    `Folder` 는 여러개의 `Unit` 을 가질 수 있다.

    `File`은 설명의 구슬에 해당한다.

    `Unit` 은 `Folder` 와 `File` 의 부모클래스이므로 `Folder` 는 또 다른 `Folder` 나 `File` 을 가질 수 있다.
