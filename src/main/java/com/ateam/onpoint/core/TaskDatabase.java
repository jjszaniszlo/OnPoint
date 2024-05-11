package com.ateam.onpoint.core;

import java.util.Date;

/*
 * Handles saving tasks alongside other kinds of data to a local file format.
 */
public class TaskDatabase {
    private static TaskDatabase instance;

    private TaskDatabase() {}

    public TaskDatabase getInstance() {
        if (instance == null) {
            instance = new TaskDatabase();
        }
        return instance;
    }

    private static class TaskRecord {
        private String description;
        private Date creationDate;
        private Date completionDate;
    }
}
