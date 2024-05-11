package com.ateam.onpoint.gui.components;

import com.ateam.onpoint.core.Task;
import com.ateam.onpoint.gui.content.TaskView;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.StringConverter;
import org.jetbrains.annotations.NotNull;
import java.time.LocalTime;

public class TaskEditor extends Stage {
    final static int WINDOW_WIDTH = 400;
    final static int WINDOW_HEIGHT = 180;

    private static TaskEditor instance;

    private final VBox root = new VBox();
    private final TextField descriptionField = new TextField();
    private final DatePicker datePicker = new DatePicker();
    private final Spinner<Integer> hoursSpinner = new Spinner<>();
    private final Spinner<Integer> minutesSpinner = new Spinner<>();

    private Task currentTask;

    public static TaskEditor getInstance() {
        if (instance == null) {
            instance = new TaskEditor();
        }
        return instance;
    }

    private TaskEditor() {
        super();
        this.setWidth(WINDOW_WIDTH);
        this.setHeight(WINDOW_HEIGHT);
        this.setResizable(false);

        this.root.setAlignment(Pos.TOP_CENTER);

        this.descriptionField.setMinWidth(WINDOW_WIDTH * 0.8);
        this.descriptionField.setPrefWidth(WINDOW_WIDTH * 0.8);
        this.descriptionField.setMaxWidth(WINDOW_WIDTH * 0.8);

        var timeContainer = new HBox();
        timeContainer.setAlignment(Pos.CENTER);

        var hoursSpinnerFactory = createHoursSpinnerFactory();
        hoursSpinner.setEditable(true);
        hoursSpinner.setValueFactory(hoursSpinnerFactory);
        hoursSpinner.setPrefWidth(70);
        hoursSpinner.setPromptText("HH");

        var minutesSpinnerFactory = createMinutesSpinnerFactory();
        minutesSpinner.setEditable(true);
        minutesSpinner.setValueFactory(minutesSpinnerFactory);
        minutesSpinner.setPrefWidth(70);
        minutesSpinner.setPromptText("mm");

        timeContainer.getChildren().addAll(hoursSpinner, new Spacer(20), minutesSpinner);

        this.root.getChildren().addAll(
                new Spacer(Orientation.VERTICAL, 10),
                descriptionField,
                new Spacer(Orientation.VERTICAL, 10),
                datePicker,
                new Spacer(Orientation.VERTICAL, 10),
                timeContainer);

        this.setOnCloseRequest(e -> this.saveToCurrentTask());

        this.focusedProperty().addListener((obs, oldFocused, newFocused) -> {
            if (!newFocused) {
                this.saveToCurrentTask();
                this.close();
            }
        });

        Scene scene = new Scene(this.root, 200, 200);
        this.setScene(scene);
    }

    private void saveToCurrentTask() {
        this.currentTask.descriptionProperty().set(this.descriptionField.getText());
        this.currentTask.completionDateProperty().set(this.datePicker.getValue());
        this.currentTask.completionTimeProperty().set(LocalTime.of(hoursSpinner.getValue(), minutesSpinner.getValue()));

        var index = TaskView.getTaskList().getItems().indexOf(this.currentTask);
        TaskView.getTaskList().getItems().remove(this.currentTask);
        TaskView.getTaskList().getItems().add(index, this.currentTask);
    }

    private static SpinnerValueFactory.@NotNull IntegerSpinnerValueFactory createMinutesSpinnerFactory() {
        var minutesSpinnerFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 60, 0, 1);
        minutesSpinnerFactory.setConverter(new StringConverter<>() {
            @Override
            public String toString(Integer integer) {
                return String.format("%02d", integer);
            }

            @Override
            public Integer fromString(String s) {
                return Integer.valueOf(s);
            }
        });
        minutesSpinnerFactory.setWrapAround(true);
        return minutesSpinnerFactory;
    }

    private static SpinnerValueFactory.@NotNull IntegerSpinnerValueFactory createHoursSpinnerFactory() {
        var hoursSpinnerFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 24, 0, 1);
        hoursSpinnerFactory.setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer integer) {
                return String.format("%02d", integer);
            }

            @Override
            public Integer fromString(String s) {
                return Integer.valueOf(s);
            }
        });
        hoursSpinnerFactory.setWrapAround(true);
        return hoursSpinnerFactory;
    }

    public void openTaskEditor(Window win, Task task) {
        this.currentTask = task;
        final var centerPosX = win.getX() + win.getWidth()/2;
        final var centerPosY = win.getY() + win.getHeight()/2;
        final var winPosX = centerPosX - this.getWidth()/2;
        final var winPosY = centerPosY - this.getHeight()/2;

        this.setX(winPosX);
        this.setY(winPosY);

        this.descriptionField.setText(this.currentTask.getDescription());
        if (this.currentTask.completionDateProperty().get() == null) {
            this.datePicker.setValue(this.currentTask.getCreationDate());
        } else {
            this.datePicker.setValue(this.currentTask.getCompletionDate());
        }

        int hours, minutes;
        if (this.currentTask.completionTimeProperty().get() == null) {
            hours = this.currentTask.getCreationTime().getHour();
            minutes = this.currentTask.getCreationTime().getMinute();
        } else {
            hours = this.currentTask.getCompletionTime().getHour();
            minutes = this.currentTask.getCompletionTime().getMinute();
        }
        this.hoursSpinner.getValueFactory().setValue(hours);
        this.minutesSpinner.getValueFactory().setValue(minutes);

        Platform.runLater( ()-> {
            TaskEditor.getInstance().show();
            TaskEditor.getInstance().requestFocus();
        });
    }
}
