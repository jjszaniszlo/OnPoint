package com.ateam.onpoint.gui.components;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class DateSelector extends Stage {
    private static DateSelector instance;

    private final Pane root = new Pane();

    public static DateSelector getInstance() {
        if (instance == null) {
            instance = new DateSelector();
        }
        return instance;
    }

    private DateSelector() {
        super();
        this.setWidth(200);
        this.setHeight(200);
        this.setX(0);
        this.setY(0);

        this.root.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));

        Scene scene = new Scene(this.root, 200, 200);
        this.setScene(scene);
    }

    private void openDateSelector(double x, double y, int tid) {

    }
}
