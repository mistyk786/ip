package commands;
import io.Ui;
import tasks.Tasklist;

public class HelpCommand extends Command {
    public HelpCommand(String message) {
        super(message);
    }

    public String execute(Tasklist tasks) {
        if (!message.isEmpty()) {
            return Ui.showError(String.format("""
                Your input was: help %s
                Expected input: help
                """, message));
        }
        return Ui.showHelpMessage();
    }

}
