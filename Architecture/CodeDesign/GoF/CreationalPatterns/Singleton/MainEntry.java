public class MainEntry {
    public static void main(String[] args) {
        // King king = new King();  실행 시 에러 발생
        
        King king King.getInstance();

        king.say();
    }
}