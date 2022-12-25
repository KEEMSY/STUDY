public class RunState extends State {
    public RunState(Player player) {
        super(player);
    }

    @Override
    public void standUp() {
        player.setSpeed(0);
        player.talk("뛰다가 갑자기 멈추면 큰일나!!");
        player.setState(new StandUpState(player));
    }

    @Override
    public void sitDown() {
        player.setSpeed(0);
        player.talk("뛰는데 앉으라고?");
        player.setState(new StandUpState(player));
    }

    @Override
    public void walk() {
        player.talk("속도를 줄여보자!");
        player.setSpeed(8);
        player.setState(new WalkState(player));
    }

    @Override
    public void run() {
        player.setSpeed(player.getSpeed + 2);
        player.talk("더 빠르게 뛰아보자!");
    }

    @Override
    public String getDescription() {
        return "뛰는 중."
    }
}