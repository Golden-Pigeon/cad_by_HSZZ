package main.java.cad;

/**
 * 定义图形类型
 */
public enum ShapeType {
    CadLine, // 直线
    CadCircle, // 圆形
    CadRectangle, // 直角矩形
    CadRectangle_RoundCorner, // 圆角矩形
    CadOval, // 椭圆
    CadCurve, // 曲线
    CadText; // 文本框

    public static ShapeType getShapeTypeByName(String name) {
        name = name.toLowerCase();
        if(name.equals("cadline"))
            return ShapeType.CadLine;
        if(name.equals("cadcircle"))
            return ShapeType.CadCircle;
        if(name.equals("cadrectangle"))
            return ShapeType.CadRectangle;
        if(name.equals("cadrectangle_roundcorner"))
            return ShapeType.CadRectangle_RoundCorner;
        if(name.equals("cadoval"))
            return ShapeType.CadOval;
        if(name.equals("cadcurve"))
            return ShapeType.CadCurve;
        if(name.equals("cadtext"))
            return ShapeType.CadText;
        return null;
    }
}

