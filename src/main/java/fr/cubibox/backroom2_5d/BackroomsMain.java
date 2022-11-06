package fr.cubibox.backroom2_5d;

import fr.cubibox.backroom2_5d.engine.GameEngine;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class BackroomsMain extends Application {
    public static int windowWidth = 720;
    public static int windowHeight = 480;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("scenes/mapdebug.fxml")));
        Parent root = loader.load();

        primaryStage.setTitle("Backroom2D");

        primaryStage.setOnCloseRequest(event -> {
            System.out.println("Closing...");
            GameEngine.getInstance().stop();
            System.exit(0);
        });

        primaryStage.setScene(new Scene(root));
        primaryStage.setWidth(windowWidth);
        primaryStage.setHeight(windowHeight);
        primaryStage.show();

        GameEngine.getInstance().start();
    }
}