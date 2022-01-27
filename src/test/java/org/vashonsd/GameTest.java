package org.vashonsd;

import org.junit.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    public void testPlayerMove() throws IOException {
        Game game = new Game();
        SentenceDeconstructor sentence = new SentenceDeconstructor();

        sentence.setUserSentence("move south");
        game.doCommand(sentence);
        assertEquals("Main Road", game.player.getCurrentRoom().toString());

        sentence.setUserSentence("go north");
        game.doCommand(sentence);
        assertEquals("Town Center", game.player.getCurrentRoom().toString());

        sentence.setUserSentence("HEaD NoRth");
        game.doCommand(sentence);
        assertEquals("Town Center", game.player.getCurrentRoom().toString());

        sentence.setUserSentence("go up");
        game.doCommand(sentence);
        assertEquals("Town Center", game.player.getCurrentRoom().toString());

        sentence.setUserSentence("head to the west");
        game.doCommand(sentence);
        assertEquals("Market", game.player.getCurrentRoom().toString());
    }

    @Test
    public void testVisualMap() throws IOException {
        Game game = new Game();
        SentenceDeconstructor sentence = new SentenceDeconstructor();

        sentence.setUserSentence("map");
        game.doCommand(sentence);
        assertEquals("|------------|------------|------------|\n" +
                "| Inn      ^ | Castle     |            |\n" +
                "|-----||-----|-----||-----|------------|\n" +
                "= Market     = Town Cente = East Entra =\n" +
                "|------------|-----||-----|------------|\n" +
                "|            | Main Road  |            |\n" +
                "|------------|------------|------------|\n", game.gameMap.generateVisualMap(game.player.getCurrentRoom()));

        sentence.setUserSentence("head west");
        game.doCommand(sentence);

        sentence.setUserSentence("check the map");
        game.doCommand(sentence);
        assertEquals("|------------|------------|------------|\n" +
                "|            | Inn      ^ | Castle     |\n" +
                "|------------|-----||-----|-----||-----|\n" +
                "= West Entra = Market     = Town Cente =\n" +
                "|------------|------------|-----||-----|\n" +
                "|            |            | Main Road  |\n" +
                "|------------|------------|------------|\n", game.gameMap.generateVisualMap(game.player.getCurrentRoom()));
    }
}