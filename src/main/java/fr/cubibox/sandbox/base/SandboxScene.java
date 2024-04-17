package fr.cubibox.sandbox.base;

import fr.cubibox.sandbox.base.renderer.MinimapRenderer;
import fr.cubibox.sandbox.base.renderer.SandboxRenderer;
import fr.cubibox.sandbox.engine.GameScene;
import fr.cubibox.sandbox.engine.graphics.Canvas;
import fr.cubibox.sandbox.engine.maths.vectors.Vector2;
import fr.cubibox.sandbox.base.entities.Entity;
import fr.cubibox.sandbox.base.entities.Player;
import fr.cubibox.sandbox.level.Map;

import java.awt.event.KeyEvent;
import java.io.File;
import static fr.cubibox.sandbox.Main.keyboard;
import static fr.cubibox.sandbox.level.MapUtils.importMap;

public class SandboxScene implements GameScene {
    private final Map map;
    private final Player player;
    private final Camera camera;

    private final MinimapRenderer minimapRenderer;
    private final SandboxRenderer renderer;

    public SandboxScene(String mapPath) {
        this.map = importMap(new File(mapPath));
        this.player = map.getPlayer();
        this.camera = new Camera(0F, 0F);

        this.minimapRenderer = new MinimapRenderer(map, camera);
        this.renderer = new SandboxRenderer();
    }

    @Override
    public void init() {

    }

    @Override
    public void render(Canvas canvas, float dt) {
        minimapRenderer.render(canvas, dt);
    }

    @Override
    public void update(float dt) {
        updateEntity(this.player, dt);
    }

    @Override
    public void input() {
        // TODO: Reimplement this.
    }

    private void updateEntity(Entity entity, float dt) {
        Vector2 nextEntityPosition = entity.getPosition().add(entity.getVelocity().multiply(dt));
        entity.setPosition(nextEntityPosition);
    }
}
