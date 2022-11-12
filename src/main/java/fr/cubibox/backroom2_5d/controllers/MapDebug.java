package fr.cubibox.backroom2_5d.controllers;

import fr.cubibox.backroom2_5d.engine.GameScene;
import fr.cubibox.backroom2_5d.engine.graphics.Canvas;
import fr.cubibox.backroom2_5d.game.Backroom2D;
import fr.cubibox.backroom2_5d.game.entities.Player;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.PixelBuffer;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;

import java.net.URL;
import java.nio.IntBuffer;

import java.util.ResourceBundle;

import static fr.cubibox.backroom2_5d.BackroomsMain.*;
import static fr.cubibox.backroom2_5d.engine.GameEngine.ONE_SECOND_IN_MILLIS;
import static fr.cubibox.backroom2_5d.engine.GameEngine.getInstance;

public class MapDebug extends AnimationTimer implements Initializable {

    private final long timeForRender = ONE_SECOND_IN_MILLIS / 30L;
    public Label ppos;
    public Label pdir;
    public Label pvel;
    private float deltaR = 0f;
    private long lastTime;
    private long renderTime = System.currentTimeMillis();

    public Slider mapScale;
    public CheckBox drawPlayerRays;
    public CheckBox drawPlayerDirection;
    public CheckBox drawPlayerCollision;

    @FXML
    private javafx.scene.canvas.Canvas fxCanvas;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lastTime = System.currentTimeMillis();
        this.start();
    }

    @Override
    public void handle(long currentTime) {
        long now = System.currentTimeMillis();
        deltaR += (now - lastTime) / timeForRender;

        getInstance().getGameScene().input();

        if (deltaR >= 1) {
            this.render(now - renderTime);
            deltaR--;
            renderTime = now;
        }
        
        try {
            long sleepTime = now + timeForRender - System.currentTimeMillis();

            if (sleepTime > 0) {
                Thread.sleep(sleepTime);
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }

        lastTime = now;
    }

    /*

    private void update(Canvas canvas) {
        int rayCount = 72;

        Player player = getEngine().getPlayer();
        drawMap(canvas);

        if (drawPlayerRays.isSelected()) {
            int angleStep = windowWidth / rayCount;
            float halfWindowWidth = windowWidth / 2f;

            for (int x = 0; x <= rayCount; x++) {
                float step = x * angleStep;
                float rayAngle = ((float) Math.atan((step - halfWindowWidth) / halfWindowWidth) / RADIAN_PI_2) + player.getAngle();

                Ray r = new Ray(player.getX(), player.getY(), rayAngle);
                updateRay(r);

                drawRay(r, x, canvas);
            }
        }
        canvas.fillRect((int) toScreenX(player.getX()) - 2, (int) toScreenX(player.getY()) - 2, 4, 4, new Color(233, 0, 0, 255).getRGB());
    }

    private void drawMap(Canvas canvas) {
        canvas.fillRect(0, 0, canvas.width, canvas.height, new Color(0, 0, 0, 255).getRGB());

        // Draw the map
        Chunk[][] chunks = getEngine().getMap().getChunks();

        for (Chunk[] chunksL : chunks) {
            for (Chunk chunk: chunksL) {
                if (chunk != null) {
                    for (MapObject object : chunk.getMapObjects()) {
                        if (object != null) {
                            for (Line2F edge : object.getEdges()) {
                                canvas.drawLine(
                                        (int) toScreenX(edge.getA().getX()),
                                        (int) toScreenX(edge.getA().getY()),
                                        (int) toScreenX(edge.getB().getX()),
                                        (int) toScreenX(edge.getB().getY()),
                                        new Color(0, 255, 255).getRGB()
                                );
                            }
                        }
                    }
                }
            }
        }
    }

    private void drawRay(Ray ray, int rayIndex, Canvas canvas) {
        if (ray.getIntersectionX() < Float.MAX_VALUE && ray.getIntersectionY() < Float.MAX_VALUE) {
            canvas.drawLine(
                    (int) toScreenX(getEngine().getPlayer().getX()),
                    (int) toScreenX(getEngine().getPlayer().getY()),
                    (int) toScreenX(ray.getIntersectionX()),
                    (int) toScreenX(ray.getIntersectionY()),
                    new Color(0, 255, 0).getRGB()
            );
        }
    }

    private void getIntersectEdge(Ray r, ArrayList<Chunk> chunks) {
        float dRay = Float.MAX_VALUE;
        float tempX = r.getIntersectionX();
        float tempY = r.getIntersectionY();

        for (Chunk chunk : chunks) {
            if (chunk != null) {
                for (MapObject mapObject : chunk.getMapObjects()) {
                    for (Line2F edge : mapObject.getEdges()) {
                        float p1X = r.getStartX();
                        float p1Y = r.getStartY();
                        float p2X = r.getIntersectionX();
                        float p2Y = r.getIntersectionY();

                        float p3X = edge.getA().getX();
                        float p3Y = edge.getA().getY();
                        float p4X = edge.getB().getX();
                        float p4Y = edge.getB().getY();

                        float s1_x, s1_y, s2_x, s2_y;
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
                            }
                        }
                    }
                }
            }
        }

        r.setIntersectionX(tempX);
        r.setIntersectionY(tempY);
    }

    private float computeSquareRayDistance(Ray r, float x, float y) {
        float x1 = r.getStartX();
        float y1 = r.getStartY();

        float dx = x1 - x;
        float dy = y1 - y;

        return dx * dx + dy * dy;
    }

    private void updateRay(Ray r) {
        ArrayList<Chunk> chunksFound = new ArrayList<>();
        Chunk[][] chunks = getEngine().getMap().getChunks();

        for (Chunk[] LineChunk : chunks)
            Collections.addAll(chunksFound, LineChunk);

        getIntersectEdge(r, chunksFound);
    }*/

    public void render(float dt) {
        Canvas canvas = new Canvas(windowWidth, windowHeight);
        GameScene gameScene = getInstance().getGameScene();

        gameScene.render(canvas, dt);

        if (gameScene instanceof Backroom2D bscene) {
            Player p = bscene.getPlayer();
            ppos.setText(p.getPos().toString());
            pdir.setText(p.getDirection().toString());
            pvel.setText(p.getVelocity().toString());
        }

        PixelBuffer<IntBuffer> pixelBuffer = new PixelBuffer<>(canvas.width, canvas.height, canvas.getBuffer(), PixelFormat.getIntArgbPreInstance());
        pixelBuffer.updateBuffer(b -> null);
        WritableImage image = new WritableImage(pixelBuffer);

        fxCanvas.setWidth(canvas.width);
        fxCanvas.setHeight(canvas.height);
        fxCanvas.getGraphicsContext2D().clearRect(0, 0, canvas.width, canvas.height);
        fxCanvas.getGraphicsContext2D().drawImage(image, 0, 0);
    }
}
