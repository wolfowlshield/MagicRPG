package org.vashonsd;

import java.util.ArrayList;

public class Item {

    String name;

    ArrayList<String> keywords = new ArrayList<>();
    String text;
    // Maybe make items based on a keyword system...
    // They have a list of keywords that dictate what commands they have answers for...

    public Item(String name, Room room) {
        this.name = name;
        room.addItem(this); // Beautiful
    }

    public String toString() {
        return name;
    }

    public void makeReadable(String text) {
        this.text = text;

        keywords.add("readable");
    }

    public String readThis() {
        if (keywords.contains("readable")) {
            return "It reads \"" + text + "\"";
        }
        return "You can't read this item";
    }
}
