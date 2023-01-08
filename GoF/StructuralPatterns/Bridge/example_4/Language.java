package example_4;

// 브리지 패턴의 abstract 부분에 해당한다.
// 추상클래스를 통해 상위 클래스와 하위 클래스 사이에서 역할을 분담한다.
//  추상화를 통해 역할을 분담할 때 계층이 구별된다.

abstract class Language {
    public Hello language;

    abstract void greeting();
}
