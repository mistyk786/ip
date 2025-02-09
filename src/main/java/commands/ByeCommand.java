package commands;

import tasks.Tasklist;
import io.Ui;

public class byeCommand extends Command {
    public byeCommand(String message) {
        super(message);
    }

    @Override
    public boolean execute(Tasklist tasks) {
        if (message.isEmpty()) {
            return false;
        }
        Ui.showError(String.format("""
                Your input was: bye %s
                Expected input: bye
                """, message));
        return true;
    }
}

