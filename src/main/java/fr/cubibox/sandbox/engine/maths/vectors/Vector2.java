package fr.cubibox.sandbox.engine.maths.vectors;

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
        return new Vector2(x + v.x, y + v.y);
    }

    public Vector2 add(float scalar) {
        return new Vector2(x + scalar, y + scalar);
    }

    public Vector2 subtract(Vector2 v) {
        return new Vector2(x - v.x, y - v.y);
    }

    public Vector2 subtract(float scalar) {
        return new Vector2(x - scalar, y - scalar);
    }

    public Vector2 multiply(Vector2 v) {
        return new Vector2(x * v.x, y * v.y);
    }

    public Vector2 multiply(float scalar) {
        return new Vector2(x * scalar, y * scalar);
    }

    public Vector2 divide(Vector2 v) {
        return new Vector2(x / v.x, y / v.y);
    }

    public Vector2 divide(float scalar) {
        return new Vector2(x / scalar, y / scalar);
    }

    public float length() {
        return (float) Math.sqrt(this.lengthSquared());
    }

    public float lengthSquared() {
        return dot(this);
    }

    /**
     * The dot product, commonly named scalar product or inner product. <br>
     * <br>
     * <ul>
     * <li>If the result is <strong>positive</strong>, the vectors are pointing in the same directions</li>
     * <li>If the result is <strong>negative</strong>, the vectors are pointing in opposite directions</li>
     * <li>If the result is <strong>0</strong>, vectors are perpendicular</li>
     * </ul>
     * @param v The vector to test
     * @return The directional relation of the vectors
     */
    public float dot(Vector2 v) {
        return x * v.x + y * v.y;
    }

    public Vector2 normalize() {
        float length = this.length();
        return new Vector2(this.x / length, this.y / length);
    }

    /**
     * Rotates vector with the given angle.
     * @param angle Rotation angle, in Radians.
     * @return Rotated vector
     */
    public Vector2 rotate(float angle) {
        return new Vector2(
                (float) (this.x * cos(angle) - this.y * sin(angle)),
                (float) (this.x * sin(angle) + this.y * cos(angle))
        );
    }

    public Vector2 reflection(Vector2 normal) {
        return this.subtract(
                normal.multiply(
                        2 * this.dot(normal)
                )
        );
    }

    public float[] asArray() {
        return new float[]{x, y};
    }

    @Override
    public String toString() {
        return "(" + this.x + "; " + this.y + ")";
    }
}
