package fr.cubibox.backroom2_5d.engine;

import fr.cubibox.backroom2_5d.game.Map;

public class OldEngine {
    private Map room;
    private Player player;

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
}
