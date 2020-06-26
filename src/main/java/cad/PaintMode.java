package main.java.cad;

import java.io.Serializable;

/**
 * 定义图形类型
 */
public enum PaintMode implements Serializable {
    CadLine, // 直线
    CadCircle, // 圆形
    CadRectangle, // 直角矩形
    CadRectangle_RoundCorner, // 圆角矩形
    CadOval, // 椭圆
    CadCurve, // 曲线
    CadText, // 文本框
    CadEraser, //橡皮擦
    CadCursor, //鼠标
    CadFiller; //填充

    public static PaintMode getPaintModeByName(String name) {
        name = name.toLowerCase();
        if(name.equals("cadline"))
            return PaintMode.CadLine;
        if(name.equals("cadcircle"))
            return PaintMode.CadCircle;
        if(name.equals("cadrectangle"))
            return PaintMode.CadRectangle;
        if(name.equals("cadrectangle_roundcorner"))
            return PaintMode.CadRectangle_RoundCorner;
        if(name.equals("cadoval"))
            return PaintMode.CadOval;
        if(name.equals("cadcurve"))
            return PaintMode.CadCurve;
        if(name.equals("cadtext"))
            return PaintMode.CadText;
        if(name.equals("caderaser"))
            return PaintMode.CadEraser;
        if(name.equals("cadcursor"))
            return PaintMode.CadCursor;
        if(name.equals("cadfiller"))
            return PaintMode.CadFiller;
        return null;
    }
}

