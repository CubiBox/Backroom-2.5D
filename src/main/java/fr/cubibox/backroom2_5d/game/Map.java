package fr.cubibox.backroom2_5d.game;

import fr.cubibox.backroom2_5d.graphics.Texture;

public class Map {
    // ArrayList<Texture> indexTexture;
    private Texture wall;
    private final Chunk[][] chunks;
    private final String levelID;
    private final int size;

    public Map(Chunk[][] chunks, String levelID, float mapSize) {
        this.levelID = levelID;
        this.size = (int) mapSize;
        this.chunks = chunks;
    }

    public int getSize() {
        return size;
    }

    public Chunk[][] getChunks() {
        return chunks;
    }

    public Chunk getChunk(int x, int y) {
        if (x >= 0 && y >= 0 && x < size && y < size) {
            return chunks[y][x];
        } else {
            return null;
        }
    }


    public Texture getWall() {
        return wall;
    }

    public void setWall(Texture wall) {
        this.wall = wall;
    }

    public String getLevelID() {
        return levelID;
    }
}
