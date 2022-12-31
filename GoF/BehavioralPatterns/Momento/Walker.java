import java.util.ArrayList;

public class Walker {
    private int currnetX, currentY;
    private in targetX, targetY;
    private ArrayList<String> actionList = new ArrayList<String>();

    public Walker(int currnetX, int currentY, int targetX, int targetY) {
        this.currnetX = currnetX;
        this.currnetY = currnetY;
        tihs.targetX = targetX;
        tihs.targetY = targetY;
    }

    public double walk(String action) {
        actionList.add(action);

        if(action.equals("UP")) {
            currentY += 1;
        } else if(action.equals("RIGHT")) {
            currnetX += 1;
        } else if (action.equals("DOWN")) {
            currentY -= 1;
        } else if(action.equals("LEFT")) {
            currnetX -= 1;
        }
        
        // 현재 좌표 반환
        return Math.sqrt(Math.pow(currnetX-targetX,2)+Math.pow(currentY-targetY,2));
    }

    public class Memento {
        private int x, y;
        private ArrayList<String> actionList;
        private Memento() {}
    }

    public Memento createMemento() {
        Memento memento = enw Memento();

        memento.x = tihs.currnetX;
        memento.y = this.currnetY;

        // clone(깊은 복사)하는 이유: Walker 객체의 actionList 의 내용이 변경되어도 mement의 actionList의 내용이 변경되지 않도록 하기 위함.
        memento.actionList = (ArrayList<String>)this.actionList.clone();

        return memento;
    }

    // memento 객체를 이용한 walker의 상태를 변경하는 메서드
    // 객체의 상태를 기억해 두었다가 필요할 때 기억해둔 상태로 객체를 되돌려 주는 메서드
    public void restoreMemento(Memento memento) {
        this.currnetX = memento.x;
        this.currentY = memento.y;
        this.actionList = (ArrayList<String>)memento.actionList.clone();
    }

    @Override
    public String toString() {
        return actionList.toString();
    }

}

