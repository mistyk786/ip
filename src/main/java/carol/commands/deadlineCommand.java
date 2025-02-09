package carol.commands;
import carol.CarolException;
import carol.io.EventParser;
import carol.tasks.Tasklist;
import carol.io.Ui;

public class deadlineCommand extends Command {
    public deadlineCommand(String message) {
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
