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
        int size = tasks.size();
        int i = getIndex(message, size);
        Task t = tasks.getTask(i);
        t.markAsDone();
        return Ui.print(String.format("Task completed: %s", t.toString()));
    }
}
