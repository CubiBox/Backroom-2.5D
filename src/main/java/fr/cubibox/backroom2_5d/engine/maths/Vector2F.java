package fr.cubibox.backroom2_5d.engine.maths;

import static fr.cubibox.backroom2_5d.engine.maths.MathUtils.RADIAN_PI_2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Vector2F {
    private float x;
    private float y;

    public Vector2F(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2F rotate(float angle) {
        angle *= RADIAN_PI_2;

        return new Vector2F(
                (float) (this.x * cos(angle) - this.y * sin(angle)),
                (float) (this.x * sin(angle) + this.y * cos(angle))
        );
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

    public Vector2F normalize() {
        float length = this.length();
        return new Vector2F(this.x / length, this.y / length);
    }

    @Override
    public String toString() {
        return "[" + this.x + ";" + this.y + "]";
    }
}
