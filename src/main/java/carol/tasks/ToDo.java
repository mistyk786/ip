package carol.tasks;

import carol.io.Ui;

public class ToDo extends Task{
    private String type;
    public ToDo(String description) {
        super(description);
        this.type = "T";
    }

    @Override
    public String toFileString() {
        return String.format("%s | %s", type, super.toFileString());
    }

    @Override
    public String toString() {
        return String.format("[%s]%s", type, super.toString());
    }

}
