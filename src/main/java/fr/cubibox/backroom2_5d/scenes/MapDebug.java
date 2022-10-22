package fr.cubibox.backroom2_5d.scenes;

import fr.cubibox.backroom2_5d.Main;
import fr.cubibox.backroom2_5d.engine.Ray;
import fr.cubibox.backroom2_5d.engine.maths.Line2F;
import fr.cubibox.backroom2_5d.engine.maths.Point2F;
import fr.cubibox.backroom2_5d.engine.maths.shapes.Rectangle2F;
import fr.cubibox.backroom2_5d.entities.Player;
import fr.cubibox.backroom2_5d.game.Chunk;
import fr.cubibox.backroom2_5d.game.MapObject;
import fr.cubibox.backroom2_5d.graphics.Canvas;
import fr.cubibox.backroom2_5d.utils.TimeUtils;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelBuffer;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.net.URL;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import static fr.cubibox.backroom2_5d.Main.*;
import static fr.cubibox.backroom2_5d.engine.Engine.screenDistance;
import static fr.cubibox.backroom2_5d.engine.Engine.wallHeight;
import static fr.cubibox.backroom2_5d.engine.Ray.RADIAN_PI_2;
import static fr.cubibox.backroom2_5d.utils.ImageUtils.TILE_SIZE;
import static java.lang.Math.abs;

public class MapDebug extends AnimationTimer implements Initializable {
    @FXML
    private Pane pane;

    // Variables nécessaire pour la boucle
    private long targetFps = 60L;
    private long lastTime = 0L;
    //


    // Variables pour charger les modèles / textures / objects à charger sur la map.
    
    //


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.start();
    }

    @Override
    public void handle(long currentTime) {
        long interval = TimeUtils.ONE_SECOND_IN_NANO / targetFps;

        if (currentTime - lastTime > interval) {
            clear();

            update();

            lastTime = currentTime;
        }
    }

    private void clear() {
        pane.getChildren().clear();
    }

    private void update() {
        pane.getChildren().clear();

        Canvas canvas = new Canvas(windowWidth, windowHeight);
        canvas.fillRect(new Rectangle2F(0, 0, windowWidth, windowHeight), new Color(0, 0, 0).getRGB());

        float size = toScreenX(1);

        // draw grid
        for (int y = 0; y < canvas.height; y++) {
            for (int x = 0; x < canvas.width; x++) {
                canvas.drawRect(
                        new Rectangle2F(
                                toScreenX(x),
                                toScreenX(y),
                                size,
                                size
                        ),
                        new Color(25, 25, 25).getRGB());
            }
        }


        // draw the player's rays
        float width = windowWidth;
        float height = windowHeight;
        float halfHeight = height / 2f;

        Ray[] rays = Main.getEngine().getRays();

        if (rays.length > 0) {
            Player player = Main.getEngine().getPlayer();
            canvas.drawPixel(
                    new Point2F(
                            toScreenX(player.getX()),
                            toScreenX(player.getY())
                    ),
                    new Color(255, 0, 0).getRGB()
            );

            for (int rayIndex = 0; rayIndex < rays.length; rayIndex++) {
                Ray ray = rays[rayIndex];

                if (ray != null) {
                    float rayDX = ray.getIntersectionX() - ray.getStartX();
                    float rayDY = ray.getIntersectionY() - ray.getStartY();

                    float rayDistance = (float) (Math.pow((rayDX * rayDX) + (rayDY * rayDY), 0.5));

                    // Perspective correction
                    float rayAngleFromMiddle = player.getAngle() - ray.getAngle();
                    float rayAngleDiffAbsCos = (float) abs(Math.cos(rayAngleFromMiddle * RADIAN_PI_2));
                    rayDistance = rayDistance * rayAngleDiffAbsCos;

                    float perceivedHeight = (screenDistance * (wallHeight / rayDistance)) / 6f;

                    // Draw Rectangle

                    float wallToBorder = (canvas.height - perceivedHeight) / 2f;

                    // Draw floor and ceil
                    if (wallToBorder > 0) {
                        float pixelsToBottom = (float) Math.floor(wallToBorder);
                        float pixelRowHeight = halfHeight - pixelsToBottom;

                        for (float y = pixelRowHeight; y < halfHeight; y++) {
                            float directDistFloor = (screenDistance * halfHeight) / (int) (y);
                            float realDistFloor = (float) (directDistFloor / Math.cos((player.getAngle() - ray.getAngle()) * RADIAN_PI_2));

                            float floorX = (float) (player.getX() + Math.cos(ray.getAngle() * RADIAN_PI_2) * realDistFloor / (screenDistance / 2f));
                            float floorY = (float) (player.getY() + Math.sin(ray.getAngle() * RADIAN_PI_2) * realDistFloor / (screenDistance / 2f));

                            canvas.drawPixel(
                                    new Point2F(
                                            toScreenX(floorX),
                                            toScreenX(floorY)
                                    ),
                                    new Color(0, 255, 0).getRGB()
                            );
                        }
                    }
                }
            }
        }

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
                                        new Color(0, 0, 255).getRGB()
                                );
                            }
                        }
                    }
                }
            }
        }

        PixelBuffer<IntBuffer> pixelBuffer = new PixelBuffer<>(canvas.width, canvas.height, canvas.getBuffer(), PixelFormat.getIntArgbPreInstance());
        WritableImage image = new WritableImage(pixelBuffer);
        pixelBuffer.updateBuffer(b -> null);
        pane.getChildren().add(new ImageView(image));
    }
}
