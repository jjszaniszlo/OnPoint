package com.ateam.onpoint.gui.content;

import javafx.scene.Parent;
import javafx.scene.control.Label;
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
        VBox vbox = new VBox();
        vbox.getChildren().addAll(
                new ContentHeader("Tasks")
        );
        return vbox;
    }
}
