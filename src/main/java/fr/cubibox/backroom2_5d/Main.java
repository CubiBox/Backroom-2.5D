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
    private static final Keyboard keyboard = new Keyboard();
    public static float windowWidth = 720;
    public static float windowHeight = 480;
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
        return (float) ((windowWidth * (y) / (engine.getMap().getSize())));
    }

    public static float toScreenX(double x, int mapSize) {
        return (float) (windowHeight * (x) / mapSize);
    }

    public static float toScreenY(double y, int mapSize) {
        return (float) ((windowWidth * (y) / mapSize));
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

        //update windows size when resized
        primaryStage.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> {
            if (newSceneWidth.doubleValue() < Main.getEngine().getRayCount()) {
                Main.getEngine().setRayCount((int) newSceneWidth.doubleValue());
            }
            windowWidth = (float) newSceneWidth.doubleValue();
            Engine.screenDistance = windowWidth / 2;
            System.out.println("Width: " + newSceneWidth + " Height: " + windowHeight + " RayCount: " + Main.getEngine().getRayCount() + " ScreenDistance: " + Engine.screenDistance);
        });

        primaryStage.heightProperty().addListener((observableValue, oldSceneHeight, newSceneHeight) -> {
            windowHeight = (float) newSceneHeight.doubleValue();
            System.out.println("Width: " + windowWidth + " Height: " + newSceneHeight + " RayCount: " + Main.getEngine().getRayCount() + " ScreenDistance: " + Engine.screenDistance);
        });

        primaryStage.setScene(scene);
        primaryStage.setWidth(windowWidth);
        primaryStage.setHeight(windowHeight);
        primaryStage.show();

        getEngine().start();
    }
}