package fr.cubibox.backroom2_5d.engine;

import fr.cubibox.backroom2_5d.game.Map;
import fr.cubibox.backroom2_5d.game.Player;

import java.util.ArrayList;

public class Ray {
    public static final float PI_2_RADIAN = 0.01745f;
    private float angle;

    private Point2F intersectPoint;
    private Point2F startPoint;

    public Ray(float x, float y, float angle) {
        this.angle = angle * PI_2_RADIAN;
        this.startPoint = new Point2F(x, y);
        this.intersectPoint = new Point2F(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
    }

    public Ray(Point2F startPoint, float angle) {
        this.angle = angle;
        this.startPoint = startPoint;
        this.intersectPoint = new Point2F(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
    }

    /*
    public float calcDRay(float A, Player p, Map map){
        float pX= p.getX(); //0
        float pY= p.getY(); //0

        float sX= p.getX(); //0
        float sY= p.getY(); //0

        float d = 0.005f;
        float a = d;
        int axisX = 1;

        while (A >= 360)
            A = A - 360;
        while (A < 0)
            A = A + 360;


        // Axis Y
        if ((A<315 && A>=225) || (A<135 && A>=45)) {
            if (A >= 225)
                axisX = -1;

            A = -(A+90)*PI_2_RADIAN;

            sX = (pY + (axisX * a));
            sY = pX + (axisX * a * (float) Math.tan(A));
            while (!isColision2(sX, sY, map)) {
                a += d;
                sX = (pY + (axisX * a));
                sY = pX + (axisX * a * (float) Math.tan(A));
            }
            this.intersectPoint = new Point2F(sY*80,sX*80);

            return (float)Math.sqrt(
                    ((p.getY() - sX)*(p.getY() - sX)) +
                    ((p.getX() - sY)*(p.getX() - sY))
            );
        }

        // Axis X
        else if ((A<225 && A>=135) || ((A<45 && A>=0) ||(A>=315 && A<=360))) {
            if ((A < 225 && A >= 135))
                axisX = -1;

            A = (A*PI_2_RADIAN);
            while (!isColision(sX,sY, map)) {
                a += d;
                sX = (pX + (axisX * a));
                sY = pY + (axisX * a * (float)Math.tan(A));
            }
            this.intersectPoint = new Point2F(sX*80, sY*80);
        }
        return (float)Math.sqrt(
              ((p.getX() - sX)*(p.getX() - sX)) +
              ((p.getY() - sY)*(p.getY() - sY))
        );
    }

    public boolean isColision(float x, float y, Map map){
        int postX = (int)(1000*(x));
        int postY = (int)(1000*(y));
        if (postY > 7000 || postX > 7000 || postY < 0 || postX < 0 || map.getMapContent()[postY/1000][postX/1000] == 1) {
            return true;
        }
        return false;
    }

    public boolean isColision2(float x, float y, Map map){
        int postX = (int)(1000*(x));
        int postY = (int)(1000*(y));
        if (postY > 7000 || postX > 7000 || postY < 0 || postX < 0 || map.getMapContent()[postX/1000][postY/1000] == 1) {
            return true;
        }
        return false;
    }*/

    public String toString(){
        return "angle : " + this.angle +"; "+ this.intersectPoint;
    }


    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public Point2F getIntersection() {
        return intersectPoint;
    }

    public float getX() {
        return startPoint.getX();
    }

    public float getY() {
        return startPoint.getY();
    }
}
