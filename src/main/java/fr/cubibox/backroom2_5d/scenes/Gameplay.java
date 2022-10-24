package fr.cubibox.backroom2_5d.scenes;

import fr.cubibox.backroom2_5d.Main;
import fr.cubibox.backroom2_5d.engine.Engine;
import fr.cubibox.backroom2_5d.engine.Ray;
import fr.cubibox.backroom2_5d.engine.maths.Point2F;
import fr.cubibox.backroom2_5d.engine.maths.shapes.Rectangle2F;
import fr.cubibox.backroom2_5d.entities.Player;
import fr.cubibox.backroom2_5d.graphics.Canvas;
import fr.cubibox.backroom2_5d.utils.ImageUtils;
import fr.cubibox.backroom2_5d.utils.TimeUtils;
import javafx.animation.AnimationTimer;
import javafx.css.Styleable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelBuffer;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.nio.IntBuffer;
import java.util.Random;
import java.util.ResourceBundle;

import static fr.cubibox.backroom2_5d.Main.*;
import static fr.cubibox.backroom2_5d.engine.Engine.screenDistance;
import static fr.cubibox.backroom2_5d.engine.Engine.wallHeight;
import static fr.cubibox.backroom2_5d.engine.Ray.RADIAN_PI_2;
import static fr.cubibox.backroom2_5d.utils.ImageUtils.TILE_SIZE;
import static fr.cubibox.backroom2_5d.utils.TimeUtils.ONE_SECOND_IN_NANO;
import static java.lang.Math.abs;

public class Gameplay extends AnimationTimer implements Initializable {
    @FXML
    private Pane pane;

    // Variables nécessaire pour la boucle
    private long targetFps = 60L;
    private long lastTime = 0L;
    //


    // Variables pour charger les modèles / textures / objects à charger sur la map.
    private int[][] wallTextureMatrix;
    private int[][] floorTextureMatrix;
    private int[][] ceilTextureMatrix;
    //


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // es
        System.out.println("Chargement de la map..");

        // Chargement des textures de mur
        wallTextureMatrix = new int[TILE_SIZE][TILE_SIZE];
        BufferedImage wallTexture = ImageUtils.readImage("textures/wall.png");

        // Chargement des textures de sol
        ceilTextureMatrix = new int[TILE_SIZE][TILE_SIZE];
        BufferedImage ceilTexture = ImageUtils.readImage("textures/ceil.png");

        // Chargement des textures de plafond
        floorTextureMatrix = new int[TILE_SIZE][TILE_SIZE];
        BufferedImage floorTexture = ImageUtils.readImage("textures/floor.png");

        for (int y = 0; y < (TILE_SIZE); y++) {
            for (int x = 0; x < (TILE_SIZE); x++) {
                wallTextureMatrix[y][x] = wallTexture.getRGB(x, y);
                ceilTextureMatrix[y][x] = ceilTexture.getRGB(x, y);
                floorTextureMatrix[y][x] = floorTexture.getRGB(x, y);
            }
        }

        System.out.println("Map chargée !");

        this.start();
    }

    @Override
    public void handle(long currentTime) {
        long interval = ONE_SECOND_IN_NANO / targetFps;

        if (currentTime - lastTime > interval) {
            update();
            lastTime = currentTime;
        }
    }

    private void update() {
        if (getEngine().isReady()) {
            pane.getChildren().clear();

            Canvas canvas = new Canvas(windowWidth, windowHeight);

            float width = windowWidth;
            float height = windowHeight;
            float halfHeight = height / 2f;

            Ray[] rays = getEngine().getRays().clone();

            Player player = getEngine().getPlayer();
            int bandWidth = (int) (width / (rays.length - 1));

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
                    float startX = rayIndex * bandWidth;
                    float startY = (height - perceivedHeight) / 2f;

                    float wallToBorder = (canvas.height - perceivedHeight) / 2f;

                    // Draw floor and ceil
                    if (wallToBorder > 0) {
                        float y = 1;

                        for (Point2F p : ray.getFloorPoints()) {
                            int floorTextureX = (int) ((p.getX() - Math.floor(p.getX())) * TILE_SIZE) % TILE_SIZE;
                            int floorTextureY = (int) ((p.getY() - Math.floor(p.getY())) * TILE_SIZE) % TILE_SIZE;

                            canvas.drawPixel(
                                    new Point2F(startX, halfHeight - y),
                                    floorTextureMatrix[floorTextureY][floorTextureX]
                            );

                            canvas.drawPixel(
                                    new Point2F(startX, halfHeight + y - 1),
                                    ceilTextureMatrix[floorTextureY][floorTextureX]
                            );

                            y++;
                        }
                    }

                    canvas.fillRect(
                            new Rectangle2F(startX, startY, bandWidth, perceivedHeight),
                            new Color(100, 100, 100).getRGB()
                    );
                }
            }

            PixelBuffer<IntBuffer> pixelBuffer = new PixelBuffer<>(canvas.width, canvas.height, canvas.getBuffer(), PixelFormat.getIntArgbPreInstance());
            WritableImage image = new WritableImage(pixelBuffer);
            pixelBuffer.updateBuffer(b -> null);
            pane.getChildren().add(new ImageView(image));
        }
    }
}
