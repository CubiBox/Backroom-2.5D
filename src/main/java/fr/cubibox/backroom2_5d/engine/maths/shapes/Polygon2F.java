package fr.cubibox.backroom2_5d.engine.maths.shapes;

import fr.cubibox.backroom2_5d.engine.maths.Line2F;
import fr.cubibox.backroom2_5d.engine.maths.Point2F;
import fr.cubibox.backroom2_5d.engine.maths.Vector2F;

import java.util.ArrayList;

public class Polygon2F {
    private final ArrayList<Line2F> edges;

    private int height;

    public Polygon2F(ArrayList<Line2F> edges) {
        this.edges = edges;
    }

    public Polygon2F(ArrayList<Point2F> points, boolean isLine) {
        this.edges = new ArrayList<>();
        if (isLine) {
            for (int i = 0; i < points.size() - 1; i++) {
                edges.add(new Line2F(points.get(i), points.get(i + 1)));
            }
        } else {
            for (int i = 0; i < points.size() - 1; i++) {
                edges.add(new Line2F(points.get(i), points.get((i + 1) % points.size())));
            }
        }
    }

    public Polygon2F(Point2F... points) {
        this.edges = new ArrayList<>();
        for (int i = 0; i < points.length; i++) {
            edges.add(new Line2F(points[i], points[(i + 1) % points.length]));
        }
    }

    public ArrayList<Line2F> getEdges() {
        return edges;
    }

    public Vector2F[] getAxes() {
        Vector2F[] axes = new Vector2F[edges.size()];
        for (int i = 0; i < edges.size(); i++) {
            axes[i] = edges.get(i).getNormal();
        }
        return axes;
    }
}
