package tasks;

import java.time.LocalDate;
import java.time.LocalTime;

public class Deadline extends Task {
    private LocalDate deadlineDate;
    private LocalTime deadlineTime;

    public Deadline(String description, LocalDate deadlineDate, LocalTime deadlineTime) {
        super(description, "D");
        this.deadlineDate = deadlineDate;
        this.deadlineTime = deadlineTime;
    }

    @Override
    public LocalDate getDate() {
        return deadlineDate;
    }

    @Override
    public LocalTime getTime() {
        return deadlineTime;
    }

    @Override
    public String toFileString() {
        return String.format("%s | %s %s", super.toFileString(), getDateString(deadlineDate), getTimeString(deadlineTime));
    }

    @Override
    public String toString() {
        return String.format("%s (by: %s %s)", super.toString(), getDateString(deadlineDate), getTimeString(deadlineTime));
    }
}

