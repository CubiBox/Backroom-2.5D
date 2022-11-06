package fr.cubibox.backroom2_5d.engine.maths;

public class Vector2F {
    private float x;
    private float y;

    public Vector2F(float x, float y) {
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

    public Vector2F add(Vector2F v) {
        return new Vector2F(x + v.getX(), y + v.getY());
    }

    public Vector2F sub(Vector2F v) {
        return new Vector2F(x - v.getX(), y - v.getY());
    }

    public Vector2F mul(float f) {
        return new Vector2F(x * f, y * f);
    }

    public Vector2F div(float f) {
        return new Vector2F(x / f, y / f);
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public float dot(Vector2F v) {
        return x * v.getX() + y * v.getY();
    }

    @Override
    public String toString() {
        return "[" + (int)this.x + ";" + (int)this.y + "]";
    }
}
