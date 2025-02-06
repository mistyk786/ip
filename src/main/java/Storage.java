import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.ArrayList;

public class Storage {
    protected ArrayList<Task> tasks;
    public void saveTasks(String filePath, ArrayList<Task> tasklist) {
        this.tasks = tasklist;
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(this.tasks);
        } catch (IOException e) {
            System.err.println("Error saving tasks to file: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Task> loadTasks(String filePath) {
        ArrayList<Task> tasklist = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            tasklist = (ArrayList<Task>) ois.readObject();

        }
        catch (FileNotFoundException e) {
            File file = new File(filePath);
        }
        catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading tasks from file: " + e.getMessage());
        }
        return tasklist.isEmpty() ? new ArrayList<Task>() : tasks;
    }

}
