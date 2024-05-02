package com.ateam.onpoint.core;

public class TaskManager {
    private static TaskManager instance;

    private TaskManager() {}

    public TaskManager getInstance() {
        if (instance == null) {
            instance = new TaskManager();
        }
        return instance;
    }

    private static class Task {
        private TaskInfo info;
    }
}
