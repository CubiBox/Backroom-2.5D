package fr.cubibox.sandbox.old;

import fr.cubibox.sandbox.engine.observers.EngineObserver;
import fr.cubibox.sandbox.engine.observers.events.Event;
import fr.cubibox.sandbox.engine.maths.Vector2F;
import fr.cubibox.sandbox.base.Map;
import fr.cubibox.sandbox.base.entities.Player;
import fr.cubibox.sandbox.io.Keyboard;

import java.awt.event.KeyEvent;
import java.util.HashSet;

import static fr.cubibox.sandbox.engine.Ray.RADIAN_PI_2;
import static fr.cubibox.sandbox.engine.PhyzEngine.ONE_SECOND_IN_NANO;

public class oEngine implements Runnable {
    private final Thread engineThread = new Thread(this, "ENGINE");
    private final HashSet<EngineObserver> engineObservers = new HashSet<>();
    private final int targetUPS;
    private final Player player;
    private final Map map;
    public boolean shouldStop = false;

    public oEngine(int targetUPS, Player player, Map map) {
        this.targetUPS = targetUPS;
        this.player = player;
        this.map = map;
    }

    @Override
    public void run() {
        loop();
        System.out.println("Engine stopped !");
    }

    private void loop() {
        long updateInterval = ONE_SECOND_IN_NANO / targetUPS;
        long nextUpdateTime = System.nanoTime() + updateInterval;

        while (!shouldStop) {
            pollKeyEvents();
            update(updateInterval);

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

    private void update(float dt) {
        System.out.println("Delta time : " + dt);
        updatePlayer(dt);
    }

    private void updatePlayer(float dt) {
        //Chunk getPlayerChunk = map.getChunk((int) (player.getX() / 16), (int) (player.getY() / 16));

        /*if (getPlayerChunk != null) {
            for (Shape collisionBoxShard : player.getCollisionBox()) {
                if (collisionBoxShard instanceof Circle2F circle) {
                    for (MapObject mapObject : getPlayerChunk.getMapObjects()) {
                        for (Line2F edge : mapObject.getEdges()) {
                            Point2F nextCirclePos = new Point2F(
                                    circle.getX() + player.getX() + player.getVelocity().getX(),
                                    circle.getY() + player.getY() + player.getVelocity().getY()
                            );
                            Circle2F nextCircle = new Circle2F(nextCirclePos, circle.getRadius());

                            if (LineCircle.lineCircle(edge, nextCircle)) {
                                Point2F nextCirclePosX = new Point2F(
                                        circle.getX() + player.getX() + player.getVelocity().getX(),
                                        circle.getY() + player.getY()
                                );
                                Circle2F nextCircleX = new Circle2F(nextCirclePosX, circle.getRadius());

                                Point2F nextCirclePosY = new Point2F(
                                        circle.getX() + player.getX(),
                                        circle.getY() + player.getY() + player.getVelocity().getY()
                                );
                                Circle2F nextCircleY = new Circle2F(nextCirclePosY, circle.getRadius());

                                if (LineCircle.lineCircle(edge, nextCircleX)) {
                                    player.getVelocity().setX(0f);
                                }

                                if (LineCircle.lineCircle(edge, nextCircleY)) {
                                    player.getVelocity().setY(0f);
                                }
                            }
                        }
                    }
                }
            }
        }*/

        player.setX(player.getX() + player.getVelocity().getX());
        player.setY(player.getY() + player.getVelocity().getY());
    }

    public void pollKeyEvents() {
        Keyboard keyboard = null;//Main.getKeyboard();
        if (keyboard.isKeyPressed(KeyEvent.VK_Z)) {
            player.getVelocity().setX((float) Math.cos(player.getAngle() * RADIAN_PI_2) * 0.5f);
            player.getVelocity().setY((float) Math.sin(player.getAngle() * RADIAN_PI_2) * 0.5f);
        } else if (keyboard.isKeyPressed(KeyEvent.VK_S)) {
            player.getVelocity().setX((float) Math.cos(player.getAngle() * RADIAN_PI_2) * -0.5f);
            player.getVelocity().setY((float) Math.sin(player.getAngle() * RADIAN_PI_2) * -0.5f);
        }

        if (!keyboard.isKeyPressed(KeyEvent.VK_S) && !keyboard.isKeyPressed(KeyEvent.VK_Z)) {
            player.setVelocity(new Vector2F(0.0f, 0.0f));
        }

        if (keyboard.isKeyPressed(KeyEvent.VK_Q)) {
            player.setAngle((player.getAngle() - 10f));
        } else if (keyboard.isKeyPressed(KeyEvent.VK_D)) {
            player.setAngle((player.getAngle() + 10f));
        }
    }

    public void start() {
        engineThread.start();
    }

    public void stop() {
        shouldStop = true;
    }

    public Map getMap() {
        return map;
    }

    public Player getPlayer() {
        return player;
    }
    public void addObserver(EngineObserver engineObserver) {
        engineObservers.add(engineObserver);
    }

    public void removeObserver(EngineObserver engineObserver) {
        engineObservers.remove(engineObserver);
    }

    public void notifyObservers(Event event) {
    }
}
