package fr.cubibox.backroom2_5d.utils;

import fr.cubibox.backroom2_5d.Main;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

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

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        int w = img.getWidth();
        int h = img.getHeight();

        BufferedImage dimg = new BufferedImage(newW, newH, img.getType());
        Graphics2D g = dimg.createGraphics();

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
        g.dispose();

        writeImage("test.png", dimg);

        return dimg;
    }

    public static BufferedImage getImagePixel(BufferedImage image, int x, int y) {
        return image.getSubimage(x, y, 1, 1);
    }

    public static BufferedImage getSubImage(BufferedImage image, int x, int y, int width, int height) {
        return image.getSubimage(x, y, width, height);
    }

    public static javafx.scene.image.Image convertToFxImage(BufferedImage image) {
        WritableImage fxImage = new WritableImage(image.getWidth(), image.getHeight());
        PixelWriter pw = fxImage.getPixelWriter();

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                pw.setArgb(x, y, image.getRGB(x, y));
            }
        }

        return fxImage;
    }
}
