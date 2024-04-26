import fr.cubibox.sandbox.engine.Engine;
import fr.cubibox.sandbox.engine.Scene;
import renderer.TestScene;

public class MainTest {
    public static void main(String[] args) {
        Engine engine = Engine.getInstance();
        Scene scene = new TestScene();
        engine.setScene(scene);
        scene.init();
        engine.start();
    }
}
