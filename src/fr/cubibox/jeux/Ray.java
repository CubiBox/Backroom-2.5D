package fr.cubibox.jeux;

import java.awt.*;
import java.util.ArrayList;

public class Ray {
    private float angle;
    private float dRay;
    private Points colPos;

    private Color c;

    public Ray(float angle, Player p, Map map) {
        this.angle = (float) (angle*(Math.PI/180));
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
        float sX= p.getX(); //0
        float sY= p.getY(); //0
        float d = 0.005f;
        float a = d;

        int axisX = 1;
        int axisY = 1;

        while (A >= 360)
            A = A - 360;
        while (A <= 0)
            A = A + 360;


        // Axis Y
        if ((A<315 && A>=225) || (A<135 && A>=45)) {
            if ((A < 315 && A >= 225)) {
                axisX = -1;
                this.c = Color.CYAN;
            } else if ((A < 135 && A >= 45)) {
                this.c = Color.YELLOW;
            }

            A = -((A+90)*((float)Math.PI/180));
            while (!isColision2(axisX*a,(axisX*a*(float)Math.tan(axisY*A)), sY, sX, map))
                a+=d;

            sX = (sX+(axisX * a));
            sY = sY+(axisX*a*(float)Math.tan(A));

            this.colPos = new Points((sY)*Engine.WIDTH,sX*Engine.WIDTH);
        }

        // Axis X
        else if ((A<225 && A>=135) || ((A<45 && A>=0) ||(A>=315 && A<=360))) {
            if ((A < 225 && A >= 135)) {
                axisX = -1;
                //axisY = -1;
                this.c = Color.RED;
            } else if ((A<45 && A>=0) ||(A>=315 && A<=360)) {
                this.c = Color.GREEN;
            }

            A = (A*((float)Math.PI/180));
            while (!isColision(axisX*a,(axisX*a*(float)Math.tan(axisY*A)), sX, sY, map))
                a+=d;

            sX = (sX+(axisX * a));
            sY = sY+(axisX*a*(float)Math.tan(axisY*A));

            this.colPos = new Points(sX*Engine.WIDTH, (sY)*Engine.WIDTH);
        }
        return (float)Math.sqrt(
                        ((p.getX() - sY)*(p.getX() - sY)) +
                        ((p.getY() - sX)*(p.getY() - sX))
                );
    }

    public boolean isColision(float x, float y, float sX, float sY, Map map){
        int postX = (int)(1000*(x+sX));
        int postY = (int)(1000*(y+sY));
        if (postY > 7000 || postX > 7000 || postY < 0 || postX < 0 || map.getMapContent()[postY/1000][postX/1000] == 1) {
            return true;
        }
        return false;
    }

    public boolean isColision2(float x, float y, float sX, float sY, Map map){
        int postX = (int)(1000*(x+sX));
        int postY = (int)(1000*(y+sY));
        if (postY > 7000 || postX > 7000 || postY < 0 || postX < 0 || map.getMapContent()[postX/1000][postY/1000] == 1) {
            return true;
        }
        return false;
    }

    public String toString(){
        return "angle : " + this.angle +"; disX : "+ this.dRay +"; "+ this.colPos;
    }


    public Color getC() {
        return c;
    }

    public void setC(Color c) {
        this.c = c;
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
