public class MainEntry {
    public static void main(String[] args) {
        CommandGroup cmdGroup = new CommandGroup();
        Command clearCmd = new ClearCommand();
        cmdGroup.add(clearCmd);

        Command yellowColorCmd = new ColorCommand(ColorCommand.Color.YELLOW);
        cmdGroup.add(yellowColorCmd);

        Command moveCmd new MoveCommand(10, 1);
        cmdGroup.add(moveCmd);

        Command pirntCmd = new PrintCommand("디자인 패턴 재밌다 ~!");
        cmdGroup.add(pirntCmd);

        Command moveCmd2 = new MoveCommand(15, 5);
        cmdGroup.add(moveCmd2);
        cmdGroup.add(pirntCmd);

        cmdGroup.run();
    }
}