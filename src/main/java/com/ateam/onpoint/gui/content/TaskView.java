package com.ateam.onpoint.gui.content;

import com.ateam.onpoint.gui.components.TaskList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Random;

/**
 * The task view is responsible for the nodes used to display tasks.
 */
public class TaskView extends VBox implements IContent {
    private static TaskList taskList;

    public static TaskList getTaskList() {
        return taskList;
    }

    public TaskView() {
        super();

        if (taskList == null) {
            taskList = new TaskList();
            taskList.setCellFactory(p -> new TaskList.TaskCell());
            taskList.setPrefWidth(400);
        }

        this.setPadding(new Insets(0, 0, 0, 10));

        var header = new ContentHeader("Tasks");

        var toolbar = new ToolBar();
        toolbar.setPrefWidth(400);
        toolbar.setPrefHeight(40);

        var newButton = new Button();
        var plusIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/plus_white_32.png")));
        var plusIconView = new ImageView(plusIcon);
        newButton.setGraphic(plusIconView);
        newButton.setPrefSize(24, 24);
        newButton.setPadding(new Insets(1, 1, 1, 1));

        EventHandler<ActionEvent> addTaskButtonEventHandler = e -> {
            // generate unique ID.
            Random random = new Random();
            int tid;
            do {
                tid = 10000 + random.nextInt(0, 90000);
            } while (TaskList.exitingTaskIds.contains(tid));

            LocalDate creationDate = LocalDate.now();
            LocalTime creationTime = LocalTime.now();

            taskList.getItems().add(new TaskList.Task(tid, creationDate, creationTime));
            taskList.getSelectionModel().selectLast();
        };

        newButton.setOnAction(addTaskButtonEventHandler);

        var archiveButton = new Button();
        var archiveIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/archive_white_24.png")));
        var archiveIconView = new ImageView(archiveIcon);
        archiveButton.setGraphic(archiveIconView);
        archiveButton.setPrefSize(24, 24);
        archiveButton.setPadding(new Insets(1, 1, 1 , 1));

        toolbar.getItems().addAll(newButton, archiveButton);

        this.getChildren().addAll(header, toolbar, taskList);
    }

    /**
     * build and return the content view for the task system
     * @return the parent node for the task system
     */
    @Override
    public Parent getContentView() {
        return this;
    }
}
