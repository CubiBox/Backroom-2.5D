package fr.cubibox.backroom2_5d.engine;

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

    private ArrayList<Line2F> getIntersectedEdges(Ray r, ArrayList<Chunk> chunks) {
        ArrayList<Line2F> edgesFound = new ArrayList<>();

        for (Chunk chunk : chunks) {
            for (Polygon polygon : chunk.getPols()) {
                for (Line2F edge : polygon.getEdges()) {
                    float x1 = r.getStartX();
                    float y1 = r.getStartY();
                    float x2 = r.getIntersectionX();
                    float y2 = r.getIntersectionY();

                    float x3 = edge.getA().getX();
                    float y3 = edge.getA().getY();
                    float x4 = edge.getB().getX();
                    float y4 = edge.getB().getY();

                    float d = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);

                    if (d != 0) {
                        //float t = ((x1 - x3) * (y3 - y4) - (y1 - y3) * (x3 - x4)) / d;
                        float u = ((x1 - x3) * (y1 - y2) - (y1 - y3) * (x1 - x2)) / d;

                        if (u > 0 && u < 1) {
                            edgesFound.add(edge);
                        }
                    }
                }
            }
        }

        return edgesFound;
    }

    private Line2F getIntersectEdge(Ray r, ArrayList<Chunk> chunks) {
        Line2F edgeFound = null;

        Chunk chunk = map.getMapContent()[0][0];

            for (Polygon polygon : chunk.getPols()) {
                for (Line2F edge : polygon.getEdges()) {
                    float x1 = r.getStartX();
                    float y1 = r.getStartY();
                    float x2 = r.getIntersectionX();
                    float y2 = r.getIntersectionY();

                    float x3 = edge.getA().getX();
                    float y3 = edge.getA().getY();
                    float x4 = edge.getB().getX();
                    float y4 = edge.getB().getY();

                    float d = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);

                    if (d != 0) {
                        float xi = ((x3 - x4) * (x1 * y2 - y1 * x2) - (x1 - x2) * (x3 * y4 - y3 * x4)) / d;
                        float yi = ((y3 - y4) * (x1 * y2 - y1 * x2) - (y1 - y2) * (x3 * y4 - y3 * x4)) / d;

                        if (xi > Math.min(x1, x2) &&
                                xi < Math.max(x1, x2) &&
                                yi > Math.min(y1, y2) &&
                                yi < Math.max(y1, y2)) {

                            if (edgeFound == null) {
                                edgeFound = edge;
                            } else {
                                if (Math.sqrt(Math.pow(xi - x1, 2) + Math.pow(yi - y1, 2)) <
                                        Math.sqrt(Math.pow(edgeFound.getA().getX() - x1, 2) + Math.pow(edgeFound.getA().getY() - y1, 2))) {
                                    edgeFound = edge;
                                }
                            }
                        }
                    }
            }
        }

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

    private float computeSquareRayDistance(Ray r, Line2F line) {
        float x1 = r.getStartX();
        float y1 = r.getStartY();
        float x2 = r.getIntersectionX();
        float y2 = r.getIntersectionY();

        float x3 = line.getA().getX();
        float y3 = line.getA().getY();
        float x4 = line.getB().getX();
        float y4 = line.getB().getY();

        float d = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);

        if (d != 0) {
            float xi = ((x3 - x4) * (x1 * y2 - y1 * x2) - (x1 - x2) * (x3 * y4 - y3 * x4)) / d;
            float yi = ((y3 - y4) * (x1 * y2 - y1 * x2) - (y1 - y2) * (x3 * y4 - y3 * x4)) / d;

            r.setIntersectionX(xi);
            r.setIntersectionY(yi);

            return (float) (Math.pow(xi, 2) + Math.pow(yi, 2));
        }

        return Float.POSITIVE_INFINITY;
    }

    private void updateRay(Ray r) {
        ArrayList<Chunk> chunks = findTraveledChunk(r);
        //ArrayList<Line2F> lines = getIntersectedEdges(r, chunks);
        //Line2F nearestLine = getNearestLine(r, lines);

        Line2F nearestLine = getIntersectEdge(r, chunks);

        if (nearestLine != null) {
            float squareDistance = computeSquareRayDistance(r, nearestLine);
            if (squareDistance < r.getSquareDistance()) {
                r.setSquareDistance(squareDistance);
            }
        }
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
