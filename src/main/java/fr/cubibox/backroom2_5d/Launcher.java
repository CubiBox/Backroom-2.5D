package fr.cubibox.backroom2_5d;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Launcher extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Test");

        URL url = getClass().getResource("scenes/mapdebug.fxml");

        if (url != null) {
            try {
                Parent root = FXMLLoader.load(url);
                Scene scene = new Scene(root);

                primaryStage.setOnCloseRequest(event -> {
                    System.out.println("Closing...");
                    System.exit(0);
                });

                primaryStage.setScene(scene);
                primaryStage.setWidth(720);
                primaryStage.setHeight(480);
                primaryStage.show();


            }
            catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Impossible de trouver le fichier.");
            System.exit(-1);
        }
    }
}
