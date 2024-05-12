package com.ateam.onpoint.core;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.util.Callback;
import javafx.util.Duration;

import java.time.LocalDate;
import java.time.LocalTime;

public class Task {
    private ObjectProperty<LocalDate> creationDate;
    private ObjectProperty<LocalTime> creationTime;
    private ObjectProperty<LocalDate> startDate;
    private ObjectProperty<LocalTime> startTime;
    private ObjectProperty<Duration> duration;
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
                t.descriptionProperty(),
                t.isCompletedProperty()};
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

    public ObjectProperty<LocalDate> startDateProperty() {
        if (startDate == null) {
            startDate = new SimpleObjectProperty<>();
        }
        return startDate;
    }

    public LocalDate getStartDate() {
        return startDate.get();
    }

    public ObjectProperty<LocalTime> startTimeProperty() {
        if (startTime == null) {
            startTime = new SimpleObjectProperty<>();
        }
        return startTime;
    }

    public LocalTime getStartTime() {
        return startTime.get();
    }

    public Duration getDuration() {
        return duration.get();
    }

    public ObjectProperty<Duration> durationProperty() {
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
