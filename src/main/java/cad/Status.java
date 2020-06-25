package main.java.cad;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.awt.*;

public class Status {
    public static ShapeType paintMode = null;
    public static Color color = Color.BLACK;
    public static double lineWidth = 1.0;
    public static CadShape selected = null;
    public static boolean selectAll = false;
    public static String fontType = "STSong";
    public static int fontSize = 8;
    public static String text = "";
    public static boolean saved = false;
}
