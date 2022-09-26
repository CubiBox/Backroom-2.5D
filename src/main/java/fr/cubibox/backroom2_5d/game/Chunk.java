package fr.cubibox.backroom2_5d.game;

import java.util.ArrayList;

public class Chunk {
    private final ArrayList<Polygon> polygons;
    // TODO
    private int originX, originY;

    public Chunk(ArrayList<Polygon> polygons, int x, int y) {
        this.polygons = polygons;
        this.originX = x;
        this.originY = y;
    }

    public ArrayList<Polygon> getPolygons() {
        return polygons;
    }

    public void addPolygon(Polygon p) {
        polygons.add(p);
    }

    public void removePolygon(Polygon p) {
        polygons.remove(p);
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
