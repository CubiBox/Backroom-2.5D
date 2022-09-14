package fr.cubibox.jeux.io;

import fr.cubibox.jeux.game.Player;
import fr.cubibox.jeux.engine.Engine;
import fr.cubibox.jeux.engine.Ray;
import fr.cubibox.jeux.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


public class GraphicSwg2D extends JFrame{
    public GraphicSwg2D() {
        super("DOOM");
        WindowListener l = new WindowAdapter() {
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        };

        addWindowListener(l);
        setSize(800,800);
        ImageIcon image = new ImageIcon("icon.gif");
        setIconImage(image.getImage());
        setVisible(true);
    }
    public void paint(Graphics g) {
        super.paint(g);
        drawRect(g);
    }
    private void drawRect(Graphics g){
        Engine e = main.getEngine();
        int n = 0;
        Stroke stroke1 = new BasicStroke(6f);
        g.setColor(Color.DARK_GRAY);
        int[][] map = e.getRoom().getMapContent();
        for (int i=0; i < 8; i ++) {
            for (int j = 0; j < 8; j++) {
                if (map[i][j] == 1) {
                    g.fillRect(80 * j, 80 * i + 20, 80, 80);
                }
            }
        }
/*
        for (Ray r : e.getRays()) {
            float x = r.getColPos().getX() ;
            float y = r.getColPos().getY()  + 20 ;
            //g.fillRect((int) x, (int)y, 2, 2);
            g.setColor(r.getC());
            g.drawLine(
                    (int)e.getPlayer().getX()*80,
                    (int)e.getPlayer().getY()*80+20,
                    (int)x,
                    (int)y
            );
        }

 */
        Ray r = new Ray(e.getPlayer().getAngle(), e.getPlayer(), e.getRoom());
        r.calcDRay(e.getPlayer().getAngle(), e.getPlayer(), e.getRoom());

        g.setColor(Color.blue);
        g.drawLine(
                (int)e.getPlayer().getX()*80,
                (int)e.getPlayer().getY()*80+20,
                (int)r.getColPos().getX(),
                (int)r.getColPos().getY()+20
            );

        g.setColor(Color.DARK_GRAY);
        g.fillRect((int)e.getPlayer().getX()*80-10, (int)e.getPlayer().getY()*80 + 10, 20, 20);
    }
    public static boolean JHere(int x, int y, Player p){
        if (x == p.getX() && y == p.getY())
            return true;
        return false;
    }
    public static void print3Dview(Engine e){
        for (int y = 0; y< Engine.HEIGHT; y++){
            for (int x = 0; x< Engine.WIDTH; x++){
                //System.out.print(e.getRays().get(x).toString());
                if((1+e.getRays().get(x).getdRay())*20 < y)
                    System.out.print("\033[47m  \033[0m");
                else
                    System.out.print("  ");
            }
            System.out.println();
        }
    }
}
