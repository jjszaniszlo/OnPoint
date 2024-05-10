package com.ateam.onpoint.gui.components;


import com.ateam.onpoint.core.TaskManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
                        TaskRecord rec = new TaskRecord(i, true);
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

        public final boolean isNewTask;

        public TaskRecord(int index) {
            this.index = index;
            this.isNewTask = false;
        }

        public TaskRecord(int index, boolean isNewTask) {
            this.index = index;
            this.isNewTask = isNewTask;
        }
    }

    public static class TaskCell extends ListCell<TaskRecord> {
        private final HBox root;
        private final TextField descriptionField;
        private final CheckBox checkBox;
        private final ContextMenu contextMenu;

        public TaskCell() {
            super();

            this.root = new HBox();

            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            setAlignment(Pos.CENTER);

            this.checkBox = createCheckBox();
            this.descriptionField = createTextField();

            this.contextMenu = createContextMenu();

            this.root.setAlignment(Pos.CENTER_LEFT);
            this.root.prefHeight(15);
            this.root.prefWidth(450);

            this.root.setOnContextMenuRequested(e -> {
                this.contextMenu.show(this.root.getScene().getWindow(), e.getScreenX(), e.getScreenY());
            });

            this.root.getChildren().addAll(this.checkBox, this.descriptionField);
        }

        private @NotNull ContextMenu createContextMenu() {
            ContextMenu contextMenu = new ContextMenu();

            MenuItem changeDescriptionMenuItem = new MenuItem("Change Description");
            changeDescriptionMenuItem.setOnAction(e -> {
                this.descriptionField.setEditable(true);
                this.descriptionField.setMouseTransparent(false);
                this.descriptionField.requestFocus();
            });

            MenuItem deleteTaskMenuItem = new MenuItem("Delete");
            deleteTaskMenuItem.setOnAction(e -> {});

            contextMenu.getItems().addAll(changeDescriptionMenuItem, deleteTaskMenuItem);
            return contextMenu;
        }

        private @NotNull CheckBox createCheckBox() {
            CheckBox checkBox = new CheckBox();
            checkBox.selectedProperty().addListener(e -> {
                TaskManager.getInstance().setTaskCompleted(this.getItem().index, checkBox.isSelected());
                this.getItem().completed = checkBox.isSelected();
            });
            return checkBox;
        }

        private ChangeListener<? super Scene> finalOnSceneChangeListener;
        private @NotNull TextField createTextField() {
            TextField descriptionField = new TextField();

            descriptionField.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ENTER) {
                    descriptionField.setEditable(false);
                    descriptionField.setMouseTransparent(true);

                    TaskManager.getInstance().setTaskDescription(this.getItem().index, descriptionField.getText());
                    this.getItem().description = descriptionField.getText();
                }
            });

            descriptionField.setOnMouseExited(e -> {
                TaskManager.getInstance().setTaskDescription(this.getItem().index, descriptionField.getText());
                this.getItem().description = descriptionField.getText();
            });

            descriptionField.setStyle("-fx-font-weight: 600;");
            descriptionField.setPrefWidth(350);

            return descriptionField;
        }

        @Override
        protected void updateItem(TaskRecord rec, boolean empty) {
            super.updateItem(rec, empty);

            if (rec == null || empty) {
                this.setGraphic(null);
            } else {
                if (this.getItem() != null && this.getItem().isNewTask) {
                    this.finalOnSceneChangeListener = (obs, old, newScene) -> {
                        if (newScene != null) {
                            descriptionField.requestFocus();
                            descriptionField.sceneProperty().removeListener(this.finalOnSceneChangeListener);
                        }
                    };
                    descriptionField.sceneProperty().addListener(this.finalOnSceneChangeListener);
                } else {
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
