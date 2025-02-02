import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import static java.lang.System.out;

public class Carol {
    public static final String INTRO_MSG = lined(" Hello! I'm Carol, your personal assistant.\n"
            + " What can I do for you?\n");
    public static final String END_MSG = lined(" Bye! Hope to hear from you soon!\n");
    public static ArrayList<String> list = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        out.println(INTRO_MSG);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String msg = br.readLine();
        boolean running = readmsg(msg);
        while (running) {
            msg = br.readLine();
            running = readmsg(msg);
        }
    }

    public static boolean readmsg(String msg) {
        if (msg.isEmpty()) {
            out.println(lined(" Please enter a valid command!\n"));
            return true;
        }

        switch (msg.toLowerCase()) {
            case "bye":
                out.println(END_MSG);
                return false;
            case "list":
                if (list.isEmpty()) {
                    out.println(lined(" Your list is empty!\n"));
                }
                else {
                    StringBuilder sb = new StringBuilder();
                    int i = 1;
                    for (String s: list) {
                        sb.append(" " + i++ + ". " + s + "\n");
                    }
                    out.println(lined(sb.toString()));
                }
                return true;
            default:
                out.println(lined(" Added: " + msg + "\n"));
                list.add(msg);
                return true;
        }
    }

    public static String lined(String s) {
        String line = "⸺".repeat(40) + "\n";
        return line + s + line;
    }
}
