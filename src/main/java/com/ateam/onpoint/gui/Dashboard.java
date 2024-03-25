package com.ateam.onpoint.gui;

import javafx.scene.layout.BorderPane;

/*
 * The MainLayout class contains the content view and sidebar
 */
public class Dashboard extends BorderPane {
    private final ContentView contentView = new ContentView();
    private final Sidebar sidebar = new Sidebar(contentView);
    public Dashboard() {
        super();

        sidebar.setMinWidth(177);
        sidebar.setMaxWidth(177);

        this.setLeft(sidebar);
    }
}
