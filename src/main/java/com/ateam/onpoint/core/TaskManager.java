package com.ateam.onpoint.core;

import javafx.beans.InvalidationListener;
import javafx.beans.binding.ObjectExpression;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ListCell;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TaskManager {
    private static TaskManager instance;

    private final ObservableList<Task> tasks = FXCollections.observableArrayList();

    public void tasksListAddListener(ListChangeListener<? super Task> listener) {
        tasks.addListener(listener);
    }

    public void changeTaskDescription(int index, String description) {
        tasks.get(index).description = description;
    }

    public ObservableList<Task> getTaskList() {
        return tasks;
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
        private String description;

        public Task(String description) {
            this.description = description;
        }

        public void setDescription(@NotNull String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}
