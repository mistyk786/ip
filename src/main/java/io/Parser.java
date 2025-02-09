package io;

import carol.CarolException;
import commands.*;

public class Parser {
    /**
     * Parses user input for command to be executed
     * @param input String that was inputted by the user
     * @return Command that is executed by respective Command classes
     * @throws CarolException Input that does not contain any command
     */
    public Command parseCommand(String input) throws CarolException {
        input = input.trim();
        if (input.isEmpty()) {
            Ui.showError("Empty command.");
        }
        String[] parts = input.split(" ", 2);
        String action = parts[0];
        String message = parts.length > 1 ? parts[1] : "";
        switch (action) {
        case "bye":
            return new ByeCommand(message);
        case "list":
            return new ListCommand(message);
        case "mark":
            return new MarkCommand(message);
        case "unmark":
            return new UnmarkCommand(message);
        case "delete":
            return new DeleteCommand(message);
        case "todo":
            return new TodoCommand(message);
        case "deadline":
            return new DeadlineCommand(message);
        case "event":
            return new EventCommand(message);
        default:
            throw new CarolException("Invalid command.\n");
        }
    }
}

