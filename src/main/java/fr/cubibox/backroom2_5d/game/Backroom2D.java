package fr.cubibox.backroom2_5d.game;

import fr.cubibox.backroom2_5d.engine.GameScene;
import fr.cubibox.backroom2_5d.engine.graphics.Canvas;
import fr.cubibox.backroom2_5d.engine.maths.Line2F;
import fr.cubibox.backroom2_5d.engine.maths.Vector2F;
import fr.cubibox.backroom2_5d.engine.maths.shapes.Rectangle2F;
import fr.cubibox.backroom2_5d.engine.maths.shapes.Shape;
import fr.cubibox.backroom2_5d.engine.observers.Event;
import fr.cubibox.backroom2_5d.game.entities.Entity;
import fr.cubibox.backroom2_5d.game.entities.Player;
import fr.cubibox.backroom2_5d.game.events.CollisionEvent;
import fr.cubibox.backroom2_5d.game.level.Chunk;
import fr.cubibox.backroom2_5d.game.level.Map;
import fr.cubibox.backroom2_5d.game.level.MapObject;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;

import static fr.cubibox.backroom2_5d.BackroomsMain.keyboard;
import static fr.cubibox.backroom2_5d.engine.GameEngine.ONE_SECOND_IN_MILLIS;
import static fr.cubibox.backroom2_5d.game.level.MapUtils.importMap;

public class Backroom2D implements GameScene {
    private final Map map;
    private final Player player;
    private final Camera camera;

    private final ArrayDeque<Event> events;
    private final ArrayList<CollisionEvent> collisionEvents;

    public Backroom2D(String mapPath) {
        this.events = new ArrayDeque<>();
        this.collisionEvents = new ArrayList<>();
        this.map = importMap(new File(mapPath));
        this.player = map.getPlayer();
        this.camera = new Camera(0F, 0F);
    }

    @Override
    public void init() {

    }

    @Override
    public void render(Canvas canvas, float dt) {
        drawMap(canvas, dt);

        Rectangle2F canvasZone = new Rectangle2F(canvas.width/2F, canvas.height/2F, canvas.width, canvas.height);

        for (Shape shape : player.getCollisionBox()) {
            for (Line2F vertice : shape.getVertices()) {
                if (canvasZone.intersects(vertice.getA().add(player.getPos()).add(camera.getPosition()).mul(camera.getScale()))
                        || canvasZone.intersects(vertice.getB().add(player.getPos()).add(camera.getPosition()).mul(camera.getScale()))) {
                    float scale = camera.getScale();

                    int aX = (int) ((player.getX() + vertice.getA().getX() + camera.getPosX()) * scale);
                    int aY = (int) ((player.getY() + vertice.getA().getY() + camera.getPosY()) * scale);
                    int bX = (int) ((player.getX() + vertice.getB().getX() + camera.getPosX()) * scale);
                    int bY = (int) ((player.getY() + vertice.getB().getY() + camera.getPosY()) * scale);

                    canvas.drawLine(aX, aY, bX, bY, new Color(255, 0, 255).getRGB());
                }
            }
        }
    }

    @Override
    public void update(float dt) {
        collisionEvents.clear();

        Event event;
        while ((event = events.poll()) != null) {
            event.execute();
        }

        updateEntity(this.player, dt);

        Collections.sort(collisionEvents);
    }

    @Override
    public void input() {
        if (keyboard.isKeyPressed(KeyEvent.VK_Z)) {
            this.player.setVelocity(this.player.getDirection().normalize());
        }
        if (keyboard.isKeyPressed(KeyEvent.VK_Q)) {
            this.player.setAngle(this.player.getAngle() - 5f);
        }
        if (keyboard.isKeyPressed(KeyEvent.VK_S)) {
            this.player.setVelocity(this.player.getDirection().normalize().mul(-1f));
        }
        if (keyboard.isKeyPressed(KeyEvent.VK_D)) {
            this.player.setAngle(this.player.getAngle() + 5f);
        }

        if (keyboard.isKeyPressed(KeyEvent.VK_O) && camera.getScale() < 100) {
            camera.setScale(camera.getScale() + 1);
        } else if (keyboard.isKeyPressed(KeyEvent.VK_K) && camera.getScale() > 1) {
            camera.setScale(camera.getScale() - 1);
        }

        if (keyboard.isKeyPressed(KeyEvent.VK_UP)) {
            camera.setPosY(camera.getPosY() + 1);
        } else if (keyboard.isKeyPressed(KeyEvent.VK_DOWN)) {
            camera.setPosY(camera.getPosY() - 1);
        }

        if (keyboard.isKeyPressed(KeyEvent.VK_RIGHT)) {
            camera.setPosX(camera.getPosX() + 1);
        } else if (keyboard.isKeyPressed(KeyEvent.VK_LEFT)) {
            camera.setPosX(camera.getPosX() - 1);
        }

        if (!keyboard.isKeyPressed(KeyEvent.VK_Z) && !keyboard.isKeyPressed(KeyEvent.VK_S)) {
            this.player.setVelocity(new Vector2F(0f, 0f));
        }
    }

    private void drawMap(Canvas canvas, float dt) {
        canvas.fillRect(0, 0, canvas.width, canvas.height, new Color(0, 0, 0, 255).getRGB());

        Rectangle2F canvasZone = new Rectangle2F(canvas.width/2F, canvas.height/2F, canvas.width, canvas.height);

        for (Chunk[] chunksL : map.getChunks()) {
            for (Chunk chunk : chunksL) {
                for (MapObject object : chunk.getMapObjects()) {
                    for (Line2F edge : object.getEdges()) {
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

    public Player getPlayer() {
        return this.player;
    }

    public Camera getCamera() {
        return camera;
    }

    public ArrayList<CollisionEvent> getCollisionEvents() {
        return this.collisionEvents;
    }

    private void updateEntity(Entity entity, float dt) {
        Vector2F nextEntityPosition = entity.getPosition().add(entity.getVelocity().mul(dt));
        entity.setPosition(nextEntityPosition);
    }
}
