package commands;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import tasks.Tasklist;
import tasks.Task;
import io.Ui;

public class ReminderCommand extends Command{
    public ReminderCommand(String message) { super(message);}

    @Override
    public String execute(Tasklist tasks) {
        StringBuilder sb = new StringBuilder();
        boolean exists = false;
        sb.append("""
        Incoming Transmission: Task Reminders
        
        """);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime cutoff = now.plusHours(24);

        for (Task t : tasks.getList()) {
            String type = t.getType();
            if (type.equals("T")) {
                continue;
            }
            LocalDate date = t.getDate();
            LocalTime time = t.getTime();

            if (date == null || time == null) {
                continue;
            }
            LocalDateTime taskDateTime = LocalDateTime.of(date, time);

            if (taskDateTime.isBefore(cutoff)) {
                exists = true;
                sb.append(t.toString()).append("\n");
            }
        }

        if (!exists) {
            return Ui.showError("No matching tasks found.");
        }
        return Ui.print(sb.toString());
    }
}
