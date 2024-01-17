package fr.cubibox.backroom.game;

public class Map {
    private String idLevel;
    private Chunk[][] mapContent;

    private int mapSize;

    public Map(String name, float mapSize) {
        this.idLevel = name;
        this.mapSize = (int) mapSize;
        this.mapContent = new Chunk[this.mapSize / Chunk.CHUNK_SIZE][this.mapSize / Chunk.CHUNK_SIZE];
    }

    public Map(Chunk[][] chunks, String name, float mapSize) {
        this.idLevel = name;
        this.mapSize = (int) mapSize;
        this.mapContent = chunks;
    }

    public int getMapSize() {
        return mapSize;
    }

    public void setMapSize(int mapSize) {
        this.mapSize = mapSize;
    }

    public String getIdLevel() {
        return idLevel;
    }

    public void setIdLevel(String idLevel) {
        this.idLevel = idLevel;
    }

    public Chunk[][] getMapContent() {
        return mapContent;
    }

    public void setMapContent(Chunk[][] mapContent) {
        this.mapContent = mapContent;
    }
}
