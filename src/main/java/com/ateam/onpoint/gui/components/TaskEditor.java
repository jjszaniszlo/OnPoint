package com.ateam.onpoint.gui.components;

import com.ateam.onpoint.core.Task;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.StringConverter;
import org.jetbrains.annotations.NotNull;
import java.time.LocalTime;

public class TaskEditor extends Stage {
    final static int WINDOW_WIDTH = 600;
    final static int WINDOW_HEIGHT = 220;

    private static TaskEditor instance;

    private final VBox root = new VBox();
    private final TextField descriptionField = new TextField();
    private final DatePicker datePicker = new DatePicker();
    private final Spinner<Integer> hoursSpinner = new Spinner<>();
    private final Spinner<Integer> minutesSpinner = new Spinner<>();
    private final Spinner<Integer> durationSpinner = new Spinner<>();

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

        durationSpinner.setEditable(true);
        var durationSpinnerFactory = createDurationSpinnerFactory();
        durationSpinner.setValueFactory(durationSpinnerFactory);


        timeContainer.getChildren().addAll(new Label("Start Time"), new Spacer(10), hoursSpinner, new Spacer(20), minutesSpinner);
        timeContainer.setAlignment(Pos.CENTER);

        var descContainer = new HBox(new Label("Description"), new Spacer(10), descriptionField);
        descContainer.setAlignment(Pos.CENTER);

        var dateContainer = new HBox(new Label("Start Date"), new Spacer(10), datePicker);
        dateContainer.setAlignment(Pos.CENTER);

        var durationContainer = new HBox(new Label("Duration"), new Spacer(10), durationSpinner);
        durationContainer.setAlignment(Pos.CENTER);

        this.root.getChildren().addAll(
                new Spacer(Orientation.VERTICAL, 10),
                descContainer,
                new Spacer(Orientation.VERTICAL, 10),
                dateContainer,
                new Spacer(Orientation.VERTICAL, 10),
                timeContainer,
                new Spacer(Orientation.VERTICAL, 10),
                durationContainer);

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

    private static SpinnerValueFactory.@NotNull IntegerSpinnerValueFactory createDurationSpinnerFactory() {
        var durationSpinnerFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1440, 0);
        durationSpinnerFactory.setAmountToStepBy(5);
        durationSpinnerFactory.setConverter(new StringConverter<>() {
            @Override
            public String toString(Integer integer) {
                int hours = integer / 60;
                int minutes = integer % 60;
                return String.format("%02d:%02d", hours, minutes);
            }

            @Override
            public Integer fromString(String s) {
                String[] splitMinutesAndHours = s.split(":");
                int hours = Integer.valueOf(splitMinutesAndHours[0]);
                int minutes = Integer.valueOf(splitMinutesAndHours[1]);
                return hours*60 + minutes;
            }
        });
        return durationSpinnerFactory;
    }

    private void saveToCurrentTask() {
        this.currentTask.descriptionProperty().set(this.descriptionField.getText());
        this.currentTask.startDateProperty().set(this.datePicker.getValue());
        this.currentTask.startTimeProperty().set(LocalTime.of(hoursSpinner.getValue(), minutesSpinner.getValue()));
        this.currentTask.durationProperty().set(this.durationSpinner.getValue());
        System.out.println(this.durationSpinner.getValue());
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

        this.descriptionField.setText(this.currentTask.descriptionProperty().get());
        if (this.currentTask.startDateProperty().get() == null) {
            this.datePicker.setValue(this.currentTask.creationDateProperty().get());
        } else {
            this.datePicker.setValue(this.currentTask.startDateProperty().get());
        }

        int hours, minutes;
        if (this.currentTask.startTimeProperty().get() == null) {
            hours = this.currentTask.creationTimeProperty().get().getHour();
            minutes = this.currentTask.creationTimeProperty().get().getMinute();
        } else {
            hours = this.currentTask.startTimeProperty().get().getHour();
            minutes = this.currentTask.startTimeProperty().get().getMinute();
        }
        this.hoursSpinner.getValueFactory().setValue(hours);
        this.minutesSpinner.getValueFactory().setValue(minutes);

        Platform.runLater( ()-> {
            TaskEditor.getInstance().show();
            TaskEditor.getInstance().requestFocus();
        });
    }
}
