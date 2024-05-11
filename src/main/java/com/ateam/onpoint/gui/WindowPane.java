package com.ateam.onpoint.gui;


import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

/**
 * Manages all the components specific to the application
 */
public class WindowPane extends AnchorPane {
    public static final int MIN_WINDOW_WIDTH = 640;
    public static final int MIN_WINDOW_HEIGHT = 590;
    public static final int SIDEBAR_WIDTH = 200;

    public WindowPane() {
        var body = new StackPane(new Dashboard(new OnPointGUI()));
        body.getStyleClass().add("body");

        this.getChildren().add(body);
    }
}
