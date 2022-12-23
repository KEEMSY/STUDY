public abstract class Button {
    protected String caption;

    public Button(String caption) {
        this.caption = caption;
    }

    public void clickEvent() {
        System.out.println(caption + " 버튼을 클릭했다.");
    }

    abstract void render();
}