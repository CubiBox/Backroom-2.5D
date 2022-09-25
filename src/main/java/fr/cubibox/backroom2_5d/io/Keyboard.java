package fr.cubibox.backroom2_5d.io;

import javafx.scene.input.KeyEvent;

public class Keyboard {
    private final boolean[] keys = new boolean[256];

    public void keyPressed(KeyEvent e) {
        if (e.getCode().getCode() >= 0 && e.getCode().getCode() <= 255) {
            keys[e.getCode().getCode()] = true;
        } else {
            System.out.println("Key not supported");
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getCode().getCode() >= 0 && e.getCode().getCode() <= 255) {
            keys[e.getCode().getCode()] = false;
        } else {
            System.out.println("Key not supported");
        }
    }

    public boolean isKeyPressed(int keyCode) {
        if (keyCode >= 0 && keyCode <= 255) {
            return keys[keyCode];
        } else {
            System.out.println("Key not supported");
            return false;
        }
    }
}
