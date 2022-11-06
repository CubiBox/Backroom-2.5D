package fr.cubibox.backroom2_5d.engine.maths;

public class Line2F {
    private Vector2F a;
    private Vector2F b;

    public Line2F(Vector2F a, Vector2F b) {
        this.a = a;
        this.b = b;
    }

    public String toString() {
        return "\t\t@[" + (int) a.getX() + ";" + (int) a.getY() + "]-[" + (int) b.getX() + ";" + (int) b.getY() + "]\n";
    }

    public Vector2F getA() {
        return a;
    }

    public void setA(Vector2F a) {
        this.a = a;
    }

    public Vector2F getB() {
        return b;
    }

    public void setB(Vector2F b) {
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
}
