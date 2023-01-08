package example_4;

// 브리지 패턴의 설게 요소 중 refinedAbstract 부분에 해당한다.
public class Message extends Language{

    public Message(Hello obj) {
        language = obj;
    }

    @Override
    void greeting() {
        language.greeting();
    }

    
}
