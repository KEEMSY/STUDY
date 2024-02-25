package example_3;

// 복합 객체인 Language는 구현클래스의 객체
public class Language {
    public English english;
    public Korean korean;

    public void setEnglish(English obj) {
        english = obj;
    }

    public void setKorean(Korean obj) {
        korean = obj;
    }
}
