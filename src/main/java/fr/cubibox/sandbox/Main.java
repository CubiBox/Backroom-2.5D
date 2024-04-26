package fr.cubibox.sandbox;

import fr.cubibox.sandbox.base.SandboxScene;
import fr.cubibox.sandbox.engine.Engine;
import fr.cubibox.sandbox.engine.Scene;
import fr.cubibox.sandbox.level.Map;

public class Main {
    private static final Engine engine = Engine.getInstance();
    private static final Scene scene = new SandboxScene();

    public static void main(String[] args) {
        scene.init();
        engine.setScene(scene);
        engine.start();
    }

    public static Map defaultMapFactory() {
        return null;
    }
}
