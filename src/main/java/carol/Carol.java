package carol;

import commands.Command;
import tasks.Tasklist;
import io.Storage;
import io.Ui;
import io.Parser;
import java.io.IOException;

public class Carol {
    protected Storage storage;
    protected Tasklist tasks;
    protected Ui ui;
    protected Parser parser;

    /**
     * Creates chatbot and retrieves tasks from storage
     * @param filePath Storage location
     */
    public Carol(String filePath) {
        ui = new Ui();
        parser = new Parser();
        storage = new Storage(filePath);
        try {
            tasks = storage.loadTasks();
        } catch (CarolException e) {
            Ui.showError(e.getMessage());
            tasks = new Tasklist();
        }
    }
    /**
     * Runs chatbot
     * Continues running unless execute(tasks) returns false, which
     * only occurs if users input 'bye'
     * Saves tasks into storage after each input
     */
    public void run() {
        ui.showIntro();
        boolean running = true;
        while (running) {
            try {
                String input = Ui.readLine();
                Ui.line();
                Command c = parser.parseCommand(input);
                running = c.execute(tasks);
                storage.saveTasks();
            } catch (CarolException | IOException e) {
                Ui.showError(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Carol("data/tasks.txt").run();
    }
}
