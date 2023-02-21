# **ch.6.도메인이벤트와 전술적 설계**

![6.topic](/img/6.topic.png)

도메인 이벤트가 전략적 설계를 위해 매우 중요한 도구이다. 

- 도메인 이벤트를 사용함으로써, 분산된 시스템의 동일한 요청 내에 존재하는 모든 의존적인 노드들에게 보여지는 경우, 의존관계 일관성을 보여준다.
- 특정한 오퍼레이션이 다른 애그리게잇에서 명확하게 발생하기 전에는 한 애그리게잇이 생성되거나 수정될 수 없다.
- 이벤트에 관심이 있는 이벤트 리스너들에게 관련 상황을 알리는데 매우 유용하다.

<br>

<br><hr><hr>

## **도메인 이벤트를 설계, 구현, 사용하기**

```C#
public interface DomainEvent
{
    public Date OccurredOn
    {
        get;
    }
}
```

이 C# 코드는 모든 도메인 이벤트가 반드시 지원해야하는 최소한의 인터페이스를 말한다.

- 일반적으로 도메인이벤트가 발생할 때, 그 날짜와 시각을 전달한다.(이를 위해 OccurredOn 프로퍼티를 제공한다.)

<br>

도메인 이벤트에 이름은 세심한 주의가 필요하다.

- 사용되는 단어들은 도메인 모델의 보편언어를 반영한다.
- 이 단어들은 도메인 모델안에서 발생하는 사건과 밖을 이어주는 다리 역할을 한다.
- 이런 다리들은 이벤트를 통해 모델들이 원활히 의사소통하는데 필수적이다.

<br>

![domainEventExamples](/img/domainEventExamples.png)

도메인 이벤트 타입을 나타내는 이름은 과거에 발생한 것을 서술하는데, 이는 과거형 동사로 표현할 수 있다.

*ProductCreated: 어떤 과거 시점에 스크럼 제품이 생성됐음을 나타낸다.*

- 각 이름들은 핵심 도메인에서 발생한 사건을 명확하고 간결하게 서술한다.
- 도메인 모델에서 발생한 사건의 기록을 온전히 전달하기 위해선, 도메인 이름과 프로퍼티가 모두 필요하다.

<br>

![domainEvent_Command](/img/domainEvent_Command.png)

CreateProduct 명령은 다음과 같은 프로퍼티를 갖고 있다.

- tenantId: 구독 테넌트를 식별한다.
- productId: 생성되는 고유한 Product를 식별한다.
- Product name
- Product description

<br>

ProductCreated 도메인 이벤트는 이벤트가 만들어지는 시점에 명령이 제공하는 모든 프로퍼티들을 담고 있어야 한다.

- tenantId, productId, name, description 을 통해 모든 이벤트 구독자들에게 모델안에서 무슨일(Product가 생성됬다.)이 발생했는지 온전하고 정확하게 알릴 수 있다.
- 테넌트는 tenantId로, Product는 productId로 고유하게 식별한다.
- 도메인 이벤트가 풍부한 추가 데이터를 담을 수 있으나, 도메인 이벤트에 그 의미를 잃을 정도로 너무 많은 데이터를 담지 않도록 주의한다.

<br>

![aggregateWithDomainEvent](/img/aggregateWithDomainEvent.png)

수정된 애그리게잇과 도메인 이벤트가 같은 트랜잭션에서 함께 저장될 필요가 있음을 인지한다.

- 도메인 이벤트를 이벤트 리포지토리에 유지하는 것은 도메인 모델 간에 발생한 것에 대한 인과관계의 순서를 지속시켜준다.
- 객체-관계 매핑 도구를 사용한다면, 애그리게잇을 하나의 테이블에 그리고 도메인 이벤트를 리포지토리에 테이블에 저장하고 난 후, 트랜잭션을 설정할 수 있다.
- 이벤트 소싱(Event Sourcing)을 사용한다면, 애그리게잇의 상태는 도메인 이벤트 자체로 온전히 표현할 수 있다.

<br>

![savedAndPublishedDomainEvent](/img/savedAndPublishedDomainEvent.png)