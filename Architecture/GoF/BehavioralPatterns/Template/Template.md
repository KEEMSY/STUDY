# **Template 패턴**

어떤 기능에 대해 실행되어야 할 각 단계에 대한 순서는 정해져 있으나, 각 단계에 대한 세부 구현을 상황에 맞게 다르게 구현할 수 있도록하는 패턴 

<br>

![TemplatePattern.png](/img/TemplatePattern.png)

<br>


### **템플릿**
- 단계가 존재(단계는 존재하나 각 단계에대한 구체적인 코드는 정의되지 않은 상태)
- 각 단계에 대한 코드는 정해져 있지 않는 상태
- 각 구현 코드는 상황에 맞게 구현될 수 있다.


<br><hr><br>

## **예제 코드**


<br>

### **코드 구조**
![TemplateExample.png](/img/TemplateExample.png)

<br>

- ### **설명** 

    `DisplayArticleTemplate`는 템플릿 구현부에 속하는 클래스이다. 아직 구현되지 않은 각 단계를 정해진 순서대로 실행해주는 클래스이다. 각 단계에 해당하는 메서드를 순서대로 실행만 해주는 클래스이다. 추상클래스이다.

    `SimpleDisplayArticle` 및 `CaptionDisplayArticle` 은 `DisplayArticleTemplate` 을 상속받아 각 단계에 대한 메서드를 구현한다.

    Article 클래스는 `DisplayArticleTemplate` 혹은 `SimpleDisplayArticle` 혹은 `CaptionDisplayArticle` 에서 출력 할 데이터를 얻을 수 있는 클래스 이다.