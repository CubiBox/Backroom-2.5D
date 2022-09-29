package fr.cubibox.backroom2_5d.engine;

import fr.cubibox.backroom2_5d.Main;
import fr.cubibox.backroom2_5d.engine.collisions.LineCircle;
import fr.cubibox.backroom2_5d.engine.maths.Line2F;
import fr.cubibox.backroom2_5d.engine.maths.Point2F;
import fr.cubibox.backroom2_5d.engine.maths.Vector2F;
import fr.cubibox.backroom2_5d.engine.maths.shapes.Circle2F;
import fr.cubibox.backroom2_5d.engine.maths.shapes.Shape;
import fr.cubibox.backroom2_5d.entities.Player;
import fr.cubibox.backroom2_5d.game.Chunk;
import fr.cubibox.backroom2_5d.game.Map;
import fr.cubibox.backroom2_5d.game.Polygon;
import fr.cubibox.backroom2_5d.io.Keyboard;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;

import static fr.cubibox.backroom2_5d.engine.Ray.RADIAN_PI_2;

public class Engine implements Runnable {
    public static float screenDistance = 50.0f;
    public static float wallHeight = 16.0f;
    public static float eyeLevel = 1.0f;
    private final Thread engineThread = new Thread(this, "ENGINE");
    private final Player player;
    private final int rayCount;
    private final Map map;
    private final float fov = 70;
    public boolean shouldStop = false;
    private ArrayList<Ray> rays = new ArrayList<>();


    public Engine(int rayCount, Player player, Map map) {
        this.rayCount = rayCount;
        this.player = player;
        this.map = map;
    }

    @Override
    public void run() {
        while (!shouldStop) {
            pollKeyEvents();
            updateRays();
            updatePlayer();

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void pollKeyEvents() {
        Keyboard keyboard = Main.getKeyboard();
        if (keyboard.isKeyPressed(KeyEvent.VK_Z)) {
            player.getVelocity().setX((float) Math.cos(player.getAngle() * RADIAN_PI_2) * 0.01f);
            player.getVelocity().setY((float) Math.sin(player.getAngle() * RADIAN_PI_2) * 0.01f);
        } else if (keyboard.isKeyPressed(KeyEvent.VK_S)) {
            player.getVelocity().setX((float) Math.cos(player.getAngle() * RADIAN_PI_2) * -0.01f);
            player.getVelocity().setY((float) Math.sin(player.getAngle() * RADIAN_PI_2) * -0.01f);
        }

        if (keyboard.isKeyPressed(KeyEvent.VK_UP)) {
            screenDistance++;
        } else if (keyboard.isKeyPressed(KeyEvent.VK_DOWN)) {
            if (screenDistance > 1) {
                screenDistance--;
            }
        }

        if (!keyboard.isKeyPressed(KeyEvent.VK_S) && !keyboard.isKeyPressed(KeyEvent.VK_Z)) {
            player.setVelocity(new Vector2F(0.0f, 0.0f));
        }

        if (keyboard.isKeyPressed(KeyEvent.VK_Q)) {
            player.setAngle((player.getAngle() * 10 - 5) / 10);
        } else if (keyboard.isKeyPressed(KeyEvent.VK_D)) {
            player.setAngle((player.getAngle() * 10 + 5) / 10);
        }
    }

    private void updatePlayer() {
        Chunk getPlayerChunk = map.getChunk((int) (player.getX() / 16), (int) (player.getY() / 16));


        if (getPlayerChunk != null) {
            for (Shape collisionBoxShard : player.getCollisionBox()) {
                if (collisionBoxShard instanceof Circle2F circle) {
                    for (Polygon polygon : getPlayerChunk.getPolygons()) {
                        for (Line2F edge : polygon.getEdges()) {
                            Point2F nextCirclePos = new Point2F(
                                    circle.getX() + player.getX() + player.getVelocity().getX(),
                                    circle.getY() + player.getY() + player.getVelocity().getY()
                            );
                            Circle2F nextCircle = new Circle2F(nextCirclePos, circle.getRadius());

                            if (LineCircle.lineCircle(edge, nextCircle)) {
                                Point2F nextCirclePosX = new Point2F(
                                        circle.getX() + player.getX() + player.getVelocity().getX(),
                                        circle.getY() + player.getY()
                                );
                                Circle2F nextCircleX = new Circle2F(nextCirclePosX, circle.getRadius());

                                Point2F nextCirclePosY = new Point2F(
                                        circle.getX() + player.getX(),
                                        circle.getY() + player.getY() + player.getVelocity().getY()
                                );
                                Circle2F nextCircleY = new Circle2F(nextCirclePosY, circle.getRadius());

                                if (LineCircle.lineCircle(edge, nextCircleX)) {
                                    player.getVelocity().setX(0f);
                                }

                                if (LineCircle.lineCircle(edge, nextCircleY)) {
                                    player.getVelocity().setY(0f);
                                }
                            }
                        }
                    }
                }
            }
        }

        player.setX(player.getX() + player.getVelocity().getX());
        player.setY(player.getY() + player.getVelocity().getY());
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
        Chunk[][] chunks = this.getMap().getChunks();

        for (Chunk[] LineChunk : chunks)
            Collections.addAll(chunksFound, LineChunk);

        return chunksFound;
    }

    private void getIntersectEdge(Ray r, ArrayList<Chunk> chunks) {
        float dRay = Float.MAX_VALUE;
        float tempX = r.getIntersectionX();
        float tempY = r.getIntersectionY();

        for (Chunk chunk : chunks) {
            if (chunk != null) {
                for (Polygon polygon : chunk.getPolygons()) {
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
                        t = (s2_x * (p1Y - p3Y) - s2_y * (p1X - p3X)) / v;

                        if (s >= 0 && s <= 1 && t >= 0 && t <= 1) {
                            float intx = p1X + (t * s1_x);
                            float inty = p1Y + (t * s1_y);

                            float tempdRay = computeSquareRayDistance(r, intx, inty);
                            if (tempdRay < dRay) {
                                dRay = tempdRay;
                                tempX = intx;
                                tempY = inty;

                                float dx = tempX - p3X;
                                float dy = tempY - p3Y;

                                int textureIndex = (int) (Math.pow(
                                        ((dx * dx) + (dy * dy)),
                                        0.5f
                                ) * 32) % 16;
                                r.setTextureIndex(textureIndex);
                                r.setColPol(polygon);
                                r.setHeight(polygon.getHeight());
                            }
                        }
                    }
                }
            }
        }

        r.setIntersectionX(tempX);
        r.setIntersectionY(tempY);
    }
    private void getIntersectEdge(Ray r, ArrayList<Chunk> chunks, ArrayList<String> dejavu) {
        float dRay = Float.MAX_VALUE;
        float tempX = r.getIntersectionX();
        float tempY = r.getIntersectionY();

        for (Chunk chunk : chunks) {
            if (chunk != null) {
                for (Polygon polygon : chunk.getPolygons()) {
                    for (Line2F edge : polygon.getEdges()) {
                        boolean isFind = false;
                        for (String s : dejavu)
                            if (s == polygon.getId())
                                isFind = true;
                        if (isFind)
                            break;

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
                        t = (s2_x * (p1Y - p3Y) - s2_y * (p1X - p3X)) / v;

                        if (s >= 0 && s <= 1 && t >= 0 && t <= 1) {
                            float intx = p1X + (t * s1_x);
                            float inty = p1Y + (t * s1_y);

                            float tempdRay = computeSquareRayDistance(r, intx, inty);
                            if (tempdRay < dRay) {
                                dRay = tempdRay;
                                tempX = intx;
                                tempY = inty;

                                float dx = tempX - p3X;
                                float dy = tempY - p3Y;

                                int textureIndex = (int) (Math.pow(
                                        ((dx * dx) + (dy * dy)),
                                        0.5f
                                ) * 32) % 16;
                                r.setTextureIndex(textureIndex);
                                r.setColPol(polygon);
                                r.setHeight(polygon.getHeight());
                            }
                        }
                    }
                }
            }
        }
        r.setIntersectionX(tempX);
        r.setIntersectionY(tempY);
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

        return !(cY < rY) || !(cY + 16 > rY2);
    }

    /**
     * actualise la distance entre la line et le joueur du Rayon (DRay)
     * actualise egalement le intersectionPoint du Rayon
     */
    private float computeSquareRayDistance(Ray r, float x, float y) {
        float x1 = r.getStartX();
        float y1 = r.getStartY();

        float dx = x1 - x;
        float dy = y1 - y;

        return dx * dx + dy * dy;
    }

    private void updateRay(Ray r) {
        ArrayList<Chunk> chunks = findTraveledChunk(r);
        getIntersectEdge(r, chunks);
    }

    public void updateRay2(Ray r, ArrayList<String> dejavu) {
        ArrayList<Chunk> chunks = findTraveledChunk(r);
        getIntersectEdge(r, chunks, dejavu);
    }


    public void start() {
        engineThread.start();
    }

    public void stop() {
        shouldStop = true;
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
