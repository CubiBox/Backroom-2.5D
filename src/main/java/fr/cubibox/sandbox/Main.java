package fr.cubibox.sandbox;

import fr.cubibox.sandbox.engine.Engine;
import fr.cubibox.sandbox.engine.io.Keyboard;

public class Main {
    public static final Keyboard keyboard = new Keyboard();

    public static void main(String[] args) {
        Engine.getInstance().start();
    }
}
