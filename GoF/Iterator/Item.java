public class Item {
    // 필드는 name, cost
    private String name;
    private int cost;

    // 생성자
    public Item(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    // Item 객체를 문자열로 변환할 때 자동으로 호출되는 메서드
    @Override
    public String toString() {
        return "(" + name + "," + cost +")"l
    }
}