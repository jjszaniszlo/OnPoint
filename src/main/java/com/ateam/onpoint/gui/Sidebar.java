package com.ateam.onpoint.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/*
 * Contains the user account snippet, and the navbar for app navigation.
 */
public class Sidebar extends VBox {
    public Sidebar(ContentView contentView) {
        super();

        this.getChildren().addAll(new Header());
    }

    /*
     * The header has the user's name and profile photo, alongside the open settings functionality
     */
    private class Header extends HBox {
        private final Button openProfile;
        public Header() {
            super();

            openProfile = new Button("Your Name");
            openProfile.setPrefWidth(OnPointGUI.SIDEBAR_WIDTH);
            openProfile.setPrefHeight(60);
            openProfile.getStyleClass().add("flat");
            openProfile.setStyle("-fx-underline: false");
            openProfile.setContentDisplay(ContentDisplay.LEFT);
            openProfile.setAlignment(Pos.BASELINE_LEFT);

            ImageView img = new ImageView(getClass().getResource("/img/placeholder.png").toExternalForm());
            img.setPreserveRatio(true);
            img.fitWidthProperty().bind(openProfile.widthProperty().divide(6));
            openProfile.setGraphic(img);

            this.getChildren().add(openProfile);
        }
    }
}
