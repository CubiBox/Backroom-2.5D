package fr.cubibox.backroom2_5d.game;

import fr.cubibox.backroom2_5d.engine.maths.Line2F;
import fr.cubibox.backroom2_5d.engine.maths.Vector2F;

import java.util.ArrayList;

public class MapObject {
    public final String ID;
    private final ArrayList<Vector2F> points;
    private final float height;

    private final boolean isLine;

    public MapObject(String id, ArrayList<Vector2F> points, float height) {
        this.ID = id;
        this.height = height;
        this.points = points;
        this.isLine = false;
    }

    public MapObject(String id, ArrayList<Vector2F> points, float height, boolean isLine) {
        this.ID = id;
        this.height = height;
        this.points = points;
        this.isLine = isLine;
    }

    public ArrayList<Vector2F> getPoints() {
        return points;
    }

    public float getHeight() {
        return height;
    }

    public ArrayList<Line2F> getEdges() {
        ArrayList<Line2F> edges = new ArrayList<>();
        int pSize = points.size() - 1;
        for (int i = 0; i < pSize; i++) {
            edges.add(new Line2F(points.get(i), points.get(i + 1)));
        }

        if (!isLine) {
            edges.add(new Line2F(points.get(pSize), points.get(0)));
        }

        return edges;
    }

    public String toString() {
        String out = "$" + ID + "\n";
        ArrayList<Line2F> edges = getEdges();

        for (Line2F e : edges) {
            out += e.toString();
        }

        out += "\t\t%" + (int) height + "\n";
        return out;
    }
}