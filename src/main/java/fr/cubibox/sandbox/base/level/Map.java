package fr.cubibox.sandbox.base.level;

import fr.cubibox.sandbox.base.entities.Entity;
import fr.cubibox.sandbox.base.entities.Player;

import java.util.HashSet;

public class Map {
    private final Chunk[][] chunks;

    private final Player player;
    private final HashSet<Entity> entities;
    private final String levelID;
    private final int size;

    public Map(Chunk[][] chunks, String levelID, float mapSize) {
        this.levelID = levelID;
        this.size = (int) mapSize;
        this.chunks = chunks;
        this.player = new Player(0, 0, 45);
        this.entities = new HashSet<>();
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

    public Player getPlayer() {
        return player;
    }

    public HashSet<Entity> getEntities() {
        return entities;
    }
}
