package com.ateam.onpoint;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
 * The class which the application is launched from.
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
        Application.setUserAgentStylesheet(getClass().getResource("/css/cupertino-dark.css").toString());
        stage.setTitle("OnPoint");

        Parent root = FXMLLoader.load(getClass().getResource("/main_layout.fxml"));
        Scene rootScene = new Scene(root, 840, 640);

        stage.setScene(rootScene);
        stage.show();
    }
}
