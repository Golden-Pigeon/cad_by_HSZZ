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

    PaintMode type;
    CadPoint startPoint = new CadPoint(0, 0);//起始点(左上角)
    CadPoint endPoint = new CadPoint(0, 0);//终点(右下角)
    public Color lineColor = Color.BLACK;// 边线颜色
    public Color fillColor = Color.TRANSPARENT;// 填充颜色, 默认透明
    List<CadPoint> curvePoints; //自定义曲线的采样点
    String textContent; //文本"图形的内容"

    public double getLineWidth() {
        return lineWidth;
    }

    double lineWidth = 8; // 默认线宽

    private static int idCnt = 1;//总id计数器
    private int id = idCnt++; // 当前图形的ID

    public void setId(int id) {
        this.id = id;
    }

    public static int getIdCnt() {
        return idCnt;
    }

    public static void setIdCnt(int idCnt) {
        CadShape.idCnt = idCnt;
    }

    public int getId(){
        return id;
    }

    private CadShape() {
    }



    public static CadShape getCadShape(PaintMode type, CadPoint startPoint, CadPoint endPoint,
                                       Color lineColor, Color fillColor, double lineWidth) {
        CadShape newShape = new CadShape();
        newShape.type = type;
        newShape.startPoint = startPoint;
        newShape.endPoint = endPoint;
        newShape.lineColor = lineColor;
        newShape.fillColor = fillColor;
        newShape.lineWidth = lineWidth;
        return newShape;
    }

    public static CadShape getCadShape(PaintMode type, String textContent, Color lineColor, double lineWidth) {
        CadShape newShape = new CadShape();
        if (type != PaintMode.CadText)
            return null;
        newShape.type = PaintMode.CadText;
        newShape.textContent = textContent;
        newShape.lineColor = lineColor;
        newShape.lineWidth = lineWidth;
        return newShape;
    }

    public static CadShape getCadShape(PaintMode type, List<CadPoint> curvePoints, Color lineColor, double lineWidth) {
        CadShape newShape = new CadShape();
        if (type != PaintMode.CadCurve)
            return null;
        newShape.type = PaintMode.CadCurve;
        newShape.curvePoints = curvePoints;
        newShape.lineColor = lineColor;
        newShape.lineWidth = lineWidth;
        return newShape;
    }
}