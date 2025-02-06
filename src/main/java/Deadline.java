public class Deadline extends Task {
    private String type;
    private String deadline;

    public Deadline(String description, String deadline) {
        super(description);
        this.type = "D";
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return String.format("%s | %s | %s", type, super.toString(), deadline);
    }
}
