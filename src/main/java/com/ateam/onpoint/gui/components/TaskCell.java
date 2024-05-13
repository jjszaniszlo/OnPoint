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

public class TaskCell extends Cell {
    public TaskCell() {
        super();

        ImageView clockImage = new ImageView(Objects.requireNonNull(getClass().getResource("/img/clock_48.png")).toString());
        clockImage.setFitWidth(20);
        clockImage.setFitHeight(20);

        ImageView arrowImage = new ImageView(Objects.requireNonNull(getClass().getResource("/img/arrow_50.png")).toString());
        arrowImage.setFitWidth(20);
        arrowImage.setFitHeight(20);

        this.description.setStyle("-fx-font-weight: 600;");
        this.description.setPrefWidth(OnPointGUI.CONTENT_VIEW_WIDTH * 0.4);
        this.description.setMinWidth(Control.USE_PREF_SIZE);
        this.description.setMaxWidth(Control.USE_PREF_SIZE);

        this.description.setWrapText(true);

        this.root.setAlignment(Pos.CENTER_LEFT);
        this.root.prefHeight(15);
        this.root.prefWidth(450);

        this.dateLabel.setPadding(new Insets(0, 5, 0,0));

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
                this.dateLabel,
                new Spacer(5),
                this.timeLabel
        );
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
                        "%d/%02d/%02d",
                        date.getYear(), date.getMonthValue(), date.getDayOfMonth())
                );
                this.timeLabel.setText(String.format(
                        "%02d:%02d",
                        time.getHour(), time.getMinute()
                ));
            }

            final var duration = this.getItem().durationProperty().get();
            final int minutes = duration % 60;
            final int hours = duration / 60;
            this.durationLabel.setText(String.format("%02dh %02dm", hours, minutes));

            this.setGraphic(this.root);
        }
    }
}
