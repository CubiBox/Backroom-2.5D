package fr.cubibox.backroom2_5d.game.entities;

import fr.cubibox.backroom2_5d.engine.maths.Vector2F;
import fr.cubibox.backroom2_5d.engine.maths.shapes.Shape;

import java.util.HashSet;

import static fr.cubibox.backroom2_5d.engine.maths.MathUtils.RADIAN_PI_2;

public abstract class Entity {
    protected HashSet<Shape> collisionBox;
    protected Vector2F position;
    protected Vector2F velocity;
    protected Vector2F direction;
    protected String id;

    public Entity(float x, float y, float width, float height) {
        this.collisionBox = new HashSet<>();
        this.position = new Vector2F(x, y);
        this.direction = new Vector2F(0f, 0f);
        this.velocity = new Vector2F(0f, 0f);
    }

    public Vector2F getPosition() {
        return position;
    }

    public float getAngle() {
        return (float) Math.atan2(direction.getY(), direction.getX()) / RADIAN_PI_2;
    }

    public void setAngle(float angle) {
        this.direction.setX((float) Math.cos(angle * RADIAN_PI_2));
        this.direction.setY((float) Math.sin(angle * RADIAN_PI_2));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public HashSet<Shape> getCollisionBox() {
        return collisionBox;
    }

    public Vector2F getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2F velocity) {
        this.velocity = velocity;
    }

    public Vector2F getDirection() {
        return direction;
    }

    public void setDirection(Vector2F direction) {
        this.direction = direction;
    }
}
