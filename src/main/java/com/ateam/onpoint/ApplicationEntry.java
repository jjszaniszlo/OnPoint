package com.ateam.onpoint;

import com.ateam.onpoint.core.TaskDatabase;
import com.ateam.onpoint.gui.WindowPane;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

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
    @Override
    public void start(Stage stage) throws Exception {
        Application.setUserAgentStylesheet(Objects.requireNonNull(getClass().getResource("/css/cupertino-dark.css")).toString());
        stage.setTitle("OnPoint");

        var guiHandle = new WindowPane();

        stage.setScene(new Scene(guiHandle, WindowPane.MIN_WINDOW_WIDTH, WindowPane.MIN_WINDOW_HEIGHT));
        stage.setWidth(WindowPane.MIN_WINDOW_WIDTH);
        stage.setHeight(WindowPane.MIN_WINDOW_HEIGHT);
        stage.setResizable(false);

        stage.setOnCloseRequest(e -> {
            TaskDatabase.getInstance().saveDatabase();
        });

        Platform.runLater(() -> {
            stage.show();
            stage.requestFocus();
        });
    }
}