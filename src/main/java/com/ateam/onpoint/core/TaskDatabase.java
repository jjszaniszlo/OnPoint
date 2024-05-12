package com.ateam.onpoint.core;

import com.ateam.onpoint.ApplicationConfig;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/*
 * Handles saving tasks alongside other kinds of data to a local file format.
 */
public class TaskDatabase {
    private static TaskDatabase instance;
    private final ObservableList<Task> tasksList = FXCollections.observableArrayList(Task.extractor());

    private TaskDatabase() {
        ensureDatabaseFileExists();

        final var parser = new TaskDatabaseFileParser();
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
}
