public class StandUpState extends State {
    public  StandUpState(Player player) {
        super(player);
    }

    @Override
    public void standUp() {
        player.talk("언제 움직일거야!!?")
    }

    @Override
    public void sitDown() {
        player.setState(new SitDownState(player));
        player.talk("앉으니깐 편하고 좋다~!");
    }

    @Override
    public void walk() {
        player.setSpeed(5);
        player.setState(new walkState(player));
        player.talk("걷기는 유산소로는 아쉽다..");
    }

    @Override
    public void run() {
        player.setSpeed(10);
        player.setState(new RunState(player));
        player.talk("그래! 역시 뛰어야지!");

    }

    @Override
    public String getDescription() {
        return "제자리에 서 있음."
    }
}