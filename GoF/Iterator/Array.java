public class Array implements Aggregator {
    private Item[] items;

    // 생성자
    public Array(Item[] items) {
        this.items = items;
    }

    // 해당 인덱스에 해당하는 배열의 값을 가져옴
    public Item getItem(int idex) {
        return items[index];
    }

    // 배열의 구성 데이터의 갯수를 가져옴
    public int getCount() {
        return items.length; 
    }

    // ArrayIterator 객체 생성
    @Override
    public Iterator iterator() {
        return new ArrayIterator(this);
    }

}