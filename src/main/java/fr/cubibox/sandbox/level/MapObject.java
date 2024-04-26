package fr.cubibox.sandbox.level;

import fr.cubibox.sandbox.engine.maths.shapes.Line;
import fr.cubibox.sandbox.engine.maths.vectors.Vector2;

import java.util.ArrayList;

public class MapObject {
    public final String ID;
    private final ArrayList<Vector2> points;
    private final float height;
    private float orientation;

    private final boolean isLine;

    public MapObject(String id, ArrayList<Vector2> points, float height, boolean isLine) {
        this.ID = id;
        this.height = height;
        this.points = points;
        this.isLine = isLine;
        this.orientation = 0;
    }

    public ArrayList<Vector2> getPoints() {
        return points;
    }

    public float getHeight() {
        return height;
    }

    public ArrayList<Line> getEdges() {
        ArrayList<Line> edges = new ArrayList<>();
        int pSize = points.size() - 1;
        for (int i = 0; i < pSize; i++) {
            edges.add(new Line(points.get(i), points.get(i + 1)));
        }

        if (!isLine) {
            edges.add(new Line(points.get(pSize), points.get(0)));
        }

        return edges;
    }

    public String toString() {
        String out = "$" + ID + "\n";
        ArrayList<Line> edges = getEdges();

        for (Line e : edges) {
            out += e.toString();
        }

        out += "\t\t%" + (int) height + "\n";
        return out;
    }
}
