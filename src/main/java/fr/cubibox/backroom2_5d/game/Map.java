package fr.cubibox.backroom2_5d.game;

public class Map {
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

    public String getLevelID() {
        return levelID;
    }
}
