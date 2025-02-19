package commands;

import tasks.Tasklist;
import tasks.Task;
import io.Ui;

public class FindCommand extends Command {
    public FindCommand(String message) {
        super(message);
    }

    @Override
    public String execute(Tasklist tasks) {
        StringBuilder sb = new StringBuilder();
        boolean exists = false;
        sb.append("""
                Here are the matching tasks in your list:
                
                """);

        for (Task t : tasks.getList()) {
            String description = t.getDescription();
            if (description.contains(message)) {
                exists = true;
                sb.append(t.toString()).append("\n");
            }
        }

        if (!exists) {
            return Ui.showError("No matching tasks found.");
        }
        return Ui.print(sb.toString());
    }
}
