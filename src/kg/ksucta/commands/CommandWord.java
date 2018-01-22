package kg.ksucta.commands;

public class CommandWord {

    private final String[] commands = {"GO", "TAKE", "DROP", "HELP", "QUIT", "ITEMS", "ATTACK", "USE", "HP"};

    public boolean isCommand(String command) {
        for (String commandWord : commands) {
            if (commandWord.equalsIgnoreCase(command)) {
                return true;
            }
        }
        return false;
    }

    public void showCommands() {
        for (String command : commands) {
            System.out.print(command + " -- ");
        }
        System.out.println();
    }
}
