public interface Expression {
    boolean parse(Context context);
    boolean run();
}