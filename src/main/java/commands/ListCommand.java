package commands;

import tasks.Tasklist;
import io.Ui;

public class listCommand extends Command {
    public listCommand(String message) {
        super(message);
    }

    @Override
    public boolean execute(Tasklist tasks) {
        if (!message.isEmpty()) {
            Ui.showError(String.format("""
                    Your input was: list %s
                    Expected input: list
                    """, message));
            return true;
        }
        if (tasks.isEmpty()) {
            Ui.showError(" Your list is empty!\n");
            return true;
        }
        String sb = "Here are the tasks in your list:\n\n" +
                tasks.toString();
        Ui.print(sb);
        Ui.line();
        return true;
    }
}

