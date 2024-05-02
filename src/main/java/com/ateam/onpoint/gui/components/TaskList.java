package com.ateam.onpoint.gui.components;


import com.ateam.onpoint.core.TaskManager;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

public class TaskList extends ListView<TaskList.TaskRecord> {
    public TaskList() {
        super();
    }

    public static class TaskRecord {
        String description;
    }

    public static class TaskCell extends ListCell<TaskRecord> {
        private final HBox root;
        private final Label description;

        public TaskCell() {
            super();

            // setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            // setAlignment(Pos.CENTER);

            this.description = new Label();
            this.description.setGraphicTextGap(5);
            this.description.setStyle("-fx-font-weight: 600;");

            this.root = new HBox();
            this.setAlignment(Pos.CENTER_LEFT);
            this.root.getChildren().add(this.description);

            // wip dragging and moving around
            setOnDragDetected(event -> {
                if (getItem() == null) {
                    return;
                }
                ObservableList<TaskList.TaskRecord> items = getListView().getItems();
            });
        }

        @Override
        protected void updateItem(TaskRecord rec, boolean empty) {
            super.updateItem(rec, empty);

            if (rec == null || empty) {
                this.setGraphic(null);
                this.description.setText(null);
            } else {
                this.setGraphic(root);
                this.description.setText(rec.description);
            }
        }
    }
}
