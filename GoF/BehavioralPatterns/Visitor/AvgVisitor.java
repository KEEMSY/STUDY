public class AvgVisitor implements Visitor {
    private int sum = 0;
    private int count = 0;

    public double getValue() {
        return sum / count;
    }

    @Override
    public void visit(Unit unit) {
        if(unit instanceof Item) {
            sum += ((Item)unit).getValue();
            count ++;
        } else {
            unit.accept(this);
        }
    }
}