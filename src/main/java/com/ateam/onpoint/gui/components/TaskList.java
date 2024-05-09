package com.ateam.onpoint.gui.components;


import com.ateam.onpoint.core.TaskManager;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;

public class TaskList extends ListView<TaskList.TaskRecord> {
    public TaskList() {
        super();

        TaskManager.getInstance().tasksListAddListener(change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    int st = change.getFrom();
                    int ed = change.getTo();

                    for (int i = st; i < ed; i++) {
                        this.getItems().add(new TaskRecord("Task"));
                    }
                }
            }
        });
    }

    public static class TaskRecord {
        String description;
        public TaskRecord(String description) {
            this.description = description;
        }
    }

    public static class TaskCell extends ListCell<TaskRecord> {
        private final HBox root;
        private final Label description;
        private final TextField descriptionField;
        private final CheckBox checkBox;

        public TaskCell() {
            super();

            this.root = new HBox();

            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            setAlignment(Pos.CENTER);

            this.description = new Label();
            this.description.setGraphicTextGap(5);
            this.description.setStyle("-fx-font-weight: 600;");

            this.checkBox = new CheckBox();

            this.descriptionField = new TextField();
            this.descriptionField.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ENTER) {
                    this.descriptionField.setEditable(false);

                    this.getItem().description = this.descriptionField.getText();
                    this.descriptionField.setDisable(true);

                    this.root.getChildren().remove(this.descriptionField);
                    this.root.getChildren().add(this.description);
                }
            });

            this.root.setAlignment(Pos.CENTER_LEFT);
            this.root.prefHeight(15);
            this.root.prefWidth(400);

            this.root.getChildren().addAll(this.checkBox, this.descriptionField);

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
            } else {
                this.description.setText(rec.description);
                this.setGraphic(this.root);
            }
        }
    }
}
