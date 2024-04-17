package fr.cubibox.sandbox.base;

import fr.cubibox.sandbox.engine.maths.Vector2;

public class Camera {
    private final Vector2 position;
    private float scale = 1F;

    public Camera(float x, float y) {
        this.position = new Vector2(x, y);
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getPosX() {
        return position.getX();
    }

    public void setPosX(float posX) {
        this.position.setX(posX);
    }

    public float getPosY() {
        return position.getY();
    }

    public void setPosY(float posY) {
        this.position.setY(posY);
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
