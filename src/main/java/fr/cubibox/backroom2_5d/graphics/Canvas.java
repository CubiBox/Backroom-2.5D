package fr.cubibox.backroom2_5d.graphics;

import fr.cubibox.backroom2_5d.engine.maths.Line2F;
import fr.cubibox.backroom2_5d.engine.maths.Point2F;
import fr.cubibox.backroom2_5d.engine.maths.shapes.Rectangle2F;

import java.nio.IntBuffer;

import static java.lang.Math.abs;

public class Canvas {
    public final int width, height;

    private final IntBuffer buffer;
    private final int[] pixels;

    public Canvas(int width, int height) {
        this.width = width;
        this.height = height;
        this.buffer = IntBuffer.allocate(width * height);
        this.pixels = buffer.array();
    }

    public IntBuffer getBuffer() {
        return buffer;
    }

    public int[] getPixels() {
        return pixels;
    }

    public void drawLine(int startX, int startY, int endX, int endY, int color) {
        int dx = abs(endX - startX);
        int dy = abs(endY - startY);
        int sx = startX < endX ? 1 : -1;
        int sy = startY < endY ? 1 : -1;

        int err = dx - dy;
        int e2;

        while (true) {
            drawPixel(new Point2F(startX, startY), color);

            if (startX == endX && startY == endY) {
                break;
            }

            e2 = 2 * err;

            if (e2 > -dy) {
                err = err - dy;
                startX = startX + sx;
            }

            if (e2 < dx) {
                err = err + dx;
                startY = startY + sy;
            }
        }
    }

    public void drawPixel(Point2F pixel, int color) {
        int x = (int) pixel.getX();
        int y = (int) pixel.getY();
        if (x < 0 || x >= width || y < 0 || y >= height) return;
        pixels[x + y * width] = color;
    }

    public void fillRect(Rectangle2F rectangle2F, int color) {
        int startY = (int) rectangle2F.getY();
        int startX = (int) rectangle2F.getX();

        int endY = (int) (startY + rectangle2F.getHeight());
        int endX = (int) (startX + rectangle2F.getWidth());

        for (int y = startY; y < endY; y++) {
            if (y >= 0 && y < height) {
                for (int x = startX; x < endX; x++) {
                    if (x >= 0 && x < width) {
                        pixels[x + y * width] = color;
                    }
                }
            }
        }
    }

    public void drawRect(Rectangle2F rectangle2F, int color) {
        int startY = (int) rectangle2F.getY();
        int startX = (int) rectangle2F.getX();

        int endY = (int) (startY + rectangle2F.getHeight());
        int endX = (int) (startX + rectangle2F.getWidth());

        for (int y = startY; y < endY; y++) {
            if (y >= 0 && y < height) {
                for (int x = startX; x < endX; x++) {
                    if (x >= 0 && x < width) {
                        if (x == startX || x == endX - 1 || y == startY || y == endY - 1) {
                            pixels[x + y * width] = color;
                        }
                    }
                }
            }
        }
    }

    public void fillRect2(Rectangle2F rectangle2F, int color) {
        int startY = (int) rectangle2F.getY();
        int startX = (int) rectangle2F.getX();

        int endY = (int) (startY + rectangle2F.getHeight());
        int endX = (int) (startX + rectangle2F.getWidth());

        for (int y = 0; y < height * width; y += width) {
            for (int x = 0; x < width; x++) {
                if (x >= startX && x < endX && y >= startY && y < endY) {
                    pixels[x + y] = color;
                }
            }
        }
    }
}
