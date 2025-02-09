package io;

import carol.CarolException;
import tasks.Task;
import tasks.Tasklist;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;

public class Storage {
    /** User's existing list */
    protected Tasklist list;
    protected String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
        this.list = new Tasklist();
    }

    /**
     * Converts Strings from Storage into tasks that are stored as Tasks in a Tasklist
     * @return Tasklist
     * @throws CarolException File is not found || Error in reading data from file
     */
    public Tasklist loadTasks() throws CarolException {
        String directoryPath = new File(filePath).getParent();
        File dir = new File(directoryPath);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new CarolException("Failed to create data directory.");
            }
        }

        File dataFile = new File(filePath);
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                throw new CarolException("Error creating data file: " + e.getMessage());
            }
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
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

    /**
     * Converts Tasks from Tasklist back to Strings in Storage
     * @throws CarolException Error in saving tasks
     */
    public void saveTasks() throws CarolException {
        File file = new File(filePath);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (Task t : list.getList()) {
                bw.write(t.toFileString() + "\n");
            }
        } catch (IOException i) {
            throw new CarolException("Error saving data tasks: " + i.getMessage());
        }
    }
}
