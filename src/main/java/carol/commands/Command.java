package carol.commands;

import carol.CarolException;
import carol.tasks.Tasklist;

abstract public class Command {
    protected String message;
    public Command(String message) {
        this.message = message;
    }
    public abstract boolean execute(Tasklist tasks) throws CarolException;
}
