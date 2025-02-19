package commands;

import tasks.Tasklist;
import tasks.Task;
import io.Ui;

public class DeleteCommand extends Command {
    public DeleteCommand(String message) {
        super(message);
    }

    @Override
    public String execute(Tasklist tasks) {
        if (!(message.length() == 1)) {
            return Ui.showError(String.format("""
                    Your input was: delete %s
                    Expected input: delete [number]
                    """, message));
        }
        int i;
        try {
            i = Integer.parseInt(message) - 1;
        } catch (NumberFormatException e) {
            return Ui.showError(String.format("""
                    Your input was: delete %s
                    Expected input: delete [number]
                    """, message));
        }
        if (i < 0 || i > tasks.size() - 1) {
            return Ui.showError(String.format("""
                    Your input was: delete %s
                    Expected input: delete [number]
                    """, message));
        }
        Task t = tasks.removeTask(i);
        return Ui.print(String.format("Task removed: %s", t.toString()));
    }
}
