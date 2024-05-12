package com.ateam.onpoint.core;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.util.Callback;
import java.time.LocalDate;
import java.time.LocalTime;

public class Task {
    private ObjectProperty<LocalDate> creationDate;
    private ObjectProperty<LocalTime> creationTime;
    private ObjectProperty<LocalDate> startDate;
    private ObjectProperty<LocalTime> startTime;
    private ObjectProperty<Integer> duration;
    private StringProperty description;
    private BooleanProperty isComplete;

    public Task(LocalDate date, LocalTime time) {
        creationDateProperty().set(date);
        creationTimeProperty().set(time);
    }

    public static Callback<Task, Observable[]> extractor() {
        return (Task t) -> new Observable[]{
                t.startDateProperty(),
                t.creationTimeProperty(),
                t.startDateProperty(),
                t.startTimeProperty(),
                t.durationProperty(),
                t.descriptionProperty(),
                t.isCompletedProperty()};
    }

    public ObjectProperty<LocalDate> creationDateProperty() {
        if (creationDate == null) {
            creationDate = new SimpleObjectProperty<>();
        }
        return creationDate;
    }

    public ObjectProperty<LocalTime> creationTimeProperty() {
        if (creationTime == null) {
            creationTime = new SimpleObjectProperty<>();
        }
        return creationTime;
    }

    public ObjectProperty<LocalDate> startDateProperty() {
        if (startDate == null) {
            startDate = new SimpleObjectProperty<>();
        }
        return startDate;
    }

    public ObjectProperty<LocalTime> startTimeProperty() {
        if (startTime == null) {
            startTime = new SimpleObjectProperty<>();
        }
        return startTime;
    }

    public ObjectProperty<Integer> durationProperty() {
        if (duration == null) {
            duration = new SimpleObjectProperty<>();
        }
        return duration;
    }

    public StringProperty descriptionProperty() {
        if (description == null) {
            description = new SimpleStringProperty();
        }
        return description;
    }

    public  BooleanProperty isCompletedProperty() {
        if (isComplete == null) {
            isComplete = new SimpleBooleanProperty();
        }
        return isComplete;
    }
}
