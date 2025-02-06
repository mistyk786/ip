public class Event extends Task {
    private String type;
    private String start;
    private String end;

    public Event(String description, String start, String end) {
        super(description);
        this.type = "E";
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return String.format("%s | %s | %s | %s", type, super.toString(), start, end);
    }
}

