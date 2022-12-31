public abstract class CheckBox {
    protected boolean bChecked;
    public CheckBox(boolean bChecked) {
        this.bChecked = bChecked;
    }

    void setChecked(boolean bChecked) {
        this.bChecked = bChecked;
        render();
    }

    abstract void render();
}