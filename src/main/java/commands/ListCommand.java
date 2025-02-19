package commands;

import tasks.Tasklist;
import io.Ui;

public class ListCommand extends Command {
    public ListCommand(String message) {
        super(message);
    }

    @Override
    public String execute(Tasklist tasks) {
        StringBuilder sb = new StringBuilder();
        if (!message.isEmpty()) {
            return Ui.showError(String.format("""
                    Your input was: list %s
                    Expected input: list
                    """, message));
        }
        if (tasks.isEmpty()) {
            return Ui.showError(" Your list is empty!");
        } else if (!tasks.isEmpty()) {
            sb.append("Here are the tasks in your list:\n\n");
            sb.append(tasks.toString());
            return Ui.print(sb.toString());
        } else {
            return Ui.showError(" Unknown error.");
        }
    }
}

