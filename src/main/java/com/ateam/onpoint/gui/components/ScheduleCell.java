package com.ateam.onpoint.gui.components;

import com.ateam.onpoint.core.Task;
import javafx.geometry.Pos;

public class ScheduleCell extends Cell {
    public ScheduleCell() {
        super();

        this.root.setAlignment(Pos.CENTER_LEFT);
        this.root.prefHeight(30);
        this.root.prefWidth(450);

        this.root.getChildren().addAll(this.checkBox, this.description);
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
