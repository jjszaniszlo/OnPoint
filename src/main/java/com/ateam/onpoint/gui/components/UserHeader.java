package com.ateam.onpoint.gui.components;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class UserHeader extends Pane {
    public UserHeader() {
        super();

        this.getChildren().add(new Label("YOUR NAME"));
    }
}
