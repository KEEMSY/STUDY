public class MainEntry {
    public static void main(String[] args) {
        String script = "BEGIN FRONT LOOP 3 LOOP 2 RIGHT FRONT END LOOP 3 LEFT END BACK RIGHT END BACK END";
        System.out.println(script);

        Context context = new Context(script);
        Expression expression - new BeginExpression();

        if(expression.parse(context)) {
            System.out.pirntln(expression);

            expression.run();
        }
    }

}