public class MainEntry {
    public static void main(String[] args) {
        SumPrinter cal = new SumPrinter();

        cal.print(new SimplySumStrategy(), 10);
        cal.print(new GaussSumStrategy(), 10);
    }
}