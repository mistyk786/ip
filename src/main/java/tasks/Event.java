package tasks;

import java.time.LocalDate;
import java.time.LocalTime;

public class Event extends Task {
    private String type;
    private String eventDateStart;
    private String eventDateEnd;
    private String eventTimeStart;
    private String eventTimeEnd;

    public Event(String description, String eventDateStart, String eventDateEnd, String eventTimeStart, String eventTimeEnd) {
        super(description);
        this.type = "E";
        this.eventDateStart = eventDateStart;
        this.eventDateEnd = eventDateEnd;
        this.eventTimeStart = eventTimeStart;
        this.eventTimeEnd = eventTimeEnd;
    }

    @Override
    public String toFileString() {
        return String.format("%s | %s | %s %s | %s %s", type, super.toFileString(), this.eventDateStart, this.eventTimeStart, this.eventDateEnd, this.eventTimeEnd);
    }

    @Override
    public String toString() {
        return String.format("[%s]%s (from: %s %s)(to: %s %s)", type, super.toString(), this.eventDateStart, this.eventTimeStart, this.eventDateEnd, this.eventTimeEnd);
    }
}

