package fr.cubibox.backroom2_5d.engine.maths.shapes;

import fr.cubibox.backroom2_5d.engine.maths.Point2F;
import fr.cubibox.backroom2_5d.engine.maths.Vector2F;

public class Rectangle2F extends Shape {
    private final Point2F origin;
    private final Vector2F size;

    public Rectangle2F(float x, float y, float width, float height) {
        this.origin = new Point2F(x, y);
        this.size = new Vector2F(width, height);
    }

    public Rectangle2F(Vector2F min, Vector2F max) {
        this.origin = new Point2F(min.getX(), min.getY());
        this.size = new Vector2F(max.getX() - min.getX(), max.getY() - min.getY());
    }

    public float getX() {
        return origin.getX();
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

    public float getWidth() {
        return this.size.getX();
    }

    public void setWidth(float width) {
        this.size.setX(width);
    }

    public float getHeight() {
        return this.size.getY();
    }

    public void setHeight(float height) {
        this.size.setY(height);
    }

    public Vector2F getMin() {
        Vector2F p1 = new Vector2F(this.origin.getX(), this.origin.getY());
        Vector2F p2 = new Vector2F(this.origin.getX() + this.size.getX(), this.origin.getY() + this.size.getY());

        return new Vector2F(Math.min(p1.getX(), p2.getX()), Math.min(p1.getY(), p2.getY()));
    }

    public Vector2F getMax() {
        Vector2F p1 = new Vector2F(this.origin.getX(), this.origin.getY());
        Vector2F p2 = new Vector2F(this.origin.getX() + this.size.getX(), this.origin.getY() + this.size.getY());

        return new Vector2F(Math.max(p1.getX(), p2.getX()), Math.max(p1.getY(), p2.getY()));
    }
}
