package com.ateam.onpoint.gui.content;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ScheduleView extends VBox implements IContent {

    public ScheduleView() {
        super();

        ContentHeader header = new ContentHeader("Schedule");

        this.getChildren().add(header);
    }

    @Override
    public Parent getContentView() {
        return this;
    }
}
