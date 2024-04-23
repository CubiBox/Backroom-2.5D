package fr.cubibox.sandbox.base;

import fr.cubibox.sandbox.base.renderer.MinimapRenderer;
import fr.cubibox.sandbox.base.renderer.SandboxRenderer;
import fr.cubibox.sandbox.engine.Scene;
import fr.cubibox.sandbox.engine.PixelDrawer;
import fr.cubibox.sandbox.engine.io.Keyboard;
import fr.cubibox.sandbox.engine.maths.vectors.Vector2;
import fr.cubibox.sandbox.base.entities.Entity;
import fr.cubibox.sandbox.base.entities.Player;
import fr.cubibox.sandbox.level.Map;
import org.lwjgl.glfw.GLFW;

import java.io.File;

import static fr.cubibox.sandbox.level.MapUtils.importMap;

public class SandboxScene implements Scene {
    private final Map map;
    private final Player player;
    private final Camera camera;

    private final MinimapRenderer minimapRenderer;
    private final SandboxRenderer renderer;

    public SandboxScene(String mapPath) {
        this.map = importMap(new File(mapPath));
        this.player = map.getPlayer();
        this.camera = new Camera(600, 400);

        this.minimapRenderer = new MinimapRenderer(map, camera);
        this.renderer = new SandboxRenderer();
    }

    @Override
    public void init() {

    }

    @Override
    public void render(PixelDrawer pixelDrawer) {
        minimapRenderer.render(pixelDrawer);
    }

    @Override
    public void update(float dt) {
        updateEntity(this.player, dt);
        input();
    }

    @Override
    public void input() {
        if (Keyboard.isKeyPressed(GLFW.GLFW_KEY_Z)) {
            camera.move(camera.getOrientation().divide(10f));
        }
        if (Keyboard.isKeyPressed(GLFW.GLFW_KEY_S)) {
            camera.move(camera.getOrientation().multiply(-.1f));
        }
        if (Keyboard.isKeyPressed(GLFW.GLFW_KEY_Q)) {
            System.out.println("Q");
        }
        if (Keyboard.isKeyPressed(GLFW.GLFW_KEY_D)) {
            System.out.println("D");
        }
        if (Keyboard.isKeyPressed(GLFW.GLFW_KEY_LEFT)) {
            camera.rotate(-0.05f);
            System.out.println(camera.getOrientation().angle());
        }
        if (Keyboard.isKeyPressed(GLFW.GLFW_KEY_RIGHT)) {
            camera.rotate(0.05f);
            System.out.println(camera.getOrientation().angle());
        }

        if (Keyboard.isKeyPressed(GLFW.GLFW_KEY_KP_ADD)) {
            minimapRenderer.setScale(minimapRenderer.getScale() + 0.25f);
            System.out.println("Minimap scale : " + minimapRenderer.getScale());
        }
        if (Keyboard.isKeyPressed(GLFW.GLFW_KEY_KP_SUBTRACT)) {
            minimapRenderer.setScale(minimapRenderer.getScale() - 0.25f);
            System.out.println("Minimap scale : " + minimapRenderer.getScale());
        }
    }

    private void updateEntity(Entity entity, float dt) {
        Vector2 nextEntityPosition = entity.getPosition().add(entity.getVelocity().multiply(dt));
        entity.setPosition(nextEntityPosition);
    }
}
