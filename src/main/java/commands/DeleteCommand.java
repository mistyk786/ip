package commands;

import cortana.CortanaException;
import tasks.Tasklist;
import tasks.Task;
import io.Ui;

public class DeleteCommand extends Command {
    public DeleteCommand(String message) {
        super(message);
    }

    @Override
    public String execute(Tasklist tasks) throws CortanaException {
        int size = tasks.size();
        int i = getIndex(message, size);
        Task t = tasks.removeTask(i);
        return Ui.print(String.format("Task removed: %s", t.toString()));
    }
}
