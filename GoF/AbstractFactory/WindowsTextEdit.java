public class WindowsTextEdit extends TextEdit {
    public WindowsTextEdit(String value) {
        super(value);
    }

    @Override
    void render() {
        System.out.println(
            "Windows 렌더링 API를 이용해 "
            + this.value + "값을 가진"
            + " 텍스트 에디트를 그린다."
        );
    }
}