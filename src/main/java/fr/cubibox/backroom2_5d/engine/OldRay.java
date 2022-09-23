package fr.cubibox.backroom2_5d.engine;

import fr.cubibox.backroom2_5d.engine.maths.Point2F;
import fr.cubibox.backroom2_5d.entities.Player;
import fr.cubibox.backroom2_5d.game.Map;

import java.util.ArrayList;

public class OldRay {
    public static final float RADIAN = 0.01745f;
    private float angle;
    private float dRay;
    private Point2F colPos;

    public OldRay(float angle, Player p, Map map) {
        this.angle = angle * RADIAN;
        this.dRay = calcDRay(angle,p,map);
        this.calcDRay(angle, p,map);
    }

    public static ArrayList<OldRay> setRays(Player p, Map map){
        ArrayList<OldRay> rays = new ArrayList<>();
        for (int n = 0; n < OldEngine.WIDTH; n ++){
            float angle = (p.getFov()/ OldEngine.WIDTH*n)+(p.getAngle()-(p.getFov()/2));
            rays.add(new OldRay(angle,p,map));
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

            sX = (pY + (axisX * a));
            sY = pX + (axisX * a * (float) Math.tan(A));
            while (!isColision2(sX, sY, map)) {
                a += d;
                sX = (pY + (axisX * a));
                sY = pX + (axisX * a * (float) Math.tan(A));
            }
            this.colPos = new Point2F(sY*80,sX*80);

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
            this.colPos = new Point2F(sX*80, sY*80);
        }
        return (float)Math.sqrt(
              ((p.getX() - sX)*(p.getX() - sX)) +
              ((p.getY() - sY)*(p.getY() - sY))
        );
    }

    public boolean isColision(float x, float y, Map map){
        int postX = (int)(1000*(x));
        int postY = (int)(1000*(y));
        /*if (postY > Main.xSize*1000 || postX > Main.xSize*1000 || postY < 0 || postX < 0){ // || map.getMapContent()[postY/1000][postX/1000] == 1) {
            return true;
        }*/
        return false;
    }

    public boolean isColision2(float x, float y, Map map){
        int postX = (int)(1000*(x));
        int postY = (int)(1000*(y));
        /*if (postY > Main.xSize || postX > Main.xSize || postY < 0 || postX < 0){ //|| map.getMapContent()[postX/1000][postY/1000] == 1) {
            return true;
        }*/
        return false;
    }


    public String toString(){
        return "angle : " + this.angle +"; disX : "+ this.dRay +"; "+ this.colPos;
    }


    public Point2F getColPos() {
        return colPos;
    }

    public void setColPos(Point2F colPos) {
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
