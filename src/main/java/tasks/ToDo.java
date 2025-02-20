package tasks;

import java.time.LocalDate;
import java.time.LocalTime;

public class ToDo extends Task{

    public ToDo(String description) {
        super(description,"T");
    }

    @Override
    public LocalDate getDate() {
        return null;
    }

    @Override
    public LocalTime getTime() {
        return null;
    }

    @Override
    public String toFileString() {
        return super.toFileString();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
