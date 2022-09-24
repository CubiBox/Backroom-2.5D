package fr.cubibox.backroom2_5d.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ImageUtils {
    public static final int TILE_SIZE = 16;

    public static BufferedImage readImage(String inputPath) {
        URL pathURL = ImageUtils.class.getResource("/" + inputPath);

        if (pathURL != null) {
            try {
                return ImageIO.read(pathURL);
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

        return img;
    }
}
