// 누군가 원하는 숫자에 대한 Digit 객체를 요청하면 
// 해당 객체를 전달해주는 기능을 담당한다.
public class DigitFactory {
    private Digit[] pool = new Digit[] {
        null, null, null, null, null, null, null, null, null, null,
    };

    public Digit getDigit(int n) {
        if(pool[n] != null) {
            return pool[n];
        } else{
            String fileName = String.format("./data/%d.txt", n);
            Digit digit = new Digit(fileName);
            pool[n] = digit;
            return digit;
        }
    }
}