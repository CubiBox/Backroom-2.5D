//package fr.cubibox.backroom2_5d.io;
//
//import fr.cubibox.backroom2_5d.engine.Engine;
//import fr.cubibox.backroom2_5d.main;
//
//import java.awt.*;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseMotionListener;
//
//public class Mouse implements MouseMotionListener {
//
//    @Override
//    public void mouseDragged(MouseEvent e) {
//
//    }
//
//    @Override
//    public void mouseMoved(MouseEvent e) {
//        int x = (e.getComponent().getX() + e.getX());
//        if (x < 800){
//            main.getEngine().getPlayer().setAngle(main.getEngine().getPlayer().getAngle() - (800 - e.getX())*0.2f);
//        }
//        if (x > 800){
//            main.getEngine().getPlayer().setAngle(main.getEngine().getPlayer().getAngle() + (e.getX()-800)*0.2f);
//        }
//
//        Robot r = null;
//        try {r = new Robot();}
//        catch (AWTException ex) {throw new RuntimeException(ex);}
//        r.mouseMove(e.getComponent().getX() + Engine.WIDTH/2,e.getComponent().getY() + Engine.HEIGHT*20/2);
//
//        try {
//            Thread.sleep(16l);
//        } catch (InterruptedException ex) {
//            throw new RuntimeException(ex);
//        }
//    }
//}
