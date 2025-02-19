package commands;

import cortana.CortanaException;
import io.EventParser;
import io.Ui;
import tasks.Tasklist;


public class DeadlineCommand extends Command {
    public DeadlineCommand(String message) {
        super(message);
    }

    @Override
    public String execute(Tasklist tasks) throws CortanaException {
        if (message == null || message.trim().isEmpty()) {
            return Ui.showError("""
                    Your input was: deadline
                    Expected input: deadline [action]
                    """);
        }
        String output = EventParser.parseTask(message, "deadline", tasks);
        return Ui.print("Added task:\n%s".formatted(message));
    }
}
