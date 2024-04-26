package fr.cubibox.sandbox.base.renderer;

import fr.cubibox.sandbox.base.Camera;
import fr.cubibox.sandbox.engine.PixelDrawer;
import fr.cubibox.sandbox.engine.maths.shapes.Line;
import fr.cubibox.sandbox.engine.maths.shapes.Rectangle;
import fr.cubibox.sandbox.engine.maths.vectors.Vector2;
import fr.cubibox.sandbox.level.Chunk;
import fr.cubibox.sandbox.level.Map;
import fr.cubibox.sandbox.level.MapObject;

public class MinimapRenderer {
    private final Map map;
    private final Camera camera;

    private float scale = 10f;

    public MinimapRenderer(Map map, Camera camera) {
        this.map = map;
        this.camera = camera;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void render(PixelDrawer pixelDrawer) {
        drawMap(pixelDrawer);
    }

    private Vector2 worldVectorToCameraVector(Vector2 vector) {
        return vector.subtract(camera.getPosition()).rotate(-camera.getOrientation().angle()).multiply(scale).add(camera.getScreenOffset());
    }

    private Line worldLineToCameraLine(Line line) {
        return new Line(
                worldVectorToCameraVector(line.getA()),
                worldVectorToCameraVector(line.getB())
        );
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
                        Line cameraEdge = worldLineToCameraLine(edge);

                        if (canvasZone.intersects(cameraEdge.getA()) || canvasZone.intersects(cameraEdge.getB())) {
                            pixelDrawer.line(cameraEdge, PixelDrawer.CYAN);
                        }
                    }
                }
            }
        }

        pixelDrawer.circle(new Vector2().add(camera.getScreenOffset()), (int) (scale / 2f), PixelDrawer.RED);
        pixelDrawer.line(
                new Line(new Vector2().add(camera.getScreenOffset()), new Vector2(scale, 0).add(camera.getScreenOffset())),
                PixelDrawer.GREEN
        );
    }
}
