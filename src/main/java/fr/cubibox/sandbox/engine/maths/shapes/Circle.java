package fr.cubibox.sandbox.engine.maths.shapes;

import fr.cubibox.sandbox.engine.maths.Line;
import fr.cubibox.sandbox.engine.maths.vectors.Vector2;

import static java.lang.Math.pow;

public class Circle implements Shape {
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
        Vector2 difference = this.origin.subtract(vector);
        return difference.lengthSquared() <= getSquareRadius();
    }

    // TODO: Implement this method
    public boolean intersects(Circle circle) {
        return false;
    }

    // TODO: Implement this method
    public boolean intersects(Line line) {
        return false;
    }

    @Override
    public float signedDistanceFunction(Vector2 position) {
        return origin.subtract(position).length() - (radius);
    }
}
