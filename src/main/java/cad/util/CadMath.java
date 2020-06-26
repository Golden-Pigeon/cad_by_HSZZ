package main.java.cad.util;

public final class CadMath {
    private CadMath() {}

    /**
     * 确定 x 是否被 a 与 b 夹在中间
     * @param x 需要比较的数
     * @param a 边界1
     * @param b 边界2
     * @return x 是否在 a 与 b 之间
     */
    public static boolean isSqueezed(double x, double a, double b){
        if(a == b)
            return false;
        if(a > b){
            double temp = a;
            a = b;
            b = temp;
        }
        return a < x && x < b;
    }
}
