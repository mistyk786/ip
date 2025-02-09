package tasks;

import java.util.ArrayList;

public class Tasklist {
    protected ArrayList<Task> list;

    public Tasklist() {
        this.list = new ArrayList<Task>();
    }

    public Tasklist(ArrayList<Task> list) {
        this.list = list;
    }

    public ArrayList<Task> getList() {
        return list;
    }

    public void addTask(Task t) {
        list.add(t);
    }

    public Task removeTask(int i) {
        return list.remove(i);
    }

    public Task getTask(int i) {
        return list.get(i);
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Task task : list) {
            sb.append(task.toString()).append("\n");
        }
        return sb.toString();
    }
}
