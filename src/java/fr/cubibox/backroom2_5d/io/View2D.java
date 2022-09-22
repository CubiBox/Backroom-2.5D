package fr.cubibox.backroom2_5d.io;

import fr.cubibox.backroom2_5d.Game;
import fr.cubibox.backroom2_5d.engine.Engine;
import fr.cubibox.backroom2_5d.engine.Ray;
import fr.cubibox.backroom2_5d.game.*;
import fr.cubibox.backroom2_5d.game.Polygon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class View2D extends JPanel implements Runnable {
    private final int width, height;
    private int scale = 10;

    private Keyboard keyboard;

    private final JFrame frame = new JFrame("Backroom 2.5D");

    private Thread renderThread = new Thread(this, "RENDER");

    private final Engine engine;

    public View2D(int width, int height, Engine engine) {
        this.width = width;
        this.height = height;
        this.keyboard = new Keyboard();

        this.setBackground(Color.BLACK);
        this.setPreferredSize(new Dimension(width, height));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyboard);
        this.setFocusable(true);
        this.engine = engine;
    }

    public void init() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void start() {
        renderThread.start();
    }

    public void paint(Graphics g) {
        super.paint(g);

        Map m = engine.getMap();

        g.setColor(Color.WHITE);

        for (int i = 0; i < m.getMapContent().length; i++) {
            for (int j = 0; j < m.getMapContent()[i].length; j++) {
                Chunk c = m.getMapContent()[i][j];
                for (Polygon p : c.getPolygons()) {
                    for (Edge e : p.getEdges()) {
                        g.drawLine((int) (e.getP1().getX() * scale), (int) (e.getP1().getY() * scale), (int) (e.getP2().getX() * scale), (int) (e.getP2().getY() * scale));
                    }
                }
            }
        }

        int i = 0;

        g.setColor(Color.RED);
        for (Ray r : engine.getRays()) {
            if (r.getIntersection().getX() != Float.POSITIVE_INFINITY && r.getIntersection().getY() != Float.POSITIVE_INFINITY) {
                g.drawLine((int) (engine.getPlayer().getX() * scale), (int) (engine.getPlayer().getY() * scale), (int) (r.getIntersection().getX() * scale), (int) (r.getIntersection().getY() * scale));
            }
            i++;
        }

        g.setColor(Color.green);
        g.drawRect((int) (engine.getPlayer().getX() * scale), (int) (engine.getPlayer().getY() * scale), 1, 1);


        g.dispose();
    }

    @Override
    public void run() {
        while (true) {
            repaint();
            pollInput();

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void pollInput() {
        Player player = engine.getPlayer();

        if (isKeyPressed(KeyEvent.VK_UP)) {
            System.out.println("UP");
            player.setX(player.getX() + (float) Math.cos(Math.toRadians(player.getOrientation())) * 0.1f);
            player.setY(player.getY() + (float) Math.sin(Math.toRadians(player.getOrientation())) * 0.1f);
            System.out.println(player.getX() + " " + player.getY());
        } else if (isKeyPressed(KeyEvent.VK_DOWN)) {
            System.out.println("DOWN");
            player.setX(player.getX() - (float) Math.cos(Math.toRadians(player.getOrientation())) * 0.1f);
            player.setY(player.getY() - (float) Math.sin(Math.toRadians(player.getOrientation())) * 0.1f);
            System.out.println(player.getX() + " " + player.getY());
        }

        if (isKeyPressed(KeyEvent.VK_LEFT)) {
            player.setOrientation(player.getOrientation() - 1);
            System.out.println("ORIENTATION : " + player.getOrientation());
        } else if (isKeyPressed(KeyEvent.VK_RIGHT)) {
            player.setOrientation(player.getOrientation() + 1);
            System.out.println("ORIENTATION : " + player.getOrientation());
        }
    }

    public boolean isKeyPressed(int keyCode) {
        return this.keyboard.isKeyDown(keyCode);
    }
}
