package com.ateam.onpoint.gui.content;

import com.ateam.onpoint.gui.components.Spacer;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ScheduleView extends VBox implements IContent {
    public ScheduleView() {
        super();

        this.setPrefWidth(400);
        this.setAlignment(Pos.TOP_CENTER);

        final var header = new ContentHeader("Schedule");

        final var prevDateButton = new Button("Yesterday");
        final var todayLabel = new Label("Today");
        final var nextDateButton = new Button("Tomorrow");

        final var datesContainer = new HBox();
        datesContainer.getChildren().addAll(prevDateButton, new Spacer(), todayLabel, new Spacer(), nextDateButton);

        this.getChildren().addAll(header, datesContainer);
    }

    @Override
    public Parent getContentView() {
        return this;
    }
}
