package com.ateam.onpoint.gui;

import com.ateam.onpoint.gui.content.IContent;
import javafx.animation.FadeTransition;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.Objects;

/**
 * The MainLayout class contains the content view and sidebar
 */
public class Dashboard extends BorderPane {
    private final OnPointGUI onPointGUI;
    private final Sidebar sidebar;
    private final StackPane contentLayer = new StackPane();
    /**
     * Construct the dashboard and all of its components
     */
    public Dashboard(OnPointGUI onPointGUI) {
        super();

        this.onPointGUI = onPointGUI;
        this.sidebar = new Sidebar(onPointGUI);

        sidebar.setPrefWidth(WindowPane.SIDEBAR_WIDTH);

        loadListeners();

        sidebar.select(OnPointGUI.DEFAULT_CONTENT);

        this.setLeft(sidebar);
        this.setCenter(contentLayer);
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
            final IContent prevContent = (IContent) this.contentLayer.getChildren().stream().filter(c -> c instanceof IContent).findFirst().orElse(null);
            final IContent nextContent = contentClass.getDeclaredConstructor().newInstance();

            if (getScene() == null) {
                this.contentLayer.getChildren().add(nextContent.getContentView());
                return;
            }

            Objects.requireNonNull(prevContent);

            this.contentLayer.getChildren().remove(prevContent);
            this.contentLayer.getChildren().add(nextContent.getContentView());
            var transition = new FadeTransition(Duration.millis(250), nextContent.getContentView());
            transition.setFromValue(0);
            transition.setToValue(1);
            transition.play();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
