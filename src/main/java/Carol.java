import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import static java.lang.System.out;

public class Carol {
    public static final String INTRO_MSG = lined("""
             Hello! I'm Carol, your personal assistant.
             What can I do for you?
            """);
    public static final String END_MSG = lined(" Bye! Hope to hear from you soon!\n");
    public static final String ERROR_MSG = lined(" Please enter a valid command!\n");
    public static ArrayList<Task> list = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        out.println(INTRO_MSG);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String inp = br.readLine().trim();
        boolean running = command(inp);
        while (running) {
            inp = br.readLine().trim();
            running = command(inp);
        }
    }

    public static boolean command(String inp) {
        if (inp.isEmpty()) return true;
        String action = inp.split("\\s+")[0];
        String msg = inp.substring(action.length()).trim();
        switch (action) {
            case "bye":
                return byeaction(msg);
            case "list":
                listaction(msg);
                break;
            case "mark":
                markaction(msg);
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
        return true;
    }

    public static String lined(String s) {
        String line = "⸺".repeat(80) + "\n";
        return line + s + line;
    }

    public static boolean byeaction(String msg) {
        if (msg.isEmpty()) {
            out.println(END_MSG);
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
            int i = 1;
            for (Task t : list) {
                sb.append(" ").append(i++).append(".").append(t.toString()).append("\n");
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
        String s = lined(String.format("Nice! I've marked this task as done!\n    %s\n", t));
        out.println(s);
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
}
