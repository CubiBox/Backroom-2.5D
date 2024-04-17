package fr.cubibox.sandbox.old;

import fr.cubibox.sandbox.engine.observers.EngineObserver;
import fr.cubibox.sandbox.engine.observers.events.Event;
import fr.cubibox.sandbox.base.Map;
import fr.cubibox.sandbox.base.entities.Player;

import java.util.HashSet;

import static fr.cubibox.sandbox.engine.PhyzEngine.ONE_SECOND_IN_NANO;

public class Engine implements Runnable {
    private final Thread engineThread = new Thread(this, "ENGINE");
    private final HashSet<EngineObserver> engineObservers = new HashSet<>();
    private final int TARGET_UPS;
    private boolean running = false;

    private final Player player;
    private final Map map;

    public Engine(int TARGET_UPS, Player player, Map map) {
        this.TARGET_UPS = TARGET_UPS;
        this.player = player;
        this.map = map;
    }

    public void start() {
        this.engineThread.start();
        this.running = true;
    }

    public void stop() {
        this.running = false;
    }

    public void loop() {
        long updateInterval = ONE_SECOND_IN_NANO / TARGET_UPS;
        long nextUpdateTime = System.nanoTime() + updateInterval;

        while (running) {
            try {
                long sleepTime = nextUpdateTime - System.nanoTime();

                if (sleepTime > 0) {
                    Thread.sleep(sleepTime / 1000000, (int) (sleepTime % 1000000));
                }

                nextUpdateTime += updateInterval;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void addObserver(EngineObserver observer) {
        this.engineObservers.add(observer);
    }

    public void removeObserver(EngineObserver observer) {
        this.engineObservers.remove(observer);
    }

    public void notifyObservers(Event event) {
        for (EngineObserver observer : engineObservers) {
            observer.addEvent(event);
        }
    }

    @Override
    public void run() {
        loop();
        System.out.println("Engine stopped !");
    }
}
