package fr.cubibox.backroom2_5d.engine.maths;

public class Point2F {
    private float x;
    private float y;

    public Point2F(float x, float y) {
        this.x = x;
        this.y = y;
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

    public String toString() {
        return "[" + (int)this.x + ";" + (int)this.y + "]";
    }


    public void addToX(float x){
        this.x += x;
    }

    public void addToY(float y){
        this.y += y;
    }

    public void add(Point2F p){
        this.x += p.getX();
        this.y += p.getY();
    }

    public void sub(Point2F p){
        this.x -= p.getX();
        this.y -= p.getY();
    }

    public void subToX(float x){
        this.x -= x;
    }

    public void subToY(float y){
        this.y -= y;
    }
}