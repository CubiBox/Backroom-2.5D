package fr.cubibox.backroom2_5d.engine.maths;

import static fr.cubibox.backroom2_5d.engine.maths.MathUtils.RADIAN_PI_2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Vector2 {
    private float x;
    private float y;

    public Vector2() {
        this.x = 0f;
        this.y = 0f;
    }

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 rotate(float angle) {
        angle *= RADIAN_PI_2;

        return new Vector2(
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

    public Vector2 add(Vector2 v) {
        return new Vector2(x + v.getX(), y + v.getY());
    }

    public Vector2 sub(Vector2 v) {
        return new Vector2(x - v.getX(), y - v.getY());
    }

    public Vector2 mul(float f) {
        return new Vector2(x * f, y * f);
    }

    public Vector2 div(float f) {
        return new Vector2(x / f, y / f);
    }

    public float length() {
        return (float) Math.sqrt(this.squareLength());
    }

    public float squareLength() {
        return x * x + y * y;
    }

    public float dot(Vector2 v) {
        return x * v.getX() + y * v.getY();
    }

    public Vector2 normalize() {
        float length = this.length();
        return new Vector2(this.x / length, this.y / length);
    }

    @Override
    public String toString() {
        return "[" + this.x + ";" + this.y + "]";
    }
}
