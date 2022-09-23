package fr.cubibox.backroom2_5d.game;

import fr.cubibox.backroom2_5d.Main;
import fr.cubibox.backroom2_5d.engine.maths.Point2F;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class Polygon {
    private ArrayList<Edge> edges = new ArrayList<>();
    private ArrayList<Point2F> points = new ArrayList<>();
    private float height;
    private String id;

    private boolean isLine;

    private javafx.scene.shape.Shape polShape;


    public Polygon(ArrayList<Point2F> points, float height){
        this.isLine = false;
        this.height = height;
        if (!points.isEmpty()) {
            this.points = points;
            setupEdges();
        }
    }
    public Polygon(ArrayList<Edge> edges, float height, String id){
        this.isLine = false;
        this.height = height;
        this.edges = edges;
        this.id = id;
    }

    public Polygon(ArrayList<Point2F> points, float height, boolean isLine){
        this.isLine = isLine;
        this.height = height;
        if (!points.isEmpty()) {
            this.points = points;
            setupEdges();
        }
    }
    public Polygon(ArrayList<Edge> edges, ArrayList<Point2F> points, float height, String id, boolean isLine){
        this.isLine = isLine;
        this.height = height;
        this.points = points;
        this.id = id;
        if (!points.isEmpty()) {
            this.points = points;
            setupEdges();
        }

    }

    public void setupEdges(){
        edges = new ArrayList<>();
        int pSize = points.size() - 1;
        double[] polPoints = new double[points.size()*2];

        for (int countP = 0; countP < pSize; countP ++){
            edges.add(new Edge(points.get(countP),points.get(countP+1)));
        }
        int countP = 0;
        for (Point2F p : points){
            polPoints[countP] = Main.toScreenX(p.getX());
            countP ++;
            polPoints[countP] = Main.toScreenY(p.getY());
            countP ++;
        }
        if (this.isLine == true) {
            this.polShape = new javafx.scene.shape.Polyline(polPoints);
            this.polShape.setFill(Color.TRANSPARENT);
            this.polShape.setStrokeWidth(2.0);
            this.polShape.setStroke(Color.CYAN);
        }
        else {
            edges.add(new Edge(points.get(pSize), points.get(0)));
            this.polShape = new javafx.scene.shape.Polygon(polPoints);
            this.polShape.setFill(Color.TRANSPARENT);
            this.polShape.setStrokeWidth(2.0);
            this.polShape.setStroke(Color.CYAN);
        }
    }

    public String toString(){
        String out = "$"+id+"\n";
        for (Edge e : edges){
            out += e.toString();
        }
        out += "\t\t%" + (int)height + "\n";
        return out;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isLine() {
        return isLine;
    }

    public void setLine(boolean line) {
        isLine = line;
    }

    public void setPolShape(Shape polShape) {
        this.polShape = polShape;
    }

    public ArrayList<Point2F> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<Point2F> points) {
        this.points = points;
    }

    public javafx.scene.shape.Shape getPolShape() {
        return polShape;
    }

    public void setPolShape(javafx.scene.shape.Polygon polShape) {
        this.polShape = polShape;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

}