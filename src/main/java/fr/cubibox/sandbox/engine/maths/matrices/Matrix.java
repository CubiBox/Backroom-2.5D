package fr.cubibox.sandbox.engine.maths.matrices;

import java.util.Arrays;
import java.util.Objects;

public class Matrix {
    private final int rows;
    private final int cols;

    protected final float[] values;

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        values = new float[rows * cols];
    }

    public Matrix(int rows, int cols, float... values) {
        if (rows == 0) {
            throw new IllegalArgumentException("rows = 0");
        } else if (cols == 0) {
            throw new IllegalArgumentException("cols = 0");
        } else if (values.length != rows * cols) {
            throw new IllegalArgumentException("values != rows * cols");
        }

        this.rows = rows;
        this.cols = cols;
        this.values = values;
    }

    public Matrix transpose() {
        Matrix transposed = new Matrix(cols, rows);

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                transposed.setValue(i, j, getValue(j, i));
            }
        }

        return transposed;
    }

    public Matrix multiply(float v) {
        Matrix m = new Matrix(rows, cols, values);

        for (int i = 0; i < values.length; i++) {
            m.values[i] *= v;
        }

        return m;
    }

    public Matrix multiply(Matrix matrix) {
        if (matrix.rows != cols) {
            throw new IllegalArgumentException("Matrix rows != cols");
        } else if (matrix.cols != rows) {
            throw new IllegalArgumentException("Matrix cols != rows");
        }

        Matrix m = new Matrix(rows, matrix.cols, new float[rows * matrix.cols]);

        for (int row = 0; row < rows; row++) {
            for (int matCol = 0; matCol < matrix.cols; matCol++) {
                int i = row * matrix.cols + matCol;
                m.values[i] = 0f;

                for (int col = 0; col < cols; col++) {
                    float a = getValue(col, row);
                    float b = matrix.getValue(col, matCol);

                    m.values[i] += a * b;
                }
            }
        }

        return m;
    }

    public float determinant() {
        if (rows != cols) {
            throw new IllegalArgumentException("rows != cols");
        }

        // TODO: implement determinant
        return 0f;
    }

    public Matrix inverse() {
        float determinant = determinant();

        if (determinant == 0f) {
            throw new IllegalArgumentException("determinant == 0");
        }

        return this.multiply(determinant);
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public float[] getValues() {
        return values.clone();
    }

    public float getValue(int col, int row) {
        return values[row * rows + col];
    }

    public void setValue(int col, int row, float value) {
        values[row * cols + col] = value;
    }

    public float[] asArray() {
        return values.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix matrix = (Matrix) o;
        return rows == matrix.rows && cols == matrix.cols && Objects.deepEquals(values, matrix.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rows, cols, Arrays.hashCode(values));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(rows).append("x").append(cols).append(" matrix\n");
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                builder.append(values[row * cols + col]).append(" ");
            }
            builder.append("\n");
        }

        return builder.toString();
    }
}
