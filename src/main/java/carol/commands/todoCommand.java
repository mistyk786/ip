package carol.commands;

import carol.CarolException;
import carol.io.EventParser;
import carol.io.Ui;
import carol.tasks.Tasklist;


public class todoCommand extends Command {
    public todoCommand(String message) {
        super(message);
    }

    @Override
    public boolean execute(Tasklist tasks) throws CarolException {
        if (message == null || message.trim().isEmpty()) {
            Ui.showError("""
                    Your input was: todo
                    Expected input: todo [action]
                    """);
            return true;
        }
        EventParser.parseTask(message, "todo", tasks);
        return true;
    }
}


