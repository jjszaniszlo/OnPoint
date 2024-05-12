package com.ateam.onpoint.gui.content;

import com.ateam.onpoint.gui.components.Spacer;
//import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
//import javafx.scene.control.Button;
//import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ScheduleView extends VBox implements IContent {
    public ScheduleView() {
        super();

        this.setPrefWidth(400);
        this.setAlignment(Pos.TOP_CENTER);

        ContentHeader header = new ContentHeader("Schedule");
        Label todayLabel = new Label("Today");
        Label tomorrowLabel = new Label("Tomorrow");
        Label upcomingLabel = new Label("Upcoming");

        HBox datesContainer = new HBox();
        datesContainer.getChildren().addAll(todayLabel, new Spacer(), tomorrowLabel, new Spacer(), upcomingLabel);

        this.getChildren().addAll(header, datesContainer);
    }

    @Override
    public Parent getContentView() {
        return this;
    }
}
