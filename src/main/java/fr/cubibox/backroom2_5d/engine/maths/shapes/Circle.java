package fr.cubibox.backroom2_5d.engine.maths.shapes;

import fr.cubibox.backroom2_5d.engine.maths.Vector2;

public class Circle {
    private final Vector2 position;
    private float radius;

    public Circle(Vector2 position, float radius) {
        this.position = position;
        this.radius = radius;
    }

    public Circle(float radius) {
        this.position = new Vector2();
        this.radius = radius;
    }

    public Vector2 getPosition() {
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
