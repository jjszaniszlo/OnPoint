package com.ateam.onpoint.gui;

import com.ateam.onpoint.gui.content.IContent;
import javafx.scene.layout.BorderPane;

/**
 * The MainLayout class contains the content view and sidebar
 */
public class Dashboard extends BorderPane {
    private final OnPointGUI onPointGUI;
    private final Sidebar sidebar;
    /**
     * Construct the dashboard and all of its components
     */
    public Dashboard(OnPointGUI onPointGUI) {
        super();

        this.onPointGUI = onPointGUI;
        this.sidebar = new Sidebar(onPointGUI);

        sidebar.setPrefWidth(WindowPane.SIDEBAR_WIDTH);

        loadListeners();

        this.setLeft(sidebar);
        sidebar.select(OnPointGUI.DEFAULT_CONTENT);
    }

    private void loadListeners() {
        onPointGUI.getContentProperty().addListener((obs, old, val) -> {
            if (val != null) {
                loadContent(val);
            }
        });
    }

    private void loadContent(Class<? extends IContent> contentClass) {
        try {
            final IContent content = contentClass.getDeclaredConstructor().newInstance();
            this.setCenter(content.getContentView());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
