package fr.cubibox.backroom2_5d;

import fr.cubibox.backroom2_5d.engine.OldEngine;


import fr.cubibox.backroom2_5d.game.Map;
import fr.cubibox.backroom2_5d.engine.Player;

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
    private static OldEngine engine;
    public static float DIML = 600;
    public static float DIMC = 600;
    public static float xSize = 32.0f;
    public static float ySize = xSize;


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view2D.fxml"));
        primaryStage.setTitle("Backroom2D");
        //primaryStage.getIcons().add(new Image(new BufferedInputStream(main.class.getResourceAsStream("images/icon.png"))));
        Scene scene = new Scene(root);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent e) -> {
            Player p = Main.getEngine().getPlayer();
            System.out.println(e.getCode().getCode());
            switch (e.getCode().getCode()){
                case 90 :
                case 38 :
                    p.Avancer(0.2f);
                    break;
                case 39 : p.setAngle(p.getAngle() + 10f);
                    break;
                case 37 : p.setAngle(p.getAngle() - 10f);
                    break;
            }
        });

        primaryStage.setScene(scene);
        primaryStage.setWidth(DIML);
        primaryStage.setHeight(DIMC);
        primaryStage.show();
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
        Map map = Map.importMap(new File("map1.map"));
        engine = new OldEngine(
                map,
                new Player(2f,2f, 90f),
                20,
                45
        );
        engine.getPlayer().setAngle(45f);

        launch(args);
    }

    public static float toScreenX(double x){
        return (float)(DIMC*(x)/(xSize));
    }

    public static float toScreenY(double y){
        return (float) ((DIML*(y)/(ySize)));
    }

    public static float toPlotX(double scrX){
        return (float) ((scrX/DIMC)*(xSize));
    }

    public static float toPlotY(double scrY){
        return (float) ((scrY/DIML)*(ySize));
    }

    public static OldEngine getEngine() {
        return engine;
    }

    public static void setEngine(OldEngine engine) {
        Main.engine = engine;
    }

    public static float getDIML() {
        return DIML;
    }

    public static void setDIML(float DIML) {
        Main.DIML = DIML;
    }

    public static float getDIMC() {
        return DIMC;
    }

    public static void setDIMC(float DIMC) {
        Main.DIMC = DIMC;
    }

    public static float getxSize() {
        return xSize;
    }

    public static void setxSize(float xSize) {
        Main.xSize = xSize;
    }

    public static float getySize() {
        return ySize;
    }

    public static void setySize(float ySize) {
        Main.ySize = ySize;
    }
}