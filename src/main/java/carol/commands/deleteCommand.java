package carol.commands;

import carol.tasks.Tasklist;
import carol.io.Ui;

public class deleteCommand extends Command {
    public deleteCommand(String message) {
        super(message);
    }

    @Override
    public boolean execute(Tasklist tasks) {
        if (!(message.length() == 1)) {
            Ui.showError(String.format("""
                    Your input was: delete %s
                    Expected input: delete [number]
                    """, message));
            return true;
        }
        int i;
        try {
            i = Integer.parseInt(message) - 1;
        } catch (NumberFormatException e) {
            Ui.showError(String.format("""
                    Your input was: delete %s
                    Expected input: delete [number]
                    """, message));
            return true;
        }
        if (i < 0 || i > tasks.size() - 1) {
            Ui.showError(String.format("""
                    Your input was: delete %s
                    Expected input: delete [number]
                    """, message));
            return true;
        }
        tasks.removeTask(i);
        return true;
    }
}
