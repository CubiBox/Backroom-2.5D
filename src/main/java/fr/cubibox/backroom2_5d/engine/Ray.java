package fr.cubibox.backroom2_5d.engine;

import fr.cubibox.backroom2_5d.engine.maths.Point2F;

import java.util.ArrayList;
import java.util.Objects;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Classe de définition de rayon pour le raycasting
 */
public class Ray {
    public static final float RADIAN_PI_2 = 0.0174532925199f;
    private static final float dist = 32f;

    private final float angle;
    private final Point2F startPoint;
    private final Point2F intersectionPoint;

    ArrayList<Point2F> points;

    private int textureIndex;
    private ArrayList<Point2F> floorHit;

    /**
     * @param startPoint
     * @param angle      Crée un rayon à partir d'un point de départ et d'un angle
     */
    public Ray(Point2F startPoint, float angle) {
        this.startPoint = startPoint;
        this.angle = angle;

        this.intersectionPoint = new Point2F(
                (float) (cos(angle * RADIAN_PI_2) * dist) + startPoint.getX(),
                (float) (sin(angle * RADIAN_PI_2) * dist) + startPoint.getY()
        );
    }

    /**
     * @param x
     * @param y
     * @param angle Crée un rayon à partir d'une coordonée x et y, puis d'un angle
     */
    public Ray(float x, float y, float angle) {
        this.startPoint = new Point2F(x, y);
        this.angle = angle;
        this.floorHit = new ArrayList<>();

        //set the intersection point with a length of 32

        this.intersectionPoint = new Point2F(
                (float) (cos(angle * RADIAN_PI_2) * dist) + startPoint.getX(),
                (float) (sin(angle * RADIAN_PI_2) * dist) + startPoint.getY()
        );
    }

    /**
     * @return Retourne l'angle du rayon
     */
    public float getAngle() {
        return angle;
    }

    public float getStartX() {
        return startPoint.getX();
    }

    public float getStartY() {
        return startPoint.getY();
    }

    public float getIntersectionX() {
        return intersectionPoint.getX();
    }

    public void setIntersectionX(float x) {
        intersectionPoint.setX(x);
    }

    public float getIntersectionY() {
        return intersectionPoint.getY();
    }

    public void setIntersectionY(float y) {
        intersectionPoint.setY(y);
    }

    public int getTextureIndex() {
        return textureIndex;
    }

    public void setTextureIndex(int textureIndex) {
        this.textureIndex = textureIndex;
    }

    public void addPoint(Point2F point) {
        if (points == null) {
            points = new ArrayList<>();
        }
        points.add(point);
    }

    public ArrayList<Point2F> getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return "Ray{" +
                "textureIndex=" + textureIndex +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ray ray)) return false;
        return Float.compare(ray.getAngle(), getAngle()) == 0 && getTextureIndex() == ray.getTextureIndex() && Objects.equals(startPoint, ray.startPoint) && Objects.equals(intersectionPoint, ray.intersectionPoint) && Objects.equals(getPoints(), ray.getPoints());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAngle(), startPoint, intersectionPoint, getPoints(), getTextureIndex());
    }

    public void addFloorHit(Point2F floorHit) {
        this.floorHit.add(floorHit);
    }

    public ArrayList<Point2F> getFloorPoints() {
        return floorHit;
    }
}