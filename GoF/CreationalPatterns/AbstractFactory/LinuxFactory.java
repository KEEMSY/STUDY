public class LinuxFactory extends ComponentFactory {
    @Override
    public Button createButton(String caption) {
        return new LinuxButton(caption);
    }

    @Override
    public Button createCheckBox(boolean bChecked) {
        return new LinuxCheckBox();
    }

    @Override
    public Button createTextEdit(String value) {
        return new LinuxTextEdit();
    }
}