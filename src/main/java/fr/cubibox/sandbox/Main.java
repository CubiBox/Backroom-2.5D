package fr.cubibox.sandbox;

import fr.cubibox.sandbox.engine.GameEngine;
import fr.cubibox.sandbox.io.Keyboard;
import fr.cubibox.sandbox.io.Mouse;

public class Main {
    public static final Keyboard keyboard = new Keyboard();
    public static final Mouse mouse = new Mouse();
    public static int windowWidth = 720;
    public static int windowHeight = 480;

    public static void main(String[] args) {
        GameEngine.getInstance().start();
    }
}
