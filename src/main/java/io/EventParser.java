package io;

import cortana.CortanaDateTimeException;
import cortana.CortanaException;
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
    /** SPECIFIC date formats for user input to follow */
    private static final DateTimeFormatter[] DATE_FORMATS = {
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy"),
    };

    /** SPECIFIC time formats for user input to follow */
    private static final DateTimeFormatter[] TIME_FORMATS = {
            DateTimeFormatter.ofPattern("HH:mm"),     // 24-hour format (17:30)
            DateTimeFormatter.ofPattern("hh:mm a"),   // 12-hour format with AM/PM (05:30 PM)
    };


    /**
     * Parses string for date
     * @param dateStr;
     * @return Date that is stored in Task
     * @throws DateTimeParseException Input does not match date formats
     */
    private static LocalDate parseDate(String dateStr) throws DateTimeParseException {
        for (DateTimeFormatter format : DATE_FORMATS) {
            try {
                return LocalDate.parse(dateStr, format);
            } catch (DateTimeParseException ignored) {
            }
        }
        throw new DateTimeParseException(
                """
                        Invalid date format. Please use one of the following:
                        - Date Formats: YYYY-MM-DD, DD-MM-YYYY, DD/MM/YYY (e.g., 2025-12-19, 19-12-2025, 19/12/2025)
                        """,
                dateStr,
                0
        );

    }

    /**
     * Parses string for time
     * @param timeStr;
     * @return Time that is stored in Task
     * @throws DateTimeParseException Input does not match time formats
     */
    private static LocalTime parseTime(String timeStr) throws DateTimeParseException {
        for (DateTimeFormatter format : TIME_FORMATS) {
            try {
                return LocalTime.parse(timeStr, format);
            } catch (DateTimeParseException ignored) {
            }
        }
        throw new DateTimeParseException(
                """
                        Invalid time format. Please use one of the following:
                        - Time Formats: HH:mm (24-hour), hh:mm a (12-hour) (e.g., 17:30, 05:30 PM)
                        """,
                timeStr,
                0
        );
    }

    /**
     * Parses tasks from file
     * @param line Task strings that is stored in storage
     * @return Task stored in Tasklist
     * @throws CortanaException Storage contains incorrect Date/Time formats
     * or other incorrect fields
     */
    public static Task parseTaskFromFile(String line) throws CortanaException {
        String[] parts = line.split(" \\| ");

        if (parts.length < 3) {
            throw new CortanaException("Invalid task format: Missing essential fields. \n");
        }

        String taskType = parts[0];
        String isDoneStr = parts[1];
        String description = parts[2];

        if (!isDoneStr.equals(" ") && !isDoneStr.equals("X")) {
            throw new CortanaException("Invalid task format: Unrecognized essential field: " + isDoneStr + "\n");
        }
        boolean isDone = isDoneStr.equals("X");

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
                    throw new CortanaException("Invalid Deadline format: Missing date/time.");
                }

                String[] deadlineParts = parts[3].split(" ", 2);
                if (deadlineParts.length < 2) {
                    throw new CortanaDateTimeException();
                }

                LocalDate date = parseDate(deadlineParts[0]);
                LocalTime time = parseTime(deadlineParts[1]);

                Deadline deadline = new Deadline(description, date, time);
                if (isDone) {
                    deadline.markAsDone();
                }
                return deadline;
            case "E":
                if (parts.length < 5) {
                    throw new CortanaException("Invalid event format: Missing start or end date/time.");
                }

                String[] eventStartParts = parts[3].split(" ", 2);
                String[] eventEndParts = parts[4].split(" ", 2);

                if (eventStartParts.length < 2 || eventEndParts.length < 2) {
                    throw new CortanaDateTimeException();
                }

                LocalDate eventDateStart = parseDate(eventStartParts[0]);
                LocalTime eventTimeStart = parseTime(eventStartParts[1]);
                LocalDate eventDateEnd = parseDate(eventEndParts[0]);
                LocalTime eventTimeEnd = parseTime(eventEndParts[1]);

                Event event = new Event(description, eventDateStart, eventDateEnd, eventTimeStart, eventTimeEnd);
                if (isDone) {
                    event.markAsDone();
                }
                return event;
            default:
                throw new CortanaException("Unknown task type: " + taskType);
            }
        } catch (DateTimeParseException e) {
            throw new CortanaException(e.getMessage());
        }
    }

    /**
     * Completes creation of tasks
     * @param line Task message
     * @param taskType Todo, Deadline and Event
     * @param tasks Tasklist
     * @throws CortanaException Input contains incorrect Date/Time formats
     * or other incorrect fields
     */
    public static String parseTask(String line, String taskType, Tasklist tasks) throws CortanaException {
        String output = "";
        try {
            switch (taskType) {
            case "todo":
                ToDo todo = new ToDo(line);
                tasks.addTask(todo);
                output =  Ui.print("Task added:\n" + todo.toString());
                break;
            case "deadline":
                String[] parts = line.split(" /by ", 2);
                if (parts.length != 2) {
                    throw new CortanaDateTimeException();
                }

                String deadlineDescription = parts[0].trim();
                String deadlineString = parts[1].trim();

                String[] dateTimeParts = deadlineString.split(" ");
                if (dateTimeParts.length < 2) {
                    throw new CortanaDateTimeException();
                }

                try {
                    LocalDate date = parseDate(dateTimeParts[0]);
                    LocalTime time = parseTime(dateTimeParts[1]);

                    Deadline deadline = new Deadline(deadlineDescription, date, time);
                    tasks.addTask(deadline);
                    output = Ui.print("Task added:\n" + deadline.toString());
                } catch (DateTimeParseException e) {
                    throw new CortanaDateTimeException();
                }
                break;
            case "event":
                String[] parts2 = line.split(" /from | /to ");
                if (parts2.length != 3) {
                    throw new CortanaDateTimeException();
                }

                String eventDescription = parts2[0].trim();
                String eventStartString = parts2[1].trim();
                String eventEndString = parts2[2].trim();

                String[] startParts = eventStartString.split(" ");
                String[] endParts = eventEndString.split(" ");

                if (startParts.length < 2 || endParts.length < 2) {
                    throw new CortanaDateTimeException();
                }

                try {
                    LocalDate startDate = parseDate(startParts[0]);
                    LocalTime startTime = parseTime(startParts[1]);
                    LocalDate endDate = parseDate(endParts[0]);
                    LocalTime endTime = parseTime(endParts[1]);

                    Event event = new Event(eventDescription, startDate, endDate, startTime, endTime);
                    tasks.addTask(event);
                    output = Ui.print("Task added:\n" + event.toString());
                } catch (DateTimeParseException e) {
                    throw new CortanaDateTimeException();
                }
                break;
            default:
                throw new CortanaException("Unknown task type: " + taskType);
            }
        } catch (DateTimeParseException e) {
            throw new CortanaDateTimeException();
        }
        return output;
    }
}
