public class LinuxButton extends Button {
    public LinuxButton(String caption) {
        super(caption);
    }

    @Override
    void render() {
        System.out.println(
            "Linux 렌더링 API를 이용해 "
            + this.caption
            + " 버튼을 그린다."
        );
    }
}