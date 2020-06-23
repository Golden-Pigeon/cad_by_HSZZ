package main.java.cad.MainCadStageParts;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import main.java.cad.CommonDefinitions.CommonSize;

import java.util.ArrayList;
import java.util.List;

public class CadColorPanel {

    private VBox content;
    private TilePane tilePane;
    private Color presentColor = Color.BLACK;
    private ColorPicker colorPicker = new ColorPicker();
    private List<CadButton> colorButton = new ArrayList<>();
    private String[] color = {"#ffffff", "#000000", "#848683", "#c4c3be", "#cdbedb", "#b97b56",
            "#ffadd6", "#f01e1f", "#89010d", "#fef007", "#ffc80c", "#ff7c26",
            "#efe2ab", "#b6e51d", "#24b04f", "#93dceb", "#6997bb", "#07a0e6",
            "#d9a2dc", "#9c4ca1", "#3b46e0"};

    public CadColorPanel() {
        initCadColorPanelContent();
    }

    private void initCadColorPanelContent() {

        content = new VBox();
        content.setAlignment(Pos.CENTER);

        colorPicker = new ColorPicker();
        colorPicker.setPrefWidth(CommonSize.COLOR_PICKER_WIDTH);
        colorPicker.setStyle("-fx-background-color:orange;-fx-color-label-visible:false;");
        colorPicker.setValue(presentColor);
        colorPicker.setOnAction((ActionEvent t) -> {
            presentColor = colorPicker.getValue();
            CadCurrentStat.color = presentColor;
        });

        tilePane = new TilePane();
        tilePane.setAlignment(Pos.CENTER);
        tilePane.setPadding(new Insets(10, 10, 10, 10));
        tilePane.setPrefColumns(2);
        tilePane.setHgap(5);
        tilePane.setVgap(5);

        DropShadow shadow = new DropShadow();
        for (int i = 0; i < color.length; i++) {
            CadButton cButton = new CadButton(color[i]);
            cButton.setStyle("-fx-base: " + color[i] + ";");
            cButton.setPrefSize(CommonSize.COLOR_BUTTON_WIDTH, CommonSize.COLOR_BUTTON_HEIGHT);
            cButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                cButton.setEffect(shadow);
            });

            cButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                cButton.setEffect(null);
            });

            cButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
                presentColor = Color.web(((CadButton) e.getSource()).getName());
                colorPicker.setValue(presentColor);
                CadCurrentStat.color = presentColor;
            });
            colorButton.add(cButton);
        }
        tilePane.getChildren().addAll(colorButton);
        content.getChildren().add(colorPicker);
        content.getChildren().add(tilePane);
    }

    public VBox getColorPanel() {
        return content;
    }
}
