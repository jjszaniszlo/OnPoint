package com.ateam.onpoint.gui.content;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ScheduleView implements IContent {
    @Override
    public Parent getContentView() {
        VBox vbox = new VBox();

        ContentHeader header = new ContentHeader("Schedule");

        vbox.getChildren().add(
                header
        );
        return vbox;
    }
}
