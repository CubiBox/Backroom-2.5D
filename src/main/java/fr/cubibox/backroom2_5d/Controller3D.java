package fr.cubibox.backroom2_5d;

import fr.cubibox.backroom2_5d.engine.Ray;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static fr.cubibox.backroom2_5d.Main.DIMC;
import static fr.cubibox.backroom2_5d.Main.DIML;

public class Controller3D implements Initializable {

    public static float screenDistance = 15.0f;
    public static float wallHeight = 20.0f;
    public static float eyeLevel = 1.0f;
    @FXML
    private Pane coordinateSystem;
    @FXML
    private TextArea functionText;
    @FXML
    private VBox vBoxPanel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Movement clock = new Movement();
        clock.start();
    }

    public void drawFunction() {
        coordinateSystem.getChildren().clear();

        Rectangle bottomScreen = new Rectangle(0, 0, DIML, DIMC);
        bottomScreen.setFill(Color.BROWN);
        coordinateSystem.getChildren().add(bottomScreen);

        Rectangle topScreen = new Rectangle(0, 0, DIML, DIMC / 2f);
        topScreen.setFill(Color.BLACK);
        coordinateSystem.getChildren().add(topScreen);

        ArrayList<Ray> playersRay = Main.getEngine().getRays();

        if (playersRay.size() > 0) {
            Ray middleRay = playersRay.get(playersRay.size() / 2);

            float mul = 0;
            float width = DIML / playersRay.size();

            for (Ray ray : playersRay) {
                float rayDX = ray.getIntersectionX() - ray.getStartX();
                float rayDY = ray.getIntersectionY() - ray.getStartY();

                float rayDistance = (float) (Math.pow((rayDX * rayDX) + (rayDY * rayDY), 0.5));

                float rayAngleDiff = Math.abs(ray.getAngle() - middleRay.getAngle());
                float rayAngleDiffAbsCos = (float) Math.cos(rayAngleDiff * Math.PI / 180);

                float perceivedHeight = screenDistance * (wallHeight / (rayDistance * rayAngleDiffAbsCos));

                float startX = mul * width;
                float startY = (DIMC - perceivedHeight) / 2f;
                
                Rectangle r = new Rectangle(startX, startY, width, perceivedHeight);
                r.setStroke(Color.GRAY);
                r.setFill(Color.GRAY);
                coordinateSystem.getChildren().add(r);

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
