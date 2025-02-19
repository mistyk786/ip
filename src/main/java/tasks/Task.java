package tasks;

public class Task {
    private String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getDescription() { return description; }

    public String getStatusIcon() {
        if (isDone) {
            return "X";
        }
        else {
            return " ";
        }
    }

    public void markAsDone() {
        isDone = true;
    }

    public void markAsUndone() {
        isDone = false;
    }

    public String toFileString() {
        return String.format("%s | %s", getStatusIcon(), description);
    }

    @Override
    public String toString() {
        return String.format(" [%s] %s", getStatusIcon(), description);
    }
}

