public class MainEntry {
    public static void main(String[] args) {
        Strings strings = new Strings();

        strings.add("퇴근후에 공부하는");
        strings.add("오늘 날짜는 2022-12-14 입니다.");
        strings.add("하루가 조금만 더 길었으면 좋겠다.");
        strings.add("하지만 하루는 야속하게 24시간이다~");

        // strings.print();

        Item decorator = new SideDecorator(strings, "\'");
        decorator = new LineNumberDecorator(decorator);
        decorator = new BoxDecorator(decorator);

        decorator.print();
    }
}