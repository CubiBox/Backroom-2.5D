package fr.cubibox.sandbox.engine.maths.shapes;

import fr.cubibox.sandbox.engine.maths.Line;
import fr.cubibox.sandbox.engine.maths.Vector2;

public class Rectangle implements Shape {
    private final Vector2 origin;
    private final Vector2 size;

    public Rectangle(float x, float y, float width, float height) {
        this.origin = new Vector2(x, y);
        this.size = new Vector2(width / 2f, height / 2f);
    }

    public Vector2 getOrigin() {
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

    public Vector2 getMin() {
        return this.origin.sub(this.size);
    }

    public Vector2 getMax() {
        return this.origin.add(this.size);
    }

    @Override
    public Line[] getVertices() {
        Vector2 min = this.getMin();
        Vector2 max = this.getMax();

        Vector2 nw = new Vector2(min.getX(), max.getY());
        Vector2 ne = new Vector2(max.getX(), max.getY());
        Vector2 sw = new Vector2(min.getX(), min.getY());
        Vector2 se = new Vector2(max.getX(), min.getY());

        return new Line[] {
                new Line(nw, ne),
                new Line(ne, se),
                new Line(se, sw),
                new Line(sw, nw)
        };
    }

    public boolean intersects(Vector2 vector) {
        Vector2 min = getMin();
        Vector2 max = getMax();

        return vector.getX() >= min.getX() && vector.getX() <= max.getX()
                && vector.getY() >= min.getY() && vector.getY() <= max.getY();
    }
}
