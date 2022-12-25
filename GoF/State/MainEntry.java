import java.util.Scanner;

public class MainEntry {
    public static void main(String[] args) {
        Plyaer player = new Player();
        Scanner scanner = new Scanner(System.in);

        while(true) {
            Sytstem.out.println("플레이어의 상태: "
            + plyaer.getState().getDescription()
            + " / 속도: " + plyaer.getSpeed() + "km/h");

            System.out.print("[1]제자리 서기 [2]앉기 [3]걷기 [4]뛰기 [5]종료: ");

            String input = scanner.next();
            System.out.println();

            if(input.equals("1")) player.getState.standUp();
            else if(input.equals("2")) player.getState().sitDown();
            else if(input.equals("3")) player.getState().walk();
            else if(input.equals("4")) player.getState.run();
            else if(input.equals("5")) break;
        }

        scanner.close();
    }
}