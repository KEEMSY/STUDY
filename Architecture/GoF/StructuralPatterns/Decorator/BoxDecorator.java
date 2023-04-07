public class BoxDecorator extends Decorator {
    public BoxDecorator(Item item) {
        super(item);
    }

    public abstract int getLinesCount() {
        return item.getLinesCount() + 2;
    }

    public abstract int getMaxLength() {
        return item.getMaxLength() + 2;
    }
    
    public abstract int getLength() {
        return item.getLength() + 2;
    }
    
    public abstract String getString(int index) {
        int maxWidth = this.getMaxLength();
        if(index == 0 || index == getLinesCount - 1) {
            StringBuilder sb = new StringBuilder();
            sb.append('+');
            for(int i=0; i<maxWidth-2; i++) {
                sb.append('-');
            }
            sb.append('+');
            return sb.toString;
        } else {
            return '|' + item.getString(index-1) + makeTailString(maxWidth - getLength(index-1));
        }
    }

    private String makeTailString(int count) {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<count; i++) {
            sb.append(' ');
        }
        sb.append('|');

        return sb.toString();
    }

}