import java.util.Random;
import java.util.Iterator;

public class FairDiceGame extends DiceGame {
    private Random random = new Random();

    @Override
    public void play() {
        int diceNumber = random.nextInt(6) + 1;
        System.out.println("주사위 받아라~ " + diceNumber);

        Iterator<Player> iter = playerList.iterator();
        while(iter.hasNext()) {
            iter.next().update(diceNumber);
        }
    }
}