package fr.cubibox.backroom2_5d.engine;

public class GameEngine implements Runnable {
    private final Thread engineThread = new Thread(this, "ENGINE_THREAD");
    public static final long ONE_SECOND_IN_NANO = (long) 1E9;
    public static final long ONE_SECOND_IN_MILLIS = (long) 1E3;

    private static GameEngine instance = null;

    private final float TARGET_UPS = 30F;
    private final float TARGET_FPS = 60F;
    private boolean running = false;

    private GameEngine() {
    }

    public static GameEngine getInstance() {
        if (instance == null) {
            instance = new GameEngine();
        }
        return instance;
    }

    public void start() {
        running = true;
        this.engineThread.start();
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        float timeU = ONE_SECOND_IN_MILLIS / TARGET_UPS;
        float timeR = ONE_SECOND_IN_MILLIS / TARGET_FPS;
        float deltaU = 0f;
        float deltaR = 0f;

        long updateTime = startTime;

        while (running) {
            long now = System.currentTimeMillis();
            deltaU += (now - startTime) / timeU;
            deltaR += (now - startTime) / timeR;

            if (deltaR >= 1) {
                //scene.input();
            }

            if (deltaU >= 1) {
                long dt = now - updateTime;
                //scene.update(dt);
                updateTime = now;
                deltaU--;
            }

            if (deltaR >= 1) {
                long dt = now - updateTime;
                //scene.render(dt);
                deltaR--;
            }

            startTime = now;
        }
    }
}
