package com.ateam.onpoint;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
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
        stage.setTitle("OnPoint");

        Button btn = new Button();
        btn.setText("Say 'Hello World!'");
        btn.setOnAction(actionEvent -> System.out.println("Hello World!"));

        StackPane root = new StackPane();
        root.getChildren().add(btn);
        stage.setScene(new Scene(root, 300, 250));
        stage.show();
    }
}
