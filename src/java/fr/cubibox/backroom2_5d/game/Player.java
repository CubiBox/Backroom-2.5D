package fr.cubibox.backroom2_5d.game;

import fr.cubibox.backroom2_5d.engine.Point2F;

public class Player {
    Point2F position;
    float orientation;

    public Player(float x, float y, float orientation) {
        this.position = new Point2F(x, y);
        this.orientation = orientation;
    }

    public void setX(float x) {
        this.position.setX(x);
    }

    public float getX() {
        return this.position.getX();
    }


    public void setY(float y) {
        this.position.setY(y);
    }

    public float getY() {
        return this.position.getY();
    }

    public float getOrientation() {
        return orientation;
    }

    public void setOrientation(float orientation) {
        this.orientation = orientation;
    }
}