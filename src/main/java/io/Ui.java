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

    public static String readLine() throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = reader.readLine().trim();
        if (input.isEmpty()) {
            return readLine();
        }
        return input;
    }

    public void showIntro() {
        line();
        System.out.print(INTRO_MSG);
        line();
    }

    public void showEnd() {
        System.out.print(END_MSG);
        line();
    }

    public static void showError(String message) {
        System.out.print("Error: \n"
                + message);
        line();
    }

    public static void print(String message) {
        System.out.println(message);
    }

    public static void line() {
        System.out.println("⸺".repeat(80));
    }

}

