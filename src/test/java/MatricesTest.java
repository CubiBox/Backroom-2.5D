import fr.cubibox.sandbox.engine.maths.matrices.Matrix;
import org.junit.Test;

public class MatricesTest {
    @Test
    public void multiplyMatrices() {
        Matrix a = new Matrix(1, 3, new float[] {
                1, 2, 3,
        });
        Matrix b = new Matrix(1, 3, new float[] {
                4, 5, 6,
        });
        Matrix c = new Matrix(1, 3, new float[] {
                7, 8, 9,
        });

        System.out.println(a);
        System.out.println(b);
        System.out.println(c);

        System.out.println(b.transpose());

        Matrix ab = a.multiply(a.transpose());

        System.out.println(ab);
    }
}
