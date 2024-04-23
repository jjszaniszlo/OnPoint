package com.ateam.onpoint;

import com.ateam.onpoint.gui.WindowPane;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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

        stage.setScene(new Scene(guiHandle, guiHandle.MIN_WINDOW_WIDTH, guiHandle.MIN_WINDOW_HEIGHT + 200));
        stage.setMinWidth(guiHandle.MIN_WINDOW_WIDTH);
        stage.setMinHeight(guiHandle.MIN_WINDOW_HEIGHT);

        Platform.runLater(() -> {
            stage.show();
            stage.requestFocus();
        });
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