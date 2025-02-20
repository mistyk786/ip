package commands;

import tasks.Tasklist;
import tasks.Task;
import io.Ui;

public class ListCommand extends Command {
    public ListCommand(String message) {
        super(message);
    }

    @Override
    public String execute(Tasklist tasks) {
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the tasks in your list:\n\n");

        if (!message.isEmpty()) {
            return Ui.showError(String.format("""
                    Your input was: list %s
                    Expected input: list
                    """, message));
        }

        if (tasks.isEmpty()) {
            return Ui.showError("Your list is empty!");
        }
        for (Task t : tasks.getList()) {
            sb.append(t.toString()).append("\n");
        }
        return sb.toString();
    }
}

