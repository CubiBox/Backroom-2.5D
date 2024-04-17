package fr.cubibox.sandbox.engine.maths.vectors;

public class Vector3 {
    private float x;
    private float y;
    private float z;

    public Vector3() {
        this.x = 0f;
        this.y = 0f;
        this.z = 0f;
    }

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
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

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public Vector3 add(Vector3 v) {
        return new Vector3(x + v.x, y + v.y, z + v.z);
    }

    public Vector3 add(float scalar) {
        return new Vector3(x + scalar, y + scalar, z + scalar);
    }

    public Vector3 subtract(Vector3 v) {
        return new Vector3(x - v.x, y - v.y, z - v.z);
    }

    public Vector3 subtract(float scalar) {
        return new Vector3(x - scalar, y - scalar, z - scalar);
    }

    public Vector3 multiply(Vector3 v) {
        return new Vector3(x * v.x, y * v.y, z * v.z);
    }

    public Vector3 multiply(float scalar) {
        return new Vector3(x * scalar, y * scalar, z * scalar);
    }

    public Vector3 divide(Vector3 v) {
        return new Vector3(x / v.x, y / v.y, z / v.z);
    }

    public Vector3 divide(float scalar) {
        return new Vector3(x / scalar, y / scalar, z / scalar);
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
    public float dot(Vector3 v) {
        return x * v.x + y * v.y + z * v.z;
    }

    public Vector3 normalize() {
        float length = this.length();
        return new Vector3(this.x / length, this.y / length, this.z / length);
    }

    public Vector3 reflection(Vector3 normal) {
        return this.subtract(
                normal.multiply(
                        2 * this.dot(normal)
                )
        );
    }

    public float[] asArray() {
        return new float[]{x, y, z};
    }

    @Override
    public String toString() {
        return "(" + this.x + "; " + this.y + "; " + this.z + ")";
    }
}
