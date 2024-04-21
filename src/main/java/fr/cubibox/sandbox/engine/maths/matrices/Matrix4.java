package fr.cubibox.sandbox.engine.maths.matrices;

import fr.cubibox.sandbox.engine.maths.vectors.Vector4;

public class Matrix4 extends Matrix {
    /**
     * Identity matrix constructor
     */
    public Matrix4() {
        super(4, 4, new float[] {
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1
        });
    }

    public Matrix4(Vector4 a, Vector4 b, Vector4 c, Vector4 d) {
        super(4, 4, new float[] {
                a.getX(), b.getX(), c.getX(), d.getX(),
                a.getY(), b.getY(), c.getY(), d.getY(),
                a.getZ(), b.getZ(), c.getZ(), d.getZ(),
                a.getW(), b.getW(), c.getW(), d.getW()
        });
    }

    public Matrix4(final float[] values) {
        super(4, 4, values);
    }

    public Vector4 multiply(Vector4 v) {
        Matrix mV = this.multiply(new Matrix(4, 1, v.asArray()));
        float [] result = mV.asArray();
        return new Vector4(result[0], result[1], result[2], result[3]);
    }
}
