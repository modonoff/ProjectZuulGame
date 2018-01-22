package kg.ksucta.units;

import kg.ksucta.commands.Command;
import kg.ksucta.items.Item;
import kg.ksucta.main.Room;

import java.util.ArrayList;
import java.util.List;

public class Player implements Unit {

    private String name;
    private Room currentRoom;
    private List<Item> items;
    private Integer health;
    private Integer maxHealth = 1500;
    private Integer slotCount = 4;

    public Player() {
        items = new ArrayList<>();
        health = 1000;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Integer getHealth() {
        return health;
    }

    @Override
    public void setHealth(Integer health) {
        this.health -= health;
        System.out.println("У вас осталось " + this.health + " здоровья.");
        if (this.health <= 0) {
            System.out.println("Вас убили!");
        }
    }

    @Override
    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
        currentRoom.setPlayer(this);
    }

    public void attack(Command command, Monster monster) {
        if (!command.hasSecondWord()) {
            System.out.println("Выберите предмет. Комадна 'ITEMS', чтобы узнать, какие у вас есть предметы.");
            return;
        }
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(command.getSecondWord())) {
                monster.setHealth(item.getDamageValue());
                return;
            }
        }
        System.out.println("Такого оружия в вашем арсенале, к сожалению, не существует.");
    }

    public Integer getSlotCount() {
        return slotCount;
    }

    public void setSlotCount(int count) {
        slotCount += count;
        System.out.println("В вашем арсенале осталось " + getSlotCount() + " слотов.");
    }

    public void changeRoom(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Куда хочешь пойти?");
            System.out.print("Доступные выходы: ");
            for (Room element : getCurrentRoom().getAvailableRooms()) {
                System.out.print(element.getName() + " -- ");
            }
            System.out.println();
            return;
        }
        if (command.hasSecondWord()) {
            for (Room room : currentRoom.getAvailableRooms()) {
                if (room.getName().equalsIgnoreCase(command.getSecondWord())) {
                    currentRoom.setPlayer(null);
                    currentRoom = room;
                    currentRoom.setPlayer(this);
                    System.out.println("Ты находишься в " + this.getCurrentRoom().getName() + ". " + this.getCurrentRoom().getDescription());
                    System.out.print("Здесь есть: ");
                    getCurrentRoom().showItems();
                    getCurrentRoom().showAvailableRooms();
                    return;
                }
            }
            System.out.println("Такой комнаты, к сожалению, не существует.");
        }
    }

    public void checkHealthLevel() {
        System.out.println("Ваше здоровье составляет " + getHealth() + ".");
    }

    public void takeItem(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Что хочешь взять?");
            return;
        }
        if (getSlotCount() == 0) {
            System.out.println("У вас недостаточно места в арсенале. Выбросьте некоторые вещи, используя команду 'DROP'.");
        }
        if (getSlotCount() > 0) {
            if (command.hasSecondWord()) {
                int count = 0;

                Item currentItem = null;
                for (Item element : currentRoom.getItems()) {
                    if (element.getName().equalsIgnoreCase(command.getSecondWord())) {
                        currentItem = element;
                        this.items.add(element);
                        setSlotCount(-1);
                        System.out.print("Вы подобрали " + element.getName() + ". ");
                        count++;
                    }
                }
                if (count == 0) {
                    System.out.println("Такого предмета в данной комнате не существует");
                    return;
                }
                currentRoom.removeItem(currentItem);
            }
        }
    }

    public void dropItem(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Что ты хочешь выбросить?");
            return;
        }
        if (command.hasSecondWord()) {
            int c = 0;
            Item currentItem = null;
            for (Item element : getItems()) {
                if (element.getName().equalsIgnoreCase(command.getSecondWord())) {
                    currentItem = element;
                    currentRoom.getItems().add(element);
                    setSlotCount(1);
                    System.out.print("Вы выбросили " + element.getName() + ". ");
                    c++;
                }
            }
            if (c == 0) {
                System.out.println("Такого предмета в вашем арсенале не существует.");
                return;
            }
            items.remove(currentItem);
        }
    }

    public void showItems() {
        System.out.print("В вашем арсенале: ");
        for (Item element : items) {
            System.out.print(element.getName() + " || ");
        }
        System.out.println();
    }

    public List<Item> getItems() {
        return items;
    }

    public void useArtifact(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Что хочешь использовать?");
            return;
        }
        if (command.hasSecondWord()) {
            if (health < maxHealth) {
                for (Item element : items) {
                    String className = element.getClass().getName();
                    if (className.equals("items.Artifact") && element.getName().equalsIgnoreCase(command.getSecondWord())) {
                        this.health = element.getPoints() + health;
                        System.out.println("Вы использовали вещь " + element.getName() + ". К вашему здоровью прибавилось " + element.getPoints() + ".");
                        System.out.println("Теперь ваше здоровье составляет " + getHealth() + ".");
                        setSlotCount(1);
                        return;
                    }
                }
            }
            if (health > maxHealth) {
                System.out.println("Достигнут максимум здоровья. Воспользуйтесь вещью позднее.");
                System.out.println("Чтобы проверить здоровье, воспользуйтесь командой 'HP'.");
                return;
            }
            System.out.println("Такого артифакта в списке, к сожалению, нет.");
        }
    }
}
