package com.ateam.onpoint.gui.content;

import com.ateam.onpoint.core.TaskManager;
import com.ateam.onpoint.gui.components.TaskList;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.ToolBar;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;

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
        Button newButton = new Button("New Task");
        Button archiveButton = new Button("Archive Finished Tasks");
        toolbar.getItems().addAll(newButton, archiveButton);

        TaskList taskList = new TaskList();
        taskList.setCellFactory(p -> new TaskList.TaskCell());
        taskList.setPrefWidth(400);

        parent.getChildren().addAll(header, toolbar, taskList);
        return parent;
    }
}
