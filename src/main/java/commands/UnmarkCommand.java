package commands;

import tasks.Tasklist;
import tasks.Task;
import io.Ui;

public class UnmarkCommand extends Command {
    public UnmarkCommand(String message) {
        super(message);
    }

    @Override
    public boolean execute(Tasklist tasks) {
        if (!(message.length() == 1)) {
            Ui.showError(String.format("""
                    Your input was: unmark %s
                    Expected input: unmark [number]
                    """, message));
            return true;
        }
        int i;
        try {
            i = Integer.parseInt(message) - 1;
        } catch (NumberFormatException e) {
            Ui.showError(String.format("""
                    Your input was: unmark %s
                    Expected input: unmark [number]
                    """, message));
            return true;
        }
        if (i < 0 || i > tasks.size() - 1) {
            Ui.showError(String.format("""
                    Your input was: unmark %s
                    Expected input: unmark [number]
                    """, message));
            return true;
        }
        Task t = tasks.getTask(i);
        t.markAsUndone();
        return true;
    }
}

