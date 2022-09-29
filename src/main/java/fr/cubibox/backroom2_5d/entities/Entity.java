package fr.cubibox.backroom2_5d.entities;

import fr.cubibox.backroom2_5d.engine.maths.Point2F;
import fr.cubibox.backroom2_5d.engine.maths.Vector2F;
import fr.cubibox.backroom2_5d.engine.maths.shapes.Shape;

import java.util.ArrayList;

public abstract class Entity {
    protected final ArrayList<Shape> collisionBox;
    protected final Point2F position;
    protected Vector2F velocity;
    protected float angle;

    protected float height = 20f;
    protected String id;

    public Entity(Point2F position, float width, float height) {
        this.collisionBox = new ArrayList<>();
        this.position = position;
        this.angle = 0f;
        this.height = 2f;
    }

    public Entity(float x, float y, float width, float height) {
        this.collisionBox = new ArrayList<>();
        this.position = new Point2F(x, y);
        this.angle = 0f;
        this.height = 2f;
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


    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Shape> getCollisionBox() {
        return collisionBox;
    }

    public Vector2F getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2F velocity) {
        this.velocity = velocity;
    }
}
