package tasks;

import java.time.LocalDate;
import java.time.LocalTime;

public class Deadline extends Task {
    private String type;
    private String deadlineDate;
    private String deadlineTime;

    public Deadline(String description, String deadlineDate, String deadlineTime) {
        super(description);
        this.type = "D";
        this.deadlineDate = deadlineDate;
        this.deadlineTime = deadlineTime;
    }

    @Override
    public String toFileString() {
        return String.format("%s | %s | %s %s", type, super.toFileString(), this.deadlineDate, this.deadlineTime);
    }

    @Override
    public String toString() {
        return String.format("[%s]%s (by: %s %s)", type, super.toString(), this.deadlineDate, this.deadlineTime);
    }
}

