package com.ateam.onpoint.gui.content;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;

public class ArchiveView extends Pane implements IContent {
    public ArchiveView() {
        super();
    }

    @Override
    public Parent getContentView() {
        return this;
    }
}
