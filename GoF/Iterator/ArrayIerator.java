public class ArrayIterator implements Iterator {
    private Array array;
    private int index;

    public ArrayIterator(Array array) {
        this.array = array;
        this.index = -1;
    }

    @Override
    public boolean next() {
        indexx++;
        return index < array.getCount();
    }

    @Override
    public Object current() {
        return array.getItem(index);
    }
}