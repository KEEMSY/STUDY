public abstract class Factory {
    public Item create(String name) {
        boolean bCreatable = this.isCreatable(name);
        if(bCreatable) {
            Item item = this.createItem(name);
            postprocessitem(name);
            return item;
        }

        return null;
    }

    public abstract boolean isCreatable(String name);
    public abstract Item createItem(String name);
    public abstract void postprocessitem(String name);
}