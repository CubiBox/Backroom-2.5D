package fr.cubibox.backroom2_5d;

import fr.cubibox.backroom2_5d.engine.Engine;
import fr.cubibox.backroom2_5d.entities.Player;
import fr.cubibox.backroom2_5d.game.Map;
import fr.cubibox.backroom2_5d.io.Keyboard;
import fr.cubibox.backroom2_5d.utils.ImageUtils;
import fr.cubibox.backroom2_5d.utils.MapUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class Main extends Application {
    public final static float windowsWidth = 720;
    public final static float windowHeight = 480;
    private static final Keyboard keyboard = new Keyboard();
    private static Engine engine;

    public static void main(String[] args) throws URISyntaxException, IOException {
        Map map = MapUtils.importMap(new File("map1.map"));
        ImageUtils.writeImage("test.png", ImageUtils.placeHolder());
        engine = new Engine(
                360,
                new Player(12, 11, 0),
                map
        );

        launch(args);
    }

    public static float toScreenX(double x) {
        return (float) (windowHeight * (x) / (engine.getMap().getSize()));
    }

    public static float toScreenY(double y) {
        return (float) ((windowsWidth * (y) / (engine.getMap().getSize())));
    }

    public static float toScreenX(double x, int mapSize) {
        return (float) (windowHeight * (x) / mapSize);
    }

    public static float toScreenY(double y, int mapSize) {
        return (float) ((windowsWidth * (y) / mapSize));
    }

    public static Engine getEngine() {
        return engine;
    }

    public static void setEngine(Engine engine) {
        Main.engine = engine;
    }

    public static Keyboard getKeyboard() {
        return keyboard;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view3D.fxml"));
        primaryStage.setTitle("Backroom2D");
        Scene scene = new Scene(root);

        primaryStage.setOnCloseRequest(event -> {
            System.out.println("Closing...");
            engine.stop();
            System.exit(0);
        });

        scene.addEventHandler(KeyEvent.KEY_PRESSED, keyboard::keyPressed);
        scene.addEventHandler(KeyEvent.KEY_RELEASED, keyboard::keyReleased);

        primaryStage.setScene(scene);
        primaryStage.setWidth(windowsWidth);
        primaryStage.setHeight(windowHeight);
        primaryStage.show();

        getEngine().start();
    }
}