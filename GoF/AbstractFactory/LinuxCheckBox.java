public class LinuxCheckBox extends CheckBox {
    public LinuxCheckBox(boolean bChecked) {
        super(bChecked);
    }
    
    @Override
    void render() {
        System.out.println(
            "Linux 렌더링 API를 이용해 "
            + (this.bChecked ? "체크된" : "체크안된")
            + " 체크박스를 그린다."
        );
    }
}