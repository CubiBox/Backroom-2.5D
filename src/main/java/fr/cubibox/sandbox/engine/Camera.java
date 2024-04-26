package fr.cubibox.sandbox.engine;

import fr.cubibox.sandbox.engine.maths.vectors.Vector2;

import static fr.cubibox.sandbox.engine.Engine.HEIGHT;
import static fr.cubibox.sandbox.engine.Engine.WIDTH;

public class Camera {
    private final Vector2 screenOffset;
    private Vector2 position;
    private Vector2 orientation;

    public Camera() {
        this.screenOffset = new Vector2(WIDTH / 2f, HEIGHT / 2f);
        this.position = new Vector2();
        this.orientation = new Vector2(1f, 0f);
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getOrientation() {
        return orientation;
    }

    public Vector2 getScreenOffset() {
        return screenOffset;
    }

    public void move(Vector2 delta) {
        this.position = position.add(delta);
    }

    public void rotate(float angle) {
        orientation = (orientation.rotate(angle));
    }
}
