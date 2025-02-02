import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.InputMismatchException;

public class Carol {
    public static String line = "⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺\n";
    public static String intromsg = line
            + " Hello! I'm Carol, your personal assistant.\n"
            + " What can I do for you?\n"
            + line;
    public static String endmsg = line
        + " Bye! Hope to hear from you soon!\n"
        + line;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(intromsg);
        String msg = "";
        while (true) {
            msg = br.readLine();
            if (msg.equals("bye")) {
                System.out.println(endmsg);
                return;
            }
            String output = String.format(" %s\n", msg);
            System.out.println(line
                    + output
                    + line);
        }
    }
}
