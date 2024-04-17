package fr.cubibox.backroom2_5d.engine.maths;

public class Interval2F {
    private float min;
    private float max;

    public Interval2F(float min, float max) {
        this.max = max;
        this.min = min;
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }
}
