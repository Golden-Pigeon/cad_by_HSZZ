package main.java.cad;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import main.java.cad.util.CadPoint;

import java.util.ArrayList;
import java.util.List;

public class Status {
    public static Shape lastShape;
    public static List<Shape> deleteCache = new ArrayList<>();
    public static List<Line> tempLines;
    public static List<CadPoint> points;
    public static Pane mainPane = null;
    public static boolean penDrawable = false;
    public static CadPoint startPoint = null;
    public static PaintMode paintMode = PaintMode.CadLine;
    public static Color strokeColor = Color.BLACK;
    public static Color fillColor = Color.TRANSPARENT;
    public static double lineWidth = 1.0;
    public static CadShape selected = null;
    public static boolean selectAll = false;
    public static String fontType = "STSong";
    public static int fontSize = 8;
    public static String text = "";
    public static boolean saved = false;
}
