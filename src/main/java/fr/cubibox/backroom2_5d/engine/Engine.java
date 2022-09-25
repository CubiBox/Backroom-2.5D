package fr.cubibox.backroom2_5d.engine;

import fr.cubibox.backroom2_5d.Main;
import fr.cubibox.backroom2_5d.engine.maths.Line2F;
import fr.cubibox.backroom2_5d.engine.maths.Point2F;
import fr.cubibox.backroom2_5d.entities.Player;
import fr.cubibox.backroom2_5d.game.Chunk;
import fr.cubibox.backroom2_5d.game.Map;
import fr.cubibox.backroom2_5d.game.Polygon;
import javafx.scene.shape.Line;

import java.util.ArrayList;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

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

        for (int i = 0; i <= rayCount; i++) {
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
                //if (isOnChunk(r, chunk.getOriginX(), chunk.getOriginY()))
                    chunksFound.add(chunk);

        return chunksFound;
    }

    private Line2F getIntersectEdge(Ray r, ArrayList<Chunk> chunks) {
        Line2F edgeFound = null;
        float dRay = 1024;
        float tempX = 0;
        float tempY = 0;

        //Chunk chunk = map.getMapContent()[0][0];
        for (Chunk chunk : chunks) {
            for (Polygon polygon : chunk.getPols()) {
                for (Line2F edge : polygon.getEdges()) {
                    float p1X = r.getStartX();
                    float p1Y = r.getStartY();
                    float p2X = r.getIntersectionX();
                    float p2Y = r.getIntersectionY();

                    float p3X = edge.getA().getX();
                    float p3Y = edge.getA().getY();
                    float p4X = edge.getB().getX();
                    float p4Y = edge.getB().getY();

                    float s1_x, s1_y = 0, s2_x, s2_y;
                    s1_x = p2X - p1X;
                    s1_y = p2Y - p1Y;
                    s2_x = p4X - p3X;
                    s2_y = p4Y - p3Y;

                    float s, t;
                    float v = -s2_x * s1_y + s1_x * s2_y;
                    s = (-s1_y * (p1X - p3X) + s1_x * (p1Y - p3Y)) / v;
                    t = ( s2_x * (p1Y - p3Y) - s2_y * (p1X - p3X)) / v;

                    if (s >= 0 && s <= 1 && t >= 0 && t <= 1){
                        float intx = p1X + (t*s1_x);
                        float inty = p1Y + (t*s1_y);

                        float tempdRay = computeSquareRayDistance(r, intx, inty);
                        if (tempdRay < dRay) {
                            dRay = tempdRay;
                            tempX = intx;
                            tempY = inty;
                            edgeFound = edge;
                        }
                    }
                }
            }
        }

        r.setIntersectionX(tempX);
        r.setIntersectionY(tempY);
        return edgeFound;
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

    private float computeSquareRayDistance(Ray r, float x, float y) {
        float x1 = r.getStartX();
        float y1 = r.getStartY();

        float dx = x1-x;
        float dy = y1-y;

        return dx*dx + dy*dy;
    }

    private void updateRay(Ray r) {
        ArrayList<Chunk> chunks = findTraveledChunk(r);
        //ArrayList<Line2F> lines = getIntersectedEdges(r, chunks);
        //Line2F nearestLine = getNearestLine(r, lines);

        Line2F nearestLine = getIntersectEdge(r, chunks);
/*
        if (nearestLine != null) {
            float squareDistance = computeSquareRayDistance(r, nearestLine);
            if (squareDistance < r.getSquareDistance()) {
                r.setSquareDistance(squareDistance);
            }
        }
        */
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
