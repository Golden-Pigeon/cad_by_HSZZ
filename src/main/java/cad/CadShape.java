package main.java.cad;

import javafx.scene.paint.Color;

import java.util.*;

/**
 * 图形类, 对所有图形对象定义了统一的类型与接口
 *
 * @author 侯文轩
 * @version 1.1
 */
public class CadShape {

    ShapeType type;
    CadPoint startPoint = new CadPoint(0, 0);//起始点(左上角)
    CadPoint endPoint = new CadPoint(0, 0);//终点(右下角)
    Color lineColor = Color.BLACK;// 边线颜色, 文本颜色
    Color fillColor = Color.TRANSPARENT;// 填充颜色, 默认透明
    List<CadPoint> curvePoints; //自定义曲线的采样点
    String textContent; //文本"图形的内容"
    String lineWidth = "8"; // 线宽, 文本字号大小

    static int idCnt = 0;//总id计数器
    public final int id = idCnt++; // 当前图形的ID

    private CadShape() {
    }

    public static CadShape getCadShape(ShapeType type, CadPoint startPoint, CadPoint endPoint, Color lineColor, Color fillColor) {
        CadShape newShape = new CadShape();
        newShape.type = type;
        newShape.startPoint = startPoint;
        newShape.endPoint = endPoint;
        newShape.lineColor = lineColor;
        newShape.fillColor = fillColor;
        return newShape;
    }

    public static CadShape getCadShape(ShapeType type, String textContent, Color lineColor) {
        CadShape newShape = new CadShape();
        if (type != ShapeType.CadText)
            return null;
        newShape.type = ShapeType.CadText;
        newShape.textContent = textContent;
        newShape.lineColor = lineColor;
        return newShape;
    }

    public static CadShape getCadShape(ShapeType type, List<CadPoint> curvePoints, Color lineColor) {
        CadShape newShape = new CadShape();
        if (type != ShapeType.CadCurve)
            return null;
        newShape.type = ShapeType.CadCurve;
        newShape.curvePoints = curvePoints;
        newShape.lineColor = lineColor;
        return newShape;
    }
}