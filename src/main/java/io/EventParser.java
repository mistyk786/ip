package io;

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
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
    };

    /** SPECIFIC time formats for user input to follow */
    private static final DateTimeFormatter[] TIME_FORMATS = {
            DateTimeFormatter.ofPattern("HH:mm"),     // 24-hour format (17:30)
            DateTimeFormatter.ofPattern("hh:mm a")    // 12-hour format with AM/PM (05:30 PM)

    };


    /**
     * Parses string for date
     * @param dateStr;
     * @return Date that is stored in Task
     * @throws DateTimeParseException Input does not match date formats
     */
    private static String parseDate(String dateStr) throws DateTimeParseException {
        for (DateTimeFormatter format : DATE_FORMATS) {
            try {
                LocalDate date = LocalDate.parse(dateStr, format);
                return date.format(DATE_FORMATS[1]);
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
    private static String parseTime(String timeStr) throws DateTimeParseException {
        for (DateTimeFormatter format : TIME_FORMATS) {
            try {
                LocalTime time = LocalTime.parse(timeStr, format);
                return time.format(TIME_FORMATS[1]);
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

                String[] deadlineParts = parts[3].split(" ");
                if (deadlineParts.length < 2) {
                    throw new CortanaException("Invalid deadline format: Please provide both date and time.");
                }

                String date = parseDate(deadlineParts[0]);
                String time = parseTime(deadlineParts[1]);

                Deadline deadline = new Deadline(description, date, time);
                if (isDone) {
                    deadline.markAsDone();
                }
                return deadline;
            case "E":
                if (parts.length < 5) {
                    throw new CortanaException("Invalid Event format: Missing start or end date/time.");
                }

                String[] eventStartParts = parts[3].split(" ");
                String[] eventEndParts = parts[4].split(" ");

                if (eventStartParts.length < 2 || eventEndParts.length < 2) {
                    throw new CortanaException("Invalid event format: Please provide both start and end date/time.");
                }

                String eventDateStart = parseDate(eventStartParts[0]);
                String eventTimeStart = parseTime(eventStartParts[1]);
                String eventDateEnd = parseDate(eventEndParts[0]);
                String eventTimeEnd = parseTime(eventEndParts[1]);

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
        String output;
        try {
            switch (taskType) {
            case "todo":
                ToDo todo = new ToDo(line.toLowerCase());
                tasks.addTask(todo);
                output =  Ui.print("Task added:\n" + todo.toString());
                break;
            case "deadline":
                String[] parts = line.split(" /by ", 2);
                if (parts.length != 2) {
                    throw new CortanaException("""
                Invalid task format: Missing date/time.
                Use the following format for deadlines:
                deadline [message] /by [YYYY-MM-DD] [HH:mm]
                Example: deadline Submit Report /by 2024-02-20 23:59
                """);
                }

                String deadlineDescription = parts[0].trim();
                String deadlineString = parts[1].trim();

                String[] dateTimeParts = deadlineString.split(" ");
                if (dateTimeParts.length < 2) {
                    throw new CortanaException("""
                Invalid deadline format. Please provide both date and time.
                Use format: YYYY-MM-DD HH:mm
                Example: deadline Submit Report /by 2024-02-20 23:59
                """);
                }

                try {
                    String date = parseDate(dateTimeParts[0]);
                    String time = parseTime(dateTimeParts[1]);

                    Deadline deadline = new Deadline(deadlineDescription, date, time);
                    tasks.addTask(deadline);
                    output = Ui.print("Task added:\n" + deadline.toString());
                } catch (DateTimeParseException e) {
                    throw new CortanaException("Error parsing date or time: " + e.getParsedString());
                }
                break;
            case "event":
                String[] parts2 = line.split(" /from | /to ");
                if (parts2.length != 3) {
                    throw new CortanaException("""
                Invalid task format: Missing date/time.
                Use the following format for events:
                event [message] /from [YYYY-MM-DD] [HH:mm] /to [YYYY-MM-DD] [HH:mm]
                Example: event Meeting /from 2024-02-20 14:00 /to 2024-02-20 16:00
                """);
                }

                String eventDescription = parts2[0].trim();
                String eventStartString = parts2[1].trim();
                String eventEndString = parts2[2].trim();

                String[] startParts = eventStartString.split(" ");
                String[] endParts = eventEndString.split(" ");

                if (startParts.length < 2 || endParts.length < 2) {
                    throw new CortanaException("""
                Invalid date/time format. Please provide both date and time.
                Use format: YYYY-MM-DD HH:mm
                Example: event Meeting /from 2024-02-20 14:00 /to 2024-02-20 16:00
                """);
                }

                try {
                    String startDate = parseDate(startParts[0]);
                    String startTime = parseTime(startParts[1]);
                    String endDate = parseDate(endParts[0]);
                    String endTime = parseTime(endParts[1]);

                    Event event = new Event(eventDescription, startDate, endDate, startTime, endTime);
                    tasks.addTask(event);
                } catch (DateTimeParseException e) {
                    throw new CortanaException("Error parsing date or time: " + e.getParsedString());
                }
            default:
                throw new CortanaException("Unknown task type: " + taskType);
            }
        } catch (DateTimeParseException e) {
            return Ui.showError(e.getMessage());
        }
        return output;
    }
}
