package fr.cubibox.backroom2_5d.game.entities;

import fr.cubibox.backroom2_5d.engine.maths.Vector2F;
import fr.cubibox.backroom2_5d.engine.maths.shapes.Circle2F;

public class Player extends Entity {
    private float eyesHeight = 0.5f;

    public Player(float x, float y, float angle) {
        super(x, y, 1, 1);
        this.collisionBox.add(new Circle2F(
                new Vector2F(0, 0),
                0.5f
        ));
        this.setAngle(angle);
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

    public float getAngle() {
        return super.getAngle();
    }

    public void setAngle(float angle) {
        super.setAngle(angle);
    }

    public void setPos(float x, float y) {
        super.getPosition().setX(x);
        super.getPosition().setY(y);
    }

    public Vector2F getPos() {
        return super.getPosition();
    }

    public float getEyesHeight() {
        return eyesHeight;
    }

    public void setEyesHeight(float eyesHeight) {
        this.eyesHeight = eyesHeight;
    }
}
