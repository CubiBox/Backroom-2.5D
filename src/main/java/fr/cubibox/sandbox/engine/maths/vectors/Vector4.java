package fr.cubibox.sandbox.engine.maths.vectors;

public class Vector4 {
    private float x;
    private float y;
    private float z;
    private float w;

    public Vector4() {
        this.x = 0f;
        this.y = 0f;
        this.z = 0f;
        this.w = 0f;
    }

    public Vector4(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
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

    public float getW() {
        return w;
    }

    public void setW(float w) {
        this.w = w;
    }

    public Vector4 add(Vector4 v) {
        return new Vector4(x + v.x, y + v.y, z + v.z, w + v.w);
    }

    public Vector4 add(float scalar) {
        return new Vector4(x + scalar, y + scalar, z + scalar, w + scalar);
    }

    public Vector4 subtract(Vector4 v) {
        return new Vector4(x - v.x, y - v.y, z - v.z, w - v.w);
    }

    public Vector4 subtract(float scalar) {
        return new Vector4(x - scalar, y - scalar, z - scalar, w - scalar);
    }

    public Vector4 multiply(Vector4 v) {
        return new Vector4(x * v.x, y * v.y, z * v.z, w * v.w);
    }

    public Vector4 multiply(float scalar) {
        return new Vector4(x * scalar, y * scalar, z * scalar, w * scalar);
    }

    public Vector4 divide(Vector4 v) {
        return new Vector4(x / v.x, y / v.y, z / v.z, w / v.w);
    }

    public Vector4 divide(float scalar) {
        return new Vector4(x / scalar, y / scalar, z / scalar, w / scalar);
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
    public float dot(Vector4 v) {
        return x * v.x + y * v.y + z * v.z + w * v.w;
    }

    public Vector4 normalize() {
        float length = this.length();
        return new Vector4(this.x / length, this.y / length, this.z / length, this.w / length);
    }

    public Vector4 reflection(Vector4 normal) {
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
        return "(" + this.x + "; " + this.y + "; " + this.z + "; " + this.w + ")";
    }
}
