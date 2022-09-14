package fr.cubibox.jeux.game;

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

        //Axis Y
        if ((angle<315 && angle>=225) || (angle<135 && angle>=45)) {
            if ((angle < 315 && angle >= 225))
                axisX = -1;

            angle = ((angle+90)* ((float)Math.PI/180));
            System.out.println(this.x + "," + this.y);
            this.y += (float) Math.cos(angle)/(axisX)/10;
            this.x += (float) Math.sin(angle)/(axisX)/10;
            System.out.println(this.x + "," + this.y);
        }

        // Axis X
        else if ((angle<225 && angle>=135) || ((angle<45 && angle>=0) ||(angle>=315 && angle<=360))) {
            if ((angle < 225 && angle >= 135))
                axisX = -1;

            System.out.println(angle);

            angle = (angle * ((float) Math.PI / 180));
            this.x += (float) Math.cos(angle)/(axisX)/10;
            this.y += (float) Math.sin(angle)/(axisX)/10;
            System.out.println(this.x + "," + this.y);
        }
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
