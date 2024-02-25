package example_2;

// 인터페이스를 통해 구현 부분을 각각의 클래스로 분리함
public class MainEntry {
    public static void main(String[] args) {
        Korean obj1 = new Korean();
        English obj2 = new English();

        obj1.greeting();
        obj2.greeting();
    }
}
