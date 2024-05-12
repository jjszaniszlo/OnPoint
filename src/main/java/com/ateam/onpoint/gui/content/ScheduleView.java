package com.ateam.onpoint.gui.content;


// Package refs (com.ateam.onpoint.gui)
import com.ateam.onpoint.gui.OnPointGUI;
import com.ateam.onpoint.gui.components.Spacer;

import com.ateam.onpoint.core.Task;
import com.ateam.onpoint.core.TaskDatabase;

// javafx
import com.ateam.onpoint.gui.components.TaskCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

// To manage Date and Time calculations
import java.util.Collections;
import java.time.LocalDate;
import java.time.LocalTime;

// To use all helper classes
import com.ateam.onpoint.core.helpers.*;

/**
 * The main component in charge of hosting our windows, being sorted by their respective dates
 * and application!
 */
public class ScheduleView extends VBox implements IContent {

    private static ListView<Task> scheduleListViewToday;
    private static ObservableList<Task> scheduleListTaskToday;
    private static ListView<Task> scheduleListViewTomorrow;
    private static ObservableList<Task> scheduleListTaskTomorrow;
    private static ListView<Task> scheduleListViewUpcoming;
    private static ObservableList<Task> scheduleListTaskUpcoming;

    public ListView<Task> initializeListView(ObservableList<Task> lt)
    {
        ListView<Task> lv = new ListView<Task>();
        lv.setCellFactory(p -> new TaskCell());
        lv.setItems(lt);
        lv.setPrefWidth(OnPointGUI.CONTENT_VIEW_WIDTH * 0.3);
        lv.setMinWidth(Control.USE_PREF_SIZE);
        lv.setMinHeight(Control.USE_PREF_SIZE);

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
        if(scheduleListViewToday == null)
        {
            scheduleListTaskToday = FXCollections.observableArrayList(Task.extractor());
            scheduleListViewToday = initializeListView(scheduleListTaskToday);
        }
        if(scheduleListViewTomorrow == null)
        {
            scheduleListTaskTomorrow = FXCollections.observableArrayList(Task.extractor());
            scheduleListViewTomorrow = initializeListView(scheduleListTaskTomorrow);
        }
        if(scheduleListViewUpcoming == null)
        {
            scheduleListTaskUpcoming = FXCollections.observableArrayList(Task.extractor());
            scheduleListViewUpcoming = initializeListView(scheduleListTaskUpcoming);
        }

        this.setPrefWidth(400);
        this.setAlignment(Pos.TOP_CENTER);

        ContentHeader header = new ContentHeader("Schedule");
        String todayDateString = LocalDate.now().getMonth().toString() + " " + LocalDate.now().getDayOfMonth();
        String tomorrowDateString = LocalDate.now().plusDays(1).getMonth().toString() + " " + LocalDate.now().plusDays(1).getDayOfMonth();
        Label todayLabel = new Label("Today - " + todayDateString);
        Label tomorrowLabel = new Label("Tomorrow - " + tomorrowDateString);
        Label upcomingLabel = new Label("Upcoming:");

        VBox todayContainer = new VBox();
        todayContainer.getChildren().addAll(todayLabel, scheduleListViewToday);

        VBox tomorrowContainer = new VBox();
        tomorrowContainer.getChildren().addAll(tomorrowLabel, scheduleListViewTomorrow);

        VBox upcomingContainer = new VBox();
        upcomingContainer.getChildren().addAll(upcomingLabel, scheduleListViewUpcoming);

        /*
        HBox datesContainer = new HBox();
        datesContainer.getChildren().addAll(todayLabel, new Spacer(), tomorrowLabel, new Spacer(), upcomingLabel);
        */

        // Content lists (similar to TaskView.java)
        HBox listViewContainers = new HBox();
        listViewContainers.getChildren().addAll(todayContainer, new Spacer(), tomorrowContainer, new Spacer(), upcomingContainer);

        handleTLSync();

        this.getChildren().addAll(header, listViewContainers);
    }

    public void handleTLSync()
    {
        //System.out.println("Beginning sync");
        ObservableList<Task> collection = TaskDatabase.getInstance().getTasksList();

        LocalDate todayDate = LocalDate.now();
        LocalDate tomorrowDate = LocalDate.now().plusDays(1);
        LocalDate[] ignore = new LocalDate[] { todayDate, tomorrowDate };
        scanAndAssign(scheduleListViewToday, collection, todayDate);
        scanAndAssign(scheduleListViewTomorrow, collection, tomorrowDate);
        scanAndAssignUpcoming(scheduleListViewUpcoming, collection, ignore);
    }

    public void scanAndAssign(ListView<Task> lv, ObservableList<Task> tl, LocalDate ld)
    {
        ObservableList<Task> toAdd = tl.filtered(x -> x.startDateProperty().getValue().equals(ld));
        //FXCollections.sort(toAdd, new SortByTime());
        lv.getItems().setAll(toAdd);
    }

    public void scanAndAssignUpcoming(ListView<Task> lv, ObservableList<Task> tl, LocalDate[] ignore)
    {
        // Remove all from today and tomorrow
        ObservableList<Task> toAdd = tl.filtered(x -> !x.startDateProperty().getValue().equals(ignore[0]));
        toAdd = toAdd.filtered(x -> !x.startDateProperty().getValue().equals(ignore[1]));

        // Sort date and time here
        //FXCollections.sort(toAdd, new SortByDate());
        //FXCollections.sort(toAdd, new SortByTime());
        lv.getItems().setAll(toAdd);
    }

    @Override
    public Parent getContentView() {
        return this;
    }
}
