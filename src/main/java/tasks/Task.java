package tasks;

public class Task {
    private String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    private String getDescription() { return description; }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void markAsDone() {
        if (!isDone) {
            isDone = true;
        }
    }

    public void markAsUndone() {
        if (isDone) {
            isDone = false;
        }
    }

    public String toFileString() {
        return String.format("%s | %s", getStatusIcon(), description);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", getStatusIcon(), description);
    }
}

