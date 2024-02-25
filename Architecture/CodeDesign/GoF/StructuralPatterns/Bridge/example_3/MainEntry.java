package example_3;

// 인터페이스를 통해 구현 부분을 각각의 클래스로 분리함
public class MainEntry {
    public static void main(String[] args) {
        Language obj = new Language();
        obj.setEnglish(new English());
        obj.setKorean(new Korean());
    }
}
