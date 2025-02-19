package commands;

import cortana.CortanaException;
import tasks.Tasklist;
import io.Ui;

public class ByeCommand extends Command {
    public ByeCommand(String message) {
        super(message);
    }

    @Override
    public String execute(Tasklist tasks) {
        if (message.isEmpty()) {
            return Ui.showEnd();
        }
        else {
            return Ui.showError(String.format("""
                Your input was: bye %s
                Expected input: bye
                """, message));
        }
    }
}

