package fr.cubibox.jeux.game;

import java.util.ArrayList;

public class Chunk {
    private ArrayList<Polygon> pols = new ArrayList<>();
    private boolean isLoad;

    public Chunk(ArrayList<Polygon> pols) {
        this.pols = pols;
        this.isLoad = false;
    }



    public ArrayList<Polygon> getPols() {
        return pols;
    }

    public void setPols(ArrayList<Polygon> pols) {
        this.pols = pols;
    }

    public boolean isLoad() {
        return isLoad;
    }

    public void setLoad(boolean load) {
        isLoad = load;
    }
}
