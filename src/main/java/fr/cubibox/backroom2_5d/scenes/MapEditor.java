package fr.cubibox.backroom2_5d.scenes;

import fr.cubibox.backroom2_5d.utils.TimeUtils;
import javafx.animation.AnimationTimer;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class MapEditor extends AnimationTimer implements Initializable {

    // Variables nécessaire pour la boucle
    private long targetFps = 60L;
    private long lastTime = 0L;
    //


    // Variables pour charger les modèles / textures / objects à charger sur la map.

    //


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void handle(long currentTime) {
        long interval = TimeUtils.ONE_SECOND_IN_NANO / targetFps;

        if (currentTime - lastTime > interval) {
            update();
            lastTime = currentTime;
        }
    }

    private void update() {

    }
}
