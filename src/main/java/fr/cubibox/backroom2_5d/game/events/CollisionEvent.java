package fr.cubibox.backroom2_5d.game.events;

import fr.cubibox.backroom2_5d.engine.maths.Vector2;
import fr.cubibox.backroom2_5d.engine.observers.Event;
import fr.cubibox.backroom2_5d.game.entities.Entity;

public class CollisionEvent implements Event, Comparable<CollisionEvent> {
    private final float collisionTime;
    private final Entity clonedEntity;

    public CollisionEvent(Entity entity, float time) throws CloneNotSupportedException {
        this.clonedEntity = entity.cloneEntity();
        this.collisionTime = time;
    }

    @Override
    public void execute() {
        clonedEntity.setVelocity(new Vector2(0f, 0f));
    }

    public Entity getClonedEntity() {
        return clonedEntity;
    }

    public float getCollisionTime() {
        return collisionTime;
    }

    @Override
    public int compareTo(CollisionEvent ce) {
        return (int) (collisionTime - ce.collisionTime);
    }
}
