package fr.cubibox.backroom2_5d.entities;

import fr.cubibox.backroom2_5d.engine.maths.Point2F;
import fr.cubibox.backroom2_5d.engine.maths.Vector2F;
import fr.cubibox.backroom2_5d.engine.maths.shapes.Shape;

import java.util.HashSet;

public abstract class Entity {
    protected final HashSet<Shape> collisionBox;
    protected final Point2F position;
    protected Vector2F velocity;
    protected String id;
    protected float angle;

    public Entity(Point2F position, float width, float height) {
        this.collisionBox = new HashSet<>();
        this.position = position;
        this.angle = 0f;
    }

    public Entity(float x, float y, float width, float height) {
        this.collisionBox = new HashSet<>();
        this.position = new Point2F(x, y);
        this.angle = 0f;
    }

    public Point2F getPosition() {
        return position;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        while (angle < 0)
            angle += 360;

        while (angle >= 360)
            angle -= 360;

        this.angle = angle;
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
}
