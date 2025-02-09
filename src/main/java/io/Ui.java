package io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Ui {
    protected final String INTRO_MSG = """
             Hello! I'm Carol, your personal assistant.
             What can I do for you?
            """;
    protected final String END_MSG = " Bye! Hope to hear from you soon!\n";

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
    public void showIntro() {
        line();
        System.out.print(INTRO_MSG);
        line();
    }

    /**
     * Shows lined end message
     */
    public void showEnd() {
        System.out.print(END_MSG);
        line();
    }

    /**
     * Shows lined error message
     * @param message Adds message to error
     */
    public static void showError(String message) {
        System.out.print("Error: \n"
                + message);
        line();
    }

    /**
     * Prints message to user
     * @param message User message
     */
    public static void print(String message) {
        System.out.println(message);
    }

    /**
     * Prints line for user
     * Increases readability
     */
    public static void line() {
        System.out.println("⸺".repeat(80));
    }

}

