package main.java.cad;

import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.*;

/**
 * 图形类, 对所有图形对象定义了统一的类型与接口
 *
 * @author 侯文轩
 * @version 1.1
 */
public class CadShape implements Serializable {

    PaintMode type;
    CadPoint startPoint = new CadPoint(0, 0);//起始点(左上角)
    CadPoint endPoint = new CadPoint(0, 0);//终点(右下角)
    transient Color lineColor = Color.BLACK;// 边线颜色
    transient Color fillColor = Color.TRANSPARENT;// 填充颜色, 默认透明
    String lineColorString = Color.BLACK.toString();
    String fillColorString = Color.TRANSPARENT.toString();
    transient List<CadPoint> curvePoints; //自定义曲线的采样点
    CadPoint[] curvePointsArray; //自定义曲线的采样点, 用来序列化(原生List不支持序列化)
    String textContent; //文本"图形的内容"
    String lineWidth = "8"; // 默认线宽

    private static int idCnt = 0;//总id计数器
    private final int id = idCnt++; // 当前图形的ID

    private CadShape() {
    }

    public static CadShape getCadShape(PaintMode type, CadPoint startPoint, CadPoint endPoint, Color lineColor, Color fillColor) {
        CadShape newShape = new CadShape();
        newShape.type = type;
        newShape.startPoint = startPoint;
        newShape.endPoint = endPoint;
        newShape.lineColor = lineColor;
        newShape.fillColor = fillColor;
        return newShape;
    }

    public static CadShape getCadShape(PaintMode type, String textContent, Color lineColor) {
        CadShape newShape = new CadShape();
        if (type != PaintMode.CadText)
            return null;
        newShape.type = PaintMode.CadText;
        newShape.textContent = textContent;
        newShape.lineColor = lineColor;
        return newShape;
    }

    public static CadShape getCadShape(PaintMode type, List<CadPoint> curvePoints, Color lineColor) {
        CadShape newShape = new CadShape();
        if (type != PaintMode.CadCurve)
            return null;
        newShape.type = PaintMode.CadCurve;
        newShape.curvePoints = curvePoints;
        newShape.lineColor = lineColor;
        newShape.curvePointsArray = (CadPoint[]) curvePoints.toArray();
        return newShape;
    }

    public int getId(){
        return id;
    }
}