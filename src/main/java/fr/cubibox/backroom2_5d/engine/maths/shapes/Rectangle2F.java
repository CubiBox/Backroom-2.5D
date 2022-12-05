package fr.cubibox.backroom2_5d.engine.maths.shapes;

import fr.cubibox.backroom2_5d.engine.maths.Line2F;
import fr.cubibox.backroom2_5d.engine.maths.Vector2F;

import javax.sound.sampled.Line;

public class Rectangle2F implements Shape {
    private final Vector2F origin;
    private final Vector2F size;

    public Rectangle2F(float x, float y, float width, float height) {
        this.origin = new Vector2F(x, y);
        this.size = new Vector2F(width / 2f, height / 2f);
    }

    public Vector2F getOrigin() {
        return this.origin;
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
        this.size.setX(width / 2F);
    }

    public float getHeight() {
        return this.size.getY();
    }

    public void setHeight(float height) {
        this.size.setY(height / 2F);
    }

    public Vector2F getMin() {
        return this.origin.sub(this.size);
    }

    public Vector2F getMax() {
        return this.origin.add(this.size);
    }

    @Override
    public Line2F[] getVertices() {
        Vector2F min = this.getMin();
        Vector2F max = this.getMax();

        Vector2F nw = new Vector2F(min.getX(), max.getY());
        Vector2F ne = new Vector2F(max.getX(), max.getY());
        Vector2F sw = new Vector2F(min.getX(), min.getY());
        Vector2F se = new Vector2F(max.getX(), min.getY());

        return new Line2F[] {
                new Line2F(nw, ne),
                new Line2F(ne, se),
                new Line2F(se, sw),
                new Line2F(sw, nw)
        };
    }

    public boolean intersects(Vector2F vector) {
        Vector2F min = getMin();
        Vector2F max = getMax();

        return vector.getX() >= min.getX() && vector.getX() <= max.getX()
                && vector.getY() >= min.getY() && vector.getY() <= max.getY();
    }

    public boolean intersects(Rectangle2F rectangle) {
        // TODO

        return false;
    }
}
