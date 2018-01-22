package kg.ksucta.main;

import kg.ksucta.items.Item;
import kg.ksucta.units.Monster;
import kg.ksucta.units.Player;

import java.util.ArrayList;
import java.util.List;

public class Room {

    private String name;
    private String description;
    private List<Item> items;
    private Player player;
    private Monster monster;
    private List<Room> availableRooms;

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
        items = new ArrayList<>();
        availableRooms = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Item> getItems() {
        return items;
    }

    public Monster getMonster() {
        return monster;
    }

    public List<Room> getAvailableRooms() {
        return availableRooms;
    }

    public void setAvailableRooms(Room room) {
        availableRooms.add(room);
    }

    public void setItem(Item item) {
        items.add(item);
    }

    public void showItems() {
        for (Item element : items) {
            System.out.print(element.getName() + " || ");
        }
        System.out.println();
    }

    public void showAvailableRooms() {
        for (Room room : availableRooms) {
            System.out.print(room.getName() + " -- ");
        }
        System.out.println();
    }

    public void removeItem(Item item) {
        items.remove(item);
    }
}
