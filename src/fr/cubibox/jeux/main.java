package fr.cubibox.jeux;

import fr.cubibox.jeux.engine.Engine;
import fr.cubibox.jeux.engine.Ray;
import fr.cubibox.jeux.game.Map;
import fr.cubibox.jeux.game.Player;
import fr.cubibox.jeux.io.GraphicSwg;
import fr.cubibox.jeux.io.GraphicSwg2D;

import javax.swing.*;
import java.io.File;

public class main {
    public final static String RESOURCE_PATH = "src/resources";
    private static Engine engine;


    public static void main(String[] args) throws InterruptedException {
        Map map = new Map(new File(RESOURCE_PATH + "\\map1.map"));
        engine = new Engine(
                map,
                new Player(2f,2f, 90f),
                1600,
                45
            );
        engine.getPlayer().setAngle(0);


        JFrame mapFrame = new GraphicSwg2D();
        JFrame frame = new GraphicSwg();

        //new JFrame window
        Thread view3D = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    frame.repaint();
                    try{Thread.sleep(100l);}
                    catch(Exception e){ }
                }
            }
        });
        view3D.start();

        Thread view2D = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    mapFrame.repaint();
                    try{Thread.sleep(100l);}
                    catch(Exception e){ }
                }
            }
        });
        view2D.start();

        while (true){
            engine.setRays(Ray.setRays(engine.getPlayer(),engine.getRoom()));
            Thread.sleep(100l);
        }


        //Graphic.affTab(map.getMapContent());
//        for (int i = 0; i < 360; i ++) {
//            engine.getPlayer().setAngle((float)i+135);
//            Graphic.print3Dview(engine);
//            Thread.sleep(10l);
//        }

        //Graphic.print2Dmap(map, engine.getPlayer());
    }

    public static Engine getEngine() {
        return engine;
    }

    public static void setEngine(Engine engine) {
        main.engine = engine;
    }
}