package fr.cubibox.backroom2_5d.entities;

import fr.cubibox.backroom2_5d.engine.OldRay;
import fr.cubibox.backroom2_5d.engine.maths.Point2F;

import java.util.ArrayList;

public class Player extends Entity {
    private float fov;

    private ArrayList<OldRay> rays = new ArrayList<>();

    public Player(float x, float y, float fov) {
        super(x, y);
        this.fov = fov;
    }

    public void Avancer(float x){
        int axisX = 1;
        float angle = super.getAngle();

        while (angle > 360)
            angle -= 360;
        while (angle < 0)
            angle += 360;

        //Axis Y
        if ((angle<315 && angle>=225) || (angle<135 && angle>=45)) {
            if ((angle < 315 && angle >= 225))
                axisX = -1;

            angle = -(angle+90)* OldRay.RADIAN;

            this.y -= (float) Math.cos(angle)/axisX * (axisX*x);
            this.x -= (float) Math.sin(angle)/axisX * (axisX*x);
        }

        // Axis X
        else {//if ((angle<225 && angle>=135) || ((angle<45 && angle>=0) ||(angle>=315 && angle<=360))) {
            if ((angle < 225 && angle >= 135))
                axisX = -1;

            System.out.println(angle);

            angle = (angle * OldRay.RADIAN);

            super.getPosition().addToX((float)Math.cos(angle)/axisX * (axisX*x));
            this.y += (float)Math.sin(angle)/axisX * (axisX*x);
        }
        System.out.println(this.x + "; " + this.y);
    }


    public ArrayList<OldRay> getRays() {
        return rays;
    }

    public void setRays(ArrayList<OldRay> rays) {
        this.rays = rays;
    }

    public float getX() {
        return super.getPosition().getX();
    }

    public void setX(float x) {
        super.getPosition().setX(x);
    }

    public float getY() {
        return super.getPosition().getY();
    }

    public void setY(float y) {
        super.getPosition().setY(y);
    }

    public float getAngle() {
        return super.getAngle();
    }

    public void setAngle(float angle) {
        super.setAngle(angle);
    }

    public float getFov() {
        return fov;
    }

    public void setFov(float fov) {
        this.fov = fov;
    }

    public void setPos(float x, float y){
        super.getPosition().setX(x);
        super.getPosition().setY(y);
    }

    public Point2F getPos(){
        return super.getPosition();
    }
}
