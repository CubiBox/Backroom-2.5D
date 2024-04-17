package fr.cubibox.sandbox.engine.maths;

import fr.cubibox.sandbox.engine.maths.vectors.Vector2;

public class Line {
    private Vector2 a;
    private Vector2 b;

    public Line(Vector2 a, Vector2 b) {
        this.a = a;
        this.b = b;
    }

    public String toString() {
        return "\t\t@[" + (int) a.getX() + ";" + (int) a.getY() + "]-[" + (int) b.getX() + ";" + (int) b.getY() + "]\n";
    }

    public Vector2 getA() {
        return a;
    }

    public void setA(Vector2 a) {
        this.a = a;
    }

    public Vector2 getB() {
        return b;
    }

    public void setB(Vector2 b) {
        this.b = b;
    }

    public Vector2 normal() {
        return new Vector2(b.getY() - a.getY(), a.getX() - b.getX());
    }

    public Vector2 vector() {
        return  new Vector2(
                this.getB().getX() - this.getA().getX(),
                this.getB().getY() - this.getA().getY()
        );
    }

    public float length() {
        return vector().length();
    }

    public float lengthSquared() {
        return vector().lengthSquared();
    }
}
