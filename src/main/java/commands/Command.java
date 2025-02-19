package commands;

import cortana.CortanaException;
import io.Ui;
import tasks.Tasklist;

abstract public class Command {
    protected String message;

    public Command(String message) {
        this.message = message.trim();
    }

    public abstract String execute(Tasklist tasks) throws CortanaException;

    public int getIndex(String message, Integer size) throws CortanaException {
        if (!(message.length() == 1)) {
            throw new CortanaException("You must enter an integer index");
        }

        int i;
        try {
            i = Integer.parseInt(message) - 1;
        } catch (NumberFormatException e) {
            throw new CortanaException("You must enter an integer index");
        }
        if (i < 0 || i > size) {
            throw new CortanaException("You must enter an integer index in range");
        }
        return i;
    }

}
