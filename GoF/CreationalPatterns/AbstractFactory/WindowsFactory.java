public class WindowsFactory extends ComponentFactory {
    @Override
    public Button createButton(String caption) {
        return new WindowsButton(caption);
    }

    @Override
    public Button createCheckBox(boolean bChecked) {
        return new WindowsCheckBox();
    }

    @Override
    public Button createTextEdit(String value) {
        return new WindowsTextEdit();
    }
}