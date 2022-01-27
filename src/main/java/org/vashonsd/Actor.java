package org.vashonsd;

public class Actor {

    String name;
    Room currentRoom;
    public Actor(String name, Room startingRoom) {
        this.name = name;
        currentRoom = startingRoom;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void moveRoom(Room nextRoom) {
        currentRoom = nextRoom;
    }

    public String getName() {
        return name;
    }
}
