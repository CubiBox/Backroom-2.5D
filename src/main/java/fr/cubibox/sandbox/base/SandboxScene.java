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

public class SandboxScene implements Scene {
    private final MinimapRenderer minimapRenderer;
    private final SandboxRenderer renderer;

    private final Player player;
    private final Camera camera;

    private Map map;

    public SandboxScene() {
        this.player = new Player(0f, 0f);
        this.camera = new Camera();

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

    @Override
    public void destroy() {
        // TODO: nothing to do.
    }

    private void updateEntity(Entity entity, float dt) {
        Vector2 nextEntityPosition = entity.getPosition().add(entity.getVelocity().multiply(dt));
        entity.setPosition(nextEntityPosition);
    }
}
