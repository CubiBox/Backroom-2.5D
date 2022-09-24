package fr.cubibox.backroom2_5d.engine;

import fr.cubibox.backroom2_5d.engine.maths.Line2F;
import fr.cubibox.backroom2_5d.entities.Player;
import fr.cubibox.backroom2_5d.game.Chunk;
import fr.cubibox.backroom2_5d.game.Map;
import javafx.scene.shape.Line;

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

    private ArrayList<Chunk> findTraveledChunk(Ray r) {
        ArrayList<Chunk> chunksFound = new ArrayList<>();
        Chunk[][] chunks = this.getMap().getMapContent();

        for (Chunk[] LineChunk : chunks)
            for (Chunk chunk : LineChunk)
                if (isOnChunk(r, chunk.getOriginX(), chunk.getOriginY()))
                    chunksFound.add(chunk);

        return chunksFound;
    }

    private boolean isOnChunk(Ray r, int cX, int cY) {
        float rX = r.getStartX();
        float rX2 = r.getIntersectionX();
        float rY = r.getStartY();
        float rY2 = r.getIntersectionY();

        if (rX < rX2) {
            float temp = rX;
            rX = rX2;
            rX2 = temp;
        }

        if (rY < rY2) {
            float temp = rY;
            rY = rY2;
            rY2 = temp;
        }

        if (cX < rX && cX + 16 > rX2)
            return false;
        if (cY < rY && cY + 16 > rY2)
            return false;
        return true;
    }

    /**
     * Retourne la Line la plus proche du joueur a partir d'une liste de lignes
     */
    private Line2F getNearestLine(Ray r, ArrayList<Line2F> lines) {
        Line2F tempLine = null;
        for (Line2F line : lines) {
            if (tempLine == null) {
                tempLine = line;
            }

            float dxPointA = line.getA().getX() - r.getStartX();
            float dyPointA = line.getA().getY() - r.getStartY();
            float dxPointB = line.getB().getX() - r.getStartX();
            float dyPointB = line.getB().getY() - r.getStartY();

            float dxTempPointA = tempLine.getA().getX() - r.getStartX();
            float dyTempPointA = tempLine.getA().getY() - r.getStartY();
            float dxTempPointB = tempLine.getB().getX() - r.getStartX();
            float dyTempPointB = tempLine.getB().getY() - r.getStartY();

            if ((dxPointA * dxPointA + dyPointA * dyPointA) < (dxTempPointA * dxTempPointA + dyTempPointA * dyTempPointA) ||
                    (dxPointB * dxPointB + dyPointB * dyPointB) < (dxTempPointB * dxTempPointB + dyTempPointB * dyTempPointB)) {
                tempLine = line;
            }

        }
        return tempLine;
    }

    /**
     * @param r
     * @param line
     *
     * actualise la distance entre la line et le joueur du Rayon (DRay)
     * actualise egalement le intersectionPoint du Rayon
     */

    private float computeSquareRayDistance(Ray r, Line2F line) {
        float x1 = line.getA().getX();
        float y1 = line.getA().getY();
        float x2 = line.getB().getX();
        float y2 = line.getB().getY();
        float x3 = r.getStartX();
        float y3 = r.getStartY();
        float x4 = r.getIntersectionX();
        float y4 = r.getIntersectionY();

        float d = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);

        if (d != 0) {
            float xi = ((x3 - x4) * (x1 * y2 - y1 * x2) - (x1 - x2) * (x3 * y4 - y3 * x4)) / d;
            float yi = ((y3 - y4) * (x1 * y2 - y1 * x2) - (y1 - y2) * (x3 * y4 - y3 * x4)) / d;

            r.setIntersectionX(xi);
            r.setIntersectionY(yi);

            return ((xi - x3) * (xi - x3) + (yi - y3) * (yi - y3));
        }

        return Float.POSITIVE_INFINITY;
    }

    private void updateRay(Ray r) {
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
