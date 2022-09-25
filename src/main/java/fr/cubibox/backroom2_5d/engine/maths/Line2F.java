package fr.cubibox.backroom2_5d.engine.maths;
import javafx.scene.shape.Line;

public class Line2F {
    private Point2F a;
    private Point2F b;

    private boolean view;

    public Line2F(Point2F a, Point2F b) {
        this.a = a;
        this.b = b;
    }

    public String toString() {
        return "\t\t@[" + (int) a.getX() + ";" + (int) a.getY() + "]-[" + (int) b.getX() + ";" + (int) b.getY() + "]\n";
    }

    public Point2F getA() {
        return a;
    }

    public void setA(Point2F a) {
        this.a = a;
    }

    public Point2F getB() {
        return b;
    }

    public void setB(Point2F b) {
        this.b = b;
    }

    public void isView(){
        this.view = true;
    }

    public void isNotView(){
        this.view = false;
    }

    public boolean getView(){
        return this.view;
    }
}
