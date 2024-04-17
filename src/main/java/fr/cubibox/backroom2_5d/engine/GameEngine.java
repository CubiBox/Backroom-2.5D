package fr.cubibox.backroom2_5d.engine;

import fr.cubibox.backroom2_5d.game.Backroom2D;

public class GameEngine implements Runnable {
    private final Thread engineThread = new Thread(this, "ENGINE_THREAD");

    public static final double ONE_SECOND_IN_MILLIS = 1E3;

    private static GameEngine instance = null;

    private final GameScene gameScene = new Backroom2D("map1.map");

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
        engineThread.start();
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        long targetUps = 30L;
        long targetUpdateTime = (long) (ONE_SECOND_IN_MILLIS / targetUps);
        long updateTime = startTime;

        float time = 0;

        while (running) {
            long now = System.currentTimeMillis();

            float dt = (float) ((now - updateTime) / ONE_SECOND_IN_MILLIS);
            gameScene.update(dt);

            if (time >= 1) {
                time--;
            }

            try {
                long currentTime = System.currentTimeMillis();
                float sleepTime = (now + targetUpdateTime) - currentTime;

                time += dt;

                if (sleepTime > 0) {
                    wait((long) sleepTime);
                }
            } catch (Exception e) {
                throw new RuntimeException();
            }

            updateTime = now;
        }

        System.out.println("Bye byee !");
    }

    public GameScene getGameScene() {
        return gameScene;
    }
}
