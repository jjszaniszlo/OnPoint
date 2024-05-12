package com.ateam.onpoint.gui.content;

import com.ateam.onpoint.core.Task;
import com.ateam.onpoint.core.TaskDatabase;
import com.ateam.onpoint.gui.components.TaskCell;
import com.ateam.onpoint.gui.components.TaskEditor;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * The task view is responsible for the nodes used to display tasks.
 */
public class TaskView extends VBox implements IContent {
    private final ListView<Task> taskListView = new ListView<>();

    public TaskView() {
        super();

        taskListView.setCellFactory(p -> new TaskCell());
        taskListView.setItems(TaskDatabase.getInstance().getTasksList());

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
            LocalDate creationDate = LocalDate.now();
            LocalTime creationTime = LocalTime.now();

            Task task = new Task(creationDate, creationTime);

            TaskDatabase.getInstance().addTask(task);
            taskListView.getSelectionModel().selectLast();

            TaskEditor.getInstance().openTaskEditor(getScene().getWindow(), task);
        };

        newButton.setOnAction(addTaskButtonEventHandler);

        var archiveButton = new Button();
        var archiveIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/archive_white_24.png")));
        var archiveIconView = new ImageView(archiveIcon);
        archiveButton.setGraphic(archiveIconView);
        archiveButton.setPrefSize(24, 24);
        archiveButton.setPadding(new Insets(1, 1, 1 , 1));

        toolbar.getItems().addAll(newButton, archiveButton);

        this.getChildren().addAll(header, toolbar, taskListView);
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
