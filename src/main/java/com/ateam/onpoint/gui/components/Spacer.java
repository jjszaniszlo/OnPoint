package com.ateam.onpoint.gui.components;


import javafx.geometry.Orientation;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class Spacer extends Region {
    public Spacer() {
        this(Orientation.HORIZONTAL);
    }

    public Spacer(Orientation orientation) {
        super();

        switch(orientation) {
            case HORIZONTAL -> HBox.setHgrow(this, Priority.ALWAYS);
            case VERTICAL -> VBox.setVgrow(this, Priority.ALWAYS);
        }
    }
}
