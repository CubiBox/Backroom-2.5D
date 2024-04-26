package fr.cubibox.sandbox.engine.maths.shapes;

import fr.cubibox.sandbox.engine.maths.vectors.Vector2;

import static java.lang.Math.clamp;

public class Line implements Shape {
    private Vector2 a;
    private Vector2 b;

    public Line(Vector2 a, Vector2 b) {
        this.a = a;
        this.b = b;
    }

    public String toString() {
        return "(" + a + ";" + b + ")";
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

    @Override
    public float signedDistanceFunction(Vector2 position) {
        Vector2 pa = position.subtract(a);
        Vector2 ba = b.subtract(a);
        float h = clamp(pa.dot(ba) / ba.dot(ba) , 0f, 1f);
        return pa.subtract(ba.multiply(h)).length();
    }
}
