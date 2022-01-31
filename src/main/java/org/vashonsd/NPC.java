package org.vashonsd;

import java.util.Locale;
import java.util.Scanner;

public class NPC extends Actor {

    Topic startingTopic;
    Topic currentTopic;
    Boolean named;

    public NPC(String name, Room startingRoom, Topic startingTopic) {
        super(name, startingRoom);
        startingRoom.getNonPlayers().add(this);
        this.startingTopic = startingTopic;
        currentTopic = startingTopic;
        named = true;
    }

    public NPC(String name, Room startingRoom, Topic startingTopic, boolean named) {
        super(name, startingRoom);
        startingRoom.getNonPlayers().add(this);
        this.startingTopic = startingTopic;
        currentTopic = startingTopic;
        this.named = named;
    }

    public boolean isNamed() {
        return named;
    }

    public void startConversation() { // Re-do This, this is sloppy, we should be able to test conversations w/o using a scanner
        boolean stillTalking = true;
        Scanner input = new Scanner(System.in);

        while (stillTalking) {
            System.out.println("\"" + currentTopic.getText() + "\"");
            if (currentTopic.getNextTopicMap().isEmpty()) {
                stillTalking = false;
            } else {
                nextTopic(input.nextLine().toLowerCase(Locale.ROOT));
            }
        }
    }
    public Topic getCurrentTopic() {
        return currentTopic;
    }

    public void nextTopic(String nextTopic) {
        for (String str : currentTopic.getNextTopicMap().keySet()) {
            if (nextTopic.equals(str.toLowerCase(Locale.ROOT))) {
                currentTopic = currentTopic.getNextTopicMap().get(nextTopic);
                break;
            }
        }
    }
}
