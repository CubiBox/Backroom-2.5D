package fr.cubibox.backroom2_5d.engine;

public class Points {
    private float x;
    private float y;

    public Points(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public String toString(){
        return "[" + this.x + "," + this.y + "]";
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
