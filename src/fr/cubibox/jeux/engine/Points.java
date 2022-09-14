package fr.cubibox.jeux.engine;

public class Points {
    private float x;
    private float y;
    private int content;


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

    public int getContent() {
        return content;
    }

    public void setContent(int content) {
        this.content = content;
    }
}
