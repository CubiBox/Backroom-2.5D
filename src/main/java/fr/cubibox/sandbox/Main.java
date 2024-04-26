package fr.cubibox.sandbox;

import fr.cubibox.sandbox.engine.Engine;
import fr.cubibox.sandbox.level.Chunk;
import fr.cubibox.sandbox.level.Map;

public class Main {
    private static final Engine engine = Engine.getInstance();
    private static final SandboxScene scene = new SandboxScene();

    public static void main(String[] args) {
        scene.init();
        scene.setMap(defaultMapFactory());
        engine.setScene(scene);
        engine.start();
    }

    public static Map defaultMapFactory() {
        return new Map(new Chunk[0][0], "", 0f);
    }
}
