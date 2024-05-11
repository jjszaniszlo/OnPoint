package com.ateam.onpoint.gui;

import com.ateam.onpoint.gui.components.NavTree;
import com.ateam.onpoint.gui.components.Spacer;
import com.ateam.onpoint.gui.content.IContent;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Objects;

/**
 * Contains the user account snippet, and the navbar for app navigation.
 */
public class Sidebar extends VBox {
    private final NavTree navTree;

    /**
     * construct the sidebar associated with a content view
     * @param view the content view which the sidebar is attached to.
     */
    public Sidebar(OnPointGUI view) {
        super();

        this.navTree = new NavTree(view);

        this.getChildren().addAll(new Header(), navTree);
    }

    public void select(Class<? extends IContent> content) {
        ObservableList<TreeItem<NavTree.ContentRecord>> list = navTree.getRoot().getChildren();
        list.forEach(c -> {
            if (c.getValue().getContent() == content) {
                navTree.getSelectionModel().select(c);
            }
        });
    }

    /**
     * The header has the user's name and profile photo, alongside the open settings functionality
     */
    private static class Header extends HBox {
        private final Button openProfile;
        /**
         * construct the header for the sidebar
         */
        public Header() {
            super();

            this.openProfile = new Button("YOUR NAME");
            this.openProfile.setContentDisplay(ContentDisplay.LEFT);
            this.openProfile.setAlignment(Pos.BASELINE_LEFT);
            this.openProfile.setPrefWidth(WindowPane.SIDEBAR_WIDTH);
            this.openProfile.setPrefHeight(60);
            this.openProfile.getStyleClass().add("flat");
            this.openProfile.setStyle("-fx-underline: false");

            var img = new ImageView(Objects.requireNonNull(getClass().getResource("/img/placeholder.png")).toExternalForm());
            img.setPreserveRatio(true);
            img.fitWidthProperty().bind(this.openProfile.widthProperty().divide(6));
            this.openProfile.setGraphic(img);

            this.getChildren().add(this.openProfile);
        }
    }
}
