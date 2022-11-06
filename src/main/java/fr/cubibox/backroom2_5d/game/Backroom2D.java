package fr.cubibox.backroom2_5d.game;

import fr.cubibox.backroom2_5d.engine.GameScene;
import fr.cubibox.backroom2_5d.engine.graphics.Canvas;
import fr.cubibox.backroom2_5d.engine.maths.Line2F;
import fr.cubibox.backroom2_5d.game.entities.Player;
import fr.cubibox.backroom2_5d.game.level.Chunk;
import fr.cubibox.backroom2_5d.game.level.Map;
import fr.cubibox.backroom2_5d.game.level.MapObject;
import fr.cubibox.backroom2_5d.game.level.MapUtils;

import java.awt.*;
import java.io.File;

public class Backroom2D implements GameScene {
    private final Map map;
    private final Player player;

    public Backroom2D(String mapPath) {
        this.map = MapUtils.importMap(new File(mapPath));
        this.player = new Player(5, 5, 0);
    }

    @Override
    public void init() {

    }

    @Override
    public void render(Canvas canvas, float dt) {
        drawMap(canvas, dt);
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void input() {

    }

    private void drawMap(Canvas canvas, float dt) {
        canvas.fillRect(0, 0, canvas.width, canvas.height, new Color(0, 0, 0, 255).getRGB());

        for (Chunk[] chunksL : map.getChunks()) {
            for (Chunk chunk: chunksL) {
                if (chunk != null) {
                    for (MapObject object : chunk.getMapObjects()) {
                        if (object != null) {
                            for (Line2F edge : object.getEdges()) {
                                canvas.drawLine(
                                        canvas.toCanvasX(edge.getA().getX()),
                                        canvas.toCanvasX(edge.getA().getY()),
                                        canvas.toCanvasX(edge.getB().getX()),
                                        canvas.toCanvasX(edge.getB().getY()),
                                        new Color(0, 255, 255).getRGB()
                                );
                            }
                        }
                    }
                }
            }
        }
    }
}
