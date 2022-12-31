public interface Prototype {
    // Prototype 패턴에서는 copy 메서드의 반환 타입을 최상위 클래스인 Object 와 같은 타입으로 많이 사용한다.
    Object copy();
}