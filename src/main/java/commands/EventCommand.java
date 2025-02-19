package commands;

import cortana.CortanaException;
import io.EventParser;
import tasks.Tasklist;
import io.Ui;

public class EventCommand extends Command {
    public EventCommand(String message) {
        super(message);
    }

    @Override
    public String execute(Tasklist tasks) throws CortanaException {
        if (message == null || message.trim().isEmpty()) {
            return Ui.showError("""
                        Your input was: event
                        Expected input: event [action]
                        """);
        }
        String output = EventParser.parseTask(message, "event", tasks);
        return output;
    }
}
