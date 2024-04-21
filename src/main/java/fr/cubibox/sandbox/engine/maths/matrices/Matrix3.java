package fr.cubibox.sandbox.engine.maths.matrices;

import fr.cubibox.sandbox.engine.maths.vectors.Vector3;

public class Matrix3 extends Matrix {
    /**
     * Identity matrix constructor
     */
    public Matrix3() {
        super(3, 3, new float[] {
                1, 0, 0,
                0, 1, 0,
                0, 0, 1
        });
    }

    public Matrix3(Vector3 a, Vector3 b, Vector3 c) {
        super(3, 3, new float[] {
                a.getX(), b.getX(), c.getX(),
                a.getY(), b.getY(), c.getY(),
                a.getZ(), b.getZ(), c.getZ()
        });
    }

    public Matrix3(final float[] values) {
        super(3, 3, values);
    }

    public Vector3 multiply(Vector3 v) {
        Matrix mV = this.multiply(new Matrix(3, 1, v.asArray()));
        float [] result = mV.asArray();
        return new Vector3(result[0], result[1], result[2]);
    }
}
