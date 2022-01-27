package org.vashonsd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

public class Room {

    // public enum Direction {EAST, WEST, NORTH, SOUTH, UP, DOWN}

    ArrayList<Item> items = new ArrayList<>();
    ArrayList<NPC> nonPlayers = new ArrayList<>();


    HashMap<String, String> blockedPaths = new HashMap<>();
    String name;
    String description;

    public Room(String name) {
        this.name = name;
    }

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public Item findItem(String itemName) {
        for (Item i:items) {
            if (Objects.equals(i.toString(), itemName)) {
                return i;
            }
        }
        return null;
    }

    public void addBlockedPath(String direction, String excuse) {
        blockedPaths.put(direction, excuse);
    }

    public HashMap<String, String> getBlockedPaths() {
        return blockedPaths;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public ArrayList<NPC> getNonPlayers() {
        return nonPlayers;
    }

    public NPC findNPC(String name) {
        for (NPC i:nonPlayers) {
            if (Objects.equals(i.getName().toLowerCase(Locale.ROOT), name)) {
                return i;
            }
        }
        return null;
    }

    public String getDescription() {
        if (description != null) {
            return description + "\n";
        }
        return "";
    }

    public String getSummary() {
        String result = "You are standing in the " + name;
        for (Item i: items) {
            result = result.concat("\nThere is a " + i);
        }

        for (NPC c: nonPlayers) {
            if (c.isNamed()) {
                result = result.concat("\nYou see " + c.getName());
            } else {
                result = result.concat("\nYou see a " + c.getName());
            }
        }

        return result;
    }
    public String toString() {
        return name;
    }
}