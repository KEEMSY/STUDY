# **Commit Message Rule**
>*목표*
1. 한 문장으로 설명이 가능하도록 커밋 메세지를 쪼갠다. 
2. 작성한 당사자가 아닌 처음 보는 제 3자가 이해할 수 있는 문장을 만든다.

## **필요성**
개인 공부를 하면서 커밋 메세지의 규칙이 없어, 커밋메세지를 작성할 때, 제 멋대로 작성하고 있었다. 이는 내가 작성한 메세지임에도 불구하고, 나중에 보았을 때 내가 이해를 하지 못하는 경우가 발생하였고, 커밋로그의 가독성과, 미래의 더 나은 협업을 위해 규칙의 필요성을 느끼게 되었다.

> Commit 메세지 규칙의 필요성

1. 팀원과의 소통
2. 편리한 과거의 기록 추적
3. 이슈를 함께 작성하면서 이슈와 관련된 진행사항을 확인 가능함


<br><hr><br>

## **메세지 구조**
```html
<type>(<scope>): <subject>          -- 헤더
<BLANK LINE>
<body>                              -- 본문
<BLANK LINE>
<footer>                            -- 바닥글
```
<br>

- `<type>` 은 커밋의 성격을 나타내며 아래의 내용들 중 하나를 선택한다.
    - 예시 : `Feat: 신규 RFID 인식 기능 추가`

```text
feat : 새로운 기능에 대한 커밋
fix : 버그 수정에 대한 커밋
build : 빌드 관련 파일 수정에 대한 커밋
chore : 그 외 자잘한 수정에 대한 커밋
ci : CI관련 설정 수정에 대한 커밋
docs : 문서 수정에 대한 커밋
style : 코드 스타일 혹은 포맷 등에 관한 커밋
refactor :  코드 리팩토링에 대한 커밋
test : 테스트 코드 수정에 대한 커밋
```

- `body`는 본문으로 헤더로 표현할 수 없는 상세한 내용을 적어 넣는다. <br>
*header*로 표현이 가능하다면 생략한다. 
    - `Fix` : 올바르지 않은 동작을 고친 경우 사용한다.
        - `Fix A` : A를 수정한다.
        - `Fix A in B` : B의 A를 수정한다.
        - `Fix A which B`, `Fix A that B` : B절인 A를 수정한다.
            - 무엇을 수정한 것인지 보다 상세하게 설명할때 사용한다.
        - `Fix A to B`, `Fix A to be B` : B를 위해 A를 수정한다.
        - `Fix A so that B` : A를 수정해서 B가 되었다.
        - `Fix A where B` : B처럼 발생하는 A를 수정했다.
        - `Fix A when B` : B일 때 발생하는 A를 수정했다.
    
    <br>

    - `ADD` : 코드, 테스트, 예제, 문서 등의 추가가 있을 때 사용한다.
        - `Add A` : A를 추가한다.
        - `Add A for B` : B를 위해 A를 추가한다.
        - `Add A to B` : B에 A를 추가한다.
    
    <br>

    - `REMOVE` : 코드의 삭제가 있을 때 사용한다. 보통 A 앞에 unnecessary, useless, unneeded, unused, duplicated 가 붙는 경우가 많다.
        - `Remove A` : A를 삭제한다.
        - `Remove A from B` : B에서 A를 삭제한다.
    
    <br>

    - REFACTOR : 전면 수정이 있을 때 사용한다.
         - Refactor A 
    
    <br>

    - `SIMPLIFY` : 복잡한 코드를 단순화 할 때 사용한다. Refactor의 성격이 강하지만 이보다는 약한 수정의 경우 사용한다.
         - `Simplify A `: A를 단순화한다.
    
    <br>

    - `UPDATE` : 개정 혹은 버전 업데이트가 있을 경우 사용한다. Fix와 달리 Update는 잘못된것을 바로잡는것이 아니다. 원래도 정상이지만, 수정 추가, 보완한다는 개념이다. 주로 코드보다는 주로 문서나 리소스, 라이브러리 등에 사용한다.
        - `Update A to B` : A를 B롤 업데이트한다 / A를 B하기 위해 업데이트 한다.

    <br>

    - `IMPROVE` : 향상이 있을 경우 사용한다. 호환성, 테스트 커버리지, 성능, 검증기능, 접근성 등 다양한 것이 목적이 될 수 있다.
        - `Improve A` : A를 향상시킨다.

    <br>

    - `MAKE` : 기존 동작의 변경을 명시한다. 새롭게 무언가를 만들었을 땐, Make가아닌 Add를 사용한다.
        - Make A B : A를 B화게 만든다.

    <br>

    - `IMPLEMENT` : 코드가 추가된 정도보다 더 주목할 만한 구현체를 완성시켰을 때 사용한다.
         - Implement A : A를 구현한다.
         - Implement A to B : B를 위해 A를 구현한다. <br>
         *구현 목적을 설명할 필요가 잇을 때에는 to를 사용한다.*
    
    <br>

    - `REVISE` : Update와 유사하나 문서의 개정이 있을 경우 주로 사용한다.
        - `Revise A` : A 문서를 개정한다.
    
    <br>

    - `CORRECT` : 문법의 오류나 타입의 변경, 이름 변경 등에 사용한다.
        - `Correct A` : A를 고친다.
    
    <br>

    - `ENSURE` : 무엇이 확실하게 보장받는 다는 것을 명시한다. 혹은 조건을 확실하게 해주었을 때 사용할 수 있다.
        - `Ensure A `: A가 확실히 보장되도록 수정하였다.
    
    <br>

    - `PREVENT` : 특정한 처리를 못하게 막는다.
        - `Prevent A` : A가 하지 못하게 막는다.
        - `Prevent A from B` : A를 B하지 못하게 막는다.
    
    <br>

    - `AVOID` : Avoid는 회피한다. if 구문을 통해 특정한 동작을 제외시키는 경우에도 사용할 수 있다.
        - `Avoid A` : A를 회피한다.
        -` Avoid A if B`, `Avoid A when B` : B인 상황에서 A를 회피한다.
    
    <br>

    - `MOVE` : 코드의 이동이 생길 때 사용한다.
        - `Move A to B`, `Move A into B` : A를 B로 옮긴다.
    
    <br>

    - `RENAME` : 이름을 변경할 경우 사용한다.
        - `Rename A to B` : A를 B로 이름 변경한다.

    <br>

    - `ALLOW` : Make와 유사하나 허용을 표현할 때 사용한다.
        - `Allow A to B` : A가 B 할 수 있도록 허용한다.
    
    <br>

    - `VERIFY` : 검증코드를 넣을 때 주로 사용한다.
        - `Verify A `: A를 검증한다.
    
    <br>

    - `SET` : 변수 값을 변경하는 등의 작은 수정에 주로 사용한다.
        - `Set A to B` : A를 B로 설정한다.
    
    <br>

    - `PASS` : 파라미터를 넘기는 처리에 주로 사용한다.
        - `Pass A to B` : A를 B로 넘긴다.
    


- `<footer>`는 바닥글로 어떤 이슈에서 왔는지와 같은 참조 정보들을 추가하는 용도로 사용한다.(ex. close #123) <br>
*close*는 이슈를 참조하면서 main 브램치로 푸시될 때 이슈를 닫게 된다.