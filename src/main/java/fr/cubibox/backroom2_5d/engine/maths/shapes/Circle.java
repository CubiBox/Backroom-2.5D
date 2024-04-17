package fr.cubibox.backroom2_5d.engine.maths.shapes;

import fr.cubibox.backroom2_5d.engine.maths.Line;
import fr.cubibox.backroom2_5d.engine.maths.Vector2;

public class Circle {
    private final Vector2 origin;
    private float radius;

    public Circle(Vector2 origin, float radius) {
        this.origin = origin;
        this.radius = radius;
    }

    public Circle(float radius) {
        this.origin = new Vector2();
        this.radius = radius;
    }

    public Vector2 getOrigin() {
        return origin;
    }

    public float getX() {
        return this.origin.getX();
    }

    public void setX(float x) {
        this.origin.setX(x);
    }

    public float getY() {
        return this.origin.getY();
    }

    public void setY(float y) {
        this.origin.setY(y);
    }

    public float getRadius() {
        return radius;
    }

    public float getSquareRadius() {
        return radius * radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public boolean intersects(Vector2 vector) {
        Vector2 difference = this.origin.sub(vector);
        return difference.squareLength() <= getSquareRadius();
    }

    // TODO: Implement this method
    public boolean intersects(Circle circle) {
        return false;
    }

    // TODO: Implement this method
    public boolean intersects(Line line) {
        return false;
    }
}
