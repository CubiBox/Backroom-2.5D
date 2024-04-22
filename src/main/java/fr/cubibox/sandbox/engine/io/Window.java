package fr.cubibox.sandbox.engine.io;

import fr.cubibox.sandbox.engine.PixelDrawer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;

import java.nio.ByteBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static java.sql.Types.NULL;

public class Window {
    private final String title;
    private final int width, height;

    private final PixelDrawer pixelDrawer;

    private int textureID;

    private long window;

    public Window(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;

        pixelDrawer = new PixelDrawer(width, height);
    }

    public void init() {
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

        window = glfwCreateWindow(width, height, title, NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);

        initOpenGL();

        glfwShowWindow(window);
    }

    public void initOpenGL() {
        GL.createCapabilities();

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        glViewport(0, 0, width, height);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, width, height, 0, -1, 1);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
    }

    public void initTexture() {
        textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, (ByteBuffer) null);
    }

    public void render() {
        glfwPollEvents();
        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 3);

        int[] pixels = pixelDrawer.getPixels();
        for (int pixel : pixels) {
            byte rb = (byte) ((pixel >> 16) & 0xFF);
            byte gb = (byte) ((pixel >> 8) & 0xFF);
            byte bb = (byte) (pixel & 0xFF);

            buffer.put(rb);
            buffer.put(gb);
            buffer.put(bb);
        }

        buffer.flip();

        glEnable(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, textureID);
        glTexSubImage2D(GL_TEXTURE_2D, 0, 0, 0, width, height, GL_RGB, GL_UNSIGNED_BYTE, buffer);

        //glClear(GL_COLOR_BUFFER_BIT);
        glBegin(GL_QUADS);
        glTexCoord2f(0, 0);
        glVertex2f(0, 0);
        glTexCoord2f(1, 0);
        glVertex2f(width, 0);
        glTexCoord2f(1, 1);
        glVertex2f(width, height);
        glTexCoord2f(0, 1);
        glVertex2f(0, height);
        glEnd();

        glDisable(GL_TEXTURE_2D);

        glfwSwapBuffers(window);
    }

    public PixelDrawer getCanvas() {
        return pixelDrawer;
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(window);
    }
}
