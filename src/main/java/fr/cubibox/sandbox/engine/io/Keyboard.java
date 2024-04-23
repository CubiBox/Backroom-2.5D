package fr.cubibox.sandbox.engine.io;

import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class Keyboard {
    private static final int KEYBOARD_SIZE = 512;

    private static final boolean[] keys = new boolean[KEYBOARD_SIZE];

    protected static GLFWKeyCallback keyboard = new GLFWKeyCallback()
    {
        @Override
        public void invoke(long window, int key, int scancode, int action, int mods)
        {
            keys[key] = action != GLFW_RELEASE;
        }
    };

    public static boolean isKeyPressed(int keyCode) {
        if (keyCode >= 0 && keyCode < KEYBOARD_SIZE) {
            return keys[keyCode];
        } else {
            System.err.println("Key not supported");
            return false;
        }
    }
}
