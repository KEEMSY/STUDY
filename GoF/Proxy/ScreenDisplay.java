public class ScreenDisplay implements Display {
    @Override
    public void print(String content) {
        try {
            Thread.sleep(500); // 출력을 위한 준비 작업(준비 시간이 걸린다는 가정)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(content);
    }
}