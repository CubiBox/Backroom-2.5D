package fr.cubibox.backroom2_5d.engine.collisions;

import fr.cubibox.backroom2_5d.engine.maths.Interval2F;
import fr.cubibox.backroom2_5d.engine.maths.Line2F;
import fr.cubibox.backroom2_5d.engine.maths.Vector2F;
import fr.cubibox.backroom2_5d.engine.maths.shapes.Circle2F;
import fr.cubibox.backroom2_5d.engine.maths.shapes.Polygon2F;
import fr.cubibox.backroom2_5d.engine.maths.shapes.Rectangle2F;
import fr.cubibox.backroom2_5d.engine.maths.shapes.Shape;

public class SAT {
    public static Interval2F getInterval(Rectangle2F rectangle, Vector2F axis) {
        Interval2F result = new Interval2F(0, 0);

        Vector2F min = rectangle.getMin();
        Vector2F max = rectangle.getMax();

        Vector2F[] verts = {
                new Vector2F(min.getX(), min.getY()),
                new Vector2F(max.getX(), min.getY()),
                new Vector2F(max.getX(), max.getY()),
                new Vector2F(min.getX(), max.getY())
        };

        result.setMax(axis.dot(verts[0]));
        result.setMin(result.getMax());

        for (int i = 1; i < 4; i++) {
            float projection = axis.dot(verts[i]);
            if (projection < result.getMin()) {
                result.setMin(projection);
            } else if (projection > result.getMax()) {
                result.setMax(projection);
            }
        }

        return result;
    }

    public static Interval2F getInterval(Polygon2F polygon, Vector2F axis) {
        Interval2F result = new Interval2F(0, 0);

        Vector2F[] verts = new Vector2F[polygon.getEdges().size()];

        int index = 0;
        for (Line2F line : polygon.getEdges()) {
            verts[index] = new Vector2F(
                    line.getB().getX() - line.getA().getX(),
                    line.getB().getY() - line.getA().getY()
            );
            index++;
        }

        result.setMax(axis.dot(verts[0]));
        result.setMin(result.getMax());

        for (int i = 1; i < verts.length; i++) {
            float projection = axis.dot(verts[i]);
            if (projection < result.getMin()) {
                result.setMin(projection);
            } else if (projection > result.getMax()) {
                result.setMax(projection);
            }
        }

        return result;
    }

    public static boolean overlapOnAxis(Rectangle2F rectangleA, Rectangle2F rectangleB, Vector2F axis) {
        Interval2F intervalA = getInterval(rectangleA, axis);
        Interval2F intervalB = getInterval(rectangleB, axis);

        return intervalA.getMax() < intervalB.getMin() || intervalB.getMax() < intervalA.getMin();
    }

    public static boolean overlapOnAxis(Polygon2F rectangleA, Polygon2F rectangleB, Vector2F axis) {
        Interval2F intervalA = getInterval(rectangleA, axis);
        Interval2F intervalB = getInterval(rectangleB, axis);

        return !(intervalA.getMax() < intervalB.getMin() || intervalB.getMax() < intervalA.getMin());
    }

    public static boolean GenericSAT(Circle2F circle, Shape shapeB) {


        return false;
    }

    public static boolean GenericSAT(Polygon2F polyA, Polygon2F polyB) {
        Vector2F[] axesA = polyA.getAxes();
        Vector2F[] axesB = polyB.getAxes();

        for (Vector2F vector2F : axesA) {
            if (overlapOnAxis(polyA, polyB, vector2F)) {
                return false;
            }
        }

        for (Vector2F vector2F : axesB) {
            if (overlapOnAxis(polyA, polyB, vector2F)) {
                return false;
            }
        }

        return true;
    }
}
