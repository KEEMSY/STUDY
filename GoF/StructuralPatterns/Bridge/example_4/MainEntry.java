package example_4;

// 실제 인사말의 객체는 의존성 주입하며 주입된 객체는 생성자를 통해 설정된다.
// 동작하고자 하는 기능을 구현부와 추상화 형태로 분리한다.
// 새로운 기능을 확장으로 처리하지 않고 위임으로 처리한다.
public class MainEntry {
    public static void main(String[] args) {
        String language = "Korean";

        if(language == "Korean") {
            Language obj = new Message(new Korean());
        } else {
            Language obj = new Message(new English());
        }
    }
}
