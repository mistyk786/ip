package cortana;

import commands.Command;
import tasks.Tasklist;
import io.Storage;
import io.Ui;
import io.Parser;
import java.io.IOException;

public class Cortana {
    protected Storage storage;
    protected Tasklist tasks;
    protected Parser parser;

    /**
     * Creates chatbot and retrieves tasks from storage
     * @param filePath Storage location
     */
    public Cortana(String filePath) {
        parser = new Parser();
        storage = new Storage(filePath);
        try {
            tasks = storage.loadTasks();
        } catch (CortanaException e) {
            tasks = new Tasklist();
        }
    }

    public String getOutput(String input) {
        String output;
        try {
            Command c = parser.parseCommand(input);
            output = c.execute(tasks);
            storage.saveTasks();
        } catch (CortanaException c) {
            return Ui.showError(c.getMessage());
        }
        return output;
    }
}


