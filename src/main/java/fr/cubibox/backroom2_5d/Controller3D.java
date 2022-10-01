package fr.cubibox.backroom2_5d;

import fr.cubibox.backroom2_5d.engine.Ray;
import fr.cubibox.backroom2_5d.entities.Player;
import fr.cubibox.backroom2_5d.utils.ImageUtils;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

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

public class Controller3D implements Initializable {
    private Image[] wallStripTexture;
    private Color[] floorStripTexture;
    private Color[] ceilStripTexture;

    @FXML
    private Pane drawPane;
    @FXML
    private TextArea functionText;
    @FXML
    private VBox vBoxPanel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Chargement de la map...");
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
        drawPane.getChildren().clear();

        ArrayList<Ray> playersRay = Main.getEngine().getRays();

        if (playersRay.size() > 0) {

            float mul = 0;
            float width = windowWidth / (Main.getEngine().getRayCount() + 1);

            for (Ray ray : playersRay) {
                float rayDX = ray.getIntersectionX() - ray.getStartX();
                float rayDY = ray.getIntersectionY() - ray.getStartY();

                float rayDistance = (float) (Math.pow((rayDX * rayDX) + (rayDY * rayDY), 0.5));

                float rayAngleFromMiddle = Main.getEngine().getPlayer().getAngle() - ray.getAngle();
                float rayAngleDiffAbsCos = (float) Math.abs(Math.cos(rayAngleFromMiddle * RADIAN_PI_2));
                rayDistance = rayDistance * rayAngleDiffAbsCos;

                float perceivedHeight = (screenDistance * (wallHeight / rayDistance)) / 6f;


                // Draw Rectangle
                float startX = mul * width;
                float startY = (windowHeight - perceivedHeight) / 2f;

                Rectangle r = new Rectangle(startX, startY, width, perceivedHeight);
                Rectangle shadow = new Rectangle(startX, startY, width, perceivedHeight);

                float grey = 0f + (1f / rayDistance);

                if (grey > 1f) {
                    grey = 1f;
                } else if (grey < 0f) {
                    grey = 0f;
                }

                shadow.setFill(Color.color(grey, grey, grey, 0.25f));
                shadow.setStroke(Color.color(grey, grey, grey, 0.25f));

                if (perceivedHeight > TILE_SIZE) {
                    perceivedHeight = TILE_SIZE;
                }

                System.out.println(ray.getTextureIndex());

                for (int i = 0; i < width; i++) {
                    ImagePattern ip = new ImagePattern(
                            wallStripTexture[(ray.getTextureIndex() + i) % TILE_SIZE],
                            (0.0 + i) / width,
                            0.0,
                            (1.0 / width),
                            (1.0 / perceivedHeight),
                            false
                    );


                    r.setFill(ip);
                    r.setStroke(ip);
                }

                drawPane.getChildren().add(r);
                drawPane.getChildren().add(shadow);

                mul++;

                Player player = Main.getEngine().getPlayer();
                float win2 = windowHeight / 2f;

                // Draw floor
                float floorToTop = (windowHeight - perceivedHeight) / 2f;
                if (floorToTop > 0) {
                    int pixels = (int) floorToTop;
                    int pixelRowHeight = (int) ((win2) - pixels);

                    for (int y = pixelRowHeight; y < win2; y++) {
                        float directDistFloor = (win2) / y;
                        float currentDistFloor = directDistFloor / rayDistance;

                        float floorX = player.getX() + currentDistFloor * rayDX;
                        float floorY = player.getY() + currentDistFloor * rayDY;

                        int floorTexX = (int) (floorX * 64) % 64;
                        int floorTexY = (int) (floorY * 64) % 64;

                        Rectangle floor = new Rectangle(startX, y, width, width);
                        floor.setFill(floorStripTexture[(floorTexX + floorTexY * TILE_SIZE)]);
                        floor.setStroke(floorStripTexture[(floorTexX + floorTexY * TILE_SIZE)]);
                        drawPane.getChildren().add(floor);

                        Rectangle ceil = new Rectangle(startX, y, width, width);
                        ceil.setFill(ceilStripTexture[(floorTexX + floorTexY * TILE_SIZE)]);
                        ceil.setStroke(ceilStripTexture[(floorTexX + floorTexY * TILE_SIZE)]);
                        drawPane.getChildren().add(ceil);
                    }
                }
            }
        }
    }

    public Circle drawPoint(double x, double y) {
        return new Circle(Main.toScreenX(x), Main.toScreenY(y), 0.3, Color.RED);
    }

    private class Movement extends AnimationTimer {
        private final long FRAMES_PER_SEC = 60L;
        private final long INTERVAL = 1000000000L / FRAMES_PER_SEC;

        private long last = 0;

        @Override
        public void handle(long now) {
            if (now - last > INTERVAL) {
                drawFunction();

                last = now;
            }
        }
    }
}
