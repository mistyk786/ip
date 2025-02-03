import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import static java.lang.System.out;

public class Carol {
    public static final String INTRO_MSG = lined(" Hello! I'm Carol, your personal assistant.\n"
            + " What can I do for you?\n");
    public static final String END_MSG = lined(" Bye! Hope to hear from you soon!\n");
    public static final String ERROR_MSG = lined(" Please enter a valid command!\n");
    public static ArrayList<Task> list = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        out.println(INTRO_MSG);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String msg = br.readLine().trim();
        boolean running = command(msg);
        while (running) {
            msg = br.readLine().trim();
            running = command(msg);
        }
    }

    public static boolean command(String msg) {
        int msglength = msg.split("\\s+").length;
        switch (msglength) {
            case 0:
                out.println(ERROR_MSG);
                return true;
            case 1:
                switch (msg) {
                    case "bye":
                        out.println(END_MSG);
                        return false;
                    case "list":
                        if (list.isEmpty()) {
                            out.println(lined(" Your list is empty!\n"));
                        }
                        else {
                            StringBuilder sb = new StringBuilder();
                            sb.append(" Here are the tasks in your list:\n");
                            int i = 1;
                            for (Task t: list) {
                                sb.append(" " + i++ + "." + t.toString() + "\n");
                            }
                            out.println(lined(sb.toString()));
                        }
                        return true;
                    default:

                }
            default:
                String action = msg.split("\\s+")[0];
                switch (action) {
                    case "mark":
                        String markmsg = msg.split(" ")[1];
                        int i = Integer.parseInt(markmsg) - 1;
                        Task t = list.get(i);
                        t.markAsDone();
                        String s = lined(String.format("Nice! I've marked this task as done!\n    %s\n", t));
                        out.println(s);
                        return true;
                    case "event", "deadline", "todo":
                        String actionmsg = msg.substring(action.length() + 1);
                        addtoList(action, actionmsg, list);
                        return true;
                    default:
                        out.println(ERROR_MSG);
                        return true;
                }
        }
    }

    public static String lined(String s) {
        String line = "⸺".repeat(80) + "\n";
        return line + s + line;
    }

    public static void addtoList(String action, String msg, ArrayList<Task> list) {
        String addmsg = " Got it. I've added this task to your list:\n";
        msg = msg.trim();
        switch (action) {
            case "todo":
                ToDo td = new ToDo(msg);
                list.add(td);
                out.println(lined(String.format("%s" + "  %s\n " + "Now you have %d tasks in your list.\n", addmsg, td, list.size())));
                break;
            case "deadline":
                String deadlinemsg = msg.split(" /by ")[0];
                String deadline = msg.split(" /by ")[1];
                Deadline dl = new Deadline(deadlinemsg, deadline);
                list.add(dl);
                out.println(lined(String.format("%s" + "  %s\n " + "Now you have %d tasks in your list.\n", addmsg, dl, list.size())));
                break;
            case "event":
                String eventmsg = msg.split(" /from ")[0];
                String event = msg.split(" /from ")[1];
                String start = event.split(" /to ")[0];
                String end = event.split(" /to ")[1];
                Event ev = new Event(eventmsg, start, end);
                list.add(ev);
                out.println(lined(String.format("%s" + "  %s\n " + "Now you have %d tasks in your list.\n", addmsg, ev, list.size())));
                break;
                default:
                    out.println(ERROR_MSG);
        }
    }
}
