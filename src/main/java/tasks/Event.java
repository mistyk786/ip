package tasks;

import java.time.LocalDate;
import java.time.LocalTime;

public class Event extends Task {
    private String type;
    private LocalDate eventDateStart;
    private LocalDate eventDateEnd;
    private LocalTime eventTimeStart;
    private LocalTime eventTimeEnd;

    public Event(String description, LocalDate eventDateStart, LocalDate eventDateEnd, LocalTime eventTimeStart, LocalTime eventTimeEnd) {
        super(description);
        this.type = "E";
        this.eventDateStart = eventDateStart;
        this.eventDateEnd = eventDateEnd;
        this.eventTimeStart = eventTimeStart;
        this.eventTimeEnd = eventTimeEnd;
    }

    private LocalDate getEventDateStart() { return eventDateStart; }
    private LocalDate getEventDateEnd() { return eventDateEnd; }
    private LocalTime getEventTimeStart() { return eventTimeStart; }
    private LocalTime getEventTimeEnd() { return eventTimeEnd; }

    @Override
    public String toFileString() {
        return String.format("%s | %s | %s %s | %s %s", type, super.toFileString(), this.eventDateStart, this.eventTimeStart, this.eventDateEnd, this.eventTimeEnd);
    }

    @Override
    public String toString() {
        return String.format("[%s]%s from %s %s to %s %s", type, super.toString(), this.eventDateStart, this.eventTimeStart, this.eventDateEnd, this.eventTimeEnd);
    }
}

