package fr.cubibox.sandbox.engine.io;

import fr.cubibox.sandbox.engine.PixelDrawer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;

import java.nio.ByteBuffer;

import static fr.cubibox.sandbox.engine.Engine.HEIGHT;
import static fr.cubibox.sandbox.engine.Engine.WIDTH;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static java.sql.Types.NULL;

// TODO: Refactor this class.......
public class Window {
    private final String title;

    private final PixelDrawer pixelDrawer;
    private Mouse mouse;

    private int textureID;

    private long window;
    private final ByteBuffer buffer;

    public Window(String title) {
        this.title = title;
        this.buffer = BufferUtils.createByteBuffer(WIDTH * HEIGHT * 3);

        pixelDrawer = new PixelDrawer();
    }

    public void init() {
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

        window = glfwCreateWindow(WIDTH, HEIGHT, title, NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        mouse = new Mouse(window);

        glfwSetKeyCallback(window, Keyboard.keyboard);
        glfwSetMouseButtonCallback(window, mouse);

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);

        initOpenGL();

        glfwShowWindow(window);
    }

    public void initOpenGL() {
        GL.createCapabilities();

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        glViewport(0, 0, WIDTH, HEIGHT);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, WIDTH, HEIGHT, 0, -1, 1);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
    }

    public void initTexture() {
        textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, WIDTH, HEIGHT, 0, GL_RGBA, GL_UNSIGNED_BYTE, (ByteBuffer) null);
    }

    public void render() {
        glfwPollEvents();

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
        glTexSubImage2D(GL_TEXTURE_2D, 0, 0, 0, WIDTH, HEIGHT, GL_RGB, GL_UNSIGNED_BYTE, buffer);

        //glClear(GL_COLOR_BUFFER_BIT);
        glBegin(GL_QUADS);
        glTexCoord2f(0, 0);
        glVertex2f(0, 0);
        glTexCoord2f(1, 0);
        glVertex2f(WIDTH, 0);
        glTexCoord2f(1, 1);
        glVertex2f(WIDTH, HEIGHT);
        glTexCoord2f(0, 1);
        glVertex2f(0, HEIGHT);
        glEnd();

        glDisable(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, 0);

        glfwSwapBuffers(window);
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(window);
    }

    public PixelDrawer getPixelDrawer() {
        return pixelDrawer;
    }

    public Mouse getMouse() {
        return mouse;
    }
}
