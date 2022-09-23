package fr.cubibox.backroom2_5d.engine;

import fr.cubibox.backroom2_5d.entities.Player;
import fr.cubibox.backroom2_5d.game.Chunk;
import fr.cubibox.backroom2_5d.game.Map;

import java.util.ArrayList;

public class Engine implements Runnable {
    private final Thread engineThread = new Thread(this, "ENGINE");
    private int rayCount;
    private Map map;
    private Player player;

    private float fov = 90;
    private ArrayList<Ray> rays = new ArrayList<>();
    public boolean shouldStop = false;


    public Engine(int rayCount, Player player, Map map) {
        this.rayCount = rayCount;
        this.player = player;
        this.map = map;
    }

    @Override
    public void run() {
        while (!shouldStop) {
            updateRays();

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void updateRays() {
        ArrayList<Ray> tempRays = new ArrayList<>();

        float angle = player.getAngle() - fov / 2;
        float angleStep = fov / rayCount;

        for (int i = 0; i < rayCount; i++) {
            tempRays.add(new Ray(player.getX(), player.getY(), angle));
            angle += angleStep;
        }

        for (Ray r : tempRays) {
            updateRay(r);
        }

        rays = tempRays;
    }

    private void updateRay(Ray r) {
        float x = r.getStartX();
        float y = r.getStartY();
        float angle = r.getAngle();

        Chunk c = map.getMapContent()[(int) (player.getY() / Chunk.CHUNK_SIZE)][(int) (player.getX() / Chunk.CHUNK_SIZE)];

    }

    public void start() {
        engineThread.start();
    }

    public void stop() {
        shouldStop = true;
    }

    public void setRayCount(int rayCount) {
        this.rayCount = rayCount;
    }

    public void setFov(float fov) {
        this.fov = fov;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Map getMap() {
        return map;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Ray> getRays() {
        return rays;
    }
}
