package fr.cubibox.backroom2_5d;

import fr.cubibox.backroom2_5d.engine.Engine;
import fr.cubibox.backroom2_5d.engine.Point2F;
import fr.cubibox.backroom2_5d.game.*;
import fr.cubibox.backroom2_5d.game.Polygon;
import fr.cubibox.backroom2_5d.io.View2D;

import java.util.ArrayList;

public class Game {

    public Engine engine;
    public View2D view2D;

    private static Game instance;

    public Game() {
        Player p = new Player(1, 1, 0);

        Chunk[][] mapContent = new Chunk[2][2];
        ArrayList<Polygon> c1 = new ArrayList<>();
        ArrayList<Polygon> c2 = new ArrayList<>();
        ArrayList<Polygon> c3 = new ArrayList<>();
        ArrayList<Polygon> c4 = new ArrayList<>();

        ArrayList<Edge> pc1 = new ArrayList<>();
        ArrayList<Edge> pc2 = new ArrayList<>();
        ArrayList<Edge> pc3 = new ArrayList<>();
        ArrayList<Edge> pc4 = new ArrayList<>();

        pc1.add(new Edge(new Point2F(2f, 16f), new Point2F(2f, 2f)));
        pc1.add(new Edge(new Point2F(2f, 2f), new Point2F(16f, 2f)));
        c1.add(new Polygon(pc1,1f, "1"));

        pc2.add(new Edge(new Point2F(16f, 2f), new Point2F(30f, 2f)));
        pc2.add(new Edge(new Point2F(30f, 2f), new Point2F(30f, 16f)));
        c2.add(new Polygon(pc2,1f, "2"));

        pc3.add(new Edge(new Point2F(16f, 30f), new Point2F(2f, 30f)));
        pc3.add(new Edge(new Point2F(2f, 30f), new Point2F(16f, 30f)));
        c3.add(new Polygon(pc3,1f, "3"));

        pc4.add(new Edge(new Point2F(16f, 30f), new Point2F(30f, 30f)));
        pc4.add(new Edge(new Point2F(30f, 30f), new Point2F(30f, 16f)));
        c4.add(new Polygon(pc4,1f, "4"));

        mapContent[0][0] = new Chunk(c1);
        mapContent[0][1] = new Chunk(c2);
        mapContent[1][0] = new Chunk(c3);
        mapContent[1][1] = new Chunk(c4);

        Map map = new Map(mapContent,"test",32);


        this.engine = new Engine(16, 600, 400, p, map);
        this.view2D = new View2D(600, 400, this.engine);
    }

    public static void main(String[] args){
        Game game = new Game();

        game.view2D.init();
        game.engine.start();
        game.view2D.start();
    }

    public static Game getInstance(){
        if (instance == null){
            instance = new Game();
        }
        return instance;
    }
}