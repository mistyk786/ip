package commands;

import carol.CarolException;
import io.EventParser;
import tasks.Tasklist;
import io.Ui;

public class EventCommand extends Command {
    public EventCommand(String message) {
        super(message);
    }

    @Override
    public boolean execute(Tasklist tasks) throws CarolException {
        if (message == null || message.trim().isEmpty()) {
            Ui.showError("""
                        Your input was: event
                        Expected input: event [action]
                        """);
            return true;
        }
        EventParser.parseTask(message, "event", tasks);
        return true;
    }
}
