package com.ateam.onpoint.core;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/*
 * Handles saving tasks alongside other kinds of data to a local file format.
 */
public class TaskDatabase {
    private static TaskDatabase instance;
    public TaskDatabase() {}

    private ObservableList<Task> tasksList = FXCollections.observableArrayList(Task.extractor());

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
