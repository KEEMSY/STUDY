// Car 객체를 생성하기 위한 클래스
public class CarBuilder {
    private String engine; 
    private boolean airbag;
    private String color;
    private boolean cameraSensor;
    private boolean AEB;

    // 각 메서드가 this 를 반환하는 이유는 메서드 체인을 지원하기 위함임

    public CarBuilder setEngine(String engine) {
        this.engine = engine;
        return this;
    }

    public CarBuilder setAirbag(String airbag) {
        this.airbag = airbag;
        return this;
    }

    public CarBuilder setColor(String color) {
        this.color = color;
        return this;
    }

    public CarBuilder setCameraSensor(String cameraSensor) {
        this.cameraSensor = cameraSensor;
        return this;
    }

    public CarBuilder setAEB(String AEB) {
        this.AEB = AEB;
        return this;
    }

    public Car build() {
        return new Car(engine, airbag, color, cameraSensor, AEB);
    }
}