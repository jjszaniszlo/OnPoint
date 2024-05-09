package com.ateam.onpoint.core;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ListCell;

import java.util.ArrayList;

public class TaskManager {
    private static TaskManager instance;

    private final ObservableList<Task> tasks = FXCollections.observableArrayList();

    public void tasksListAddListener(ListChangeListener<? super Task> listener) {
        tasks.addListener(listener);
    }

    private TaskManager() {}

    public static TaskManager getInstance() {
        if (instance == null) {
            instance = new TaskManager();
        }
        return instance;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public static class Task {
    }
}
