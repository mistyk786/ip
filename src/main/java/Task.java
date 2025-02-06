import java.time.LocalDate;
import java.time.LocalTime;

public class Task {
    private String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : "O"); // mark done task with X
    }

    public void markAsDone() {
        if (!isDone) {
            isDone = true;
        }
    }

    public void markAsUndone() {
        if (isDone) {
            isDone = false;
        }
    }

    public static Task parseTask(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            return null;
        }
        boolean isDone = parts[1].equals("X");
        String description = parts[2];
        switch (parts[0]) {
            case "T":
                ToDo t = new ToDo(description);
                if (isDone) {
                    t.markAsDone();
                }
                return t;
            case "D":
                LocalDate deadlineDate = LocalDate.parse(parts[3].split(" ")[0]);
                LocalTime deadlineTime = LocalTime.parse(parts[3].split(" ")[1]);
                Deadline d = new Deadline(description, deadlineDate, deadlineTime   );
                if (isDone) {
                    d.markAsDone();
                }
                return d;
            case "E":
                LocalDate eventDateStart = LocalDate.parse(parts[3].split(" ")[0]);
                LocalTime eventTimeStart = LocalTime.parse(parts[3].split(" ")[1]);
                LocalDate eventDateEnd = LocalDate.parse(parts[4].split(" ")[0]);
                LocalTime eventTimeEnd = LocalTime.parse(parts[4].split(" ")[1]);
                Event e = new Event(description, eventDateStart, eventDateEnd, eventTimeStart, eventTimeEnd);
                if (isDone) {
                    e.markAsDone();
                }
                return e;
            default:
                return null;
        }

    }

    public String toString() {
        return String.format("%s | %s", getStatusIcon(), description);
    }
}

