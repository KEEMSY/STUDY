public abstract class Decorator extends Item {
    // 장식이 될 타입
    protected Item item;

    public Decorator(Item item) {
        this.item = item;
    }
    // 문자열이 몇줄인지 반환
    public abstract int getLinesCount();

    // 문자열 중 가장 긴 문자열을 반환한다.
    public abstract int getMaxLength();
    
    // 지정된 인덱스의 문자열의 길이를 반환한다.
    public abstract int getLength();
    
    // 지정된 인덱스의 문자열을 반환한다.
    public abstract String getString(int index);
}