package fr.cubibox.backroom2_5d;

import fr.cubibox.backroom2_5d.engine.Ray;
import fr.cubibox.backroom2_5d.game.Texture;
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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static fr.cubibox.backroom2_5d.Main.windowHeight;
import static fr.cubibox.backroom2_5d.Main.windowWidth;
import static fr.cubibox.backroom2_5d.engine.Engine.screenDistance;
import static fr.cubibox.backroom2_5d.engine.Engine.wallHeight;
import static fr.cubibox.backroom2_5d.engine.Ray.RADIAN_PI_2;
import static fr.cubibox.backroom2_5d.utils.ImageUtils.readImage;

public class Controller3D implements Initializable {

    //private BufferedImage[] floorStripTexture;
    //private BufferedImage[] ceilStripTexture;
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
        BufferedImage wallTexture = readImage("textures/wall.png");
        Main.getEngine().getMap().setWall(new Texture(wallTexture));

        /*
        for (int i = 0; i < TILE_SIZE; i++) {
            InputStream is = Main.class.getResourceAsStream("textures/wall" + (i + 1) + ".png");

            if (is != null) {
                wallStripTexture[i] = new Image(is);
            }
        }*/

        /* Chargement des textures de sol
        BufferedImage ceilTexture = readImage("textures/ceil.png");
        ceilStripTexture = new BufferedImage[ImageUtils.TILE_SIZE * ImageUtils.TILE_SIZE];
        for (int i = 0; i < ImageUtils.TILE_SIZE; i++) {
            for (int j = 0; j < ImageUtils.TILE_SIZE; j++) {
                ceilStripTexture[(j + (i * TILE_SIZE))] = ceilTexture.getSubimage(i, j, 1, 1);
            }
        }

        // Chargement des textures de plafond
        BufferedImage floorTexture = readImage("textures/floor.png");
        floorStripTexture = new BufferedImage[ImageUtils.TILE_SIZE * ImageUtils.TILE_SIZE];
        for (int i = 0; i < ImageUtils.TILE_SIZE; i++) {
            for (int j = 0; j < ImageUtils.TILE_SIZE; j++) {
                floorStripTexture[(j + (i * TILE_SIZE))] = floorTexture.getSubimage(i, j, 1, 1);
            }
        }*/

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

            ImagePattern ip;
            Texture wall = Main.getEngine().getMap().getWall();
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

                if (perceivedHeight > ImageUtils.TILE_SIZE) {
                    perceivedHeight = ImageUtils.TILE_SIZE;
                }

                ip = new ImagePattern(wall.getWallStripTexture().get(ray.getTextureIndex()));
                //Color ip = Color.PAPAYAWHIP;

                r.setFill(ip);
                r.setStroke(ip);

                drawPane.getChildren().add(r);
                drawPane.getChildren().add(shadow);

                mul++;
                /*
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

                        BufferedImage bandFloor = ImageUtils.getImagePixel(floorTexture, floorTexX, floorTexY);
                        ImagePattern ipFloor = new ImagePattern(ImageUtils.convertToFxImage(bandFloor));

                        Rectangle floor = new Rectangle(startX, y, width, width);
                        floor.setFill(ipFloor);
                        floor.setStroke(ipFloor);
                        coordinateSystem.getChildren().add(floor);


                        BufferedImage bandCeil = ImageUtils.getImagePixel(ceilingTexture, floorTexX, floorTexY);
                        ImagePattern ipCeil = new ImagePattern(ImageUtils.convertToFxImage(bandCeil));

                        Rectangle ceil = new Rectangle(startX, y, width, width);
                        ceil.setFill(ipCeil);
                        ceil.setStroke(ipCeil);
                        coordinateSystem.getChildren().add(ceil);
                    }
                }*/
            }
        }

        //System.out.println(drawPane.getChildren().size());
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
