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
                        TaskRecord rec = new TaskRecord(i);
                        rec.description = tasks.get(i).getDescription();
                        rec.completed = tasks.get(i).getCompleted();

                        this.getItems().add(rec);
                    }
                }
            }
        });
    }

    public static class TaskRecord {
        public int index;
        public String description;
        public boolean completed;

        public TaskRecord(int index) {
            this.index = index;
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

            this.checkBox = getCheckBox();

            this.descriptionField = getTextField();
            this.descriptionField.setStyle("-fx-font-weight: 600;");

            this.root.setAlignment(Pos.CENTER_LEFT);
            this.root.prefHeight(15);
            this.root.prefWidth(400);

            this.root.getChildren().addAll(checkBox, descriptionField);
        }

        private @NotNull CheckBox getCheckBox() {
            CheckBox checkBox = new CheckBox();
            checkBox.selectedProperty().addListener(e -> {
                TaskManager.getInstance().setTaskCompleted(this.getItem().index, checkBox.isSelected());
                this.getItem().completed = checkBox.isSelected();
            });
            return checkBox;
        }

        private @NotNull TextField getTextField() {
            TextField descriptionField = new TextField();
            descriptionField.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ENTER) {
                    descriptionField.setEditable(false);
                    descriptionField.setMouseTransparent(true);

                    TaskManager.getInstance().setTaskDescription(this.getItem().index, descriptionField.getText());
                    this.getItem().description = descriptionField.getText();
                }
            });
            descriptionField.setOnMouseClicked(e -> {
                if (descriptionField.isEditable()) {
                    this.getListView().getSelectionModel().select(this.getItem());
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
                if (this.descriptionField.getText() != null && !this.descriptionField.getText().isBlank()) {
                    descriptionField.setEditable(false);
                    descriptionField.setMouseTransparent(true);
                }

                this.descriptionField.setText(rec.description);
                this.checkBox.setSelected(rec.completed);
                this.setGraphic(this.root);
            }
        }
    }
}
