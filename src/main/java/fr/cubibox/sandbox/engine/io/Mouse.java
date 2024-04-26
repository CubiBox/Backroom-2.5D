package fr.cubibox.sandbox.engine.io;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;

// TODO: Refactor this class.......
public class Mouse implements GLFWMouseButtonCallbackI {
    private static final int MOUSE_BUTTONS_SIZE = 16;

    private final boolean[] buttons = new boolean[MOUSE_BUTTONS_SIZE];

    private final long window;

    protected Mouse(long window) {
        this.window = window;
    }

    @Override
    public void invoke(long window, int button, int action, int mods) {
        if (button < MOUSE_BUTTONS_SIZE) {
            buttons[button] = action == GLFW_PRESS;
        }
    }

    public boolean isButtonPressed(int button) {
        if (button >= 0 && button < MOUSE_BUTTONS_SIZE) {
            return buttons[button];
        } else {
            System.err.println("Mouse button not supported");
            return false;
        }
    }

    public double getX() {
        DoubleBuffer c = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(window, c, null);
        return c.get(0);
    }

    public double getY() {
        DoubleBuffer c = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(window, null, c);
        return c.get(0);
    }
}
