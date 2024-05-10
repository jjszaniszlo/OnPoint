package com.ateam.onpoint.gui.components;


import com.ateam.onpoint.core.TaskManager;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unchecked")
public class TaskList extends ListView<TaskList.TaskRecord> {
    public TaskList() {
        super();

        TaskManager.getInstance().tasksListAddListener(change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    int st = change.getFrom();
                    int ed = change.getTo();
                    ObservableList<TaskManager.Task> tasks = (ObservableList<TaskManager.Task>) change.getList();

                    for (int i = st; i < ed; i++) {
                        this.getItems().add(new TaskRecord(i, tasks.get(i).getDescription()));
                    }
                }
            }
        });
    }

    public static class TaskRecord {
        public int index;
        public String description;

        public TaskRecord(int index, String description) {
            this.index = index;
            this.description = description;
        }
    }

    public static class TaskCell extends ListCell<TaskRecord> {
        private final HBox root;
        private final TextField descriptionField;
        private final CheckBox checkBox;

        public TaskCell() {
            super();

            this.root = new HBox();

            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            setAlignment(Pos.CENTER);

            this.checkBox = new CheckBox();

            this.descriptionField = getTextField();
            this.descriptionField.setStyle("-fx-font-weight: 600;");

            this.root.setAlignment(Pos.CENTER_LEFT);
            this.root.prefHeight(15);
            this.root.prefWidth(400);

            this.root.getChildren().addAll(checkBox, descriptionField);
        }

        private @NotNull TextField getTextField() {
            TextField descriptionField = new TextField();
            descriptionField.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ENTER) {
                    descriptionField.setEditable(false);
                    descriptionField.setMouseTransparent(true);

                    TaskManager.getInstance().changeTaskDescription(this.getItem().index, descriptionField.getText());
                    this.getItem().description = descriptionField.getText();
                }
            });
            /*descriptionField.setOnMouseClicked(e -> {
                if (descriptionField.isEditable()) {
                    this.getListView().getSelectionModel().select(this.getItem());
                }
            });
           */

            descriptionField.focusedProperty().addListener((obs, oldVal, newVal) -> {
                if (!newVal) { // If TextField loses focus save description
                    descriptionField.setEditable(false);
                    descriptionField.setMouseTransparent(true);
                    TaskManager.getInstance().changeTaskDescription(this.getItem().index, descriptionField.getText());
                    this.getItem().description = descriptionField.getText();
                }
            });
            return descriptionField;
        }

        @Override
        protected void updateItem(TaskRecord rec, boolean empty) {
            super.updateItem(rec, empty);

            if (rec == null || empty) {
                this.setGraphic(null);
            } else {
                if (!this.descriptionField.getText().isBlank()) {
                    descriptionField.setEditable(false);
                    descriptionField.setMouseTransparent(true);
                }

                this.descriptionField.setText(rec.description);
                this.setGraphic(this.root);
            }
        }
    }
}
