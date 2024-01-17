package fr.cubibox.backroom.game;

import fr.cubibox.backroom.engine.Point2F;

public class Edge {
    private Point2F p1;
    private Point2F p2;

    public Edge(Point2F p1, Point2F p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public String toString() {
        return "\t\t@[" + (int)p1.getX() + ";" + (int)p1.getY() + "]-[" + (int)p2.getX() + ";" + (int)p2.getY() + "]" + "*" + "texture/path/file" + "\n";
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
