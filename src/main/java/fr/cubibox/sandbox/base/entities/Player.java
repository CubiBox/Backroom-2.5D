package fr.cubibox.sandbox.base.entities;

import fr.cubibox.sandbox.engine.maths.vectors.Vector2;

public class Player extends Entity {
    private float eyesHeight = 0.5f;

    public Player(float x, float y) {
        super(x, y, 1);
    }

    public float getX() {
        return super.getPosition().getX();
    }

    public void setX(float x) {
        super.getPosition().setX(x);
    }

    public float getY() {
        return super.getPosition().getY();
    }

    public void setY(float y) {
        super.getPosition().setY(y);
    }

    public void setPos(float x, float y) {
        super.getPosition().setX(x);
        super.getPosition().setY(y);
    }

    public Vector2 getPos() {
        return super.getPosition();
    }

    public float getEyesHeight() {
        return eyesHeight;
    }

    public void setEyesHeight(float eyesHeight) {
        this.eyesHeight = eyesHeight;
    }
}
