package io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Ui {
    protected static final String INTRO_MSG = """
             Greetings, Spartan. I am Cortana, your AI assistant.
             What mission can I assist you with today?
            """;

    protected static final String END_MSG = """
             Goodbye, Chief. See you soon.
            """;

    /**
     * Reads line from user
     * @return input from user that is parsed by Parser
     * @throws IOException shag
     */
    public static String readLine() throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = reader.readLine().trim();
        if (input.isEmpty()) {
            return readLine();
        }
        return input;
    }

    /**
     * Shows lined intro message
     */
    public static String showIntro() {
        return line() + INTRO_MSG + line();
    }

    /**
     * Shows lined end message
     */
    public static String showEnd() {
        return line() + END_MSG + line();
    }

    /**
     * Shows lined error message
     * @param message Adds message to error
     */
    public static String showError(String message) {
        return line() + "Caution, Spartan:\n" + message + "\n" + line();
    }

    /**
     * Prints message to user
     * @param message User message
     */
    public static String print(String message) {
        return line() + message + "\n" + line();
    }

    /**
     * Prints line for user
     * Increases readability
     */
    public static String line() {
        return "⸺".repeat(10) + "\n";
    }

}

