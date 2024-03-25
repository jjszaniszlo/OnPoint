package com.ateam.onpoint.gui;


import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

/**
 * Manages all the components specific to the application
 */
public class OnPointGUI extends AnchorPane {
    public static final int MIN_WINDOW_WIDTH = 840;
    public static final int MIN_WINDOW_HEIGHT = 420;
    public static final int SIDEBAR_WIDTH = 200;

    public OnPointGUI() {
        StackPane body = new StackPane(new Dashboard());
        body.getStyleClass().add("body");

        this.getChildren().add(body);
    }
}
