package com.ateam.onpoint.gui;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

/**
 * The MainLayout class contains the content view and sidebar
 */
public class Dashboard extends BorderPane {
    private final ApplicationView applicationView = new ApplicationView();
    private final Sidebar sidebar = new Sidebar(applicationView);
    private final StackPane contentView = new StackPane();

    /**
     * Construct the dashboard and all of its components
     */
    public Dashboard() {
        super();

        sidebar.setPrefWidth(OnPointGUI.SIDEBAR_WIDTH);

        this.setLeft(sidebar);
        this.setCenter(contentView);
    }
}
