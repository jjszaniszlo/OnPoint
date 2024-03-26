package com.ateam.onpoint.gui;

import com.ateam.onpoint.gui.content.IContent;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * The MainLayout class contains the content view and sidebar
 */
public class Dashboard extends BorderPane {
    private final ApplicationView applicationView = new ApplicationView();
    private final Sidebar sidebar = new Sidebar(applicationView);
    private final Pane content = new Pane();

    /**
     * Construct the dashboard and all of its components
     */
    public Dashboard() {
        super();

        sidebar.setPrefWidth(OnPointGUI.SIDEBAR_WIDTH);

        this.setLeft(sidebar);
        this.setCenter(content);
    }

    private void loadContentView(Class<? extends IContent> content) throws Exception {
        try {
            final Parent contentView = content.getDeclaredConstructor().newInstance().getContentView();
            this.content.getChildren().setAll(contentView);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
