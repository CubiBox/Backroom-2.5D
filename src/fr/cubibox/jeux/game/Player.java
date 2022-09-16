package fr.cubibox.jeux.game;

import fr.cubibox.jeux.engine.Ray;

import java.awt.*;

public class Player {
    private float x;
    private float y;
    private float angle;
    private float pov;

    public Player(float x, float y, float pov) {
        this.x = x;
        this.y = y;
        this.pov = pov;
    }

    public void Avancer(float x){
        int axisX = 1;
        float angle = this.angle;

        while (angle >= 360)
            angle -= 360;
        while (angle <= 0)
            angle += 360;

        //Axis Y
        if ((angle<315 && angle>=225) || (angle<135 && angle>=45)) {
            if ((angle < 315 && angle >= 225))
                axisX = -1;

            angle = -(angle+90)*Ray.RADIAN;

            this.y -= (float) Math.cos(angle)/axisX * (axisX*x);
            this.x -= (float) Math.sin(angle)/axisX * (axisX*x);
        }

        // Axis X
        else {//if ((angle<225 && angle>=135) || ((angle<45 && angle>=0) ||(angle>=315 && angle<=360))) {
            if ((angle < 225 && angle >= 135))
                axisX = -1;

            angle = (angle * Ray.RADIAN);

            this.x += (float)Math.cos(angle)/axisX * (axisX*x);
            this.y += (float)Math.sin(angle)/axisX * (axisX*x);
        }
        System.out.println(this);
    }


    public String toString(){
        return "["+this.x+", "+this.y+"]\t"+this.angle+"; "+this.pov;
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

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        while (angle >= 360)
            angle = angle - 360;
        this.angle = angle;
    }

    public float getPov() {
        return pov;
    }

    public void setPov(float pov) {
        this.pov = pov;
    }
}
