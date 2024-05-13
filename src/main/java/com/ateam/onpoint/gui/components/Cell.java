package com.ateam.onpoint.gui.components;

import com.ateam.onpoint.core.Task;
import com.ateam.onpoint.core.TaskDatabase;
import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Cell extends ListCell<Task> {
    protected final HBox root = new HBox();
    protected final Label description = new Label();
    protected final ContextMenu contextMenu = new ContextMenu();
    protected final CheckBox checkBox = new CheckBox();
    protected final Label dateLabel = new Label("yyyy/mm/dd");
    protected final Label timeLabel = new Label("hh:mm");
    protected final Label durationLabel = new Label("00h 00m");

    public Cell() {
        super();

        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        setAlignment(Pos.CENTER);

        this.checkBox.setOnMouseClicked(e -> {
            this.getItem().isCompletedProperty().set(this.checkBox.isSelected());
            final var transition = new FadeTransition(Duration.millis(250), this);
            transition.setFromValue(1);
            transition.setToValue(0);
            transition.setOnFinished(e2 -> {
                TaskDatabase.getInstance().removeTask(this.getItem());
                this.setOpacity(1);
            });
            transition.play();
        });

        setupContextMenu();
        setupDragAndDrop();

        this.root.setOnContextMenuRequested(e -> this.contextMenu.show(this.root.getScene().getWindow(), e.getScreenX(), e.getScreenY()));
    }

    protected void setupDragAndDrop() {
        this.root.setOnDragDetected(e -> {
            if (getItem() == null) {
                return;
            }

            final var dragBoard = startDragAndDrop(TransferMode.MOVE);
            final var clipBoard = new ClipboardContent();
            clipBoard.putString(this.getItem().descriptionProperty().get());
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
                        .filter(c -> c.descriptionProperty().get().compareTo(dragboard.getString()) == 0)
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

    protected void setupContextMenu() {
        final var editTaskMenuItem = new MenuItem("Edit Task");
        editTaskMenuItem.setOnAction(e -> {
            TaskEditor.getInstance().openTaskEditor(getScene().getWindow(), this.getItem());
        });

        final var deleteTaskMenuItem = new MenuItem("Delete");
        deleteTaskMenuItem.setOnAction(e -> TaskDatabase.getInstance().removeTask(this.getItem()));

        this.contextMenu.getItems().addAll(editTaskMenuItem, deleteTaskMenuItem);
    }
}
