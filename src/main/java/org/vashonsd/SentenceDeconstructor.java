package org.vashonsd;

import java.util.Locale;

public class SentenceDeconstructor {

    // This code should take a string and return substrings of keywords
    // This is to let the user write readable sentences

    String userSentence;

    String firstWord;
    String directObject;
    String prepSubject; // Subject After a preposition

    String[] helpingWords = new String[]{"the ","a ","to ","up ", "I ", "my "};

    public SentenceDeconstructor() {
        // This could all be static but I don't care
    }

    public void setUserSentence(String str) {
        userSentence = str.toLowerCase(Locale.ROOT);
        findKeyWords();
    }

    private void findKeyWords() { // Sets the values of the verbs and subjects
        if (userSentence.contains(" ")) {
            for (String i: helpingWords) { // Add helping words here so that they can be ignored
                if (userSentence.contains(i)) {
                    userSentence = userSentence.replace(i, ""); // Removes helping words
                }
            }
            firstWord = userSentence.substring(0, userSentence.indexOf(' '));
            directObject = userSentence.substring(userSentence.indexOf(' ') + 1); // The word the happens immediately after the verb (Which should be the first word)

            if (userSentence.contains("with ")) { // Check for prepositions
                prepSubject = userSentence.substring(userSentence.indexOf("with ") + 1);
            } else {
                prepSubject = null;
            }
        } else { // If it's just a one word command
            firstWord = userSentence;
            directObject = null;
            prepSubject = null;
        }
    }

    public String getFirstWord() {
        return firstWord;
    }

    public String getDirectObject() {
        return directObject;
    }

    public String getPrepSubject() {
        return prepSubject;
    }

    public String findNoun(String str) {
        if (str.contains(" ")) {
            for (String i : helpingWords) { // Add helping words here so that they can be ignored
                if (str.contains(i)) {
                    str = str.replace(i, ""); // Removes helping words
                }
            }
        }
        return str;
    }
}
