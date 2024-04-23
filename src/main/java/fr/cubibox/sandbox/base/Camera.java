package fr.cubibox.sandbox.base;

import fr.cubibox.sandbox.engine.maths.vectors.Vector2;

public class Camera {
    private final Vector2 screenOffset;
    private Vector2 position;
    private Vector2 orientation;

    public Camera(int width, int height) {
        this.screenOffset = new Vector2(width / 2f, height / 2f);
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
