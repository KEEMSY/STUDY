public class WindowsCheckBox implements CheckBox {
    public WindowsCheckBox (boolean bChecked) {
        super(bChecked);
    }

    @Override
    void render() {
        System.out.println(
            "Windows 렌더링 API를 이용해 "
            + (this.bChecked ? "체크된" : "체크안된")
            + " 체크박스를 그린다."
        );
    }
}