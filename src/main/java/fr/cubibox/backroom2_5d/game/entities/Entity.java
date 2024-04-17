package fr.cubibox.backroom2_5d.game.entities;

import fr.cubibox.backroom2_5d.engine.maths.Vector2;
import fr.cubibox.backroom2_5d.engine.maths.shapes.Circle;

import static fr.cubibox.backroom2_5d.engine.maths.MathUtils.RADIAN_PI_2;

public abstract class Entity {
    protected Circle collisionArea;
    protected Vector2 position;
    protected Vector2 velocity;
    protected Vector2 direction;
    protected String id;

    public Entity(float x, float y, float radius) {
        this.collisionArea = new Circle(radius);
        this.position = new Vector2(x, y);
        this.direction = new Vector2(0f, 0f);
        this.velocity = new Vector2(0f, 0f);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
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

    public Circle getCollisionArea() {
        return collisionArea;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    /**
     * The direction of an entity is also the max speed value for that entity
     * @return Vector2F
     */
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
