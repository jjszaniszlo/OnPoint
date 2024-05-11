package com.ateam.onpoint.gui.components;


import com.ateam.onpoint.core.Task;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import org.jetbrains.annotations.NotNull;
import java.util.*;

public class TaskList extends ListView<Task> {
    public final static Set<Integer> exitingTaskIds = new HashSet<>();

    public TaskList() {
        super();
    }

    public static class TaskCell extends ListCell<Task> {
        private final HBox root;
        private final TextField descriptionField;
        private final CheckBox checkBox;
        private final ContextMenu contextMenu;

        public TaskCell() {
            super();

            this.root = new HBox();

            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            setAlignment(Pos.CENTER);

            this.checkBox = new CheckBox();
            this.checkBox.setOnMouseClicked(e -> this.getItem().isCompletedProperty().set(this.checkBox.isSelected()));

            this.descriptionField = createTextField();
            this.contextMenu = createContextMenu();

            this.root.setAlignment(Pos.CENTER_LEFT);
            this.root.prefHeight(15);
            this.root.prefWidth(450);

            this.root.setOnContextMenuRequested(e -> this.contextMenu.show(this.root.getScene().getWindow(), e.getScreenX(), e.getScreenY()));

            setupDragAndDrop();

            this.root.getChildren().addAll(this.checkBox, this.descriptionField);
        }

        private void setupDragAndDrop() {
            this.root.setOnDragDetected(e -> {
                if (getItem() == null) {
                    return;
                }

                final var dragBoard = startDragAndDrop(TransferMode.MOVE);
                final var clipBoard = new ClipboardContent();
                clipBoard.putString(this.getItem().getDescription());
                dragBoard.setDragView(this.root.snapshot(new SnapshotParameters(), null));
                dragBoard.setContent(clipBoard);

                e.consume();
            });

            this.root.setOnDragOver(e -> {
                if (e.getGestureSource() != this && e.getDragboard().hasString()) {
                    e.acceptTransferModes(TransferMode.MOVE);
                }
                e.consume();
            });

            this.root.setOnDragEntered(e -> {
                if (e.getGestureSource() != this && e.getDragboard().hasString()) {
                    this.setOpacity(0.3);
                }
            });

            this.root.setOnDragExited(e -> {
                if (e.getGestureSource() != this && e.getDragboard().hasString()) {
                    this.setOpacity(1);
                }
            });

            this.root.setOnDragDropped(e -> {
                if (this.getItem() == null) {
                    return;
                }

                final var dragboard = e.getDragboard();
                var success = false;

                if (dragboard.hasString()) {
                    final var items = getListView().getItems();
                    final var draggedItem = items.stream()
                            .filter(c -> c.getDescription().compareTo(dragboard.getString()) == 0)
                            .findFirst()
                            .orElse(null);
                    Objects.requireNonNull(draggedItem);

                    final var draggedItemIndex = items.indexOf(draggedItem);
                    final var thisIndex = items.indexOf(getItem());

                    items.set(draggedItemIndex, getItem());
                    items.set(thisIndex, draggedItem);

                    List<Task> itemsCopy = new ArrayList<>(getListView().getItems());
                    getListView().getItems().setAll(itemsCopy);

                    success = true;
                }

                e.setDropCompleted(success);
                e.consume();
            });

            setOnDragDone(DragEvent::consume);
        }

        private @NotNull ContextMenu createContextMenu() {
            final var contextMenu = new ContextMenu();

            final var editTaskMenuItem = new MenuItem("Edit Task");
            editTaskMenuItem.setOnAction(e -> {
                TaskEditor.getInstance().openTaskEditor(getScene().getWindow(), this.getItem());
            });

            final var deleteTaskMenuItem = new MenuItem("Delete");
            deleteTaskMenuItem.setOnAction(e -> this.getListView().getItems().remove(this.getListView().getSelectionModel().getSelectedItem()));

            contextMenu.getItems().addAll(editTaskMenuItem, deleteTaskMenuItem);
            return contextMenu;
        }

        private @NotNull TextField createTextField() {
            final var descriptionField = new TextField();

            // save description on enter.
            descriptionField.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ENTER) {
                    descriptionField.setEditable(false);
                    descriptionField.setMouseTransparent(true);
                    this.getItem().descriptionProperty().set(descriptionField.getText());
                }
            });

            // save description if clicked off
            descriptionField.setOnMouseClicked(e -> {
                this.getItem().descriptionProperty().set(descriptionField.getText());
                this.getListView().getSelectionModel().select(this.getItem());
            });

            descriptionField.setStyle("-fx-font-weight: 600;");
            descriptionField.setPrefWidth(350);

            return descriptionField;
        }

        @Override
        protected void updateItem(Task task, boolean empty) {
            super.updateItem(task, empty);

            if (task == null || empty) {
                this.descriptionField.setText(null);
                this.setGraphic(null);
            } else {
                Platform.runLater(descriptionField::requestFocus);

                this.descriptionField.setText(task.descriptionProperty().get());
                this.checkBox.setSelected(task.isCompletedProperty().get());
                this.setGraphic(this.root);
            }
        }
    }
}
