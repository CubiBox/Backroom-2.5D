package fr.cubibox.sandbox.engine;

import fr.cubibox.sandbox.engine.maths.shapes.Circle;
import fr.cubibox.sandbox.engine.maths.shapes.Line;
import fr.cubibox.sandbox.engine.maths.vectors.Vector2;

import static fr.cubibox.sandbox.engine.Engine.HEIGHT;
import static fr.cubibox.sandbox.engine.Engine.WIDTH;
import static java.lang.Math.*;

// TODO: Set offset in this class IO computing it when calling drawing functions.
public class PixelDrawer {
    private final int[] pixels;

    public static final int BLACK   = 0x000000;
    public static final int WHITE   = 0xFFFFFF;
    public static final int RED     = 0xFF0000;
    public static final int GREEN   = 0x00FF00;
    public static final int BLUE    = 0x0000FF;
    public static final int MAGENTA = 0xFF00FF;
    public static final int YELLOW  = 0xFFFF00;
    public static final int CYAN    = 0x00FFFF;

    public PixelDrawer() {
        this.pixels = new int[WIDTH * HEIGHT];
    }

    public int[] getPixels() {
        return pixels;
    }

    public void pixel(int x, int y, int color) {
        if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) return;
        pixels[x + y * WIDTH] = color;
    }

    public void line(int xA, int yA, int xB, int yB, int color) {
        int dx = abs(xB - xA);
        int sx = xA < xB ? 1 : -1;
        int dy = -abs(yB - yA);
        int sy = yA < yB ? 1 : -1;

        int error = dx + dy;

        while (true) {
            pixel(xA, yA, color);

            if (xA == xB && yA == yB) {
                break;
            }

            int e2 = 2 * error;

            if (e2 >= dy) {
                if (xA == xB) {
                    break;
                }
                error = error + dy;
                xA = xA + sx;
            }

            if (e2 <= dx) {
                if (yA == yB) {
                    break;
                }

                error = error + dx;
                yA = yA + sy;
            }
        }
    }

    public void line(Line line, int color) {
        line(
                (int) line.getA().getX(),
                (int) line.getA().getY(),
                (int) line.getB().getX(),
                (int) line.getB().getY(),
                color
        );
    }

    public void circle(Vector2 o, int radius, int color) {
        int x = (int) (o.getX());
        int y = (int) (o.getY());

        int xi = 0;
        int yi = radius;
        int m = 5 - 4 * radius;

        while (xi <= yi) {
            pixel( xi + x,  yi + y, color);
            pixel( yi + x,  xi + y, color);
            pixel(-xi + x,  yi + y, color);
            pixel(-yi + x,  xi + y, color);
            pixel( xi + x, -yi + y, color);
            pixel( yi + x, -xi + y, color);
            pixel(-xi + x, -yi + y, color);
            pixel(-yi + x, -xi + y, color);

            if (m > 0) {
                yi -= 1;
                m -= 8 * yi;
            }

            xi += 1;
            m += 8 * xi + 4;
        }
    }

    public void circle(Circle circle, int color) {
        circle(circle.getOrigin(), (int) circle.getRadius(), color);
    }

    public void triangle(Vector2 a, Vector2 b, Vector2 c, int color) {
        // TODO: implements Triangle drawing
    }

    public void rectangle(int x, int y, int width, int height, int color) {
        int endY = y + height;
        int endX = x + width;

        for (int j = y; j < endY; j++) {
            if (j >= 0 && j < HEIGHT) {
                for (int i = x; i < endX; i++) {
                    if (i >= 0 && i < WIDTH) {
                        pixel(i, j, color);
                    }
                }
            }
        }
    }

    public void trapezoid(int xA, int xB, int yA1, int yB1, int yA2, int yB2, int color) {
        int dt = yB1 - yA1;
        int db = yB2 - yA2;
        int dx = xB - xA;

        if (dx == 0) {
            dx = 1;
        }

        int xTS = xA;

        xA = clamp(xA, 0, WIDTH - 1);
        xB = clamp(xB, 0, WIDTH - 1);

        for (int x = xA; x < xB; x++) {
            double xtp = (double) (x - xTS) / dx;

            int yT = (int) ((dt * xtp) + yA1);
            int yB = (int) ((db * xtp) + yA2);

            // THIS IS USED TO PREVENT FROM OVERDRAWING
            // yT = clamp(yT, minY[x], maxY[x]);
            // yB = clamp(yB, minY[x], maxY[x]);

            for (int y = yT; y < yB; y++) {
                pixel(x, y, color);
            }
        }
    }
}
