package commands;

import cortana.CortanaException;
import tasks.Tasklist;

abstract public class Command {
    protected String message;
    public Command(String message) {
        this.message = message.trim();
    }
    public abstract String execute(Tasklist tasks) throws CortanaException;
}
