package fr.cubibox.sandbox.engine;

public interface Scene {
    /**
     * Initializes the scene.
     * This function needs t
     */
    void init();

    void render(PixelDrawer pixelDrawer);

    void update(float dt);

    void input();

    void destroy();
}
