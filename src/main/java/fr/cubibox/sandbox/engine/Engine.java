package fr.cubibox.sandbox.engine;

import fr.cubibox.sandbox.base.SandboxScene;
import fr.cubibox.sandbox.engine.io.Window;

import static java.lang.Thread.sleep;

public class Engine {
    public static final int WIDTH  = 600;
    public static final int HEIGHT = 400;

    private static Engine instance = null;

    private final Thread engineThread;

    private Scene scene;

    private Window window;

    private boolean running;

    private Engine() {
        this.engineThread = new Thread(this::run, "Sandbox Engine Thread");
        this.window = new Window("Sandbox");
        this.scene = new SandboxScene("map1.map");
        this.running = false;
    }

    public void start() {
        running = true;
        engineThread.start();
    }

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
            scene.render(window.getPixelDrawer());
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
                System.err.println(e);
            }

            updateTime = now;

            running = !window.shouldClose();
        }

        System.out.println("Bye bye !");
    }

    public void stop() {
        this.running = false;
        this.engineThread.interrupt();
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Window getWindow() {
        return window;
    }

    public static Engine getInstance() {
        if (instance == null) {
            instance = new Engine();
        }
        return instance;
    }
}
