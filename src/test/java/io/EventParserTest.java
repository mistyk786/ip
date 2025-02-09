package io;

import io.EventParser;
import carol.CarolException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EventParserTest {

    @Test
    void testParseTaskFromFile_validTodo() throws CarolException {
        String input = "T |  | Read book";
        Task task = EventParser.parseTaskFromFile(input);
        assertInstanceOf(ToDo.class, task);
        assertEquals("Read book", task.getDescription());
        assertFalse(task.isDone());
    }

    @Test
    void testParseTaskFromFile_validDeadline() throws CarolException {
        String input = "D |  | Submit assignment | 2025-02-15 23:59";
        Task task = EventParser.parseTaskFromFile(input);
        assertInstanceOf(Deadline.class, task);
        Deadline deadline = (Deadline) task;
        assertEquals("Submit assignment", deadline.getDescription());
        assertEquals(LocalDate.of(2025, 2, 15), deadline.getDate());
        assertEquals(LocalTime.of(23, 59), deadline.getTime());
    }

    @Test
    void testParseTaskFromFile_validEvent() throws CarolException {
        String input = "E | X | Meeting | 2025-02-20 10:00 | 2025-02-20 11:00";
        Task task = EventParser.parseTaskFromFile(input);
        assertInstanceOf(Event.class, task);
        Event event = (Event) task;
        assertEquals("Meeting", event.getDescription());
        assertEquals(LocalDate.of(2025, 2, 20), event.getStartDate());
        assertEquals(LocalTime.of(10, 0), event.getStartTime());
        assertEquals(LocalDate.of(2025, 2, 20), event.getEndDate());
        assertEquals(LocalTime.of(11, 0), event.getEndTime());
        assertTrue(event.isDone());
    }

    @Test
    void testParseTaskFromFile_invalidFormat() {
        String input = "X | Something invalid";
        assertThrows(CarolException.class, EventParser.parseTaskFromFile(input));
    }

    @Test
    void testParseTask_validTodo() throws CarolException {
        Tasklist tasks = new Tasklist();
        EventParser.parseTask("Read book", "todo", tasks);
        assertEquals(1, tasks.size());
        assertInstanceOf(ToDo.class, tasks.getTask(0));
        assertEquals("Read book", tasks.getTask(0).getDescription());
    }

    @Test
    void testParseTask_validDeadline() throws CarolException {
        Tasklist tasks = new Tasklist();
        EventParser.parseTask("deadline Submit assignment /by 2025-02-15 23:59", "deadline", tasks);
        assertEquals(1, tasks.size());
        assertInstanceOf(Deadline.class, tasks.getTask(0));
        Deadline deadline = (Deadline) tasks.getTask(0);
        assertEquals("Submit assignment", deadline.getDescription());
        assertEquals(LocalDate.of(2025, 2, 15), deadline.getDate());
        assertEquals(LocalTime.of(23, 59), deadline.getTime());
    }

    @Test
    void testParseTask_validEvent() throws CarolException {
        Tasklist tasks = new Tasklist();
        EventParser.parseTask("event Meeting /from 2025-02-20 10:00 /to 2025-02-20 11:00", "event", tasks);
        assertEquals(1, tasks.size());
        assertInstanceOf(Event.class, tasks.getTask(0));
        Event event = (Event) tasks.getTask(0);
        assertEquals("Meeting", event.getDescription());
        assertEquals(LocalDate.of(2025, 2, 20), event.getStartDate());
        assertEquals(LocalTime.of(10, 0), event.getStartTime());
        assertEquals(LocalDate.of(2025, 2, 20), event.getEndDate());
        assertEquals(LocalTime.of(11, 0), event.getEndTime());
    }

    @Test
    void testParseTask_invalidDeadlineFormat() {
        Tasklist tasks = new Tasklist();
        assertThrows(CarolException.class, EventParser.parseTask("Submit assignment", "deadline", tasks));
    }

    @Test
    void testParseTask_invalidEventFormat() {
        Tasklist tasks = new Tasklist();
        assertThrows(CarolException.class, EventParser.parseTask("Meeting /from 2025-02-20", "event", tasks));
    }
}