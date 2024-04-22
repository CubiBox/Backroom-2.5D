package fr.cubibox.sandbox.engine;

import fr.cubibox.sandbox.base.renderer.PixelDrawer;

public interface GameScene {
    void init();

    void render(PixelDrawer pixelDrawer, float dt);

    void update(float dt);

    void input();
}
