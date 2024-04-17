package fr.cubibox.sandbox.engine.io;

public class Keyboard {
    private final boolean[] keys = new boolean[256];

    public void keyPressed(KeyEvent e) {
        if (e.getCode() >= 0 && e.getCode() <= 255) {
            keys[e.getCode()] = true;
        } else {
            System.err.println("Key not supported");
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getCode() >= 0 && e.getCode() <= 255) {
            keys[e.getCode()] = false;
        } else {
            System.err.println("Key not supported");
        }
    }

    public boolean isKeyPressed(int keyCode) {
        if (keyCode >= 0 && keyCode <= 255) {
            return keys[keyCode];
        } else {
            System.err.println("Key not supported");
            return false;
        }
    }

    // TODO: This is a temporary class
    public interface KeyEvent {
        int getCode();
    }
}
