package commands;

import tasks.Tasklist;
import tasks.Task;
import io.Ui;

public class FindCommand extends Command {
    public FindCommand(String message) {
        super(message);
    }

    @Override
    public boolean execute(Tasklist tasks) {
        StringBuilder sb = new StringBuilder();
        boolean found = false;
        sb.append("""
                Here are the matching tasks in your list:
                
                """);
        for (Task t : tasks.getList()) {
            String description = t.getDescription();
            if (description.contains(message.toLowerCase())) {
                found = true;
                sb.append(t.toString()).append("\n");
            }
        }
        if (found) {
            Ui.print(sb.toString());
            Ui.line();
        } else {
            Ui.print("No matching tasks found.");
            Ui.line();
        }
        return true;
    }
}
