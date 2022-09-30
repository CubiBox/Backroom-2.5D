package fr.cubibox.backroom2_5d.game;

import fr.cubibox.backroom2_5d.utils.ImageUtils;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Texture {
    private ArrayList<BufferedImage> wallStripTexture = new ArrayList<>();
    private float offsetX;
    private float offsetY;
    private float size;

    public Texture(BufferedImage wallTexture) {
        ArrayList<BufferedImage> wallStripTexture = new ArrayList<>();
        for (int i = 0; i < ImageUtils.TILE_SIZE; i++) {
            BufferedImage subImg = wallTexture.getSubimage(i, 0, 1, ImageUtils.TILE_SIZE);
            //Image img = ImageUtils.convertToFxImage(subImg);

            wallStripTexture.add(subImg);
        }
        this.wallStripTexture = wallStripTexture;
    }

    public ArrayList<BufferedImage> getWallStripTexture() {
        return wallStripTexture;
    }

    public void setWallStripTexture(ArrayList<BufferedImage> wallStripTexture) {
        this.wallStripTexture = wallStripTexture;
    }

    public float getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(float offsetX) {
        this.offsetX = offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }
}
