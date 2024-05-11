package com.ateam.onpoint.core;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.util.Callback;

import java.time.LocalDate;
import java.time.LocalTime;

public class Task {
    private IntegerProperty taskID;
    private ObjectProperty<LocalDate> creationDate;
    private ObjectProperty<LocalTime> creationTime;
    private ObjectProperty<LocalDate> completionDate;
    private ObjectProperty<LocalTime> completionTime;
    private StringProperty description;
    private BooleanProperty isComplete;

    public Task(int tid, LocalDate date, LocalTime time) {
        taskIDProperty().set(tid);
        creationDateProperty().set(date);
        creationTimeProperty().set(time);
    }

    public static Callback<Task, Observable[]> extractor() {
        return (Task t) -> new Observable[]{
                t.taskIDProperty(),
                t.completionDateProperty(),
                t.creationTimeProperty(),
                t.completionDateProperty(),
                t.completionTimeProperty(),
                t.descriptionProperty(),
                t.isCompletedProperty()};
    }

    public IntegerProperty taskIDProperty() {
        if (taskID == null) {
            taskID = new SimpleIntegerProperty();
        }
        return taskID;
    }

    public int getTaskID() {
        return taskID.get();
    }

    public ObjectProperty<LocalDate> creationDateProperty() {
        if (creationDate == null) {
            creationDate = new SimpleObjectProperty<>();
        }
        return creationDate;
    }

    public LocalDate getCreationDate() {
        return creationDate.get();
    }

    public ObjectProperty<LocalTime> creationTimeProperty() {
        if (creationTime == null) {
            creationTime = new SimpleObjectProperty<>();
        }
        return creationTime;
    }

    public LocalTime getCreationTime() {
        return creationTime.get();
    }

    public ObjectProperty<LocalDate> completionDateProperty() {
        if (completionDate == null) {
            completionDate = new SimpleObjectProperty<>();
        }
        return completionDate;
    }

    public LocalDate getCompletionDate() {
        return completionDate.get();
    }

    public ObjectProperty<LocalTime> completionTimeProperty() {
        if (completionTime == null) {
            completionTime = new SimpleObjectProperty<>();
        }
        return completionTime;
    }

    public LocalTime getCompletionTime() {
        return completionTime.get();
    }

    public StringProperty descriptionProperty() {
        if (description == null) {
            description = new SimpleStringProperty();
        }
        return description;
    }

    public String getDescription() {
        return description.get();
    }

    public  BooleanProperty isCompletedProperty() {
        if (isComplete == null) {
            isComplete = new SimpleBooleanProperty();
        }
        return isComplete;
    }

    public boolean getIsCompleted() {
        return isComplete.get();
    }
}
