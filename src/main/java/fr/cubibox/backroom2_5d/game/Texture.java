package fr.cubibox.backroom2_5d.game;

import fr.cubibox.backroom2_5d.utils.ImageUtils;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Texture {
    private ArrayList<Image> wallStripTexture = new ArrayList<Image>();
    private float offsetX;
    private float offsetY;
    private float size;

    public Texture(BufferedImage wallTexture) {
        ArrayList<Image> wallStripTexture = new ArrayList<>();
        for (int i = 0; i < ImageUtils.TILE_SIZE; i++) {
            BufferedImage subImg = wallTexture.getSubimage(i, 0, 1, ImageUtils.TILE_SIZE);
            Image img = ImageUtils.convertToFxImage(subImg);

            wallStripTexture.add(img);
            subImg;
        }
        this.wallStripTexture = wallStripTexture;
    }

    public ArrayList<Image> getWallStripTexture() {
        return wallStripTexture;
    }

    public void setWallStripTexture(ArrayList<Image> wallStripTexture) {
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
