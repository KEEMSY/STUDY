public class MainEntry {
    public static void main(String[] args) {
        var title = "타이틀 테스트";
        var author = "김성연";
        String[] content = {
            "컨텐츠 1",
            "컨텐츠 2",
            "컨텐츠 3"
        };

        Draft draft = new Draft(title, author, content);

        Display display1 = new SimpleDisplay();
        draft.print(display1);

        Display display2 = new CaptionDisplay();
        draft.print(display2);

        var publisher = "테스트 출판사"
        var cost = 100;

        Publication publication = new Publication(title, author, content, publisher, cost);

        publication.print(display1);
        publication.print(display2);
    }
}