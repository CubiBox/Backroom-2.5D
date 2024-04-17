package fr.cubibox.backroom2_5d.engine;

import fr.cubibox.backroom2_5d.engine.graphics.Canvas;

public interface GameScene {
    void init();

    void render(Canvas canvas, float dt);

    void update(float dt);

    void input();
}
