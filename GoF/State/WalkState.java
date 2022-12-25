public class WalkState extends State {
    public WalkState(Player player) {
        super(player);
    }

    @Override
    public void standUp() {
        player.setSpeed(0);
        player.talk("멈춰!");
        player.setState(new StandUpState(player));
    }

    @Override
    public void sitDown() {
        player.setSpeed(0);
        player.talk("걷다가 앉으면 넘어질 수 있다!");
        player.setState(new SitDownState(player));
    }

    @Override
    public void walk() {
        player.talk("난 걷는게 좋아~");
    }

    @Override
    public void run() {
        player.setSpeed(20);
        player.talk("걷다가 뛰면 딱 좋아 !");
        player.setState(new RunState(player));
    }

    @Override
    public String getDescription() {
        return "걷는 중."
    }
}