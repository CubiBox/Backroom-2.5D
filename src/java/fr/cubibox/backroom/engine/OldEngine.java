package fr.cubibox.backroom.engine;

import fr.cubibox.backroom.game.Map;
import fr.cubibox.backroom.game.Player;

import java.util.ArrayList;

public class OldEngine {
    private Map room;
    private Player player;
    private ArrayList<Ray> rays = new ArrayList<>();

    public static int WIDTH;
    public static int HEIGHT;

    public OldEngine(Map room, Player player, int width, int height) {
        this.room = room;
        this.player = player;
        WIDTH = width;
        HEIGHT = height;
    }



    public Map getRoom() {
        return room;
    }

    public void setRoom(Map room) {
        this.room = room;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ArrayList<Ray> getRays() {
        return rays;
    }

    public void setRays(ArrayList<Ray> rays) {
        this.rays = rays;
    }
}
