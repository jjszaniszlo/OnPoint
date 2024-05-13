package com.ateam.onpoint.gui.components;

import com.ateam.onpoint.core.Task;
import com.ateam.onpoint.core.TaskDatabase;
import javafx.geometry.Pos;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Cell extends ListCell<Task> {
    protected final HBox root = new HBox();
    protected final Label description = new Label();
    protected final ContextMenu contextMenu = new ContextMenu();
    protected final CheckBox checkBox = new CheckBox();

    public Cell() {
        super();

        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        setAlignment(Pos.CENTER);

        setupContextMenu();
        setupDragAndDrop();
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

    protected @NotNull void setupContextMenu() {
        final var editTaskMenuItem = new MenuItem("Edit Task");
        editTaskMenuItem.setOnAction(e -> {
            TaskEditor.getInstance().openTaskEditor(getScene().getWindow(), this.getItem());
        });

        final var deleteTaskMenuItem = new MenuItem("Delete");
        deleteTaskMenuItem.setOnAction(e -> TaskDatabase.getInstance().removeTask(this.getItem()));

        this.contextMenu.getItems().addAll(editTaskMenuItem, deleteTaskMenuItem);
    }
}
