package fr.cubibox.backroom2_5d.engine.graphics;

import fr.cubibox.backroom2_5d.engine.maths.Vector2F;
import fr.cubibox.backroom2_5d.engine.maths.shapes.Rectangle2F;

import java.nio.IntBuffer;

import static java.lang.Math.abs;

public class Canvas {
    public final int width, height;
    private final int[] pixels;
    private final IntBuffer buffer;

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
            drawPixel(startX, startY, color);

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

    public void drawPixel(int x, int y, int color) {
        if (x < 0 || x >= width || y < 0 || y >= height) return;
        pixels[x + y * width] = color;
    }

    public void fillRect(int x, int y, int width, int height, int color) {
        int endY = y + height;
        int endX = x + width;

        for (int j = y; j < endY; j++) {
            if (j >= 0 && j < this.height) {
                for (int i = x; i < endX; i++) {
                    if (i >= 0 && i < this.width) {
                        pixels[i + j * this.width] = color;
                    }
                }
            }
        }
    }

    public void drawRect(int startX, int startY, int width, int height, int color) {
        int endY = startY + height;
        int endX = startX + width;

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

    public int toCanvasX(int value) {
        if (value == 0) return 0;
        return width / value;
    }

    public int toCanvasX(double value) {
        return toCanvasX((int) value);
    }
}
