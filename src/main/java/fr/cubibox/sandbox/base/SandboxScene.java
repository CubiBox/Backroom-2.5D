package fr.cubibox.sandbox.base;

import fr.cubibox.sandbox.engine.GameScene;
import fr.cubibox.sandbox.engine.graphics.Canvas;
import fr.cubibox.sandbox.engine.maths.Vector2;
import fr.cubibox.sandbox.base.entities.Entity;
import fr.cubibox.sandbox.base.entities.Player;
import fr.cubibox.sandbox.base.level.Map;
import fr.cubibox.sandbox.base.renderer.SandboxRenderer;

import java.awt.event.KeyEvent;
import java.io.File;
import static fr.cubibox.sandbox.Main.keyboard;
import static fr.cubibox.sandbox.base.level.MapUtils.importMap;

public class SandboxScene implements GameScene {
    private final Map map;
    private final Player player;
    private final Camera camera;

    private final SandboxRenderer renderer;

    public SandboxScene(String mapPath) {
        this.map = importMap(new File(mapPath));
        this.player = map.getPlayer();
        this.camera = new Camera(0F, 0F);

        this.renderer = new SandboxRenderer(map, camera);
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
