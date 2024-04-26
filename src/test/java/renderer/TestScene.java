package renderer;

import fr.cubibox.sandbox.engine.Engine;
import fr.cubibox.sandbox.engine.PixelDrawer;
import fr.cubibox.sandbox.engine.Scene;
import fr.cubibox.sandbox.engine.io.Mouse;
import fr.cubibox.sandbox.engine.io.Window;
import fr.cubibox.sandbox.engine.maths.shapes.Circle;
import fr.cubibox.sandbox.engine.maths.vectors.Vector2;

import static java.lang.Math.abs;

public class TestScene implements Scene {
    private final Vector2 screenOffset = new Vector2(300, 200);
    private final Circle c = new Circle(new Vector2(), 20);
    private final Circle c1 = new Circle(new Vector2(), 0);

    @Override
    public void init() {

    }

    @Override
    public void render(PixelDrawer pixelDrawer) {
        pixelDrawer.rectangle(0, 0, 600, 400, PixelDrawer.BLACK);
        pixelDrawer.circle(c.getOrigin().add(screenOffset), (int) c.getRadius(), PixelDrawer.RED);
        pixelDrawer.circle(c1.getOrigin().add(screenOffset), (int) c1.getRadius(), PixelDrawer.CYAN);
    }

    @Override
    public void update(float dt) {
        input();
    }

    @Override
    public void input() {
        Engine engine = Engine.getInstance();
        Window window = engine.getWindow();
        Mouse mouse = window.getMouse();

        float x = (float) (mouse.getX() - screenOffset.getX());
        float y = (float) (mouse.getY() - screenOffset.getY());

        c1.setX(x);
        c1.setY(y);

        float sdf = c.signedDistanceFunction(c1.getOrigin());

        c1.setRadius(abs(sdf));
    }
}
