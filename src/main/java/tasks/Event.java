package tasks;

import java.time.LocalDate;
import java.time.LocalTime;

public class Event extends Task {
    private LocalDate eventDateStart;
    private LocalDate eventDateEnd;
    private LocalTime eventTimeStart;
    private LocalTime eventTimeEnd;

    public Event(String description, LocalDate eventDateStart, LocalDate eventDateEnd, LocalTime eventTimeStart, LocalTime eventTimeEnd) {
        super(description, "E");
        this.eventDateStart = eventDateStart;
        this.eventDateEnd = eventDateEnd;
        this.eventTimeStart = eventTimeStart;
        this.eventTimeEnd = eventTimeEnd;
    }

    @Override
    public LocalDate getDate() {
        return eventDateEnd;
    }

    @Override
    public LocalTime getTime() {
        return eventTimeEnd;
    }

    @Override
    public String toFileString() {
        return String.format("%s | %s %s | %s %s", super.toFileString(),
                getDateString(eventDateStart), getTimeString(eventTimeStart),
                getDateString(eventDateEnd), getTimeString(eventTimeEnd));
    }

    @Override
    public String toString() {
        return String.format("%s (from: %s %s)(to: %s %s)", super.toString(),
                getDateString(eventDateStart), getTimeString(eventTimeStart),
                getDateString(eventDateEnd), getTimeString(eventTimeEnd));
    }
}

