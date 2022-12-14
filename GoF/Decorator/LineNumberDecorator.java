public class LineNumberDecorator extends Decorator {
    public LineNumberDecorator(Item item) {
        super(item);
    }

    public abstract int getLinesCount() {
        return item.getLinesCount();
    }

    public abstract int getMaxLength() {
        return item.getMaxLength() + 4;
    }
    
    public abstract int getLength() {
        return item.getLength(index) + 4;
    }
    
    public abstract String getString(int index) {
        return String.format("%02d", index) + ": " + item.getString(index);
    }
}