package fr.cubibox.sandbox.engine;

public interface Scene {
    void init();

    void render(PixelDrawer pixelDrawer);

    void update(float dt);

    void input();
}
