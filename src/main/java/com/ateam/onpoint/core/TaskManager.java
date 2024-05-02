package com.ateam.onpoint.core;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;

import java.util.ArrayList;

public class TaskManager {
    private static TaskManager instance;

    private final ObservableList<Task> tasks = FXCollections.observableArrayList();

    private TaskManager() {}

    public TaskManager getInstance() {
        if (instance == null) {
            instance = new TaskManager();
        }
        return instance;
    }

    public static class Task {
    }
}
