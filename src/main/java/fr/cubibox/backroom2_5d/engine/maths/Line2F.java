package fr.cubibox.backroom2_5d.engine.maths;

public class Line2F {
    private Point2F a;
    private Point2F b;

    public Line2F(Point2F a, Point2F b) {
        this.a = a;
        this.b = b;
    }

    public String toString() {
        return "\t\t@[" + (int) a.getX() + ";" + (int) a.getY() + "]-[" + (int) b.getX() + ";" + (int) b.getY() + "]\n";
    }

    public Point2F getA() {
        return a;
    }

    public void setA(Point2F a) {
        this.a = a;
    }

    public Point2F getB() {
        return b;
    }

    public void setB(Point2F b) {
        this.b = b;
    }

    public Vector2F getNormal() {
        return new Vector2F(b.getY() - a.getY(), a.getX() - b.getX());
    }

    public float getLength() {
        Vector2F length = new Vector2F(
                this.getB().getX() - this.getA().getX(),
                this.getB().getY() - this.getA().getY()
        );

        return (float) (Math.sqrt(length.dot(length)));
    }

    public float getSqLength() {
        Vector2F length = new Vector2F(
                this.getB().getX() - this.getA().getX(),
                this.getB().getY() - this.getA().getY()
        );

        return length.dot(length);
    }

    public boolean isPointOnLine(Point2F intersectionPoint) {
        float x1 = this.getA().getX();
        float y1 = this.getA().getY();
        float x2 = this.getB().getX();
        float y2 = this.getB().getY();
        float x3 = intersectionPoint.getX();
        float y3 = intersectionPoint.getY();

        float d = (x2 - x1) * (y3 - y1) - (x3 - x1) * (y2 - y1);

        return (d == 0);
    }
}
