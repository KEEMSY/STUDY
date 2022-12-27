public class HeatBoiler extends Participant {
    private boolean bOff = true;

    public HeatBoiler(Mediator mediator) {
        super(mediator);
    }

    public void on() {
        if(!bOffd) return;
        
        bOff = false;
        mediator.participantChanged(this);
    }

    public void off() {
        if(bOff) return;

        bOff = true;
        mediator.participantChanged(this);
    }

    public boolean isRunning() {
        return !bOff;
    }

    @Override
    public String toString() {
        if(bOff) return "# 보일러: 닫힘 ";
        else return "# 보일러: 열림";
    }
}