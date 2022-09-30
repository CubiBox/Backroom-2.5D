package fr.cubibox.backroom2_5d;

import fr.cubibox.backroom2_5d.engine.Ray;
import fr.cubibox.backroom2_5d.engine.maths.Line2F;
import fr.cubibox.backroom2_5d.engine.maths.shapes.Circle2F;
import fr.cubibox.backroom2_5d.engine.maths.shapes.Rectangle2F;
import fr.cubibox.backroom2_5d.engine.maths.shapes.Shape;
import fr.cubibox.backroom2_5d.entities.Player;
import fr.cubibox.backroom2_5d.game.Chunk;
import fr.cubibox.backroom2_5d.game.MapObject;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller2D implements Initializable {

    private final int paneWidth = 300;
    private final Color CENTERED_RAY_COLOR = new Color(1.0, 0.0, 0.0, 0.25);
    private final Color RAY_COLOR = new Color(0.0, 0.0, 1.0, 0.25);
    private final ArrayList<javafx.scene.shape.Polygon> polygons = new ArrayList<>();
    @FXML
    private Pane coordinateSystem;
    @FXML
    private TextArea functionText;
    @FXML
    private VBox vBoxPanel;
    @FXML
    private Button importer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //drawFunction();
        Movement clock = new Movement();
        clock.start();
    }

    public void drawFunction() {
        Player p = Main.getEngine().getPlayer();
        coordinateSystem.getChildren().clear();

        //grid
        for (Rectangle r : drawGrid())
            coordinateSystem.getChildren().add(r);

        //draw the player's rays
        for (Ray r : Main.getEngine().getRays()) {
            if (r.getIntersectionX() != Float.POSITIVE_INFINITY && r.getIntersectionY() != Float.POSITIVE_INFINITY) {
                Line l = new Line(Main.toScreenX(p.getX()), Main.toScreenY(p.getY()), Main.toScreenX(r.getIntersectionX()), Main.toScreenY(r.getIntersectionY()));
                l.setStroke(RAY_COLOR);
                coordinateSystem.getChildren().add(l);
            }
        }

        //draw the player's point
        coordinateSystem.getChildren().add(new Circle(Main.toScreenX(p.getX()), Main.toScreenY(p.getY()), 2, Color.RED));

        //draw the player's collision box
        /*Rectangle collisionBox = new Rectangle(
                Main.toScreenX(p.getX() - p.getCollisionBox().getWidth() / 2),
                Main.toScreenY(p.getY() - p.getCollisionBox().getHeight() / 2),
                Main.toScreenX(p.getCollisionBox().getWidth()),
                Main.toScreenY(p.getCollisionBox().getHeight())
        );*/
        for (Shape shape : p.getCollisionBox()) {
            if (shape instanceof Rectangle2F rect) {
                Rectangle collisionBox = new Rectangle(
                        Main.toScreenX(p.getX() - rect.getWidth() / 2),
                        Main.toScreenY(p.getY() - rect.getHeight() / 2),
                        Main.toScreenX(rect.getWidth()),
                        Main.toScreenY(rect.getHeight())
                );

                collisionBox.setFill(Color.TRANSPARENT);
                collisionBox.setStroke(Color.RED);
                coordinateSystem.getChildren().add(collisionBox);
            }

            if (shape instanceof Circle2F circle) {
                Circle collisionBox = new Circle(
                        Main.toScreenX(p.getX() + circle.getX() + p.getVelocity().getX()),
                        Main.toScreenY(p.getY() + circle.getY() + p.getVelocity().getY()),
                        Main.toScreenX(circle.getRadius())
                );

                collisionBox.setFill(Color.TRANSPARENT);
                collisionBox.setStroke(Color.RED);
                coordinateSystem.getChildren().add(collisionBox);
            }
        }

        coordinateSystem.getChildren().add(drawPoint(8f, 8f));

        //draw the polygons & edges normals
        int countPols = -1;
        for (Chunk[] chunkL : Main.getEngine().getMap().getChunks()) {
            for (Chunk chunk : chunkL) {
                if (chunk != null)
                    if (chunk.getPolygons() != null) {
                        for (MapObject pol : chunk.getPolygons()) {
                            if (countPols != Integer.parseInt(pol.getId())) {
                                coordinateSystem.getChildren().add(pol.getPolShape());
                                countPols++;
                                for (Line2F l : pol.getEdges()) {
                                    float midX = l.getA().getX() + (l.getB().getX() - l.getA().getX()) / 2;
                                    float midY = l.getA().getY() + (l.getB().getY() - l.getA().getY()) / 2;

                                    // place normal on the edge
                                /*
                                Line normal = new Line(
                                        Main.toScreenX(midX),
                                        Main.toScreenY(midY),
                                        Main.toScreenX(midX + l.getNormal().getX()),
                                        Main.toScreenY(midY + l.getNormal().getY())
                                );
                                normal.setStroke(Color.rgb(255, 255, 0, 1));
                                coordinateSystem.getChildren().add(normal);*/
                                }
                            }
                        }
                    }
            }
        }
    }

    public ArrayList<Rectangle> drawGrid() {
        Double w = Double.valueOf(Main.windowHeight);
        Double h = Double.valueOf(Main.windowWidth);
        ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();

        Color gray1 = Color.rgb(30, 30, 30);
        Color gray2 = Color.rgb(70, 70, 70);
        Rectangle r;

        for (int i = 0; i < Main.getEngine().getMap().getSize(); i += 8)
            for (int j = 1; j <= 7; j++) {
                r = new Rectangle(0, Main.toScreenY(i + j) - 1, w, 2.0);
                r.setFill(gray1);
                rectangles.add(r);
            }

        for (int i = 0; i < Main.getEngine().getMap().getSize(); i += 8)
            for (int j = 1; j <= 7; j++) {
                r = new Rectangle(Main.toScreenX(i + j) - 1, 0, 2.0, h);
                r.setFill(gray1);
                rectangles.add(r);
            }

        for (int i = 0; i < Main.getEngine().getMap().getSize(); i += 8) {
            r = new Rectangle(0, Main.toScreenY(i) - 1.25, w, 2.5);
            r.setFill(gray2);
            rectangles.add(r);
        }

        for (int i = 0; i < Main.getEngine().getMap().getSize(); i += 8) {
            r = new Rectangle(Main.toScreenX(i) - 1.25, 0, 2.5, h);
            r.setFill(gray2);
            rectangles.add(r);
        }

        r = new Rectangle(w / 2 - 1, 0, 2, h);
        r.setFill(Color.rgb(160, 160, 160));
        rectangles.add(r);

        r = new Rectangle(0, h / 2 - 1, w, 2);
        r.setFill(Color.rgb(180, 180, 180));
        rectangles.add(r);

        return rectangles;
    }

    public Circle drawPoint(double x, double y) {
        return new Circle(Main.toScreenX(x), Main.toScreenY(y), 0.3, Color.RED);
    }

    private class Movement extends AnimationTimer {
        private long last = 0;

        @Override
        public void handle(long now) {
            long FRAMES_PER_SEC = 60L;
            long INTERVAL = 1000000000L / FRAMES_PER_SEC;

            if (now - last > INTERVAL) {
                drawFunction();
                last = now;
            }
        }
    }
}
