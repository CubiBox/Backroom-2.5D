package fr.cubibox.backroom2_5d.engine.physics;

import fr.cubibox.backroom2_5d.engine.maths.Interval;
import fr.cubibox.backroom2_5d.engine.maths.Line;
import fr.cubibox.backroom2_5d.engine.maths.Vector2;
import fr.cubibox.backroom2_5d.engine.maths.shapes.Circle;
import fr.cubibox.backroom2_5d.engine.maths.shapes.Polygon;
import fr.cubibox.backroom2_5d.engine.maths.shapes.Rectangle;
import fr.cubibox.backroom2_5d.engine.maths.shapes.Shape;

public class SAT {
    public static Interval getInterval(Rectangle rectangle, Vector2 axis) {
        Interval result = new Interval(0, 0);

        Vector2 min = rectangle.getMin();
        Vector2 max = rectangle.getMax();

        Vector2[] verts = {
                new Vector2(min.getX(), min.getY()),
                new Vector2(max.getX(), min.getY()),
                new Vector2(max.getX(), max.getY()),
                new Vector2(min.getX(), max.getY())
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

    public static Interval getInterval(Polygon polygon, Vector2 axis) {
        Interval result = new Interval(0, 0);

        Vector2[] verts = new Vector2[polygon.getEdges().size()];

        int index = 0;
        for (Line line : polygon.getEdges()) {
            verts[index] = new Vector2(
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

    public static boolean overlapOnAxis(Rectangle rectangleA, Rectangle rectangleB, Vector2 axis) {
        Interval intervalA = getInterval(rectangleA, axis);
        Interval intervalB = getInterval(rectangleB, axis);

        return intervalA.getMax() < intervalB.getMin() || intervalB.getMax() < intervalA.getMin();
    }

    public static boolean overlapOnAxis(Polygon rectangleA, Polygon rectangleB, Vector2 axis) {
        Interval intervalA = getInterval(rectangleA, axis);
        Interval intervalB = getInterval(rectangleB, axis);

        return !(intervalA.getMax() < intervalB.getMin() || intervalB.getMax() < intervalA.getMin());
    }

    public static boolean GenericSAT(Circle circle, Shape shapeB) {


        return false;
    }

    public static boolean GenericSAT(Polygon polyA, Polygon polyB) {
        Vector2[] axesA = polyA.getAxes();
        Vector2[] axesB = polyB.getAxes();

        for (Vector2 vector2 : axesA) {
            if (overlapOnAxis(polyA, polyB, vector2)) {
                return false;
            }
        }

        for (Vector2 vector2 : axesB) {
            if (overlapOnAxis(polyA, polyB, vector2)) {
                return false;
            }
        }

        return true;
    }
}
