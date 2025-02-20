package tasks;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

abstract public class Task {
    private String description;
    private String type;
    private boolean isDone;

    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("hh:mm a");

    public Task(String description, String type) {
        this.description = description;
        this.type = type;
        this.isDone = false;
    }

    public String getType() {
        return type;
    }

    public String getDescription() { return description; }

    abstract public LocalDate getDate();

    public String getDateString(LocalDate date) {
        return date.format(DATE_FORMAT);
    }

    abstract public LocalTime getTime();

    public String getTimeString(LocalTime time) {
        return time.format(TIME_FORMAT);
    }

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
        return String.format("%s | %s | %s", getType(), getStatusIcon(), description);
    }

    @Override
    public String toString() {
        return String.format("[%s][%s] %s", getType(), getStatusIcon(), description);
    }
}

