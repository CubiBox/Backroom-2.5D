package fr.cubibox.backroom2_5d.entities;

import fr.cubibox.backroom2_5d.engine.maths.Point2F;

public class Entity {
    private final Point2F position;
    private float angle;
    private String id;

    public Entity(Point2F position) {
        this.position = position;
        this.angle = 0f;
    }

    public Entity(float x, float y) {
        this.position = new Point2F(x,y);
        this.angle = 0f;
    }

    public Point2F getPosition() {
        return position;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        while(angle < 0)
            angle += 360;

        while (angle >= 360)
            angle -= 360;

        this.angle = angle;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
