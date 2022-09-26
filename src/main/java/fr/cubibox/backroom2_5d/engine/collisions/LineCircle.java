package fr.cubibox.backroom2_5d.engine.collisions;

import fr.cubibox.backroom2_5d.engine.maths.Line2F;
import fr.cubibox.backroom2_5d.engine.maths.Point2F;
import fr.cubibox.backroom2_5d.engine.maths.Vector2F;
import fr.cubibox.backroom2_5d.engine.maths.shapes.Circle2F;

public class LineCircle {
    public static boolean lineCircle(Line2F line, Circle2F circle) {
        Vector2F ab = new Vector2F(
                line.getB().getX() - line.getA().getX(),
                line.getB().getY() - line.getA().getY()
        );

        float t = ab.dot(new Vector2F(
                circle.getPosition().getX() - line.getA().getX(),
                circle.getPosition().getY() - line.getA().getY()
        )) / ab.dot(ab);

        if (t >= 0.0f && t <= 1.0f) {
            Point2F closestPoint = new Point2F(
                    line.getA().getX() + (ab.getX() * t),
                    line.getA().getY() + (ab.getY() * t)
            );

            Line2F circleToClosest = new Line2F(circle.getPosition(), closestPoint);
            return circleToClosest.getSqLength() <= (circle.getRadius() * circle.getRadius());
        }

        return false;
    }
}
