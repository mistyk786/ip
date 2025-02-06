import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import static java.lang.System.out;


public class Carol {
    public enum Command {
        bye, list, mark, unmark, delete, todo, deadline, event
    }
    public static String INTRO_MSG = lined("""
             Hello! I'm Carol, your personal assistant.
             What can I do for you?
            """);
    public static String END_MSG = lined(" Bye! Hope to hear from you soon!\n");
    public static String ERROR_MSG = lined(" Please enter a valid command!\n");
    public static ArrayList<Task> list = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        out.println(INTRO_MSG);
        list = loadTasks();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean running = true;
        while (running) {
            String inp = br.readLine().trim();
            if (inp.isEmpty()) continue;
            Command action = Command.valueOf(inp.split("\\s+")[0]);
            String msg = inp.substring(action.toString().length()).trim();
            switch (action) {
                case bye:
                    running = byeAction(msg);
                    break;
                case list:
                    listAction(msg);
                    break;
                case mark:
                    markAction(msg);
                    break;
                case unmark:
                    unmarkAction(msg);
                    break;
                case delete:
                    deleteAction(msg);
                    break;
                case todo:
                    todoAction(msg);
                    break;
                case deadline:
                    deadlineAction(msg);
                    break;
                case event:
                    eventAction(msg);
                    break;
                default:
                    out.println(ERROR_MSG);
                    break;
            }
        }
    }

    public static String lined(String s) {
        String line = "⸺".repeat(80) + "\n";
        return line + s + line;
    }

    public static boolean byeAction(String msg) {
        if (msg.isEmpty()) {
            out.println(END_MSG);
            saveTasks();
            return false;
        }
        else {
            out.println(ERROR_MSG);
            return true;
        }
    }
    public static void listAction(String msg) {
        if (list.isEmpty()) {
            out.println(lined(" Your list is empty!\n"));
        }
        else if (!msg.isEmpty()) {
            out.println(ERROR_MSG);
        }
        else {
            StringBuilder sb = new StringBuilder();
            sb.append(" Here are the tasks in your list:\n");
            for (Task t : list) {
                sb.append(" ").append(t.toString()).append("\n");
            }
            out.println(lined(sb.toString()));
        }
    }

    public static void markAction(String msg) {
        if (!(msg.length() == 1)) {
            out.println(ERROR_MSG);
            return;
        }

        int i = Integer.parseInt(msg) - 1;
        if (i < 0 || i > list.size() - 1) {
            out.println(ERROR_MSG);
            return;
        }
        Task t = list.get(i);
        t.markAsDone();
        String s = lined(String.format("""
                 Nice! I've marked this task as done!
                    %s
                """, t));
        out.println(s);
    }

    public static void unmarkAction(String msg) {
        if (!(msg.length() == 1)) {
            out.println(ERROR_MSG);
            return;
        }

        int i = Integer.parseInt(msg) - 1;
        if (i < 0 || i > list.size() - 1) {
            out.println(ERROR_MSG);
            return;
        }
        Task t = list.get(i);
        t.markAsUndone();
        String s = lined(String.format("""
                 I've undone this task
                    %s
                """, t));
        out.println(s);
    }

    public static void deleteAction(String msg) {
        if (!(msg.length() == 1)) {
            out.println(ERROR_MSG);
            return;
        }

        int i = Integer.parseInt(msg) - 1;
        if (i < 0 || i > list.size() - 1) {
            out.println(ERROR_MSG);
            return;
        }
        Task t = list.remove(i);
        out.println(lined(String.format("""
                     Noted. I've removed this task:
                        %s
                     Now you have %d tasks left
                    """, t, list.size())));
    }

    public static void todoAction(String msg) {
        if (msg.isEmpty()) {
            out.println(ERROR_MSG);
            return;
        }
        String addMessage = "Got it. I've added this task to your list:";
        msg = msg.trim();
        ToDo td = new ToDo(msg);
        list.add(td);
        out.println(lined(String.format("""
                     %s
                        %s
                     Now you have %d tasks in your list.
                    """, addMessage, td, list.size())));
    }

    public static void deadlineAction(String msg) {
        if (msg.isEmpty()) {
            out.println(ERROR_MSG);
            return;
        }
        else if (msg.split(" /by ").length != 2) {
            out.println(ERROR_MSG);
            return;
        }
        String addMessage = "Got it. I've added this task to your list:";
        msg = msg.trim();
        String deadlineDescription = msg.split(" /by ")[0];
        String deadlineString = msg.split(" /by ")[1].trim();
        LocalDate deadlineDate = LocalDate.parse(deadlineString.split(" ")[0]);
        LocalTime deadlineTime = LocalTime.parse(deadlineString.split(" ")[1]);
        Deadline dl = new Deadline(deadlineDescription, deadlineDate, deadlineTime);
        list.add(dl);
        out.println(lined(String.format("""
                     %s
                        %s
                     Now you have %d tasks in your list.
                    """, addMessage, dl, list.size())));
    }

    public static void eventAction(String msg) {
        if (msg.isEmpty()) {
            out.println(ERROR_MSG);
            return;
        }
        String addMessage = "Got it. I've added this task to your list:";
        msg = msg.trim();
        if (msg.split(" /from ").length != 2 || msg.split(" /to ").length != 2) {
            out.println(ERROR_MSG);
        }
        else {
            String eventMessage = msg.split(" /from ")[0];
            String eventTimings = msg.split(" /from ")[1];
            String eventStartString =  eventTimings.split(" /to ")[0];
            String eventEndString = eventTimings.split(" /to ")[1];
            String eventDateStart = eventStartString.split(" ")[0];
            String eventTimeStart = eventStartString.split(" ")[1];
            String eventDateEnd = eventEndString.split(" ")[0];
            String eventTimeEnd = eventEndString.split(" ")[1];
            Event ev = new Event(eventMessage, LocalDate.parse(eventDateStart), LocalDate.parse(eventDateEnd), LocalTime.parse(eventTimeStart), LocalTime.parse(eventTimeEnd));
            list.add(ev);
            out.println(lined(String.format("""
                     %s
                        %s
                     Now you have %d tasks in your list.
                    """, addMessage, ev, list.size())));
        }
    }

    public static ArrayList<Task> loadTasks() {
        boolean directoryExists = new File("data").exists();
        if (!directoryExists) {
            new File("data").mkdir();
        }
        File dataFile = new File("data/Carol.txt");
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            }
            catch (IOException e) {
                out.println("Error creating data file.");
            }
        }
        try (BufferedReader br = new BufferedReader(new FileReader(dataFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                Task t = Task.parseTask(line);
                if (t != null) list.add(t);
            }
        } catch (IOException e) {
            out.println("Error loading tasks.");
        }
        return list;
    }

    public static void saveTasks() {
        File file = new File("data/carol.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (Task t : list) {
                bw.write(t.toString() + "\n");
            }
        } catch (IOException e) {
            out.println("Error saving tasks.");
        }
    }
}
