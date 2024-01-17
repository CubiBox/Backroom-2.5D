package fr.cubibox.backroom.io;

import fr.cubibox.backroom.engine.OldEngine;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseMotionListener {

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = (e.getComponent().getX() + e.getX());
        if (x < 800){
            //Main.getEngine().getPlayer().setAngle(Main.getEngine().getPlayer().getAngle() - (800 - e.getX())*0.2f);
        }
        if (x > 800){
            //Main.getEngine().getPlayer().setAngle(Main.getEngine().getPlayer().getAngle() + (e.getX()-800)*0.2f);
        }

        Robot r = null;
        try {r = new Robot();}
        catch (AWTException ex) {throw new RuntimeException(ex);}
        r.mouseMove(e.getComponent().getX() + OldEngine.WIDTH/2,e.getComponent().getY() + OldEngine.HEIGHT*20/2);

        try {
            Thread.sleep(16l);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }
}
