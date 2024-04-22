package fr.cubibox.sandbox.engine;

import fr.cubibox.sandbox.engine.maths.vectors.Vector2;

import static java.lang.Math.abs;

public class PixelDrawer {
    public final int width, height;
    private final int[] pixels;

    public PixelDrawer(int width, int height) {
        this.width = width;
        this.height = height;
        this.pixels = new int[width * height];
    }

    public int[] getPixels() {
        return pixels;
    }

    public void drawPixel(int x, int y, int color) {
        if (x < 0 || x >= width || y < 0 || y >= height) return;
        pixels[x + y * width] = color;
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

    public void drawCircle(Vector2 o, int radius, int color) {

    }

    public void fillCircle(Vector2 o, int radius, int color) {

    }

    public void drawTriangle(Vector2 a, Vector2 b, Vector2 c, int color) {

    }

    public void fillTriangle(Vector2 a, Vector2 b, Vector2 c, int color) {

    }

    public void drawRectangle(int startX, int startY, int width, int height, int color) {
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

    public void fillRectangle(int x, int y, int width, int height, int color) {
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

    public void drawTrapezoid(int xA, int xB, int yA1, int yB1, int yA2, int yB2, int color) {

    }

    public void fillTrapezoid(int xA, int xB, int yA1, int yB1, int yA2, int yB2, int color) {

    }
}
