package fr.cubibox.sandbox.engine.maths.matrices;

import fr.cubibox.sandbox.engine.maths.vectors.Vector3;

// TODO: Implement determinant and inverse
public class Matrix3 {
    private final float[] matrix;

    /**
     * Identity matrix constructor
     */
    public Matrix3() {
        matrix = new float[] {
                1, 0, 0,
                0, 1, 0,
                0, 0, 1
        };
    }

    public Matrix3(Vector3 a, Vector3 b, Vector3 c) {
        matrix = new float[] {
                a.getX(), b.getX(), c.getX(),
                a.getY(), b.getY(), c.getY(),
                a.getZ(), b.getZ(), c.getZ()
        };
    }

    public Matrix3(final float[] matrix) {
        this.matrix = matrix;
    }

    public Matrix3 multiply(float v) {
        Matrix3 m = new Matrix3(asArray());

        for (int i = 0; i < matrix.length; i++) {
            matrix[i] *= v;
        }

        return m;
    }

    public Vector3 multiply(Vector3 v) {
        // TODO: Implement this
        return null;
    }

    public Matrix3 multiply(Matrix3 m) {
        // TODO: Implement this
        return null;
    }

    public float[] asArray() {
        return matrix.clone();
    }

    public float getValue(int x, int y) {
        return matrix[y * 3 + x];
    }

    public void setValue(int x, int y, float value) {
        matrix[y * 3 + x] = value;
    }
}
