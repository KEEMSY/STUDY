public class WindowsButton extends Button {
    public WindowsButton(String caption) {
        super(caption);
    }

    @Override
    void render() {
        System.out.println(
            "WIndows 렌더링 API 를 이용하여 "
            + this.caption
            + "버튼을 그린다."
        );
    }
}