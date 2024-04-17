package fr.cubibox.sandbox.base.renderer;

import fr.cubibox.sandbox.engine.graphics.Canvas;
import fr.cubibox.sandbox.engine.maths.Line;
import fr.cubibox.sandbox.engine.maths.shapes.Rectangle;
import fr.cubibox.sandbox.base.Camera;
import fr.cubibox.sandbox.base.level.Chunk;
import fr.cubibox.sandbox.base.level.Map;
import fr.cubibox.sandbox.base.level.MapObject;

import java.awt.*;

public class SandboxRenderer {
    private final Map map;
    private final Camera camera;

    public SandboxRenderer(Map map, Camera camera) {
        this.map = map;
        this.camera = camera;
    }

    public void render(Canvas canvas, float dt) {
        drawMap(canvas, dt);
    }

    private void drawMap(Canvas canvas, float dt) {
        canvas.fillRect(0, 0, canvas.width, canvas.height, new Color(0, 0, 0, 255).getRGB());

        Rectangle canvasZone = new Rectangle(canvas.width/2F, canvas.height/2F, canvas.width, canvas.height);

        for (Chunk[] chunksL : map.getChunks()) {
            for (Chunk chunk : chunksL) {
                for (MapObject object : chunk.getMapObjects()) {
                    for (Line edge : object.getEdges()) {
                        if (canvasZone.intersects(edge.getA().add(camera.getPosition()).mul(camera.getScale()))
                                || canvasZone.intersects(edge.getB().add(camera.getPosition()).mul(camera.getScale()))) {
                            float scale = camera.getScale();

                            int aX = (int) (edge.getA().getX() * scale + camera.getPosX() * scale);
                            int aY = (int) (edge.getA().getY() * scale + camera.getPosY() * scale);
                            int bX = (int) (edge.getB().getX() * scale + camera.getPosX() * scale);
                            int bY = (int) (edge.getB().getY() * scale + camera.getPosY() * scale);

                            canvas.drawLine(aX, aY, bX, bY, new Color(0, 255, 255).getRGB());
                        }
                    }
                }
            }
        }
    }

}
