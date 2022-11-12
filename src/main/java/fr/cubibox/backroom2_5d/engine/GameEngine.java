package fr.cubibox.backroom2_5d.engine;

import fr.cubibox.backroom2_5d.game.Backroom2D;

public class GameEngine implements Runnable {
    private final Thread engineThread = new Thread(this, "ENGINE_THREAD");
    public static final long ONE_SECOND_IN_NANO = (long) 1E9;
    public static final long ONE_SECOND_IN_MILLIS = (long) 1E3;

    private static GameEngine instance = null;

    private final GameScene gameScene = new Backroom2D("map1.map");

    private final float TARGET_UPS = 30F;
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
        float deltaU = 0f;

        long updateTime = startTime;

        while (running) {
            long now = System.currentTimeMillis();
            deltaU += (now - startTime) / timeU;

            if (deltaU >= 1) {
                long dt = now - updateTime;
                gameScene.update(dt);
                updateTime = now;
                deltaU--;
            }

            try {
                long sleepTime = (long) (now + timeU - System.currentTimeMillis());

                if (sleepTime > 0) {
                    Thread.sleep(sleepTime);
                }
            } catch (Exception e) {
                throw new RuntimeException();
            }

            startTime = now;
        }
    }

    public GameScene getGameScene() {
        return gameScene;
    }
}
