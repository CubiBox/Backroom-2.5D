package fr.cubibox.backroom2_5d.engine.maths.shapes;

import fr.cubibox.backroom2_5d.engine.maths.Point2F;

public class Circle2F extends Shape {
    private final Point2F position;
    private float radius;

    public Circle2F(Point2F position, float radius) {
        this.position = position;
        this.radius = radius;
    }

    public Point2F getPosition() {
        return position;
    }

    public float getX() {
        return this.position.getX();
    }

    public void setX(float x) {
        this.position.setX(x);
    }

    public float getY() {
        return this.position.getY();
    }

    public void setY(float y) {
        this.position.setY(y);
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}
