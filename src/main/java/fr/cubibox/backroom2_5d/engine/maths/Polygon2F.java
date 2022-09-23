package fr.cubibox.backroom2_5d.engine.maths;

import java.util.ArrayList;

// TODO

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

    public Polygon2F(Point2F ... points) {
        this.edges = new ArrayList<>();
        for (int i = 0; i < points.length; i++) {
            edges.add(new Line2F(points[i], points[(i + 1) % points.length]));
        }
    }
}
