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

    @Override
    public String toString() {
        return String.format("%s | %s | %s %s | %s %s", type, super.toString(), this.eventDateStart, this.eventTimeStart, this.eventDateEnd, this.eventTimeEnd);
    }
}

