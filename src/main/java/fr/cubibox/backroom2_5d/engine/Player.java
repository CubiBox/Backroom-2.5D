package fr.cubibox.backroom2_5d.engine;

import fr.cubibox.backroom2_5d.engine.maths.Point2F;

import java.util.ArrayList;

public class Player {
    private final Point2F position;
    private float angle;
    private float fov;

    private ArrayList<OldRay> rays = new ArrayList<>();

    public Player(float x, float y, float fov) {
        this.position = new Point2F(x,y);
        this.fov = fov;
    }

    public void Avancer(float x){
        /*int axisX = 1;
        float angle = this.angle;

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

            this.x += (float)Math.cos(angle)/axisX * (axisX*x);
            this.y += (float)Math.sin(angle)/axisX * (axisX*x);
        }
        System.out.println(this.x + "; " + this.y);*/
    }


    public ArrayList<OldRay> getRays() {
        return rays;
    }

    public void setRays(ArrayList<OldRay> rays) {
        this.rays = rays;
    }

    public float getX() {
        return this.position.getX();
    }

    public void setX(float x) {
        this.position.setX(x);
    }

    public float getY() {
        return this.position.getY();
    }

    public void setY(float y) {
        this.position.setY(y);
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        while (angle >= 360)
            angle = angle - 360;
        this.angle = angle;
    }

    public float getFov() {
        return fov;
    }

    public void setFov(float fov) {
        this.fov = fov;
    }

    public void setPos(float x, float y){
        this.position.setX(x);
        this.position.setY(y);
    }

    public Point2F getPos(){
        return this.position;
    }
}
