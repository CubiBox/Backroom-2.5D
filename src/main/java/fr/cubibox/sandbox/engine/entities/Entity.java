package fr.cubibox.sandbox.engine.entities;

import fr.cubibox.sandbox.engine.maths.vectors.Vector2;
import fr.cubibox.sandbox.engine.maths.shapes.Circle;

public abstract class Entity {
    protected Vector2 position;

    protected Vector2 velocity;

    protected Vector2 direction;

    protected Circle collisionArea;

    public Entity(float x, float y, float radius) {
        this.position = new Vector2(x, y);
        this.direction = new Vector2();
        this.velocity = new Vector2();
        this.collisionArea = new Circle(radius);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Circle getCollisionArea() {
        return collisionArea;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public Vector2 getDirection() {
        return direction;
    }

    public void setDirection(Vector2 direction) {
        this.direction = direction;
    }

    public Entity cloneEntity() throws CloneNotSupportedException {
        return (Entity) (super.clone());
    }
}
