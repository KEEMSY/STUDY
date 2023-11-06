# 데메테르의 법칙, LoD

`LoD` 는 일반적인 설계원칙(`SOLID`, `KISS`, `DRY`) 만큼 잘 알려진 원칙은 아니지만, 이 원칙을 준수할 경우 코드에서 `높은 응집도` 와 `낮은 겹합도` 를 달성 해낼 수 있게 된다.

- `높은 응집도`와 `낮은 결합도` 는 코드의 `가독성`과 `유지 보수성`을 효과적으로 향상시키고 기능 변경으로 인한 `코드 변경 범위` 를 줄일 수 있는 매우 중요한 설계 사상이다.
- `단일 책임 원칙`, `구현이 아닌 인터페이스기반 개발` 등 많은 설계 원칙 또한 `높은 응집도`와 `낮은 결합도`를 달성하는 것을 목표로 하고 있다.
- `시스템`, `모듈`, `클래스`, `함수`의 설계와 `개발` 뿐만 아니라 `마이크로서비스`, `프레임워크`, 구성요소 클래스 라이브러리의 설계와 개발에도 적용될 수 있다.

<br>

> 높은 응집도

`높은 응집도`는 `클래스 자체의 설계`에 사용되며, 유사한 기능은 동일한 클래스에 배치 되어야하고, 유사하지 않은 기능은 다른 클래스로 분리해야 함을 의미한다. 

- 유사한 기능을 같은 클래스에 배치하여 코드를 수정하거나 유지보수하기 쉽게 한다.
- `단일 책임 원칙`을 통해 높은 코드 응집성을 효율적으로 달성할 수 있다.
	- 클래스를 수정하는 경우, 수정의 영향 범위는 단일 종속 클래스에 한정된다.

<br>

> 낮은 결합도

`낮은 결합도`는 `클래스 간의 의존성 설계`에 사용되며, 클래스 간의 `의존성`이 `단순`하고 `명확`해야 함을 의미한다.

- 두 클래스가 종속 관계에 있을 때, 둘 중 어느 한 쪽의 클래스를 수정하더라도 다른 클래스의 코드가 거의 수정되지 않아야 한다.
- `의존성 주입`, `인터페이스 분리`, `구현이 아닌 인터페이스 기반 개발`, `LoD` 등을 통해 `낮은 결합도`를 달성할 수 있다.


<br>

응집도와 결합도는 완전히 독립적이지 않기 때문에, 높은 응집도는 낮은 결합도를 이끌어내며, 반대로 낮은 결합도는 높은 응집도로 이어진다.

<br><hr>

## LoD

`LoD` 는  `최소 지식의 원칙(The least knowledge principle)` 을 의미한다.

*설계 원칙과 사상은 매우 추상적이며, 사람마다 다양한 해석이 존재한다.*

- 모든 유닛이 자신과 매우 밀접하게 관련된 유닛에 대해서 제한된 지식만 알아야 한다.
- 모든 유닛은 자신의 친구들에게만 이야기해야하며, 알지 못하는 유닛과는 이야기하면 안된다.
- 직접 의존성이 없어야 하는 클래스 사이에는 반드시 의존성이 없어야한다.
- 의존성이 있는 클래스는 필요한 인터페이스에만 의존해야 한다.