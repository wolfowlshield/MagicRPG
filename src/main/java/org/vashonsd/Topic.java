package org.vashonsd;

import java.util.HashMap;

public class Topic {

    HashMap<String, Topic> nextTopicMap = new HashMap<>();
    String text;

    public Topic(String text) {
        this.text = text;
    }

    public HashMap<String, Topic> getNextTopicMap() {
        return nextTopicMap;
    }

    public String getText() {
        return text;
    }
}
