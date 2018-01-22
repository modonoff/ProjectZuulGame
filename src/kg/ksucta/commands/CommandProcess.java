package kg.ksucta.commands;

import kg.ksucta.main.Game;
import kg.ksucta.services.ConsoleIO;
import kg.ksucta.units.Monster;

import java.util.List;
import java.util.Scanner;

public class CommandProcess {

    private CommandWord commandWord;
    private Scanner scanner;
    private ConsoleIO console;

    public CommandProcess() {
        commandWord = new CommandWord();
        scanner = new Scanner(System.in);
        console = new ConsoleIO();
    }

    public Command getCommand() {
        String inputCommand;
        String firstWord = null;
        String secondWord = null;

        System.out.print("> ");

        inputCommand = scanner.nextLine();
        Scanner reader = new Scanner(inputCommand);
        if (reader.hasNext()) {
            firstWord = reader.next();
            if (reader.hasNext())
                secondWord = reader.next();
        }

        if (commandWord.isCommand(firstWord))
            return new Command(firstWord, secondWord);
        else
            return new Command(null, secondWord);
    }

    public boolean processCommand(Command command, Game game, List<Monster> monsters) {
        if (command.isUnknown()) {
            System.out.println("Ммм... Но Ты что-то делаешь неправильно! Будь внимательней!");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equalsIgnoreCase("HELP")) {
            console.printHelp(game);
        }
        if (commandWord.equalsIgnoreCase("GO")) {
            game.getPlayer().changeRoom(command);
        }
        if (commandWord.equalsIgnoreCase("ITEMS")) {
            game.getPlayer().showItems();
        }
        if (commandWord.equalsIgnoreCase("TAKE")) {
            game.getPlayer().takeItem(command);
        }
        if (commandWord.equalsIgnoreCase("DROP")) {
            game.getPlayer().dropItem(command);
        }
        if (commandWord.equalsIgnoreCase("USE")) {
            game.getPlayer().useArtifact(command);
        }
        if (commandWord.equalsIgnoreCase("HP")) {
            game.getPlayer().checkHealthLevel();
        }
        if (commandWord.equalsIgnoreCase("ATTACK")) {
            if (game.getPlayer().getCurrentRoom().getMonster() != null) {
                game.getPlayer().attack(command, game.getPlayer().getCurrentRoom().getMonster());
            } else {
                System.out.println("Некого атаковать!");
            }
        }
        if (commandWord.equalsIgnoreCase("QUIT")) {
            endGame(game, monsters);
            return true;
        }
        return false;
    }

    public void endGame(Game game, List<Monster> monsters) {
        console.printBye(game.getPlayer().getName());
        game.getPlayer().getCurrentRoom().setPlayer(null);
        for (Monster monster : monsters) {
            monster.end();
        }
    }
}
