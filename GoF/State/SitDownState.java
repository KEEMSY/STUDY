public class SitDownState extends State {
    public SitDownState(Player player) {
        super(player);
    }

    @Override
    public void standUp() {
        player.setState(new StandUpState(player));
        player.talk("일어나자~");
    }

    @Override
    public void sitDown() {
        player.talk("너무 쉰거 같은데..?");
    }

    @Override
    public void walk() {
        player.talk("앉아서 어떻게 걸어? 일단 일어나!");
        player.setState(new StandUpState(player));
    }

    @Override
    public void run() {
        player.talk("앉아서 어떻게 뛰어? 일단 일어나!");
        player.setState(new StandUpState(player));

    }

    @Override
    public String getDescription() {
        return "앉아있음."
    }
}