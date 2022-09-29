package fr.cubibox.backroom2_5d;

import fr.cubibox.backroom2_5d.engine.Ray;
import fr.cubibox.backroom2_5d.entities.Player;
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

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import static fr.cubibox.backroom2_5d.Main.windowHeight;
import static fr.cubibox.backroom2_5d.Main.windowWidth;
import static fr.cubibox.backroom2_5d.engine.Engine.screenDistance;
import static fr.cubibox.backroom2_5d.engine.Engine.wallHeight;
import static fr.cubibox.backroom2_5d.engine.Ray.RADIAN_PI_2;

public class Controller3D implements Initializable {
    private final Image[] wallTexture = new Image[64];

    private final Image[][] floorTexture = new Image[64][64];

    private final Image[][] ceilingTexture = new Image[64][64];

    @FXML
    private Pane coordinateSystem;
    @FXML
    private TextArea functionText;
    @FXML
    private VBox vBoxPanel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (int i = 0; i < 64; i++) {
            String name = "textures/wall/wall" + (i + 1) + ".png";
            InputStream is = Main.class.getResourceAsStream(name);
            if (is != null) {
                wallTexture[i] = new Image(Objects.requireNonNull(is));
            } else {
                System.out.println("Error while loading texture " + i);
            }
        }

        for (int i = 0; i < 64; i++) {
            for (int j = 0; j < 64; j++) {
                String name = "textures/floor/floor" + ((i * 64) + (j + 1)) + ".png";
                InputStream is = Main.class.getResourceAsStream(name);
                if (is != null) {
                    floorTexture[i][j] = new Image(Objects.requireNonNull(is));
                } else {
                    System.out.println("Error while loading texture " + ((i * 64) + j));
                }
            }
        }

        for (int i = 0; i < 64; i++) {
            for (int j = 0; j < 64; j++) {
                String name = "textures/ceil/ceil" + ((i * 64) + (j + 1)) + ".png";
                InputStream is = Main.class.getResourceAsStream(name);
                if (is != null) {
                    ceilingTexture[i][j] = new Image(Objects.requireNonNull(is));
                } else {
                    System.out.println("Error while loading texture " + ((i * 64) + j));
                }
            }
        }

        Movement clock = new Movement();
        clock.start();
    }

    public void drawFunction() {
        coordinateSystem.getChildren().clear();

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
                r.setFill(new ImagePattern(wallTexture[ray.getTextureIndex()]));
                r.setStroke(new ImagePattern(wallTexture[ray.getTextureIndex()]));

                coordinateSystem.getChildren().add(r);
                coordinateSystem.getChildren().add(shadow);

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

                        Rectangle floor = new Rectangle(startX, ((win2) - y), width, width);
                        floor.setFill(new ImagePattern(floorTexture[floorTexY][floorTexX]));
                        floor.setStroke(new ImagePattern(floorTexture[floorTexY][floorTexX]));
                        coordinateSystem.getChildren().add(floor);
                    }
                }

                // Draw ceiling
                float ceilingToBottom = (windowHeight + perceivedHeight) / 2f;
                if (ceilingToBottom > 0) {
                    int pixels = (int) floorToTop;
                    int pixelRowHeight = (int) ((win2) - pixels);

                    for (int y = pixelRowHeight; y < win2; y++) {
                        float directDistCeiling = (win2) / y;
                        float currentDistCeiling = directDistCeiling / rayDistance;

                        float ceilingX = player.getX() + currentDistCeiling * rayDX;
                        float ceilingY = player.getY() + currentDistCeiling * rayDY;

                        int ceilingTexX = (int) (ceilingX * 64) % 64;
                        int ceilingTexY = (int) (ceilingY * 64) % 64;

                        Rectangle ceiling = new Rectangle(startX, ((win2) + y), width, width);
                        ceiling.setFill(new ImagePattern(ceilingTexture[ceilingTexY][ceilingTexX]));
                        ceiling.setStroke(new ImagePattern(ceilingTexture[ceilingTexY][ceilingTexX]));
                        coordinateSystem.getChildren().add(ceiling);
                    }
                }
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
