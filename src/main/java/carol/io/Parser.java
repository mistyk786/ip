package carol.io;

import carol.CarolException;
import carol.commands.*;
import carol.tasks.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class Parser {
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
                return new byeCommand(message);
            case "list":
                return new listCommand(message);
            case "mark":
                return new markCommand(message);
            case "unmark":
                return new unmarkCommand(message);
            case "delete":
                return new deleteCommand(message);
            case "todo":
                return new todoCommand(message);
            case "deadline":
                return new deadlineCommand(message);
            case "event":
                return new eventCommand(message);
            default:
                throw new CarolException("Invalid command.\n");
        }
    }
}

