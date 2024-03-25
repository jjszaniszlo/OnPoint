package com.ateam.onpoint.gui;

import javafx.scene.layout.BorderPane;

/**
 * The MainLayout class contains the content view and sidebar
 */
public class Dashboard extends BorderPane {
    private final ContentView contentView = new ContentView();
    private final Sidebar sidebar = new Sidebar(contentView);

    /**
     * Construct the dashboard and all of its components
     */
    public Dashboard() {
        super();

        sidebar.setPrefWidth(OnPointGUI.SIDEBAR_WIDTH);

        this.setLeft(sidebar);
    }
}
