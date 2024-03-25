package com.ateam.onpoint.gui;


import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

/*
 * Manages all the components specific to the application
 */
public class OnPointGUI extends AnchorPane {
    public int MIN_WINDOW_WIDTH = 1200;
    public int SIDEBAR_WIDTH = 300;

    public OnPointGUI() {
        StackPane body = new StackPane();
        body.getStyleClass().add("body");  // add css to all children of the stack pane

        body.getChildren().add(new Dashboard());

        this.getChildren().add(body);
    }
}
