package fr.cubibox.sandbox.base.renderer;

import fr.cubibox.sandbox.base.Camera;
import fr.cubibox.sandbox.engine.PixelDrawer;
import fr.cubibox.sandbox.engine.maths.Line;
import fr.cubibox.sandbox.engine.maths.shapes.Rectangle;
import fr.cubibox.sandbox.level.Chunk;
import fr.cubibox.sandbox.level.Map;
import fr.cubibox.sandbox.level.MapObject;

import java.awt.*;

public class MinimapRenderer {
    private final Map map;
    private final Camera camera;

    public MinimapRenderer(Map map, Camera camera) {
        this.map = map;
        this.camera = camera;
    }

    public void render(PixelDrawer pixelDrawer) {
        drawMap(pixelDrawer);
    }

    private void drawMap(PixelDrawer pixelDrawer) {
        int width = pixelDrawer.getWidth();
        int height = pixelDrawer.getHeight();
        pixelDrawer.rectangle(0, 0, width, height, PixelDrawer.BLACK);

        Rectangle canvasZone = new Rectangle(width / 2F, height / 2F, width, height);

        for (Chunk[] chunksL : map.getChunks()) {
            for (Chunk chunk : chunksL) {
                for (MapObject object : chunk.getMapObjects()) {
                    for (Line edge : object.getEdges()) {
                        if (canvasZone.intersects(edge.getA().add(camera.getPosition()).multiply(camera.getScale()))
                                || canvasZone.intersects(edge.getB().add(camera.getPosition()).multiply(camera.getScale()))) {
                            float scale = camera.getScale();

                            int aX = (int) (edge.getA().getX() * scale + camera.getPosX() * scale);
                            int aY = (int) (edge.getA().getY() * scale + camera.getPosY() * scale);
                            int bX = (int) (edge.getB().getX() * scale + camera.getPosX() * scale);
                            int bY = (int) (edge.getB().getY() * scale + camera.getPosY() * scale);

                            pixelDrawer.line(aX, aY, bX, bY, PixelDrawer.CYAN);
                        }
                    }
                }
            }
        }
    }
}
