package cortana;

public class CortanaDateTimeException extends CortanaException {
    public CortanaDateTimeException() {
        super("""
                Invalid task format: Missing date/time.
                Use the following format for deadlines:
                [command] [message] /by [YYYY-MM-DD] [HH:mm]
                Example: deadline Submit Report /by 2024-02-20 23:59
                """);
    }
}
