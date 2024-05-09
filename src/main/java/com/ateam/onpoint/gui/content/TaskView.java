package com.ateam.onpoint.gui.content;

import com.ateam.onpoint.core.TaskManager;
import com.ateam.onpoint.gui.components.TaskList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;

import java.util.Objects;

/**
 * The task view is responsible for the nodes used to display tasks.
 */
public class TaskView implements IContent {
    /**
     * build and return the content view for the task system
     * @return the parent node for the task system
     */
    @Override
    public Parent getContentView() {
        VBox parent = new VBox();
        ContentHeader header = new ContentHeader("Tasks");

        ToolBar toolbar = new ToolBar();
        Button newButton = new Button();
        Image plusIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/plus_white_32.png")));
        ImageView plusIconView = new ImageView(plusIcon);
        newButton.setGraphic(plusIconView);
        newButton.setPrefSize(24, 24);
        newButton.setPadding(new Insets(1, 1, 1, 1));

        EventHandler<ActionEvent> addTaskButtonEventHandler = e -> {
            TaskManager.getInstance().addTask(new TaskManager.Task());
        };

        newButton.setOnAction(addTaskButtonEventHandler);

        Button archiveButton = new Button();
        Image archiveIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/archive_white_24.png")));
        ImageView archiveIconView = new ImageView(archiveIcon);
        archiveButton.setGraphic(archiveIconView);
        archiveButton.setPrefSize(24, 24);
        archiveButton.setPadding(new Insets(1, 1, 1 , 1));

        toolbar.getItems().addAll(newButton, archiveButton);

        TaskList taskList = new TaskList();
        taskList.setCellFactory(p -> new TaskList.TaskCell());
        taskList.setPrefWidth(400);

        parent.getChildren().addAll(header, toolbar, taskList);
        return parent;
    }
}
