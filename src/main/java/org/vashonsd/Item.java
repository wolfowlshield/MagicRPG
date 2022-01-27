package org.vashonsd;

public class Item {

    String name;

    public Item(String name, Room room) {
        this.name = name;
        room.addItem(this); // Beautiful
    }

    public String toString() {
        return name;
    }
}
