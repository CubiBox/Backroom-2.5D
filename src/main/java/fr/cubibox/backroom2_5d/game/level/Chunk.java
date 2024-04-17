package fr.cubibox.backroom2_5d.game.level;

import java.util.ArrayList;

public class Chunk {
    private final ArrayList<MapObject> mapObjects;
    private int originX, originY;

    public Chunk(ArrayList<MapObject> mapObjects, int x, int y) {
        this.mapObjects = mapObjects;
        this.originX = x;
        this.originY = y;
    }

    public ArrayList<MapObject> getMapObjects() {
        return mapObjects;
    }

    public int getOriginX() {
        return originX;
    }

    public void setOriginX(int originX) {
        this.originX = originX;
    }

    public int getOriginY() {
        return originY;
    }

    public void setOriginY(int originY) {
        this.originY = originY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Chunk chunk)) return false;

        if (getOriginX() != chunk.getOriginX()) return false;
        return getOriginY() == chunk.getOriginY();
    }

    @Override
    public int hashCode() {
        int result = getOriginX();
        result = 31 * result + getOriginY();
        return result;
    }
}
