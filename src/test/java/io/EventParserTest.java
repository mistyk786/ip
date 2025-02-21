package io;

import cortana.CortanaException;
import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.Tasklist;
import tasks.ToDo;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class EventParserTest {

    @Test
    public void testParseTaskFromFile_validTodo() throws CortanaException {
        String input = "T |   | Read book";
        Task task = EventParser.parseTaskFromFile(input);
        assertInstanceOf(ToDo.class, task);
        assertEquals("Read book", task.getDescription());
        assertFalse(task.isDone());
    }

    @Test
    public void testParseTaskFromFile_validDeadline() throws CortanaException {
        String input = "D |   | Submit assignment | 2025-02-15 23:59";
        Task task = EventParser.parseTaskFromFile(input);
        assertInstanceOf(Deadline.class, task);
        Deadline deadline = (Deadline) task;
        assertEquals("Submit assignment", deadline.getDescription());
        assertEquals(LocalDate.of(2025, 2, 15), deadline.getDate());
        assertEquals(LocalTime.of(23, 59), deadline.getTime());
    }

    @Test
    public void testParseTaskFromFile_validEvent() throws CortanaException {
        String input = "E | X | Meeting | 2025-02-20 10:00 | 2025-02-20 11:00";
        Task task = EventParser.parseTaskFromFile(input);
        assertInstanceOf(Event.class, task);
        Event event = (Event) task;
        assertEquals("Meeting", event.getDescription());
        assertEquals(LocalDate.of(2025, 2, 20), event.getDate());
        assertEquals(LocalTime.of(11, 0), event.getTime());
        assertEquals("X", event.getStatusIcon());
    }


    @Test
    public void testParseTask_validTodo() throws CortanaException {
        Tasklist tasks = new Tasklist();
        EventParser.parseTask("Read book", "todo", tasks);
        assertEquals(1, tasks.size());
        assertInstanceOf(ToDo.class, tasks.getTask(0));
        assertEquals("Read book", tasks.getTask(0).getDescription());
    }

    @Test
    public void testParseTask_validDeadline() throws CortanaException {
        Tasklist tasks = new Tasklist();
        EventParser.parseTask("Submit assignment /by 2025-02-15 23:59", "deadline", tasks);
        assertEquals(1, tasks.size());
        assertInstanceOf(Deadline.class, tasks.getTask(0));
        Deadline deadline = (Deadline) tasks.getTask(0);
        assertEquals("Submit assignment", deadline.getDescription());
        assertEquals(LocalDate.of(2025, 2, 15), deadline.getDate());
        assertEquals(LocalTime.of(23, 59), deadline.getTime());
    }

    @Test
    public void testParseTask_validEvent() throws CortanaException {
        Tasklist tasks = new Tasklist();
        EventParser.parseTask("Meeting /from 2025-02-20 10:00 /to 2025-02-20 11:00", "event", tasks);
        assertEquals(1, tasks.size());
        assertInstanceOf(Event.class, tasks.getTask(0));
        Event event = (Event) tasks.getTask(0);
        assertEquals("Meeting", event.getDescription());
        assertEquals(LocalDate.of(2025, 2, 20), event.getDate());
        assertEquals(LocalTime.of(11, 0), event.getTime());
    }
}
