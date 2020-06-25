package main.java.cad;

import javafx.scene.paint.Color;

public class Status {
    public static CadPoint startPoint = null;
    public static PaintMode paintMode = PaintMode.CadLine;
    public static Color strokeColor = Color.BLACK;
    public static Color fillColor = Color.WHITE;
    public static double lineWidth = 1.0;
    public static CadShape selected = null;
    public static boolean selectAll = false;
    public static String fontType = "STSong";
    public static int fontSize = 8;
    public static String text = "";
    public static boolean saved = false;
}
