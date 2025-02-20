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

    protected static final String HELP_MSG = """
        Here are your mission directives, Spartan:
        
           Task Management
        -  todo [description] → Adds a To-Do task.
        -  deadline [description] /by [YYYY-MM-DD HH:mm] → Adds a Deadline task.
        -  event [description] /from [YYYY-MM-DD HH:mm] /to [YYYY-MM-DD HH:mm] → Adds an Event task.
        -  list → Displays all tasks.
        -  mark [task number] → Marks a task as done.
        -  unmark [task number] → Unmarks a task.
        -  delete [task number] → Deletes a task.
        -  find [keyword] → Searches for tasks containing the keyword.

           Settings & Utility**
        -  help → Displays this command list.
        -  bye → Exits the program.
        
        ️  Example Commands
        -  todo Finish Halo Campaign
        -  deadline Submit Report /by 2024-02-20 23:59
        -  event UNSC Briefing /from 2024-02-21 14:00 /to 2024-02-21 16:00
        -  mark 2
        -  delete 3
        -  find Halo
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
        return line() + "Caution, Spartan:\n" + message + "\nType help for more details\n" + line();
    }

    /**
     * Shows lined help message
     */
    public static String showHelpMessage() {
        return line() + HELP_MSG + line();
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

