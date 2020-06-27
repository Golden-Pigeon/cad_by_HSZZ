package main.java.cad.util;

public final class CadMath {
    private CadMath() {}

    /**
     * 确定 x 是否被 a 与 b 夹在中间
     * @param x 需要比较的数
     * @param a 边界1
     * @param b 边界2
     * @param width a, b的宽度
     * @return x 是否在 a 与 b 之间
     */
    public static boolean isSqueezed(double x, double a, double b, double width){
        if(a == b)
            return false;
        if(a > b){
            double temp = a;
            a = b;
            b = temp;
        }
        return a + width < x && x < b - width;
    }

    public static boolean inEllipse(double cx, double cy, double a, double b, double x, double y, double width){
        if(Math.abs(x - cx) <= 3){
            return isSqueezed(y, cy + b, cy - b, width);
        }
        double k = (y - cy) / (x - cx);
        double d = a * b * Math.sqrt((1 + k * k) / (b * b + a * a * k * k));
        double r = Math.sqrt((x - cx) * (x - cx) + (y - cy) * (y - cy));
        return r < d - width;
    }
}
