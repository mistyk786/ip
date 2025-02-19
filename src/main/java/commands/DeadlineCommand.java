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
        return EventParser.parseTask(message, "deadline", tasks);
    }
}
