package kg.ksucta.units;

import kg.ksucta.main.Room;

import java.util.Random;

public class Monster implements Unit, Runnable {
    private String name;
    private Room currentRoom;
    private int health;
    private int damage;
    private Random random;
    private volatile boolean running = true;

    public Monster(String name, int health, int damage) {
        this.health = health;
        this.name = name;
        this.damage = damage;
        random = new Random();
    }

    public void attack(Player player) {
        System.out.println(this.name + " ударил вас и отнял " + damage + " здоровья.");
        player.setHealth(damage);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        if (this.currentRoom != null) {
            this.currentRoom.setMonster(null);
        }
        this.currentRoom = currentRoom;
        this.currentRoom.setMonster(this);
    }

    @Override
    public Integer getHealth() {
        return health;
    }

    @Override
    public void setHealth(Integer health) {
        this.health -= health;
        if (this.health < 0) {
            this.health = 0;
        }
        System.out.println("Вы отняли у " + this.name + " " + health + "здоровья.");
        System.out.println("У " + this.name + " осталось " + this.health + " здоровья.");
        if (this.health == 0) {
            System.out.println("Вы убили " + this.name);
        }
    }

    public void changeRoom() throws InterruptedException {
        while (getCurrentRoom().getPlayer() == null) {
            Thread.sleep(random.nextInt(5000) + 5000);
            int roomLn = getCurrentRoom().getAvailableRooms().size();
            int randInt = random.nextInt(roomLn);
            if (getCurrentRoom().getAvailableRooms().get(randInt).getMonster() == null) {
                setCurrentRoom(getCurrentRoom().getAvailableRooms().get(randInt));
            }
        }
    }

    public boolean attackUnit() throws InterruptedException {
        System.out.println(getName() + " встретил вас и начал атаковать!");
        while (getCurrentRoom().getPlayer() != null) {
            attack(getCurrentRoom().getPlayer());
            Thread.sleep(random.nextInt(10000) + 10000);
            if (getCurrentRoom().getPlayer().getHealth() < 0 || getHealth() <= 0) {
                return false;
            }

        }
        return true;
    }

    public void state() {
        if (getHealth() <= 0) {
            System.out.println("Вы убили " + getName());
            return;
        }
        System.out.println();
    }

    public void end() {
        running = false;
        System.out.println(name);
    }

    public void run() {
        while (running) {
            try {
                changeRoom();
                running = attackUnit();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        state();
    }
}


