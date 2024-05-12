package com.ateam.onpoint.core;

import com.ateam.onpoint.ApplicationConfig;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/*
 * Handles saving tasks alongside other kinds of data to a local file format.
 */
public class TaskDatabase {
    private static TaskDatabase instance;
    private final ObservableList<Task> tasksList = FXCollections.observableArrayList(Task.extractor());

    private TaskDatabase() {
        ensureDatabaseFileExists();
    }

    private static void ensureDatabaseFileExists() {
        File databaseFile = new File(ApplicationConfig.DATABASE_FILE);
        final var path = Paths.get(ApplicationConfig.DATABASE_FILE);

        try {
            Files.createDirectories(path.getParent());
            databaseFile.createNewFile();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static TaskDatabase getInstance() {
        if (instance == null) {
            instance = new TaskDatabase();
        }
        return instance;
    }

    public void addTask(Task task) {
        tasksList.add(task);
    }

    public void removeTask(Task task) {
        tasksList.remove(task);
    }

    public ObservableList<Task> getTasksList() {
        return tasksList;
    }

    public void saveDatabase() {
        final var dbFile = new File(ApplicationConfig.DATABASE_FILE);
        try (final var ostream = new ObjectOutputStream(new FileOutputStream(dbFile))) {
            ostream.writeObject(new ArrayList<>(this.tasksList));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void loadDatabase() {
        final var dbFile = new File(ApplicationConfig.DATABASE_FILE);
        try (final var istream = new ObjectInputStream(new FileInputStream(dbFile))) {
            List<Task> loaded = (List<Task>)istream.readObject();
            this.tasksList.setAll(loaded);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
