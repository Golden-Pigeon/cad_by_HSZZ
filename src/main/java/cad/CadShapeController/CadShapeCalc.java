package main.java.cad.CadShapeController;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import main.java.cad.CadPoint;

import java.util.List;

public class CadShapeCalc {
    private GraphicsContext graphicsContext;
    private boolean isFill;

    public void setShapeFill(Canvas canvas, Color color, boolean isFill) {
        graphicsContext =canvas.getGraphicsContext2D();
        this.isFill = isFill;
        if(this.isFill)
            graphicsContext.setFill(color);
        else
            graphicsContext.setStroke(color);
    }

    public void drawCadLine(CadPoint start, CadPoint end) {

    }

    public void drawCadEclipse(CadPoint start, CadPoint end) {

    }

    public void drawCadCircle(CadPoint start, CadPoint end) {

    }

    public void drawCadRectangle(CadPoint start, CadPoint end) {

    }

    public void drawCadRectangle_RoundCorner(CadPoint start, CadPoint end) {

    }

    public void drawCadCurve(List<CadPoint> points) {

    }
}
