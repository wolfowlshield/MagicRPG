package org.vashonsd;

import com.google.gson.Gson;
import org.vashonsd.HTTPRequests.Message;
import org.vashonsd.HTTPRequests.Requester;

import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class Game {
    Scanner input = new Scanner(System.in);
    Gson gson = new Gson();

    Room center = new Room("Town Center",
            "The town center is populated, with people walking in every direction. At center of the city square is an ornate fountain. Standing by the fountain is a friendly enough looking fellow named patrick. ");
    Room market = new Room("Market");
    Room inn = new Room("Inn");
    Room bedroom = new Room("bedroom");
    Room westEntrance = new Room("West Entrance");
    Room eastEntrance = new Room("East Entrance");
    Room field = new Room("Field");
    Room mainRoad = new Room("Main Road");
    Room castle = new Room("Castle");
    Room riversideRoad = new Room("Riverside Road");
    Room coastalVillage = new Room("Coastal village");
    Room docks = new Room("Docks");
    Room beach = new Room("Beach");

    Topic hello = new Topic("Hello There!");
    Topic guardDuty = new Topic("Another lazy day of guard duty.");
    Topic backstory = new Topic("I come from the village west of here.");
    Topic leave = new Topic("See ya!");

    NPC guide = new NPC("Patrick", center, hello);
    NPC guard = new NPC("Guard", westEntrance, guardDuty, false);

    Player player;

    RoomMap gameMap = new RoomMap();
    SentenceDeconstructor sentence = new SentenceDeconstructor();


    public Game() {
        gameMap.addRoomToMap(center, gameMap.generateNearbyRoomHashMap("west", market, "east", eastEntrance, "south", mainRoad, "north", castle));
        gameMap.addRoomToMap(castle, gameMap.generateNearbyRoomHashMap("south", center));
        gameMap.addRoomToMap(market, gameMap.generateNearbyRoomHashMap("east", center, "west", westEntrance, "north", inn));
        gameMap.addRoomToMap(inn, gameMap.generateNearbyRoomHashMap("south", market, "up", bedroom));
        gameMap.addRoomToMap(bedroom, gameMap.generateNearbyRoomHashMap("down", inn));
        gameMap.addRoomToMap(westEntrance, gameMap.generateNearbyRoomHashMap("east", market, "west", riversideRoad));
        gameMap.addRoomToMap(eastEntrance, gameMap.generateNearbyRoomHashMap("west", center, "east", field));
        gameMap.addRoomToMap(field, gameMap.generateNearbyRoomHashMap("west", eastEntrance));
        gameMap.addRoomToMap(mainRoad, gameMap.generateNearbyRoomHashMap("north", center));
        gameMap.addRoomToMap(riversideRoad, gameMap.generateNearbyRoomHashMap("east", westEntrance, "west", coastalVillage));
        gameMap.addRoomToMap(coastalVillage, gameMap.generateNearbyRoomHashMap("east", riversideRoad, "west", docks, "south", beach));
        gameMap.addRoomToMap(docks, gameMap.generateNearbyRoomHashMap("east", coastalVillage));
        gameMap.addRoomToMap(beach, gameMap.generateNearbyRoomHashMap("north", coastalVillage));

        center.addBlockedPath("north", "The gates to the castle are locked.");
        eastEntrance.addBlockedPath("east", "As you try to go through the east Entrance, a guard pipes up and says \"Hey! No adventurers are allowed this way, the road has been deemed too dangerous.\"");

        buildConversations();
    }

    public void run() throws Exception {
        boolean stillRunning;
        stillRunning = mainMenu();
        if (stillRunning) {
            System.out.println("Tip: Make sure to start your commands with a verb!");
            System.out.print(player.getCurrentRoom().getDescription());
        }
        while(stillRunning) {
            System.out.println(player.getCurrentRoom().getSummary());
            System.out.println("What do you want to do?");
            sentence.setUserSentence(input.nextLine().toLowerCase(Locale.ROOT));
            stillRunning = doCommand(sentence);
        }
    }

    public boolean mainMenu() throws Exception {
        while(true) {
            // Add a Title sout here
            System.out.println("- New Game \n"
            + "- Continue\n"
            + "- Exit");
            sentence.setUserSentence(input.nextLine());
            switch (sentence.getVerb()) {
                case "exit" -> {
                    return false;
                }
                case "continue" -> {
                    String roomName = Requester.get();
                    player = new Player("Mage", gameMap.findRoom(roomName)); // Must check to see if the room isn't empty
                    return true;
                }
                case "new" -> {
                    String save = gson.toJson(new Message("User", "Town Center"));
                    player = new Player("Mage", center);
                    Requester.post(save);
                    return true;
                }
            }
        }
    }

    public boolean doCommand(SentenceDeconstructor sentence) throws IOException {
        boolean stillRunning = true;
        switch (sentence.getVerb()) {
            case "map" -> System.out.println(gameMap.generateVisualMap(player.getCurrentRoom()));
            case "quit","exit" -> stillRunning = false;
            case "head", "move", "go" -> {
                Room nextRoom = gameMap.getFullRoomMap().get(player.getCurrentRoom()).get(sentence.getDirectObject());
                if (nextRoom != null) {
                    if (player.getCurrentRoom().getBlockedPaths().containsKey(sentence.getDirectObject())) {
                        System.out.println(player.getCurrentRoom().getBlockedPaths().get(sentence.getDirectObject()));
                    } else {
                        player.moveRoom(nextRoom);
                        System.out.print(player.getCurrentRoom().getDescription());
                    }
                } else {
                    System.out.println("There's no room there");
                }
            }
            case "talk" -> {
                NPC talker = player.getCurrentRoom().findNPC(sentence.getDirectObject());
                if (talker != null) {
                    talker.startConversation();
                } else {
                    System.out.println("There's no one with that name in this room");
                }
            }
            case "check" -> {
                switch(sentence.getDirectObject()) { // Nested Switch cases... Nice
                    case "map" -> System.out.println(gameMap.generateVisualMap(player.getCurrentRoom()));
                    case "inventory" -> System.out.println("You are holding nothing");
                }
            }
            case "save" -> {
                String save = gson.toJson(new Message("User", player.getCurrentRoom().toString()));
                Requester.post(save);
            }
            // add a way to sleep
            default -> System.out.println("I don't know that command");
        }
        return stillRunning;
    }

    public void buildConversations() {
        hello.getNextTopicMap().put("backstory", backstory); // Make sure these keys are Lowercase
        hello.getNextTopicMap().put("leave", leave);
        backstory.getNextTopicMap().put("leave", leave);
        guardDuty.getNextTopicMap().put("leave", leave);
    }
}
/*
System.out.println("What's your name?");
String name = input.nextLine();
System.out.println("Give me a message");
String firstMessage = gson.toJson(new Message(input.nextLine(), name));

requester.post(firstMessage);
 */