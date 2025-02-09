package commands;

import carol.CarolException;
import io.EventParser;
import io.Ui;
import tasks.Tasklist;


public class DeadlineCommand extends Command {
    public DeadlineCommand(String message) {
        super(message);
    }

    @Override
    public boolean execute(Tasklist tasks) throws CarolException {
        if (message == null || message.trim().isEmpty()) {
            Ui.showError("""
                        Your input was: deadline
                        Expected input: deadline [action]
                        """);
            return true;
        }
        EventParser.parseTask(message, "todo", tasks);
        return true;
    }
}
