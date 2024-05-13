package com.ateam.onpoint.gui.components;

import com.ateam.onpoint.core.Task;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class ScheduleCell extends Cell {
    public ScheduleCell() {
        super();

        ImageView clockImage = new ImageView(Objects.requireNonNull(getClass().getResource("/img/clock_48.png")).toString());
        clockImage.setFitWidth(20);
        clockImage.setFitHeight(20);

        ImageView arrowImage = new ImageView(Objects.requireNonNull(getClass().getResource("/img/arrow_50.png")).toString());
        arrowImage.setFitWidth(20);
        arrowImage.setFitHeight(20);

        this.description.setMaxWidth(120);

        this.root.setAlignment(Pos.CENTER_LEFT);
        this.root.prefHeight(40);
        this.root.prefWidth(200);

        final var timeBody = new HBox();
        timeBody.getChildren().addAll(clockImage, new Spacer(5), this.timeLabel);

        final var descriptionTimeBody = new VBox();
        descriptionTimeBody.getChildren().addAll(this.description, timeBody);

        this.root.getChildren().addAll(this.checkBox, descriptionTimeBody);
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

            this.setGraphic(this.root);
        }
    }
}
