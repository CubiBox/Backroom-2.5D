package fr.cubibox.backroom2_5d.game;

import java.util.ArrayList;

public class Chunk {
    public static final int CHUNK_SIZE = 16;

    private ArrayList<Polygon> polygons;

    public Chunk(ArrayList<Polygon> polygons) {
        this.polygons = polygons;
    }

    public ArrayList<Polygon> getPolygons() {
        return polygons;
    }

    public void setPolygons(ArrayList<Polygon> polygons) {
        this.polygons = polygons;
    }
}
