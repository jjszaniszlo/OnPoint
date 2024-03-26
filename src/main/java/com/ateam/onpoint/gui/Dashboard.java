package com.ateam.onpoint.gui;

import com.ateam.onpoint.gui.content.IContent;
import com.ateam.onpoint.gui.content.TaskView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * The MainLayout class contains the content view and sidebar
 */
public class Dashboard extends BorderPane {
    private final OnPointGUI onPointGUI;
    private final Sidebar sidebar;
    private final Pane content = new Pane();

    /**
     * Construct the dashboard and all of its components
     */
    public Dashboard(OnPointGUI onPointGUI) {
        super();

        this.onPointGUI = onPointGUI;
        this.sidebar = new Sidebar(onPointGUI);

        sidebar.setPrefWidth(WindowPane.SIDEBAR_WIDTH);

        this.setLeft(sidebar);

        // temporary hard code since I haven't yet decided how to do content switching
        this.setCenter(new TaskView().getContentView());
    }

    private void loadContentView(IContent content) throws Exception {
        try {
            this.content.getChildren().setAll(content.getContentView());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
