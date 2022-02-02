package org.vashonsd;

import java.util.ArrayList;
import java.util.Objects;

public class Player extends Actor{

    ArrayList<Item> inventory = new ArrayList<>();

    public Player(String name, Room startingRoom) {
        super(name, startingRoom);
    }

    public void addToInventory(Item item) {
        inventory.add(item);
    }
    public boolean isItemInInventory(Item item) {
        return inventory.contains(item);
    }
    public Item findItemInInventory(String itemName) {
        for (Item i:inventory) {
            if (Objects.equals(i.toString(), itemName)) {
                return i;
            }
        }
        return null;
    }
    public void removeFromInventory(Item item) {
        inventory.remove(item);
    }
    public ArrayList<Item> getInventory() {
        return  inventory;
    }
    public String getInventoryString() {
        String result = "";

        if (!inventory.isEmpty()) {
            for (Item e : inventory) {
                result = result.concat("You are holding a " + e + "\n");
            }
        } else {
            return "You are holding nothing\n";
        }

        return result;
    }
}
