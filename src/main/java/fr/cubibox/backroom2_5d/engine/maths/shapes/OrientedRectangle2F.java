package fr.cubibox.backroom2_5d.engine.maths.shapes;

import fr.cubibox.backroom2_5d.engine.maths.Line2F;
import fr.cubibox.backroom2_5d.engine.maths.Vector2F;

public class OrientedRectangle2F extends Rectangle2F{
    private float angle;

    public OrientedRectangle2F(float x, float y, float width, float height, float angle) {
        super(x, y, width, height);
        this.angle = angle;
    }

    @Override
    public Vector2F getMin() {
        return super.getMin();
    }

    @Override
    public Vector2F getMax() {
        return super.getMax();
    }

    public float getAngle() {
        return this.angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    @Override
    public Line2F[] getVertices() {
        Vector2F min = this.getMin().sub(this.getOrigin());
        Vector2F max = this.getMax().sub(this.getOrigin());

        Vector2F nw = new Vector2F(min.getX(), max.getY()).rotate(this.getAngle()).add(this.getOrigin());
        Vector2F ne = new Vector2F(max.getX(), max.getY()).rotate(this.getAngle()).add(this.getOrigin());
        Vector2F sw = new Vector2F(min.getX(), min.getY()).rotate(this.getAngle()).add(this.getOrigin());
        Vector2F se = new Vector2F(max.getX(), min.getY()).rotate(this.getAngle()).add(this.getOrigin());

        return new Line2F[] {
                new Line2F(nw, ne),
                new Line2F(ne, se),
                new Line2F(se, sw),
                new Line2F(sw, nw)
        };
    }
}
