package commands;

import tasks.Tasklist;
import tasks.Task;
import io.Ui;

public class UnmarkCommand extends Command {
    public UnmarkCommand(String message) {
        super(message);
    }

    @Override
    public String execute(Tasklist tasks) {
        if (!(message.length() == 1)) {
            return Ui.showError(String.format("""
                    Your input was: unmark %s
                    Expected input: unmark [number]
                    """, message));
        }
        int i;
        try {
            i = Integer.parseInt(message) - 1;
        } catch (NumberFormatException e) {
            return Ui.showError(String.format("""
                    Your input was: unmark %s
                    Expected input: unmark [number]
                    """, message));
        }
        if (i < 0 || i > tasks.size() - 1) {
            return Ui.showError(String.format("""
                    Your input was: unmark %s
                    Expected input: unmark [number]
                    """, message));
        }
        Task t = tasks.getTask(i);
        t.markAsUndone();
        return Ui.print(String.format("Task incomplete: %s", t.toString()));
    }
}

