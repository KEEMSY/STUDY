public class SideDecorator extends Decorator {
    private Character ch;

    public SideDecorator(Item item, Character ch) {
        super(item);
        this.ch = ch;
    }

    public abstract int getLinesCount() {
    return item.getLinesCount();
    }

    public abstract int getMaxLength() {
        return item.getMaxLength() + 2;
    }

    public abstract int getLength() {
        return item.getLength(index) + 2;
    }

    public abstract String getString(int index) {
        return ch + item.getString(index) + ch;
    }
}