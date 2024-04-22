package fr.cubibox.sandbox.engine;

import fr.cubibox.sandbox.base.SandboxScene;
import fr.cubibox.sandbox.engine.io.Window;

import static java.lang.Thread.sleep;

public class Engine implements Runnable {
    private static Engine instance = null;

    private final Thread engineThread = new Thread(this, "ENGINE_THREAD");

    private boolean running = false;

    private final Scene scene = new SandboxScene("map1.map");

    private Window window = null;

    private Engine() {
        window = new Window("Sandbox", 600, 400);
    }

    public static Engine getInstance() {
        if (instance == null) {
            instance = new Engine();
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
            scene.update(dt);
            scene.render(window.getCanvas());
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

    public Scene getGameScene() {
        return scene;
    }
}
