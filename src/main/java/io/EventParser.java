package io;

import carol.CarolException;
import tasks.Task;
import tasks.Tasklist;
import tasks.ToDo;
import tasks.Deadline;
import tasks.Event;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class EventParser {
    private static final DateTimeFormatter[] DATE_FORMATS = {
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
    };

    private static final DateTimeFormatter[] TIME_FORMATS = {
            DateTimeFormatter.ofPattern("HH:mm"),     // 24-hour format (17:30)
            DateTimeFormatter.ofPattern("hh:mm a")    // 12-hour format with AM/PM (05:30 PM)
    };

    public static Task parseTaskFromFile(String line) throws CarolException {
        String[] parts = line.split(" \\| ");

        if (parts.length < 3) {
            throw new CarolException("Invalid task format: Missing essential fields. \n");
        }

        String taskType = parts[0];
        String isDoneStr = parts[1];
        String description = parts[2];

        if (!isDoneStr.equals(" ") && !isDoneStr.equals("X")) {
            throw new CarolException("Invalid task format: Unrecognized essential field: " + isDoneStr + "\n");
        }
        boolean isDone = isDoneStr.equals(" ");

        try {
            switch (taskType) {
                case "T":
                    ToDo todo = new ToDo(description);
                    if (isDone) {
                        todo.markAsDone();
                    }
                    return todo;

                case "D":
                    if (parts.length < 4) {
                        throw new CarolException("Invalid Deadline format: Missing date/time.");
                    }

                    String deadlineString = parts[3];
                    LocalDate date = LocalDate.parse(deadlineString, DATE_FORMATS[0]);
                    LocalTime time = LocalTime.parse(deadlineString, TIME_FORMATS[0]);
                    Deadline deadline = new Deadline(description, date, time);
                    if (isDone) {
                        deadline.markAsDone();
                    }
                    return deadline;

                case "E":
                    if (parts.length < 5) {
                        throw new CarolException("Invalid Event format: Missing start or end date/time.");
                    }
                    String[] eventStartParts = parts[3].split(" ");
                    String[] eventEndParts = parts[4].split(" ");
                    LocalDate eventDateStart = LocalDate.parse(eventStartParts[0]);
                    LocalTime eventTimeStart = LocalTime.parse(eventStartParts[1]);
                    LocalDate eventDateEnd = LocalDate.parse(eventEndParts[0]);
                    LocalTime eventTimeEnd = LocalTime.parse(eventEndParts[1]);

                    Event event = new Event(description, eventDateStart, eventDateEnd, eventTimeStart, eventTimeEnd);
                    if (isDone) {
                        event.markAsDone();
                    }
                    return event;

                default:
                    throw new CarolException("Unknown task type: " + taskType);
            }
        } catch (DateTimeParseException e) {
            throw new CarolException(e.getMessage());
        }
    }

    public static void parseTask(String line, String taskType, Tasklist tasks) throws CarolException {
        try {
            switch (taskType) {
                case "todo":
                    ToDo t = new ToDo(line);
                    tasks.addTask(t);
                    break;
                case "deadline":
                    String[] parts = line.split(" /by ", 2);
                    if (parts.length != 2) {
                        throw new CarolException("""
                                Invalid task format: Missing date/time
                                Use following format for deadlines: deadline [message] /by [date][time]
                                """);
                    }

                    String deadlineDescription = parts[0].trim();
                    String deadlineString = parts[1].trim();
                    LocalDate date = parseDate(deadlineString.split(" ")[0]);
                    LocalTime time = parseTime(deadlineString.split(" ")[1]);

                    Deadline deadline = new Deadline(deadlineDescription, date, time);
                    tasks.addTask(deadline);
                    break;
                case "event":
                    String[] parts2 = line.split(" /from | /to ");
                    if (parts2.length != 3) {
                        throw new CarolException("""
                                Invalid task format: Missing date/time
                                Use following format for events: event [message] /from [date][time] /to [date][time]
                                """);
                    }

                    String eventDescription = parts2[0].trim();
                    String eventStartString = parts2[1].trim();
                    String eventEndString = parts2[2].trim();

                    LocalDate startDate = parseDate(eventStartString.split(" ")[0]);
                    LocalTime startTime = parseTime(eventStartString.split(" ")[1]);
                    LocalDate endDate = parseDate(eventEndString.split(" ")[0]);
                    LocalTime endTime = parseTime(eventEndString.split(" ")[1]);

                    Event event = new Event(eventDescription, startDate, endDate, startTime, endTime);
                    tasks.addTask(event);
                    break;
                default:
                    throw new CarolException("Unknown task type: " + taskType);
            }
        } catch (DateTimeParseException e) {
            Ui.showError(e.getMessage());
        }
    }

    private static LocalDate parseDate(String dateStr) throws DateTimeParseException {
        for (DateTimeFormatter format : DATE_FORMATS) {
            try {
                return LocalDate.parse(dateStr, format);
            } catch (DateTimeParseException ignored) {
                // Try next format
            }
        }
        throw new DateTimeParseException(
                """
                        Invalid date format. Please use one of the following:
                        - Date Formats: YYYY-MM-DD, DD-MM-YYYY, DD/MM/YYY (e.g., 2025-12-19, 19-12-2025, 19/12/2025)
                        """,
                dateStr, // The input that caused the error
                0 // Error index, use 0 if unknown
        );

    }

    private static LocalTime parseTime(String timeStr) throws DateTimeParseException {
        for (DateTimeFormatter format : TIME_FORMATS) {
            try {
                return LocalTime.parse(timeStr, format);
            } catch (DateTimeParseException ignored) {
                // Try next format
            }
        }
        throw new DateTimeParseException(
                """
                        Invalid time format. Please use one of the following:
                        - Time Formats: HH:mm (24-hour), hh:mm a (12-hour) (e.g., 17:30, 05:30 PM)
                        """,
                timeStr, // The input that caused the error
                0 // Error index, use 0 if unknown
        );
    }
}
