package commands;

import cortana.CortanaException;
import tasks.Tasklist;
import tasks.Task;
import io.Ui;

public class UnmarkCommand extends Command {
    public UnmarkCommand(String message) {
        super(message);
    }

    @Override
    public String execute(Tasklist tasks) throws CortanaException {
        int size = tasks.size();
        int i = getIndex(message, size);
        Task t = tasks.getTask(i);
        t.markAsUndone();
        return Ui.print(String.format("Task incomplete: %s", t.toString()));
    }
}

