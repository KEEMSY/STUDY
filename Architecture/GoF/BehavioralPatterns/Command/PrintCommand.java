public class PrintCommand implements Command {
    private String content;

    public PrintCommand(String content) {
        this.content = content;
    }

    @Override
    public void run() {
        System.out.print(content)
    }
}