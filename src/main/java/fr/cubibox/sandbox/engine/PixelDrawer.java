package fr.cubibox.sandbox.engine;

import fr.cubibox.sandbox.engine.maths.vectors.Vector2;

import static java.lang.Math.abs;

public class PixelDrawer {
    private final int width, height;
    private final int[] pixels;

    public static final int BLACK   = 0x000000;
    public static final int WHITE   = 0xFFFFFF;
    public static final int RED     = 0xFF0000;
    public static final int GREEN   = 0x00FF00;
    public static final int BLUE    = 0x0000FF;
    public static final int MAGENTA = 0xFF00FF;
    public static final int YELLOW  = 0xFFFF00;
    public static final int CYAN    = 0x00FFFF;

    public PixelDrawer(int width, int height) {
        this.width = width;
        this.height = height;
        this.pixels = new int[width * height];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[] getPixels() {
        return pixels;
    }

    public void pixel(int x, int y, int color) {
        if (x < 0 || x >= width || y < 0 || y >= height) return;
        pixels[x + y * width] = color;
    }

    public void line(int startX, int startY, int endX, int endY, int color) {
        int dx = abs(endX - startX);
        int dy = abs(endY - startY);
        int sx = startX < endX ? 1 : -1;
        int sy = startY < endY ? 1 : -1;

        int err = dx - dy;
        int e2;

        while (true) {
            pixel(startX, startY, color);

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

    public void circle(Vector2 o, int radius, int color) {

    }

    public void triangle(Vector2 a, Vector2 b, Vector2 c, int color) {

    }

    public void rectangle(int x, int y, int width, int height, int color) {
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

    public void trapezoid(int xA, int xB, int yA1, int yB1, int yA2, int yB2, int color) {

    }
}
