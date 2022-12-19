public class MainEntry {
    public static void main(String[] args) {
        Car car1 = new Car("V7", true, "Black", true, false);

        // 이 같은 방법을 Method Chainning 이라 함
        //  Builder 패턴을 통해 실제 객체의 생성을 원하는 만큼 계속 지연시킬 수 있다.
        Car car2 = new CarBuilder()
        .setAEB(false)
        .setAirbag(false)
        .setCameraSensor(true)
        .setColor("White")
        .setEngine("V9")
        .build();
    }
}