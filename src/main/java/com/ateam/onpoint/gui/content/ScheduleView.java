package com.ateam.onpoint.gui.content;


// Package refs (com.ateam.onpoint.gui)
import com.ateam.onpoint.gui.components.Spacer;
import com.ateam.onpoint.gui.components.TaskList;

// javafx
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ScheduleView extends VBox implements IContent {

    private static TaskList scheduleTLToday;
    private static TaskList scheduleTLTomorrow;
    private static TaskList scheduleTLUpcoming;

    public TaskList InitializeTL()
    {
        TaskList tl = new TaskList();
        tl.setCellFactory(p -> new TaskList.TaskCell());
        tl.setPrefWidth(130);
        System.out.println("Successfully initialized " + tl.toString() + "!");
        return tl;
    }


    public ScheduleView() {
        super();

        // Initialize the respective static TaskLists
        if(scheduleTLToday == null)
        {
            scheduleTLToday = InitializeTL();
        }
        if(scheduleTLTomorrow == null)
        {
            scheduleTLTomorrow = InitializeTL();
        }

        if(scheduleTLUpcoming == null)
        {
            scheduleTLUpcoming = InitializeTL();
        }




        this.setPrefWidth(400);
        this.setAlignment(Pos.TOP_CENTER);

        ContentHeader header = new ContentHeader("Schedule");
        Label todayLabel = new Label("Today");
        Label tomorrowLabel = new Label("Tomorrow");
        Label upcomingLabel = new Label("Upcoming");

        HBox datesContainer = new HBox();
        datesContainer.getChildren().addAll(todayLabel, new Spacer(), tomorrowLabel, new Spacer(), upcomingLabel);

        // Content lists (similar to TaskView.java)
        HBox scheduleTLs = new HBox();
        scheduleTLs.getChildren().addAll(scheduleTLToday, new Spacer(), scheduleTLTomorrow, new Spacer(), scheduleTLUpcoming);

        this.getChildren().addAll(header, datesContainer, scheduleTLs);
    }

    @Override
    public Parent getContentView() {
        return this;
    }
}
