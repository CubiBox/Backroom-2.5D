package fr.cubibox.backroom2_5d.game;
import fr.cubibox.backroom2_5d.engine.maths.Point2F;
import javafx.scene.shape.Line;

public class Edge {
    private Point2F p1;
    private Point2F p2;

    private Line edge;
    // private Texture texture;


    public Edge(Point2F p1, Point2F p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.edge = new Line(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }



    public String toString() {
        return "\t\t@[" + (int)p1.getX() + ";" + (int)p1.getY() + "]-[" + (int)p2.getX() + ";" + (int)p2.getY() + "]\n";
    }

    public Line getEdge() {
        return edge;
    }

    public void setEdge(Line edge) {
        this.edge = edge;
    }

    public Point2F getP1() {
        return p1;
    }

    public void setP1(Point2F p1) {
        this.p1 = p1;
    }

    public Point2F getP2() {
        return p2;
    }

    public void setP2(Point2F p2) {
        this.p2 = p2;
    }
}
