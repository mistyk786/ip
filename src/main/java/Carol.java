import java.io.*;
import java.util.ArrayList;
import static java.lang.System.out;

public class Carol {
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
            String action = inp.split("\\s+")[0];
            String msg = inp.substring(action.length()).trim();

            switch (action) {
                case "bye":
                    running = byeaction(msg);
                    break;
                case "list":
                    listaction(msg);
                    break;
                case "mark":
                    markaction(msg);
                    break;
                case "unmark":
                    unmarkaction(msg);
                    break;
                case "delete":
                    deleteaction(msg);
                    break;
                case "todo":
                    todoaction(msg);
                    break;
                case "deadline":
                    deadlineaction(msg);
                    break;
                case "event":
                    eventaction(msg);
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

    public static boolean byeaction(String msg) {
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
    public static void listaction(String msg) {
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

    public static void markaction(String msg) {
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

    public static void unmarkaction(String msg) {
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

    public static void deleteaction(String msg) {
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

    public static void todoaction(String msg) {
        if (msg.isEmpty()) {
            out.println(ERROR_MSG);
            return;
        }
        String addmsg = "Got it. I've added this task to your list:";
        msg = msg.trim();
        ToDo td = new ToDo(msg);
        list.add(td);
        out.println(lined(String.format("""
                     %s
                        %s
                     Now you have %d tasks in your list.
                    """, addmsg, td, list.size())));
    }

    public static void deadlineaction(String msg) {
        if (msg.isEmpty()) {
            out.println(ERROR_MSG);
            return;
        }
        else if (msg.split(" /by ").length != 2) {
            out.println(ERROR_MSG);
            return;
        }
        String addmsg = "Got it. I've added this task to your list:";
        msg = msg.trim();
        String deadlinemsg = msg.split(" /by ")[0];
        String deadline = msg.split(" /by ")[1];
        Deadline dl = new Deadline(deadlinemsg, deadline);
        list.add(dl);
        out.println(lined(String.format("""
                     %s
                        %s
                     Now you have %d tasks in your list.
                    """, addmsg, dl, list.size())));
    }

    public static void eventaction(String msg) {
        if (msg.isEmpty()) {
            out.println(ERROR_MSG);
            return;
        }
        String addmsg = "Got it. I've added this task to your list:";
        msg = msg.trim();
        if (msg.split(" /from ").length != 2 || msg.split(" /to ").length != 2) {
            out.println(ERROR_MSG);
        }
        else {
            String eventmsg = msg.split(" /from ")[0];
            String event = msg.split(" /from ")[1];
            String start = event.split(" /to ")[0];
            String end = event.split(" /to ")[1];
            Event ev = new Event(eventmsg, start, end);
            list.add(ev);
            out.println(lined(String.format("""
                     %s
                        %s
                     Now you have %d tasks in your list.
                    """, addmsg, ev, list.size())));
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
