package kg.ksucta.main;

import kg.ksucta.commands.Command;
import kg.ksucta.commands.CommandProcess;
import kg.ksucta.items.Artifact;
import kg.ksucta.items.Weapon;
import kg.ksucta.services.ConsoleIO;
import kg.ksucta.units.Monster;
import kg.ksucta.units.Player;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Player player;
    private CommandProcess commandProcess;
    private Monster huskar;
    private Monster ember;
    private Monster doom;
    private List<Monster> monsters;
    private ConsoleIO console;

    public Game() {
        monsters = new ArrayList<>();
        console = new ConsoleIO();
        commandProcess = new CommandProcess();
        player = new Player();
        huskar = new Monster("Huskar", 964, 112);
        ember = new Monster("Ember", 1493, 199);
        doom = new Monster("Doom", 633, 59);
        monsters.add(huskar);
        monsters.add(ember);
        monsters.add(doom);
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.initialize();
        game.play();
    }

    public void play() {
        setPlayerName();
        console.printWelcome(player.getName());
        System.out.println(player.getCurrentRoom().getName());
        System.out.println("Введи HELP для справочки.");
        Thread monster1 = new Thread(huskar);
        Thread monster2 = new Thread(ember);
        Thread monster3 = new Thread(doom);
        monster1.start();
        monster2.start();
        monster3.start();
        boolean run = false;
        while (!run) {
            Command command = commandProcess.getCommand();
            if (player.getHealth() <= 0) {
                System.out.println("Вы проиграли!");
                commandProcess.endGame(this, monsters);
                return;
            }
            if (Thread.activeCount() == 1) {
                System.out.println("Поздравляю, вы выиграли!!!");
            }

            run = commandProcess.processCommand(command, this, monsters);
        }
    }

    private void setPlayerName() {
        player.setName(console.askQuetion("Как зовут Тебя?"));
    }

    public Player getPlayer() {
        return player;
    }

    private void initialize() {
        Room alabasta = new Room("Alabasta", "Пустынное место,в котором кроме песка ничего больше нет.");
        Room loguetown = new Room("Loguetown", "Место,окруженное злыми духами, где всегда светит солнце.");
        Room sabaody = new Room("Sabaody", "Огромный мангровый лес. Весьма подходящее место для остановки обычных путешественников и пиратов");
        Room skypiea = new Room("Skypiea", "Всё там состоит из облаков: и море, и земля. Однако также здесь существует половина острова с Земли – Джая, когда-то давно взлетевшая в небо, подброшенная сильным вертикальным течением.");
        Room ohara = new Room("Ohara", "Охара появилась более 5000 лет назад.");
        Room grut = new Room("Grut", "Все вокруг коричневого цвета, любимое место для монстров.");
        Room talador = new Room("Talador", "Загадочный остров, в котором обитают одни лишь змеи.");
        Room suramar = new Room("Suramar", "Тайная комната Гарри Поттера.");

        alabasta.setAvailableRooms(loguetown);
        alabasta.setAvailableRooms(sabaody);
        alabasta.setAvailableRooms(grut);
        sabaody.setAvailableRooms(alabasta);
        sabaody.setAvailableRooms(skypiea);
        sabaody.setAvailableRooms(ohara);
        loguetown.setAvailableRooms(alabasta);
        skypiea.setAvailableRooms(sabaody);
        ohara.setAvailableRooms(sabaody);
        grut.setAvailableRooms(alabasta);
        grut.setAvailableRooms(talador);
        grut.setAvailableRooms(suramar);
        talador.setAvailableRooms(grut);
        talador.setAvailableRooms(suramar);
        suramar.setAvailableRooms(grut);
        suramar.setAvailableRooms(talador);

        alabasta.setItem(new Weapon("Daedalus", 87));
        sabaody.setItem(new Weapon("Butterfury", 60));
        loguetown.setItem(new Weapon("Armlet", 137));
        loguetown.setItem(new Weapon("MKB", 100));
        ohara.setItem(new Weapon("Scadi", 200));
        grut.setItem(new Weapon("Burize", 159));
        suramar.setItem(new Weapon("Rapier", 300));
        ohara.setItem(new Artifact("Taraska", "Вещь, которая придаст тебе силы.", 417));
        suramar.setItem(new Artifact("Bloodstone", "Дополнительный запас здоровья.", 342));
        talador.setItem(new Artifact("Bracer", "Надень этот браслет на руку, доверься мне.", 251));

        huskar.setCurrentRoom(ohara);
        ember.setCurrentRoom(skypiea);
        doom.setCurrentRoom(grut);

        player.setCurrentRoom(alabasta);
    }
}
