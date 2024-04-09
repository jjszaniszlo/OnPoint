package com.ateam.onpoint.gui.content;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
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
        toolbar.getItems().addAll(new Button("New Task"));

        parent.getChildren().addAll(header, toolbar);

        return parent;
    }

}
