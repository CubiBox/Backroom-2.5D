package renderer;

import fr.cubibox.sandbox.engine.Engine;
import fr.cubibox.sandbox.engine.PixelDrawer;
import fr.cubibox.sandbox.engine.Scene;
import fr.cubibox.sandbox.engine.io.Mouse;
import fr.cubibox.sandbox.engine.io.Window;
import fr.cubibox.sandbox.engine.maths.shapes.Circle;
import fr.cubibox.sandbox.engine.maths.shapes.Line;
import fr.cubibox.sandbox.engine.maths.shapes.Rectangle;
import fr.cubibox.sandbox.engine.maths.shapes.Shape;
import fr.cubibox.sandbox.engine.maths.vectors.Vector2;

import java.util.ArrayList;
import java.util.List;

import static fr.cubibox.sandbox.engine.Engine.HEIGHT;
import static fr.cubibox.sandbox.engine.Engine.WIDTH;
import static java.lang.Math.abs;

public class TestScene implements Scene {
    private final Vector2 screenOffset = new Vector2(300, 200);
    private final Circle cursor = new Circle(new Vector2(), 0);

    private final List<Shape> shapes = new ArrayList<Shape>();

    float x, y;

    @Override
    public void init() {
        shapes.add(new Circle(new Vector2(), 20));
        shapes.add(new Line(new Vector2(70, 0), new Vector2(90, -100)));
        shapes.add(new Rectangle(
                100, 100, 50, 30
        ));
    }

    @Override
    public void render(PixelDrawer pixelDrawer) {
        pixelDrawer.rectangle(0, 0, WIDTH, HEIGHT, PixelDrawer.BLACK);

        for (Shape shape : shapes) {
            if (shape instanceof Circle c) {
                pixelDrawer.circle(c.getOrigin().add(screenOffset), (int) c.getRadius(), PixelDrawer.RED);
            }
            if (shape instanceof Line l) {
                Vector2 a = l.getA().add(screenOffset);
                Vector2 b = l.getB().add(screenOffset);

                pixelDrawer.line((int) a.getX(), (int) a.getY(), (int) b.getX(), (int) (b.getY()), PixelDrawer.RED);
            }
            if (shape instanceof Rectangle r) {
                Vector2 min = r.getMin().add(screenOffset);
                Vector2 max = r.getMax().add(screenOffset);

                pixelDrawer.rectangle((int) min.getX(), (int) min.getY(), (int) (max.getX() - min.getX()), (int) (max.getY() - min.getY()), PixelDrawer.RED );
            }
        }

        pixelDrawer.circle(cursor.getOrigin().add(screenOffset), (int) cursor.getRadius(), PixelDrawer.CYAN);
    }

    @Override
    public void update(float dt) {
        input();

        cursor.setX(x);
        cursor.setY(y);

        float radius = 0f;

        for (Shape shape : shapes) {
            float sdf = shape.signedDistanceFunction(cursor.getOrigin());

            if (sdf < radius || radius == 0f) {
                radius = sdf;
            }
        }

        cursor.setRadius(abs(radius));
    }

    @Override
    public void input() {
        Engine engine = Engine.getInstance();
        Window window = engine.getWindow();
        Mouse mouse = window.getMouse();

        x = (float) (mouse.getX() - screenOffset.getX());
        y = (float) (mouse.getY() - screenOffset.getY());
    }
}
