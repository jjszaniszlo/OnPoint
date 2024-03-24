package com.ateam.onpoint;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

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
//
//        Button btn = new Button();
//        //button label
//        btn.setText("Say 'Hello World!'");
//        //what the button prints once you click on it
//        btn.setOnAction(actionEvent -> System.out.println("Hello World!"));
//
//        StackPane root = new StackPane();
//        root.getChildren().add(btn);
//        stage.setScene(new Scene(root, 300, 250));
//        stage.show();

        // Load image
        ImageView imageView = getImageView();

        // Create UI components
        Button addButton = new Button("Add Task");
        Button editButton = new Button("Edit Task");
        Button deleteButton = new Button("Delete Task");

        // Create VBox to hold buttons
        VBox buttonBox = new VBox(10);
        buttonBox.getChildren().addAll(addButton, editButton, deleteButton);

        // Create scrollable menu
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(new VBox(addButton, editButton, deleteButton));
        scrollPane.setFitToWidth(true);

        // Create GridPane for layout
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        // Add components to the grid
        gridPane.add(imageView, 0, 0);
        gridPane.add(scrollPane, 1, 0);

        // Set up the scene
        Scene scene = new Scene(gridPane, 600, 400);

        // Set the stage
        stage.setScene(scene);
        stage.setTitle("OnPoint");
        stage.show();
    }

    private static ImageView getImageView() {
        Image image = new Image("https://www.adobe.com/express/create/planner/media_106a0503fdb74abdae8977bf4f8880599043adf6c.png?width=750&format=png&optimize=medium"); // Change URL to your image

        // Create ImageView for the image
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(200); // Adjust the width as needed
        imageView.setPreserveRatio(true);
        return imageView;
    }
}