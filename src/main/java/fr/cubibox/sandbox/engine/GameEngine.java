package fr.cubibox.sandbox.engine;

import fr.cubibox.sandbox.base.SandboxScene;
import fr.cubibox.sandbox.engine.io.Window;

import static java.lang.Thread.sleep;

public class GameEngine implements Runnable {
    private static GameEngine instance = null;

    private final Thread engineThread = new Thread(this, "ENGINE_THREAD");

    private boolean running = false;

    private final GameScene gameScene = new SandboxScene("map1.map");

    private Window window = null;

    private GameEngine() {
        window = new Window("Sandbox", 600, 400);
    }

    public static GameEngine getInstance() {
        if (instance == null) {
            instance = new GameEngine();
        }
        return instance;
    }

    public void start() {
        running = true;
        engineThread.start();
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        window.init();
        window.initTexture();

        long startTime = System.currentTimeMillis();
        long targetUps = 60L;
        long targetUpdateTime = (long) (1E3 / targetUps);
        long updateTime = startTime;

        float time = 0;

        while (running) {
            long now = System.currentTimeMillis();

            float dt = (float) ((now - updateTime) / 1E3);
            gameScene.update(dt);
            gameScene.render(window.getCanvas(), dt);
            window.render();

            if (time >= 1) {
                time--;
            }

            try {
                long currentTime = System.currentTimeMillis();
                float sleepTime = (now + targetUpdateTime) - currentTime;

                time += dt;

                if (sleepTime > 0) {
                    sleep((long) sleepTime);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            updateTime = now;

            running = !window.shouldClose();
        }

        System.out.println("Bye byee !");
    }

    public GameScene getGameScene() {
        return gameScene;
    }
}
