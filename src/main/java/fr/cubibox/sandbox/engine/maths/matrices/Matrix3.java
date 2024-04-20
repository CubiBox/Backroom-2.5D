package fr.cubibox.sandbox.engine.maths.matrices;

import fr.cubibox.sandbox.engine.maths.vectors.Vector3;

// TODO: Implement determinant and inverse
public class Matrix3 {
    public static final int DIMENSIONS = 3;

    private final float[] values;

    /**
     * Identity matrix constructor
     */
    public Matrix3() {
        values = new float[] {
                1, 0, 0,
                0, 1, 0,
                0, 0, 1
        };
    }

    public Matrix3(Vector3 a, Vector3 b, Vector3 c) {
        values = new float[] {
                a.getX(), b.getX(), c.getX(),
                a.getY(), b.getY(), c.getY(),
                a.getZ(), b.getZ(), c.getZ()
        };
    }

    public Matrix3(final float[] values) {
        this.values = values;
    }

    public Matrix3 transpose() {
        Matrix3 transposed = new Matrix3();

        for (int i = 0; i < DIMENSIONS; i++) {
            for (int j = 0; j < DIMENSIONS; j++) {
                transposed.setValue(i, j, getValue(j, i));
            }
        }

        return transposed;
    }

    public Matrix3 multiply(float v) {
        Matrix3 m = new Matrix3(asArray());

        for (int i = 0; i < values.length; i++) {
            m.values[i] *= v;
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
        return values.clone();
    }

    public float getValue(int x, int y) {
        return values[y * DIMENSIONS + x];
    }

    public void setValue(int x, int y, float value) {
        values[y * DIMENSIONS + x] = value;
    }
}
