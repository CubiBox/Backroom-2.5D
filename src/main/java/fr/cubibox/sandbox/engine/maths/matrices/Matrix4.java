package fr.cubibox.sandbox.engine.maths.matrices;

import fr.cubibox.sandbox.engine.maths.vectors.Vector4;

// TODO: Implement determinant and inverse
public class Matrix4 {
    public static final int DIMENSIONS = 4;

    private final float[] values;

    /**
     * Identity matrix constructor
     */
    public Matrix4() {
        values = new float[] {
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1
        };
    }

    public Matrix4(Vector4 a, Vector4 b, Vector4 c, Vector4 d) {
        values = new float[] {
                a.getX(), b.getX(), c.getX(), d.getX(),
                a.getY(), b.getY(), c.getY(), d.getY(),
                a.getZ(), b.getZ(), c.getZ(), d.getZ(),
                a.getW(), b.getW(), c.getW(), d.getW()
        };
    }

    public Matrix4(final float[] values) {
        this.values = values;
    }

    public Matrix4 transpose() {
        Matrix4 transposed = new Matrix4();

        for (int i = 0; i < DIMENSIONS; i++) {
            for (int j = 0; j < DIMENSIONS; j++) {
                transposed.setValue(i, j, getValue(j, i));
            }
        }

        return transposed;
    }

    public Matrix4 multiply(float v) {
        Matrix4 m = new Matrix4(asArray());

        for (int i = 0; i < values.length; i++) {
            m.values[i] *= v;
        }

        return m;
    }

    public Vector4 multiply(Vector4 v) {
        // TODO: Implement this
        return null;
    }

    public Matrix4 multiply(Matrix4 m) {
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
