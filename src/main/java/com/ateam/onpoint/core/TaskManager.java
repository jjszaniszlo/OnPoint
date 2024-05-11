package com.ateam.onpoint.core;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.jetbrains.annotations.NotNull;

import java.util.Stack;

public class TaskManager {
    private static TaskManager instance;
    private final ObservableList<Task> inboxTasks = FXCollections.observableArrayList();

    private final ObservableList<Task> archiveTasks = FXCollections.observableArrayList();
    private final Stack<Task> deleteStack = new Stack<>();

    private TaskManager() {}

    public static TaskManager getInstance() {
        if (instance == null) {
            instance = new TaskManager();
        }
        return instance;
    }

    public void tasksListAddListener(ListChangeListener<? super Task> listener) {
        inboxTasks.addListener(listener);
    }

    public ObservableList<Task> getInboxTaskList() {
        return inboxTasks;
    }

    public void addTask(Task task) {
        inboxTasks.add(task);
    }

    public void deleteTaskFromInbox(int index) {
        Task t = inboxTasks.remove(index);
        deleteStack.push(t);
    }

    public void archiveTask(int index) {
        Task t = inboxTasks.remove(index);
        archiveTasks.add(t);
    }

    public void setTaskDescription(int index, String description) {
        inboxTasks.get(index).description = description;
    }

    public String getTaskDescription(int index) { return inboxTasks.get(index).description; }

    public void setTaskCompleted(int index, boolean value) {
        inboxTasks.get(index).completed = value;
    }

    public boolean getTaskCompleted(int index) { return inboxTasks.get(index).completed; }

    public static class Task {
        private String description;
        private boolean completed;

        public Task() {}

        public String getDescription() {
            return description;
        }

        public boolean getCompleted() {
            return completed;
        }
    }
}
