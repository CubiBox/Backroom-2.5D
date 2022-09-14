package fr.cubibox.jeux;

import javax.swing.*;
import java.io.File;
import java.lang.management.GarbageCollectorMXBean;
import java.util.ArrayList;

public class main {
    public final static String RESOURCE_PATH = "src/resources";
    private static Engine engine;

    public static void main(String[] args) throws InterruptedException {
        Map map = new Map(new File(RESOURCE_PATH + "\\map1.map"));
        engine = new Engine(
                map,
                new Player(2f,2f, 90f),
                320,
                45
            );
        engine.getPlayer().setAngle(0);


        JFrame mapFrame = new GraphicSwg2D();
        Thread view2D = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    mapFrame.repaint();
                    try{Thread.sleep(16l);}
                    catch(Exception e){ }
                }
            }
        });
        view2D.start();



        //new JFrame window
        JFrame frame = new GraphicSwg();
        Thread view3D = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    frame.repaint();
                    try{Thread.sleep(16l);}
                    catch(Exception e){ }
                }
            }
        });
        view3D.start();




        //System.out.print(engine.getRays());


        while (true){
            engine.setRays(Ray.setRays(engine.getPlayer(),engine.getRoom()));
            Thread.sleep(7l);
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