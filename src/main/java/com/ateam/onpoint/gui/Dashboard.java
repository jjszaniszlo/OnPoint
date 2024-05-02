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
        this.onPointGUI.navigate(OnPointGUI.DEFAULT_CONTENT);
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

            System.out.println("Loaded Content!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
