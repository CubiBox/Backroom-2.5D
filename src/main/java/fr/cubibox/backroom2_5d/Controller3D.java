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
import javafx.scene.image.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.nio.IntBuffer;
import java.util.HashSet;
import java.util.ResourceBundle;

import static fr.cubibox.backroom2_5d.Main.windowHeight;
import static fr.cubibox.backroom2_5d.Main.windowWidth;
import static fr.cubibox.backroom2_5d.engine.Engine.screenDistance;
import static fr.cubibox.backroom2_5d.engine.Engine.wallHeight;
import static fr.cubibox.backroom2_5d.engine.Ray.RADIAN_PI_2;
import static fr.cubibox.backroom2_5d.utils.ImageUtils.TILE_SIZE;
import static java.lang.Math.abs;

public class Controller3D implements Initializable {
    private int[][] wallTextureMatrix;
    private int[][] floorTextureMatrix;
    private int[][] ceilTextureMatrix;

    @FXML
    private Pane pane;

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

        Movement clock = new Movement();
        clock.start();
    }

    public void drawFunction() {
        pane.getChildren().clear();
        IntBuffer buffer = IntBuffer.allocate(windowWidth * windowHeight);

        int[] pixels = buffer.array();
        PixelBuffer<IntBuffer> pixelBuffer = new PixelBuffer<>(windowWidth, windowHeight, buffer, PixelFormat.getIntArgbPreInstance());
        WritableImage image = new WritableImage(pixelBuffer);



        pixelBuffer.updateBuffer(b -> null);
        pane.getChildren().add(new ImageView(image));
    }

    private void fillRect(int x, int y, int width, int height, int[] pixels) {
        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
            }
        }
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
