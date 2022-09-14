package fr.cubibox.jeux;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;


public class GraphicSwg extends JFrame{

    public GraphicSwg() {
        super("DOOM");
        WindowListener l = new WindowAdapter() {
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        };

        addWindowListener(l);
        setSize(Engine.WIDTH*5,Engine.HEIGHT*20);
        ImageIcon image = new ImageIcon("icon.gif");
        setIconImage(image.getImage());
        setVisible(true);

        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");
        getContentPane().setCursor(blankCursor);

        setCursor(Cursor.getDefaultCursor());
        add(new ScreenComponents());

        addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                int x = (e.getComponent().getX() + e.getX());
                if (x < 800){
                    main.getEngine().getPlayer().setAngle(main.getEngine().getPlayer().getAngle() - (800 - e.getX())*0.2f);
                }
                if (x > 800){
                    main.getEngine().getPlayer().setAngle(main.getEngine().getPlayer().getAngle() + (e.getX()-800)*0.2f);
                }

                Robot r = null;
                try {r = new Robot();}
                catch (AWTException ex) {throw new RuntimeException(ex);}
                r.mouseMove(e.getComponent().getX() + Engine.WIDTH*5/2,e.getComponent().getY() + Engine.HEIGHT*20/2);

                try {
                    Thread.sleep(16l);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e){

                // avancer
                if (e.getKeyChar() == 'z' ){
                    System.out.println("avancer");
                    main.getEngine().getPlayer().Avancer(0.1f);
                }

                //gauche
                else if (e.getKeyCode() == 81){

                }

                //reculer
                else if (e.getKeyCode() == 83){

                }

                //droite
                else if (e.getKeyCode() == 68 ){

                }
            }
        });
    }
    public void paint(Graphics g) {
        super.paint(g);
    }

    class ScreenComponents extends JPanel {
        public ScreenComponents() {

            setBorder(BorderFactory.createLineBorder(Color.black));

        }
        protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawRect(g);
            }
    }
    private void drawRect(Graphics g){
        g.setColor(Color.darkGray);
        g.fillRect(0, 0, Engine.WIDTH*5, Engine.WIDTH*20);
        g.setColor(Color.white);
        int n = 0;
        for (Ray r : main.getEngine().getRays()){
            if (r.getdRay() > 1)
                g.fillRect(n*(Engine.WIDTH/64), ((Engine.HEIGHT*10)-(int)(500/r.getdRay())), Engine.WIDTH/64, (int)(1000/r.getdRay()));
                //g.fillRect(n*(Engine.WIDTH/4), ((Engine.HEIGHT*10)-(int)-(20*(1+r.getdRay()))), Engine.WIDTH/4, (int)-(40*(1+r.getdRay())));
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
