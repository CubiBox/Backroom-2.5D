package fr.cubibox.backroom2_5d.engine.maths;

public class Point2F {
    private float x;
    private float y;

    public Point2F(float x, float y) {
        this.x = x;
        this.y = y;
    }
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String toString() {
        return "[" + (int)this.x + ";" + (int)this.y + "]";
    }
}