package fr.cubibox.backroom2_5d.game;

import java.util.ArrayList;

public class Chunk {
    private final ArrayList<MapObject> mapObjects;
    // TODO
    private int originX, originY;

    public Chunk(ArrayList<MapObject> mapObjects, int x, int y) {
        this.mapObjects = mapObjects;
        this.originX = x;
        this.originY = y;
    }

    public ArrayList<MapObject> getPolygons() {
        return mapObjects;
    }

    public void addPolygon(MapObject p) {
        mapObjects.add(p);
    }

    public void removePolygon(MapObject p) {
        mapObjects.remove(p);
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
}
