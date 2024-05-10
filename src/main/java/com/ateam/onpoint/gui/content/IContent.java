package com.ateam.onpoint.gui.content;

import javafx.scene.Parent;

public interface IContent {
    /**
     * retrieves the Scene parent which contains the data to display the content.
     * @return the scene parent
     */
    public Parent getContentView();
}