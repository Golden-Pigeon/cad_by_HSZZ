package main.java.cad;

import java.io.Serializable;

public class CadPoint implements Serializable {
    private final double x;
    private final double y;

    public CadPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
