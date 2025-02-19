package commands;

import cortana.CortanaException;
import io.EventParser;
import io.Ui;
import tasks.Tasklist;


public class TodoCommand extends Command {
    public TodoCommand(String message) {
        super(message);
    }

    @Override
    public String execute(Tasklist tasks) throws CortanaException {
        if (message == null || message.trim().isEmpty()) {
            return Ui.showError("""
                    Your input was: todo
                    Expected input: todo [action]
                    """);
        }
        return EventParser.parseTask(message, "todo", tasks);
    }
}


