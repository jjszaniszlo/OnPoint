package com.ateam.onpoint.gui;

import com.ateam.onpoint.gui.components.UserHeader;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/*
 * Contains the user account snippet, and the navbar for app navigation.
 */
public class Sidebar extends VBox {
    public Sidebar(ContentView contentView) {
        // init a vbox
        super();

        this.getChildren().addAll(new Button("Hello 1"),  new Button("Hello 2"));
    }
}
