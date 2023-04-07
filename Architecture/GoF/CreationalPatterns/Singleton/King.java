public class King {
    
    // 다른 곳에서 생성자가 호출 될 수 없도록 함
    private King() {}

    // 오직 한 개 생성될 객체에 대한 셀프 필드를 정의
    private static King self = null;        
    
    // 멀티스레드에서 문제가 없도록 설정
    public synchronized static King getInstance() {
        if(self == null) {
            self = new King();
        }
        return self;
    }

    public void say() {
        System.out.println("I am only one...")
    }
}