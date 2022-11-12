package fr.cubibox.backroom2_5d.game;

import fr.cubibox.backroom2_5d.engine.GameScene;
import fr.cubibox.backroom2_5d.engine.graphics.Canvas;
import fr.cubibox.backroom2_5d.engine.maths.Line2F;
import fr.cubibox.backroom2_5d.engine.maths.Vector2F;
import fr.cubibox.backroom2_5d.engine.observers.Event;
import fr.cubibox.backroom2_5d.game.entities.Player;
import fr.cubibox.backroom2_5d.game.level.Chunk;
import fr.cubibox.backroom2_5d.game.level.Map;
import fr.cubibox.backroom2_5d.game.level.MapObject;
import fr.cubibox.backroom2_5d.game.level.MapUtils;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayDeque;

import static fr.cubibox.backroom2_5d.BackroomsMain.keyboard;

public class Backroom2D implements GameScene {
    private final Map map;
    private final Player player;

    private ArrayDeque<Event> events;

    public Backroom2D(String mapPath) {
        this.events = new ArrayDeque<>();
        this.map = MapUtils.importMap(new File(mapPath));
        this.player = this.map.getPlayer();
    }

    @Override
    public void init() {

    }

    @Override
    public void render(Canvas canvas, float dt) {
        drawMap(canvas, dt);

        canvas.fillRect(
                toCanvasY(canvas, player.getX(), map.getSize()) - 4,
                toCanvasY(canvas, player.getY(), map.getSize()) - 4,
                8,
                8,
                new Color(255, 0, 0, 255).getRGB()
        );
    }

    @Override
    public void update(float dt) {
        Event event;
        while ((event = events.poll()) != null) {
            event.execute();
        }

        this.player.setPosition(this.player.getPosition().add(this.player.getVelocity()));
    }

    @Override
    public void input() {
        if (keyboard.isKeyPressed(KeyEvent.VK_Z)) {
            this.player.setVelocity(this.player.getDirection().normalize().mul(1.5f));
        }
        if (keyboard.isKeyPressed(KeyEvent.VK_Q)) {
            this.player.setAngle(this.player.getAngle() - 0.5f);
        }
        if (keyboard.isKeyPressed(KeyEvent.VK_S)) {
            this.player.setVelocity(this.player.getDirection().normalize().mul(-1.5f));
        }
        if (keyboard.isKeyPressed(KeyEvent.VK_D)) {
            this.player.setAngle(this.player.getAngle() + 0.5f);
        }

        if (!keyboard.isKeyPressed(KeyEvent.VK_Z) && !keyboard.isKeyPressed(KeyEvent.VK_S)) {
            this.player.setVelocity(new Vector2F(0f, 0f));
        }
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
                                        toCanvasY(canvas, edge.getA().getX(), map.getSize()),
                                        toCanvasY(canvas, edge.getA().getY(), map.getSize()),
                                        toCanvasY(canvas, edge.getB().getX(), map.getSize()),
                                        toCanvasY(canvas, edge.getB().getY(), map.getSize()),
                                        new Color(0, 255, 255).getRGB()
                                );
                            }
                        }
                    }
                }
            }
        }
    }

    public int toCanvasX(Canvas canvas, int value, int mapWidth) {
        if (value == 0) return 0;
        return (canvas.width * value) / mapWidth;
    }

    public int toCanvasX(Canvas canvas, double value, int mapWidth) {
        return toCanvasX(canvas, (int) value, mapWidth);
    }

    public int toCanvasY(Canvas canvas, int value, int mapHeight) {
        return (canvas.height * value) / mapHeight;
    }

    public int toCanvasY(Canvas canvas, double value, int mapHeight) {
        return toCanvasY(canvas, (int) value, mapHeight);
    }

    public Player getPlayer() {
        return this.player;
    }
}
