package fr.cubibox.backroom2_5d;

import fr.cubibox.backroom2_5d.engine.Engine;


import fr.cubibox.backroom2_5d.game.Map;
import fr.cubibox.backroom2_5d.entities.Player;

import fr.cubibox.backroom2_5d.utils.ImageUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static fr.cubibox.backroom2_5d.engine.Ray.RADIAN_PI_2;

public class Main extends Application {
    private static Engine engine;
    public final static float DIML = 600;
    public final static float DIMC = 600;


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view2D.fxml"));
        primaryStage.setTitle("Backroom2D");
        //primaryStage.getIcons().add(new Image(new BufferedInputStream(main.class.getResourceAsStream("images/icon.png"))));
        Scene scene = new Scene(root);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent e) -> {
            Player p = Main.getEngine().getPlayer();
            switch (e.getCode().getCode()){
                case 40 :
                    p.setX(p.getX() + (float) Math.cos(p.getAngle() * RADIAN_PI_2) * -0.1f);
                    p.setY(p.getY() + (float) Math.sin(p.getAngle() * RADIAN_PI_2) * -0.1f);
                    break;
                case 38 :
                    p.setX(p.getX() + (float) Math.cos(p.getAngle() * RADIAN_PI_2) * 0.1f);
                    p.setY(p.getY() + (float) Math.sin(p.getAngle() * RADIAN_PI_2) * 0.1f);
                    break;
            }

            switch (e.getCode().getCode()){
                case 39 :
                    p.setAngle(p.getAngle() + 1f);
                    break;
                case 37 :
                    p.setAngle(p.getAngle() - 1f);
                    break;
            }

            System.out.println("Player angle : " + p.getAngle() + " " + e.getCode().getCode());

        });

        primaryStage.setScene(scene);
        primaryStage.setWidth(DIML);
        primaryStage.setHeight(DIMC);
        primaryStage.show();

        getEngine().start();
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
        Map map = Map.importMap(new File("map1.map"));
        ImageUtils.writeImage("test.png", ImageUtils.placeHolder());
        engine = new Engine(
                10,
                new Player(2, 9, 90),
                map
        );

        launch(args);
    }

    public static float toScreenX(double x){
        return (float)(DIMC*(x)/(engine.getMap().getSize()));
    }

    public static float toScreenY(double y){
        return (float) ((DIML*(y)/(engine.getMap().getSize())));
    }

    public static float toScreenX(double x, int mapSize){
        return (float)(DIMC*(x)/mapSize);
    }

    public static float toScreenY(double y, int mapSize){
        return (float) ((DIML*(y)/mapSize));
    }

    public static Engine getEngine() {
        return engine;
    }

    public static void setEngine(Engine engine) {
        Main.engine = engine;
    }
}