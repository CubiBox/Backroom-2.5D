package fr.cubibox.sandbox.engine;

public interface Scene {
    /**
     * Initializes the scene.
     * This function needs to be called before calling update, render and input methods.
     */
    void init();

    void render();

    void update(float dt);

    void input();

    /**
     * Called on the current scene at the Engine.setScene() call.
     * Can be used to destroy allocated buffers with JNI.
     */
    void destroy();
}
