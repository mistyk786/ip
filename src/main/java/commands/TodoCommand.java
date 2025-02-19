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
        String output = EventParser.parseTask(message, "todo", tasks);
        return Ui.print("Added task:\n%s".formatted(message));
    }
}


