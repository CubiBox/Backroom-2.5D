package fr.cubibox.jeux.io;

import fr.cubibox.jeux.game.Player;
import fr.cubibox.jeux.engine.Engine;
import fr.cubibox.jeux.engine.Ray;
import fr.cubibox.jeux.main;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;


public class GraphicSwg extends JFrame{
    private int frame;
    private long time;

    public GraphicSwg() {
        super("Backroom");
        WindowListener l = new WindowAdapter() {
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        };
        setTitle("Backroom \t" + frame);
        addWindowListener(l);
        setSize(Engine.WIDTH,Engine.HEIGHT*20);
        ImageIcon image = new ImageIcon("icon.gif");
        setIconImage(image.getImage());
        setVisible(true);

        //invisible Cursor
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");
        getContentPane().setCursor(blankCursor);
        setCursor(Cursor.getDefaultCursor());

        //
        add(new ScreenComponents());

        addMouseMotionListener(new Mouse());
        addKeyListener(new Keyboard());
    }
    public void paint(Graphics g) {
        super.paint(g);
    }

    class ScreenComponents extends JPanel {
        public ScreenComponents() {
        }
        protected void paintComponent(Graphics g) {
            drawRect(g);
            actuFps();
        }
    }

    private void actuFps() {
        frame ++;
        if(System.currentTimeMillis() > time + 1000){
            this.setTitle("Backroom - FPS: " + frame);
            frame = 0;
            time = System.currentTimeMillis();
        }
    }

    private void drawRect(Graphics g){
        g.setColor(Color.darkGray);
        g.fillRect(0, 0, Engine.WIDTH, Engine.HEIGHT*20);
        g.setColor(Color.white);
        int refX = (main.getEngine().getRays().size()/Engine.WIDTH);

        int n = 0;
        for (Ray r : main.getEngine().getRays()){
            g.fillRect(n*refX, ((Engine.HEIGHT*10)-(int)(500/r.getdRay())), (main.getEngine().getRays().size()/Engine.WIDTH), (int)(1000/r.getdRay()));
            n++;
        }
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
                if((1+e.getRays().get(x).getdRay()) < y)
                    System.out.print("\033[47m  \033[0m");
                else
                    System.out.print("  ");
            }
            System.out.println();
        }
    }
}
