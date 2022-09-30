package fr.cubibox.backroom2_5d;

import fr.cubibox.backroom2_5d.engine.Ray;
import fr.cubibox.backroom2_5d.utils.ImageUtils;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static fr.cubibox.backroom2_5d.Main.windowHeight;
import static fr.cubibox.backroom2_5d.Main.windowWidth;
import static fr.cubibox.backroom2_5d.engine.Engine.screenDistance;
import static fr.cubibox.backroom2_5d.engine.Engine.wallHeight;
import static fr.cubibox.backroom2_5d.engine.Ray.RADIAN_PI_2;
import static fr.cubibox.backroom2_5d.utils.ImageUtils.TILE_SIZE;
import static fr.cubibox.backroom2_5d.utils.ImageUtils.readImage;

public class Controller3D2 implements Initializable {
    private Image[] wallStripTexture;
    private BufferedImage[] floorStripTexture;
    private BufferedImage[] ceilStripTexture;

    @FXML
    private Pane pane;

    private Canvas drawCanvas;

    @FXML
    private TextArea functionText;
    @FXML
    private VBox vBoxPanel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Chargement de la map...");

        // Chargement des textures de mur
        BufferedImage wallTexture = readImage("textures/wall.png");
        wallTexture = ImageUtils.resize(wallTexture, ImageUtils.TILE_SIZE * 4, ImageUtils.TILE_SIZE * 4);
        wallStripTexture = new Image[ImageUtils.TILE_SIZE];
        for (int i = 0; i < ImageUtils.TILE_SIZE; i++) {
            Image img = ImageUtils.convertToFxImage(
                    wallTexture.getSubimage(i, 0, 1, ImageUtils.TILE_SIZE)
            );

            wallStripTexture[i] = img;
        }

        // Chargement des textures de sol
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
        }

        System.out.println("Map chargée !");

        drawCanvas = new Canvas(windowWidth, windowHeight);
        pane.getChildren().add(drawCanvas);

        Movement clock = new Movement();
        clock.start();
    }

    public void drawFunction() {
        drawCanvas.getGraphicsContext2D().clearRect(0, 0, windowWidth, windowHeight);

        ArrayList<Ray> playersRay = Main.getEngine().getRays();

        if (playersRay.size() > 0) {
            float width = windowWidth / (Main.getEngine().getRayCount() + 1);

            for (int i = 0; i < playersRay.size(); i++) {
                Ray ray = playersRay.get(i);

                float rayDX = ray.getIntersectionX() - ray.getStartX();
                float rayDY = ray.getIntersectionY() - ray.getStartY();

                float rayDistance = (float) (Math.pow((rayDX * rayDX) + (rayDY * rayDY), 0.5));

                float rayAngleFromMiddle = Main.getEngine().getPlayer().getAngle() - ray.getAngle();
                float rayAngleDiffAbsCos = (float) Math.abs(Math.cos(rayAngleFromMiddle * RADIAN_PI_2));
                rayDistance = rayDistance * rayAngleDiffAbsCos;

                float perceivedHeight = (screenDistance * (wallHeight / rayDistance)) / 6f;


                // Draw Rectangle
                float startX = i * width;
                float startY = (windowHeight - perceivedHeight) / 2f;
                //Rectangle r = new Rectangle(startX, startY, width, perceivedHeight);
                //Rectangle shadow = new Rectangle(startX, startY, width, perceivedHeight);

                float grey = 0.5f + (1f / rayDistance);

                if (grey > 1f) {
                    grey = 1f;
                } else if (grey < 0f) {
                    grey = 0f;
                }

                /*shadow.setFill(Color.color(grey, grey, grey, 0.25f));
                shadow.setStroke(Color.color(grey, grey, grey, 0.25f));*/

                if (perceivedHeight > ImageUtils.TILE_SIZE) {
                    perceivedHeight = ImageUtils.TILE_SIZE;
                }

                Color ip = new Color(0f, 0f, 0f, 1f);

                System.out.println("StartX : " + startX + " StartY : " + startY + " Width : " + width + " Height : " + perceivedHeight);

                drawCanvas.getGraphicsContext2D().setFill(ip);
                drawCanvas.getGraphicsContext2D().fillRect(startX, Math.abs(startY), width, perceivedHeight);

                //Player player = Main.getEngine().getPlayer();
                //float win2 = windowHeight / 2f;

                // Draw floor
                /*float floorToTop = (windowHeight - perceivedHeight) / 2f;
                if (floorToTop > 0) {
                    int pixels = (int) floorToTop;
                    int pixelRowHeight = (int) ((win2) - pixels);

                    /*
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
    }

    public Circle drawPoint(double x, double y) {
        return new Circle(Main.toScreenX(x), Main.toScreenY(y), 0.3, Color.RED);
    }

    private class Movement extends AnimationTimer {
        private final long FRAMES_PER_SEC = 144L;
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