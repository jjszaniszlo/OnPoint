package com.ateam.onpoint.gui.content;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class ContentHeader extends HBox {
    public ContentHeader(String heading) {
        super();

        Label headingLabel = new Label(heading);
        headingLabel.setPadding(new Insets(5, 0, 5, 20));
        headingLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 30;");

        this.getChildren().add(headingLabel);
    }
}
