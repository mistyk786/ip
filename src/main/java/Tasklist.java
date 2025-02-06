import java.util.ArrayList;

public class Tasklist {
    protected ArrayList<Task> tasks;

    public Tasklist(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public int size() {
        return tasks.size();
    }

    public Task get(int i) {
        return tasks.get(i);
    }

    public Task remove(int i) {
        return tasks.remove(i);
    }

    public void add(Task t) {
        tasks.add(t);
    }
}
