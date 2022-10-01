package fr.cubibox.backroom2_5d.utils;

import fr.cubibox.backroom2_5d.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtils {
    public static final int TILE_SIZE = 128;

    public static BufferedImage readImage(String inputPath) {
        InputStream pathURL = Main.class.getResourceAsStream(inputPath);

        if (pathURL != null) {
            try {
                BufferedImage image = ImageIO.read(pathURL);
                pathURL.close();
                return image;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return placeHolder();
    }

    public static void writeImage(String outputPath, Object content) {
        if (content instanceof BufferedImage) {
            try {
                ImageIO.write((BufferedImage) content, "png", new File(outputPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Unknown or unsupported content format.");
        }
    }

    public static BufferedImage placeHolder() {
        BufferedImage img = new BufferedImage(TILE_SIZE, TILE_SIZE, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2 = img.createGraphics();

        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, TILE_SIZE / 2, TILE_SIZE / 2);
        g2.fillRect(TILE_SIZE / 2, TILE_SIZE / 2, TILE_SIZE / 2, TILE_SIZE / 2);

        g2.setColor(Color.MAGENTA);
        g2.fillRect(TILE_SIZE / 2, 0, TILE_SIZE / 2, TILE_SIZE / 2);
        g2.fillRect(0, TILE_SIZE / 2, TILE_SIZE / 2, TILE_SIZE / 2);

        g2.dispose();
        return img;
    }
}
