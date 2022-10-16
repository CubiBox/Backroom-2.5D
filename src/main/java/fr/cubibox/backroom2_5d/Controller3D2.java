package fr.cubibox.backroom2_5d;

import fr.cubibox.backroom2_5d.engine.Ray;
import fr.cubibox.backroom2_5d.entities.Player;
import fr.cubibox.backroom2_5d.utils.ImageUtils;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static fr.cubibox.backroom2_5d.Main.windowHeight;
import static fr.cubibox.backroom2_5d.Main.windowWidth;
import static fr.cubibox.backroom2_5d.engine.Engine.screenDistance;
import static fr.cubibox.backroom2_5d.engine.Engine.wallHeight;
import static fr.cubibox.backroom2_5d.engine.Ray.RADIAN_PI_2;
import static fr.cubibox.backroom2_5d.utils.ImageUtils.TILE_SIZE;
import static java.lang.Math.abs;

public class Controller3D2 implements Initializable {
    private Image[] wallStripTexture;
    private Color[] floorStripTexture;
    private Color[] ceilStripTexture;

    @FXML
    private Pane pane;
    @FXML
    private TextArea functionText;
    @FXML
    private VBox vBoxPanel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // es
        System.out.println("Chargement de la map..");

        // Chargement des textures de mur
        wallStripTexture = new Image[TILE_SIZE];
        for (int i = 0; i < TILE_SIZE; i++) {
            InputStream is2 = Main.class.getResourceAsStream("textures/wall/texture-" + (i + 1) + ".png");

            if (is2 != null) {
                wallStripTexture[i] = new Image(is2);
            }
        }

        // Chargement des textures de sol
        ceilStripTexture = new Color[TILE_SIZE * TILE_SIZE];
        BufferedImage ceilTexture = ImageUtils.readImage("textures/ceil.png");
        for (int i = 0; i < (TILE_SIZE * TILE_SIZE); i++) {
            int rgb = ceilTexture.getRGB(i % TILE_SIZE, i / TILE_SIZE);
            ceilStripTexture[i] = Color.rgb((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF);
        }

        // Chargement des textures de plafond
        floorStripTexture = new Color[TILE_SIZE * TILE_SIZE];
        BufferedImage floorTexture = ImageUtils.readImage("textures/floor.png");
        for (int i = 0; i < (TILE_SIZE * TILE_SIZE); i++) {
            int rgb = floorTexture.getRGB(i % TILE_SIZE, i / TILE_SIZE);
            floorStripTexture[i] = Color.rgb((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF);
        }

        System.out.println("Map chargée !");

        Movement clock = new Movement();
        clock.start();
    }

    public void drawFunction() {
        pane.getChildren().clear();

        Canvas canvas = new Canvas(windowWidth, windowHeight);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        float width = (float) canvas.getWidth();
        float height = (float) canvas.getHeight();
        float halfHeight = height / 2f;

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width, height);

        ArrayList<Ray> rays = Main.getEngine().getRays();

        if (rays.size() > 0) {
            Player player = Main.getEngine().getPlayer();
            int bandWidth = (int) (width / rays.size());

            for (int rayIndex = 0; rayIndex < rays.size(); rayIndex++) {
                Ray ray = rays.get(rayIndex);

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

                for (int i = 0; i < bandWidth; i++) {
                    // Set texture image on the band of the rectangle
                    Image texture = wallStripTexture[(ray.getTextureIndex() + i) % TILE_SIZE];

                    // Fit the texture to the band
                    gc.setFill(new ImagePattern(texture, startX, startY, 1, perceivedHeight, false));
                    gc.setStroke(Color.BLACK);
                    gc.fillRect(startX + i, startY, 1, perceivedHeight);
                }

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
