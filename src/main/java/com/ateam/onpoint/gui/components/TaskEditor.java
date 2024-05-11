package com.ateam.onpoint.gui.components;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.jetbrains.annotations.NotNull;

public class TaskEditor extends Stage {
    final static int WINDOW_WIDTH = 400;
    final static int WINDOW_HEIGHT = 400;

    private static TaskEditor instance;

    private final VBox root = new VBox();

    private int currentlyEditingTID = -1;

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

        var descriptionField = new TextField();
        descriptionField.setMinWidth(WINDOW_WIDTH * 0.8);
        descriptionField.setPrefWidth(WINDOW_WIDTH * 0.8);
        descriptionField.setMaxWidth(WINDOW_WIDTH * 0.8);

        var datePicker = new DatePicker();

        var timeContainer = new HBox();
        timeContainer.setAlignment(Pos.CENTER);
        timeContainer.setPadding(new Insets(20, 0, 20, 0));

        var hours = new Spinner<Integer>();
        var hoursSpinnerFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 24, 0, 1);
        hoursSpinnerFactory.setWrapAround(true);
        hours.setEditable(true);
        hours.setValueFactory(hoursSpinnerFactory);
        hours.setPrefWidth(70);

        var minutes = new Spinner<Integer>();
        var minutesSpinnerFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 60, 0, 1);
        minutesSpinnerFactory.setWrapAround(true);
        minutes.setEditable(true);
        minutes.setValueFactory(minutesSpinnerFactory);
        minutes.setPrefWidth(70);

        timeContainer.getChildren().addAll(hours, new Spacer(20), minutes);

        this.root.getChildren().addAll(descriptionField, datePicker, timeContainer);

        this.focusedProperty().addListener((obs, oldFocused, newFocused) -> {
            if (!newFocused) {
                this.close();
            }
        });

        Scene scene = new Scene(this.root, 200, 200);
        this.setScene(scene);
    }

    public void openTaskEditor(Window win, int tid) {
        this.currentlyEditingTID = tid;
        final var centerPosX = win.getX() + win.getWidth()/2;
        final var centerPosY = win.getY() + win.getHeight()/2;
        final var winPosX = centerPosX - this.getWidth()/2;
        final var winPosY = centerPosY - this.getHeight()/2;

        this.setX(winPosX);
        this.setY(winPosY);

        Platform.runLater( ()-> {
            TaskEditor.getInstance().show();
            TaskEditor.getInstance().requestFocus();
        });
    }
}
