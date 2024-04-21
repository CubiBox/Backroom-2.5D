package fr.cubibox.sandbox.engine.maths.matrices;

import fr.cubibox.sandbox.engine.maths.vectors.Vector2;
import fr.cubibox.sandbox.engine.maths.vectors.Vector3;

public class Matrix2 extends Matrix {
    /**
     * Identity matrix constructor
     */
    public Matrix2() {
        super(2, 2, new float[] {
                1, 0,
                0, 1
        });
    }

    public Matrix2(Vector2 a, Vector2 b) {
        super(2, 2, new float[] {
                a.getX(), b.getX(),
                a.getY(), b.getY()
        });
    }

    public Matrix2(float... values) {
        super(2, 2, values);
    }

    public Matrix2(Matrix matrix) {
        super(2, 2, matrix.values);
    }

    public Vector2 multiply(Vector2 v) {
        Matrix mV = this.multiply(new Matrix(2, 1, v.asArray()));
        float [] result = mV.asArray();
        return new Vector2(result[0], result[1]);
    }
}
