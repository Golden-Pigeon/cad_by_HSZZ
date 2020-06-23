package main.java.cad;

import javafx.scene.paint.Color;
import main.java.cad.MainCadStageParts.ToolType;
import main.java.cad.ShapeType;

public class CadCurrentStat {
    public static ToolType type = ToolType.CadLine;
    public static Color color = Color.BLACK;
    public static int lineWidth = 8;
    public static int rubberWidth = 8;
    public static boolean isFill = false;
    public static String textContent = "";
    public static int textFontSize = 15;
    public static String textFontFamily = "AIGDT";
}
