import fr.cubibox.sandbox.engine.Engine;
import org.junit.Test;
import renderer.TestScene;

public class MainTest {
    public static void main(String[] args) {
        Engine engine = Engine.getInstance();
        engine.setScene(new TestScene());
        engine.start();
    }
}
