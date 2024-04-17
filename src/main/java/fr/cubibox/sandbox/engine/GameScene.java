package fr.cubibox.sandbox.engine;

import fr.cubibox.sandbox.engine.graphics.Canvas;

public interface GameScene {
    void init();

    void render(Canvas canvas, float dt);

    void update(float dt);

    void input();
}
