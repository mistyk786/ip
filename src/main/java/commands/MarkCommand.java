package commands;

import cortana.CortanaException;
import tasks.Tasklist;
import tasks.Task;
import io.Ui;

public class MarkCommand extends Command {
    public MarkCommand(String message) {
        super(message);
    }

    @Override
    public String execute(Tasklist tasks) throws CortanaException {
        if (!(message.length() == 1)) {
            return Ui.showError(String.format("""
                    Your input was: mark %s
                    Expected input: mark [number]
                    """, message));
        }
        
        int i;
        try {
            i = Integer.parseInt(message) - 1;
        } catch (NumberFormatException e) {
            return Ui.showError(String.format("""
                    Your input was: mark %s
                    Expected input: mark [number]
                    """, message));
        }
        if (i < 0 || i > tasks.size() - 1) {
            return Ui.showError(String.format("""
                    Your input was: mark %s
                    Expected input: mark [number]
                    """, message));
        }
        Task t = tasks.getTask(i);
        t.markAsDone();
        return Ui.print(String.format("Task completed: %s", t.toString()));
    }
}
