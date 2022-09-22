package fr.cubibox.backroom2_5d.io;

import javax.swing.*;


public class View3D extends JFrame{
    /*private int frame;
    private long time;

    public View3D() {
        super("Backroom");
        WindowListener l = new WindowAdapter() {
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        };
        setTitle("Backroom \t" + frame);
        addWindowListener(l);
        setSize(OldEngine.WIDTH, OldEngine.HEIGHT*20);
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
        g.fillRect(0, 0, OldEngine.WIDTH, OldEngine.HEIGHT*20);
        g.setColor(Color.white);
        int refX = (Main.getEngine().getRays().size()/ OldEngine.WIDTH);

        int n = 0;
        for (Ray r : Main.getEngine().getRays()){
            float dist = r.getdRay();
            float mul = (dist % 255) / 12.0f;
            //float mul = 255.0f / dist;

            int colorInt = (int) (255.0f - 255.0f * mul);

            g.setColor(new Color(colorInt, colorInt, colorInt));

            g.fillRect(n*refX, ((OldEngine.HEIGHT*10)-(int)(500/r.getdRay())), (Main.getEngine().getRays().size()/ OldEngine.WIDTH), (int)(1000/r.getdRay()));
            n++;
        }
    }
    public static boolean JHere(int x, int y, Player p){
        if (x == p.getX() && y == p.getY())
            return true;
        return false;
    }
    public static void print3Dview(OldEngine e){
        for (int y = 0; y< OldEngine.HEIGHT; y++){
            for (int x = 0; x< OldEngine.WIDTH; x++){
                //System.out.print(e.getRays().get(x).toString());
                if((1+e.getRays().get(x).getdRay()) < y)
                    System.out.print("\033[47m  \033[0m");
                else
                    System.out.print("  ");
            }
            System.out.println();
        }
    }*/
}
