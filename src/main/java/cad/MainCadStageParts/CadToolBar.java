package main.java.cad.MainCadStageParts;

import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class CadToolBar {

    private TilePane tpToolBtn;
    private List<Button> toolButton = new ArrayList<>();

    private VBox content;
    private CadColorPanel colorPanel;
    private CadPropertyPanel detailPanel;

    public VBox getCadToolBar() {
        return content;
    }
}
