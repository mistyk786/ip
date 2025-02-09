package carol.io;

import carol.*;
import carol.tasks.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;

public class Storage {
    protected Tasklist list;
    protected String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
        this.list = new Tasklist();
    }
    public Tasklist loadTasks() throws CarolException {
        String directoryPath = new File(filePath).getParent();
        // Ensure the directory exists
        File dir = new File(directoryPath);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new CarolException("Failed to create data directory.");
            }
        }

        // Ensure the file exists
        File dataFile = new File(filePath);
        try {
            dataFile.createNewFile();
        } catch (IOException e) {
            throw new CarolException("Error creating data file: " + e.getMessage());
        }

        // Load tasks from file
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                Task t = EventParser.parseTaskFromFile(line);
                list.addTask(t);
            }
        } catch (FileNotFoundException e) {
            throw new CarolException("Data file not found, even after creation.");
        } catch (IOException e) {
            throw new CarolException("Error reading data file: " + e.getMessage());
        }
        return list;
    }

    public void saveTasks() {
        File file = new File(filePath);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (Task t : list.getList()) {
                bw.write(t.toFileString() + "\n");
            }
        } catch (IOException ignored) {
        }
    }
}
