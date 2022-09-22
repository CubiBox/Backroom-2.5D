package fr.cubibox.backroom2_5d.engine;

import fr.cubibox.backroom2_5d.engine.maths.Point2F;

/**
 * Classe de définition de rayon pour le raycasting
 */
public class Ray {
    private final float angle;
    private final Point2F startPoint;
    private final Point2F intersectionPoint;

    /**
     * @param startPoint
     * @param angle
     *
     * Crée un rayon à partir d'un point de départ et d'un angle
     */
    public Ray(Point2F startPoint, float angle) {
        this.startPoint = startPoint;
        this.angle = angle;
        this.intersectionPoint = new Point2F(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
    }

    /**
     * @param x
     * @param y
     * @param angle
     *
     * Crée un rayon à partir d'une coordonée x et y, puis d'un angle
     */
    public Ray(float x, float y, float angle) {
        this.startPoint = new Point2F(x, y);
        this.angle = angle;
        this.intersectionPoint = new Point2F(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
    }

    /**
     * @return
     *
     * Retourne l'angle du rayon
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

    public Point2F getStartPoint() {
        return startPoint;
    }

    public float getIntersectionX() {
        return intersectionPoint.getX();
    }

    public float getIntersectionY() {
        return intersectionPoint.getY();
    }

    public Point2F getIntersectionPoint() {
        return intersectionPoint;
    }
}
