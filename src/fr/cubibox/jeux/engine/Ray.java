package fr.cubibox.jeux.engine;

import fr.cubibox.jeux.game.Map;
import fr.cubibox.jeux.game.Player;

import java.awt.*;
import java.util.ArrayList;

public class Ray {

    public static final float RADIAN = 0.01745f;
    private float angle;
    private float dRay;
    private Points colPos;

    public Ray(float angle, Player p, Map map) {
        this.angle = angle*RADIAN;
        this.dRay = calcDRay(angle,p,map);
        this.calcDRay(angle, p,map);
    }

    public static ArrayList<Ray> setRays(Player p, Map map){
        ArrayList<Ray> rays = new ArrayList<>();
        for (int n =0; n < Engine.WIDTH; n ++){
            float angle = (p.getPov()/Engine.WIDTH*n)+(p.getAngle()-(p.getPov()/2));
            rays.add(new Ray(angle,p,map));
        }
        return rays;
    }

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

            A = -(A+90)*RADIAN;
            while (!isColision2(sX, sY, map)) {
                a += d;
                sX = (pY + (axisX * a));
                sY = pX + (axisX * a * (float) Math.tan(A));
            }
            this.colPos = new Points(sY*80,sX*80);
            return (float)Math.sqrt(
                    ((p.getY() - sX)*(p.getY() - sX)) +
                    ((p.getX() - sY)*(p.getX() - sY))
            );
        }

        // Axis X
        else if ((A<225 && A>=135) || ((A<45 && A>=0) ||(A>=315 && A<=360))) {
            if ((A < 225 && A >= 135))
                axisX = -1;

            A = (A*RADIAN);
            while (!isColision(sX,sY, map)) {
                a += d;
                sX = (pX + (axisX * a));
                sY = pY + (axisX * a * (float)Math.tan(A));
            }

            this.colPos = new Points(sX*80, sY*80);
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
    }

    public String toString(){
        return "angle : " + this.angle +"; disX : "+ this.dRay +"; "+ this.colPos;
    }


    public Points getColPos() {
        return colPos;
    }

    public void setColPos(Points colPos) {
        this.colPos = colPos;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getdRay() {
        return dRay;
    }

    public void setdRay(float dRay) {
        this.dRay = dRay;
    }
}
