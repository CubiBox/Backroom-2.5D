package fr.cubibox.backroom2_5d.game;

import fr.cubibox.backroom2_5d.engine.GameScene;
import fr.cubibox.backroom2_5d.engine.graphics.Canvas;
import fr.cubibox.backroom2_5d.engine.maths.Vector2;
import fr.cubibox.backroom2_5d.engine.observers.Event;
import fr.cubibox.backroom2_5d.game.entities.Entity;
import fr.cubibox.backroom2_5d.game.entities.Player;
import fr.cubibox.backroom2_5d.game.level.Map;
import fr.cubibox.backroom2_5d.game.renderer.Backroom2DRenderer;

import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayDeque;
import static fr.cubibox.backroom2_5d.BackroomsMain.keyboard;
import static fr.cubibox.backroom2_5d.game.level.MapUtils.importMap;

public class Backroom2D implements GameScene {
    private final Map map;
    private final Player player;
    private final Camera camera;

    private final ArrayDeque<Event> events;

    private final Backroom2DRenderer renderer;

    public Backroom2D(String mapPath) {
        this.events = new ArrayDeque<>();
        this.map = importMap(new File(mapPath));
        this.player = map.getPlayer();
        this.camera = new Camera(0F, 0F);

        this.renderer = new Backroom2DRenderer(map, camera);
    }

    @Override
    public void init() {

    }

    @Override
    public void render(Canvas canvas, float dt) {
        renderer.render(canvas, dt);
    }

    @Override
    public void update(float dt) {
        Event event;
        while ((event = events.poll()) != null) {
            event.execute();
        }

        updateEntity(this.player, dt);
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
            this.player.setVelocity(new Vector2(0f, 0f));
        }
    }

    private void updateEntity(Entity entity, float dt) {
        Vector2 nextEntityPosition = entity.getPosition().add(entity.getVelocity().mul(dt));
        entity.setPosition(nextEntityPosition);
    }
}
