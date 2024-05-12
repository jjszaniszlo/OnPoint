package com.ateam.onpoint.gui.content;


// Package refs (com.ateam.onpoint.gui)
import com.ateam.onpoint.gui.components.Spacer;

import com.ateam.onpoint.core.Task;
import com.ateam.onpoint.core.TaskDatabase;


// javafx
import com.ateam.onpoint.gui.components.TaskCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

// To manage Date and Time calculations
import java.time.LocalDate;
import java.util.List;

public class ScheduleView extends VBox implements IContent {

    private static ListView<Task> scheduleLVToday;
    private static ObservableList<Task> scheduleLTToday;
    private static ListView<Task> scheduleLVTomorrow;
    private static ObservableList<Task> scheduleLTTomorrow;
    private static ListView<Task> scheduleLVUpcoming;
    private static ObservableList<Task> scheduleLTUpcoming;

    public ListView<Task> initializeLV(ObservableList<Task> lt)
    {
        ListView<Task> lv = new ListView<Task>();
        lv.setCellFactory(p -> new TaskCell());
        lv.setItems(lt);
        //lv.setItems(TaskDatabase.getInstance().getTasksList());
        //System.out.println("Successfully initialized " + tl.toString() + "!");
        return lv;
    }

    public TaskDatabase initializeDB()
    {
        return new TaskDatabase();
    }

    public ScheduleView() {
        super();

        // Initialize the respective static TaskLists
        if(scheduleLVToday == null)
        {
            scheduleLTToday = FXCollections.observableArrayList(Task.extractor());
            scheduleLVToday = initializeLV(scheduleLTToday);
        }
        if(scheduleLVTomorrow == null)
        {
            scheduleLTTomorrow = FXCollections.observableArrayList(Task.extractor());
            scheduleLVTomorrow = initializeLV(scheduleLTTomorrow);
        }
        if(scheduleLVUpcoming == null)
        {
            scheduleLTUpcoming = FXCollections.observableArrayList(Task.extractor());
            scheduleLVUpcoming = initializeLV(scheduleLTUpcoming);
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
        scheduleTLs.getChildren().addAll(scheduleLVToday, new Spacer(), scheduleLVTomorrow, new Spacer(), scheduleLVUpcoming);

        handleTLSync();

        this.getChildren().addAll(header, datesContainer, scheduleTLs);
    }

    public void handleTLSync()
    {
        System.out.println("Beginning sync");
        ObservableList<Task> collection = TaskDatabase.getInstance().getTasksList();

        LocalDate todayDate = LocalDate.now();
        LocalDate tomorrowDate = LocalDate.now().plusDays(1);
        LocalDate[] ignore = new LocalDate[] { todayDate, tomorrowDate };
        scanAndAssign(scheduleLVToday, collection, todayDate);
        scanAndAssign(scheduleLVTomorrow, collection, tomorrowDate);
        scanAndAssignUpcoming(scheduleLVUpcoming, collection, ignore);
    }

    public void scanAndAssign(ListView<Task> lv, ObservableList<Task> tl, LocalDate ld)
    {
        ObservableList<Task> toAdd = tl.filtered(x -> x.startDateProperty().getValue().equals(ld));
        lv.getItems().addAll(toAdd);
    }

    public void scanAndAssignUpcoming(ListView<Task> lv, ObservableList<Task> tl, LocalDate[] ignore)
    {
        // Remove all from today and tomorrow
        ObservableList<Task> toAdd = tl.filtered(x -> !x.startDateProperty().getValue().equals(ignore[0]));
        toAdd = tl.filtered(x -> !x.startDateProperty().getValue().equals(ignore[1]));
        lv.getItems().addAll(toAdd);
    }

    @Override
    public Parent getContentView() {
        return this;
    }
}
