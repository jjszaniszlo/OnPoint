package com.ateam.onpoint.core;

import javafx.collections.ObservableList;

/*
 * Handles saving tasks alongside other kinds of data to a local file format.
 */
public class TaskDatabase {
    private static TaskDatabase instance;
    private TaskDatabase() {}

    private ObservableList<Task> tasks;

    public TaskDatabase getInstance() {
        if (instance == null) {
            instance = new TaskDatabase();
        }
        return instance;
    }

    public ObservableList<Task> getTasks() {
        return tasks;
    }
}
