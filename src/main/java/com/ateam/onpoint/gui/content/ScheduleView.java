package com.ateam.onpoint.gui.content;

import com.ateam.onpoint.gui.OnPointGUI;
import com.ateam.onpoint.gui.components.ScheduleCell;
import com.ateam.onpoint.gui.components.Spacer;
import com.ateam.onpoint.core.Task;
import com.ateam.onpoint.core.TaskDatabase;
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
import java.time.LocalDate;

/**
 * The main component in charge of hosting our windows, being sorted by their respective dates
 * and application!
 */
public class ScheduleView extends VBox implements IContent {

    private final static ListView<Task> scheduleListViewToday = initializeListView();
    private final static ListView<Task> scheduleListViewTomorrow = initializeListView();
    private final static ListView<Task> scheduleListViewUpcoming = initializeListView();

    private static ListView<Task> initializeListView()
    {
        ListView<Task> listView = new ListView<>();
        listView.setCellFactory(p -> new ScheduleCell());
        listView.setPrefWidth(OnPointGUI.CONTENT_VIEW_WIDTH * 0.3);
        listView.setMinWidth(Control.USE_PREF_SIZE);
        listView.setMinHeight(Control.USE_PREF_SIZE);

        return listView;
    }

    public ScheduleView() {
        super();

        ContentHeader header = new ContentHeader("Schedule");

        this.setPrefWidth(400);
        this.setAlignment(Pos.TOP_CENTER);

        setupListViews();

        // create layout
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

        // Content lists (similar to TaskView.java)
        HBox listViewContainers = new HBox();
        listViewContainers.getChildren().addAll(todayContainer, new Spacer(), tomorrowContainer, new Spacer(), upcomingContainer);

        this.getChildren().addAll(header, listViewContainers);
    }

    private void setupListViews() {
        LocalDate todayDate = LocalDate.now();
        LocalDate tomorrowDate = LocalDate.now().plusDays(1);

        scheduleListViewToday.setItems(TaskDatabase.getInstance().getTasksList()
                .filtered(task -> task.startDateProperty().get().equals(todayDate))
                .sorted(new Task.SortByTime()));

        scheduleListViewTomorrow.setItems(TaskDatabase.getInstance().getTasksList()
                .filtered(task -> task.startDateProperty().get().equals(tomorrowDate))
                .sorted(new Task.SortByTime()));

        scheduleListViewUpcoming.setItems(TaskDatabase.getInstance().getTasksList()
                .filtered(task -> !task.startDateProperty().get().equals(todayDate))
                .filtered(task -> !task.startDateProperty().get().equals(tomorrowDate))
                .sorted(new Task.SortByTime()));
    }

    @Override
    public Parent getContentView() {
        return this;
    }
}
