package fr.cubibox.backroom2_5d;

import fr.cubibox.backroom2_5d.engine.Ray;
import fr.cubibox.backroom2_5d.entities.Player;
import fr.cubibox.backroom2_5d.utils.ImageUtils;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelBuffer;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.nio.IntBuffer;
import java.util.ResourceBundle;

import static fr.cubibox.backroom2_5d.Main.windowHeight;
import static fr.cubibox.backroom2_5d.Main.windowWidth;
import static fr.cubibox.backroom2_5d.engine.Engine.screenDistance;
import static fr.cubibox.backroom2_5d.engine.Engine.wallHeight;
import static fr.cubibox.backroom2_5d.engine.Ray.RADIAN_PI_2;
import static fr.cubibox.backroom2_5d.utils.ImageUtils.TILE_SIZE;
import static java.lang.Math.abs;

public class Controller3DOLD implements Initializable {
    private Color[][] wallTextureMatrix;
    private Color[][] floorTextureMatrix;
    private Color[][] ceilTextureMatrix;

    @FXML
    private Pane pane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // es
        System.out.println("Chargement de la map..");

        // Chargement des textures de mur
        wallTextureMatrix = new Color[TILE_SIZE][TILE_SIZE];
        BufferedImage wallTexture = ImageUtils.readImage("textures/wall.png");

        // Chargement des textures de sol
        ceilTextureMatrix = new Color[TILE_SIZE][TILE_SIZE];
        BufferedImage ceilTexture = ImageUtils.readImage("textures/ceil.png");

        // Chargement des textures de plafond
        floorTextureMatrix = new Color[TILE_SIZE][TILE_SIZE];
        BufferedImage floorTexture = ImageUtils.readImage("textures/floor.png");

        for (int y = 0; y < (TILE_SIZE); y++) {
            for (int x = 0; x < (TILE_SIZE); x++) {
                int rgb = wallTexture.getRGB(x, y);
                wallTextureMatrix[y][x] = Color.rgb((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF);

                rgb = ceilTexture.getRGB(x, y);
                ceilTextureMatrix[y][x] = Color.rgb((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF);

                rgb = floorTexture.getRGB(x, y);
                floorTextureMatrix[y][x] = Color.rgb((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF);
            }
        }

        System.out.println("Map chargée !");

        Movement clock = new Movement();
        clock.start();
    }

    public void drawFunction() {
        pane.getChildren().clear();

        Canvas canvas = new Canvas(windowWidth, windowHeight);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        PixelWriter pw = gc.getPixelWriter();

        float width = (float) canvas.getWidth();
        float height = (float) canvas.getHeight();
        float halfHeight = height / 2f;

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width, height);

        Ray [] rays = Main.getEngine().getRays();

        if (rays.length > 0) {
            Player player = Main.getEngine().getPlayer();
            int bandWidth = (int) (width / rays.length);

            for (int rayIndex = 0; rayIndex < rays.length; rayIndex++) {
                Ray ray = rays[rayIndex];
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

                for (int y = 0; y < perceivedHeight; y ++) {
                    for (int x = 0; x < bandWidth; x ++) {
                        int i = (int) perceivedHeight % TILE_SIZE;
                        int textureX = (ray.getTextureIndex() + x) % TILE_SIZE;
                        pw.setColor((int) (startX + x), (int) (startY + y), wallTextureMatrix[i][textureX]);
                    }
                }

                /*for (int i = 0; i < bandWidth; i++) {
                    // Set texture image on the band of the rectangle
                    int textureX = (ray.getTextureIndex() + i) % TILE_SIZE;
                    Image texture = wallStripTexture[textureX];

                    // Fit the texture to the band
                    gc.setFill(new ImagePattern(texture, startX, startY, 1, perceivedHeight, false));
                    gc.setStroke(Color.BLACK);
                    gc.fillRect(startX + i, startY, 1, perceivedHeight);
                }*/

                // Draw floor and ceil
                /*if (startY > 0) {
                    int pixelRowHeight = (int) ((halfHeight) - floorToTop);

                    for (int y = pixelRowHeight; y < halfHeight; y++) {
                        float directDistFloor = halfHeight / y;
                        float currentDistFloor = directDistFloor / rayDistance;

                        float floorX = (player.getX() + currentDistFloor * rayDX);
                        float floorY = (player.getY() + currentDistFloor * rayDY);

                        int floorTexX = (int) (floorX * (TILE_SIZE / 2)) % TILE_SIZE;
                        int floorTexY = (int) (floorY * (TILE_SIZE / 2)) % TILE_SIZE;

                        Color color = floorStripTexture[abs(floorTexX + (floorTexY * TILE_SIZE))];
                        gc.setFill(color);
                        gc.fillRect(startX, halfHeight - y, bandWidth, bandWidth);

                        Color color2 = ceilStripTexture[abs(floorTexX + (floorTexY * TILE_SIZE))];
                        gc.setFill(color2);
                        gc.fillRect(startX, halfHeight + y, bandWidth, bandWidth);
                    }
                }*/
            }
        }

        pane.getChildren().add(canvas);
    }

    private class Movement extends AnimationTimer {
        private long last = 0;

        @Override
        public void handle(long now) {
            long FRAMES_PER_SEC = 30L;
            long INTERVAL = 1000000000L / FRAMES_PER_SEC;
            if (now - last > INTERVAL) {
                drawFunction();

                last = now;
            }
        }
    }
}
