package fr.cubibox.sandbox;

import fr.cubibox.sandbox.engine.GameEngine;
import fr.cubibox.sandbox.engine.io.Keyboard;
import fr.cubibox.sandbox.engine.io.Mouse;

public class Main {
    public static final Keyboard keyboard = new Keyboard();

    public static void main(String[] args) {
        GameEngine.getInstance().start();
    }
}
