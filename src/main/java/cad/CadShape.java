package main.java.cad;

import javafx.scene.paint.Color;

import java.util.*;

/**
 * 图形类
 */
public class CadShape {

    ShapeType type;
    CadPoint startPoint = new CadPoint(0, 0);
    CadPoint endPoint = new CadPoint(0,0);
    Color color = Color.BLACK;
    List<CadPoint> curvePoints;
    String textContent;

    private CadShape() {}

    public static CadShape getCadShape(ShapeType type, CadPoint startPoint, CadPoint endPoint, Color color) {
        CadShape newShape = new CadShape();
        newShape.type = type;
        newShape.startPoint = startPoint;
        newShape.endPoint = endPoint;
        newShape.color = color;
        return newShape;
    }

    public CadShape getCadShape(ShapeType type, String textContent) {
        CadShape newShape = new CadShape();
        if(type != ShapeType.CadText)
            return null;
        newShape.type = ShapeType.CadText;
        newShape.textContent = textContent;
        return newShape;
    }

    public CadShape getCadShape(ShapeType type, List<CadPoint> curvePoints) {
        CadShape newShape = new CadShape();
        if(type != ShapeType.CadCurve)
            return null;
        newShape.type = ShapeType.CadCurve;
        newShape.curvePoints = curvePoints;
        return newShape;
    }
}