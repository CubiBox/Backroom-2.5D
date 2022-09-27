package fr.cubibox.backroom2_5d;

import fr.cubibox.backroom2_5d.engine.Ray;
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
import static fr.cubibox.backroom2_5d.Main.windowsWidth;
import static fr.cubibox.backroom2_5d.engine.Engine.screenDistance;
import static fr.cubibox.backroom2_5d.engine.Engine.wallHeight;
import static fr.cubibox.backroom2_5d.engine.Ray.RADIAN_PI_2;

public class Controller3D implements Initializable {
    private final Image[] wallTexture = new Image[64];
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

        Movement clock = new Movement();
        clock.start();
    }

    public void drawFunction() {
        coordinateSystem.getChildren().clear();

        Rectangle bottomScreen = new Rectangle(0, 0, windowsWidth, windowHeight);
        bottomScreen.setFill(Color.BROWN);
        coordinateSystem.getChildren().add(bottomScreen);

        Rectangle topScreen = new Rectangle(0, 0, windowsWidth, windowHeight / 2f);
        topScreen.setFill(Color.BLACK);
        coordinateSystem.getChildren().add(topScreen);

        ArrayList<Ray> playersRay = Main.getEngine().getRays();

        if (playersRay.size() > 0) {
            Ray middleRay = playersRay.get(playersRay.size() / 2);

            float mul = 0;
            float width = windowsWidth / playersRay.size();

            for (Ray ray : playersRay) {
                float rayDX = ray.getIntersectionX() - ray.getStartX();
                float rayDY = ray.getIntersectionY() - ray.getStartY();

                float rayDistance = (float) (Math.pow((rayDX * rayDX) + (rayDY * rayDY), 0.5));
                //float rayDistance = ((rayDX * rayDX) + (rayDY * rayDY));

                float rayAngleFromMiddle = middleRay.getAngle() - ray.getAngle();
                float rayAngleDiffAbsCos = (float) Math.abs(Math.cos(rayAngleFromMiddle * RADIAN_PI_2));
                rayDistance = rayDistance * rayAngleDiffAbsCos;
                float perceivedHeight = (screenDistance) * (wallHeight / rayDistance);


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
