import fr.cubibox.sandbox.engine.maths.matrices.Matrix;
import fr.cubibox.sandbox.engine.maths.matrices.Matrix2;
import org.junit.Test;

public class MatricesTest {
    @Test
    public void multiplyMatrices() {
        Matrix a = new Matrix(1, 3,
                1, 2, 3
        );
        Matrix b = new Matrix(1, 3,
                4, 5, 6
        );
        Matrix c = new Matrix(1, 3,
                7, 8, 9
        );

        Matrix2 d = new Matrix2(
                1, 2,
                3, 4
        );
        Matrix e = new Matrix(2, 2, 1, 2, 3, 4);

        System.out.println(a);
        System.out.println(b);
        System.out.println(c);

        System.out.println(b.transpose());

        Matrix ab = a.multiply(a.transpose());

        System.out.println(ab);

        System.out.println(d);
        System.out.println((Matrix2) e);
    }
}
