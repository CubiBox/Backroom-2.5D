package fr.cubibox.sandbox.engine.maths.matrices;

import fr.cubibox.sandbox.engine.maths.vectors.Vector2;

// TODO: Implement determinant and inverse
public class Matrix2 {
    private static final int DIMENSIONS = 2;

    private final float[] matrix;

    /**
     * Identity matrix constructor
     */
    public Matrix2() {
        matrix = new float[] {
                1, 0,
                0, 1
        };
    }

    public Matrix2(Vector2 a, Vector2 b) {
        matrix = new float[] {
                a.getX(), b.getX(),
                a.getY(), b.getY()
        };
    }

    public Matrix2(final float[] matrix) {
        this.matrix = matrix;
    }

    public Matrix2 transpose() {
        Matrix2 transposed = new Matrix2();

        for (int i = 0; i < DIMENSIONS; i++) {
            for (int j = 0; j < DIMENSIONS; j++) {
                transposed.setValue(i, j, getValue(j, i));
            }
        }

        return transposed;
    }

    public Matrix2 multiply(float v) {
        Matrix2 m = new Matrix2(asArray());

        for (int i = 0; i < matrix.length; i++) {
            matrix[i] *= v;
        }

        return m;
    }

    public Vector2 multiply(Vector2 v) {
        // TODO: Implement this
        return null;
    }

    public Matrix2 multiply(Matrix2 m) {
        // TODO: Implement this
        return null;
    }

    public float[] asArray() {
        return matrix.clone();
    }

    public float getValue(int x, int y) {
        return matrix[y * DIMENSIONS + x];
    }

    public void setValue(int x, int y, float value) {
        matrix[y * DIMENSIONS + x] = value;
    }
}
