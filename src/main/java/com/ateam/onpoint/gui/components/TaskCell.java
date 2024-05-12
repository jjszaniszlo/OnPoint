package com.ateam.onpoint.gui.components;

import com.ateam.onpoint.core.Task;
import javafx.geometry.Pos;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TaskCell extends ListCell<Task> {
    private ImageView clockImage = new ImageView(Objects.requireNonNull(getClass().getResource("/img/clock_48.png")).toString());
    private final HBox root = new HBox();
    private final Label description = new Label();
    private final CheckBox checkBox = new CheckBox();
    private final Label dateLabel = new Label("dd/mm/yyyy hh:mm");
    private final ContextMenu contextMenu;

    public TaskCell() {
        super();

        this.clockImage.setFitWidth(20);
        this.clockImage.setFitHeight(20);

        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        setAlignment(Pos.CENTER);

        this.checkBox.setOnMouseClicked(e -> this.getItem().isCompletedProperty().set(this.checkBox.isSelected()));

        this.description.setStyle("-fx-font-weight: 600;");
        this.description.setMaxWidth(200);

        this.contextMenu = createContextMenu();

        this.root.setAlignment(Pos.CENTER_LEFT);
        this.root.prefHeight(15);
        this.root.prefWidth(450);

        this.root.setOnContextMenuRequested(e -> this.contextMenu.show(this.root.getScene().getWindow(), e.getScreenX(), e.getScreenY()));

        setupDragAndDrop();

        this.root.getChildren().addAll(this.checkBox, this.description, new Spacer(), this.clockImage, new Spacer(5), this.dateLabel);
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

    @Override
    protected void updateItem(Task task, boolean empty) {
        super.updateItem(task, empty);

        if (task == null || empty) {
            this.description.setText(null);
            this.setGraphic(null);
        } else {
            this.description.setText(task.descriptionProperty().get());
            this.checkBox.setSelected(task.isCompletedProperty().get());

            this.setGraphic(this.root);
        }
    }
}
