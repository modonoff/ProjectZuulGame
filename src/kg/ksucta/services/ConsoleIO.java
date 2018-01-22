package kg.ksucta.services;

import kg.ksucta.commands.CommandWord;
import kg.ksucta.main.Game;
import kg.ksucta.main.Room;
import kg.ksucta.units.Player;

import java.util.Scanner;

public class ConsoleIO {

    private Player player;
    private Scanner scanner;
    private CommandWord commandWord;

    public ConsoleIO() {
        player = new Player();
        commandWord = new CommandWord();
        scanner = new Scanner(System.in);
    }

    public void printHelp(Game game) {
        System.out.println("Ты находишься в сказочном мире под названием Zuul, где обитают кровожадные монстры, готовые в любую минуту полакомиться Тобой.");
        System.out.println("Доступны следующие команды: ");
        commandWord.showCommands();
        System.out.println("Доступны следующие комнаты: ");
        for (Room room : game.getPlayer().getCurrentRoom().getAvailableRooms()) {
            System.out.print(room.getName() + " -- ");
        }
        System.out.println();
    }

    public void printWelcome(String name) {
        System.out.println("Привет, " + name);
        System.out.println("Это - игра Zuul. Игра Zuul погрузит Тебя, мой милый друг, в удивительный мир приключений и опасностей!");
        System.out.print("Ты сейчас находишься в месте под названием: ");
    }

    public void printBye(String name) {
        System.out.println("Спасибо за игру, " + name);
    }

    public String askQuetion(String question) {
        System.out.println(question);
        System.out.print("> ");
        String questionAnswer = scanner.nextLine().trim();
        return questionAnswer;
    }
}
