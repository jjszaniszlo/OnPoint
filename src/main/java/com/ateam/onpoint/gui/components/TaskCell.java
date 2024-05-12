package com.ateam.onpoint.gui.components;

import com.ateam.onpoint.core.Task;
import com.ateam.onpoint.core.TaskDatabase;
import com.ateam.onpoint.gui.OnPointGUI;
import javafx.geometry.Insets;
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
    private final HBox root = new HBox();
    private final Label description = new Label();
    private final CheckBox checkBox = new CheckBox();
    private final Label dateLabel = new Label("yyyy/mm/dd hh:mm");
    private final Label durationLabel = new Label("00h 00m");
    private final ContextMenu contextMenu;

    public TaskCell() {
        super();

        ImageView clockImage = new ImageView(Objects.requireNonNull(getClass().getResource("/img/clock_48.png")).toString());
        clockImage.setFitWidth(20);
        clockImage.setFitHeight(20);

        ImageView arrowImage = new ImageView(Objects.requireNonNull(getClass().getResource("/img/arrow_50.png")).toString());
        arrowImage.setFitWidth(20);
        arrowImage.setFitHeight(20);

        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        setAlignment(Pos.CENTER);

        this.checkBox.setOnMouseClicked(e -> this.getItem().isCompletedProperty().set(this.checkBox.isSelected()));

        this.description.setStyle("-fx-font-weight: 600;");
        this.description.setPrefWidth(OnPointGUI.CONTENT_VIEW_WIDTH * 0.4);
        this.description.setMinWidth(Control.USE_PREF_SIZE);
        this.description.setMaxWidth(Control.USE_PREF_SIZE);

        this.description.setWrapText(true);

        this.contextMenu = createContextMenu();

        this.root.setAlignment(Pos.CENTER_LEFT);
        this.root.prefHeight(15);
        this.root.prefWidth(450);

        this.root.setOnContextMenuRequested(e -> this.contextMenu.show(this.root.getScene().getWindow(), e.getScreenX(), e.getScreenY()));

        this.dateLabel.setPadding(new Insets(0, 5, 0,0));

        setupDragAndDrop();

        this.root.getChildren().addAll(
                this.checkBox,
                this.description,
                new Spacer(),
                durationLabel,
                new Spacer(5),
                arrowImage,
                new Spacer(5),
                clockImage,
                new Spacer(5),
                this.dateLabel
        );
    }

    private void setupDragAndDrop() {
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

    private @NotNull ContextMenu createContextMenu() {
        final var contextMenu = new ContextMenu();

        final var editTaskMenuItem = new MenuItem("Edit Task");
        editTaskMenuItem.setOnAction(e -> {
            TaskEditor.getInstance().openTaskEditor(getScene().getWindow(), this.getItem());
        });

        final var deleteTaskMenuItem = new MenuItem("Delete");
        deleteTaskMenuItem.setOnAction(e -> TaskDatabase.getInstance().removeTask(this.getItem()));

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

            final var date = this.getItem().startDateProperty().get();
            final var time = this.getItem().startTimeProperty().get();

            if (date != null && time != null) {
                this.dateLabel.setText(String.format(
                        "%d/%02d/%02d %02d:%02d",
                        date.getYear(), date.getMonthValue(), date.getDayOfMonth(), time.getHour(), time.getMinute())
                );
            }

            final var duration = this.getItem().durationProperty().get();
            if (duration != null) {
                final int minutes = duration % 60;
                final int hours = duration / 60;
                this.durationLabel.setText(String.format("%02dh %02dm", hours, minutes));
            }

            this.setGraphic(this.root);
        }
    }
}
