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
                        out.println(lined(" Added: " + msg + "\n"));
                        list.add(new Task(msg));
                        return true;
                }
            default:
                switch (msg.split("\\s+")[0]) {
                    case "mark":
                        String[] markmsg = msg.split(" ");
                        if (markmsg.length != 2) {
                            out.println(ERROR_MSG);
                        }
                        else if (Integer.parseInt(markmsg[1]) > list.size() || Integer.parseInt(markmsg[1]) < 1) {
                            out.println(ERROR_MSG);
                        }
                        else {
                            int i = Integer.parseInt(markmsg[1]) - 1;
                            Task t = list.get(i);
                            t.markAsDone();
                            String s = lined(String.format("Nice! I've marked this task as done!\n    %s\n", t));
                            out.println(s);
                        }
                        return true;
                    default:
                        out.println(lined(" Added: " + msg + "\n"));
                        list.add(new Task(msg));
                        return true;
                }
        }
    }

    public static String lined(String s) {
        String line = "⸺".repeat(80) + "\n";
        return line + s + line;
    }
}
