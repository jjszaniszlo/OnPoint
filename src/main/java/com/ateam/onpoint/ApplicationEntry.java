package com.ateam.onpoint;

import com.ateam.onpoint.gui.WindowPane;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.Objects;

import java.net.URL;

/**
 * The ApplicationEntry class is responsible for launching the application and all necessary resources and subsystems.
 */
public class ApplicationEntry extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The start point for the primary JavaFX stage.
     * @param stage the primary stage
     * @throws Exception can throw any kind of exception
     */
    public String agentStylesheet = getClass().getResource("/css/cupertino-dark.css").toString();

    @Override
    public void start(Stage stage) throws Exception {
        Application.setUserAgentStylesheet(agentStylesheet.toString());
        stage.setTitle("OnPoint");

        WindowPane guiHandle = new WindowPane();

        stage.setScene(new Scene(guiHandle, WindowPane.MIN_WINDOW_WIDTH, WindowPane.MIN_WINDOW_HEIGHT + 200));
        stage.setMinWidth(WindowPane.MIN_WINDOW_WIDTH);
        stage.setMinHeight(WindowPane.MIN_WINDOW_HEIGHT);

        Platform.runLater(() -> {
            stage.show();
            stage.requestFocus();
        });
    }
}