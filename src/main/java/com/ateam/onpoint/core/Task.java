package com.ateam.onpoint.core;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.util.Callback;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;

public class Task implements Serializable {
    private transient ObjectProperty<LocalDate> creationDate = new SimpleObjectProperty<>(null);
    private transient ObjectProperty<LocalTime> creationTime = new SimpleObjectProperty<>(null);
    private transient ObjectProperty<LocalDate> startDate = new SimpleObjectProperty<>(null);
    private transient ObjectProperty<LocalTime> startTime = new SimpleObjectProperty<>(null);
    private transient IntegerProperty duration = new SimpleIntegerProperty(0);
    private transient StringProperty description = new SimpleStringProperty("");
    private transient BooleanProperty isComplete = new SimpleBooleanProperty(false);

    public Task(LocalDate date, LocalTime time) {
        creationDateProperty().set(date);
        creationTimeProperty().set(time);
        startDateProperty().set(date);
        startTimeProperty().set(time);
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
        return creationDate;
    }

    public ObjectProperty<LocalTime> creationTimeProperty() {
        return creationTime;
    }

    public ObjectProperty<LocalDate> startDateProperty() {
        return startDate;
    }

    public ObjectProperty<LocalTime> startTimeProperty() {
        return startTime;
    }

    public IntegerProperty durationProperty() {
        return duration;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public  BooleanProperty isCompletedProperty() {
        return isComplete;
    }

    @Serial
    private void writeObject(ObjectOutputStream ostream) throws IOException {
        ostream.defaultWriteObject();
        ostream.writeObject(creationDateProperty().get());
        ostream.writeObject(creationTimeProperty().get());
        ostream.writeObject(startDateProperty().get());
        ostream.writeObject(startTimeProperty().get());
        ostream.writeInt(durationProperty().get());
        ostream.writeUTF(descriptionProperty().get());
        ostream.writeBoolean(isCompletedProperty().get());
    }

    @Serial
    private void readObject(ObjectInputStream istream) throws IOException, ClassNotFoundException {
        this.creationDate = new SimpleObjectProperty<>((LocalDate) istream.readObject());
        this.creationTime = new SimpleObjectProperty<>((LocalTime) istream.readObject());
        this.startDate = new SimpleObjectProperty<>((LocalDate) istream.readObject());
        this.startTime = new SimpleObjectProperty<>((LocalTime) istream.readObject());
        this.duration = new SimpleIntegerProperty(istream.readInt());
        this.description = new SimpleStringProperty(istream.readUTF());
        this.isComplete = new SimpleBooleanProperty(istream.readBoolean());
    }

    public static class SortByDate implements Comparator<Task> {
        @Override
        public int compare(Task o1, Task o2) {
            return 0;
        }
    }

    public static class SortByTime implements Comparator<Task> {
        @Override
        public int compare(Task t1, Task t2) {
            final var t1Time = t1.startTimeProperty().get();
            final var t2Time = t2.startTimeProperty().get();
            final var t1AfterT2 = t1Time.isAfter(t2Time);
            final var t1BeforeT2 = t1Time.isBefore(t2Time);
            if (t1AfterT2) {
                return 1;
            } else if (t1BeforeT2) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}
