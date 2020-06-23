package main.java.cad.MainCadStageParts;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import main.java.cad.CadCurrentStat;
import main.java.cad.CommonDefinitions.CommonPath;
import main.java.cad.CommonDefinitions.CommonSize;
import main.java.cad.ShapeType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CadToolBar {

    private TilePane tpToolBtn;
    private List<Button> toolButton = new ArrayList<>();

    private VBox toolBarContents;
    private CadColorPanel colorPanel;
    private CadPropertyPanel detailPanel;

    public CadToolBar() {
        initCadToolBarContent();
        toolBarContents = new VBox();
        toolBarContents.setAlignment(Pos.CENTER);
        toolBarContents.setSpacing(10);

        toolBarContents.getChildren().add(tpToolBtn);

        colorPanel = new CadColorPanel();
        toolBarContents.getChildren().add(colorPanel.getColorPanel());

        detailPanel = new CadPropertyPanel();
        toolBarContents.getChildren().add(detailPanel.getDetailPanel());
    }

    public VBox getCadToolBar() {
        return toolBarContents;
    }

    private ImageView getIconImage(Image curImg) {
        ImageView curImgV = new ImageView(curImg);
        curImgV.setFitHeight(CommonSize.TOOL_BUTTON_HEIGHT);
        curImgV.setFitWidth(CommonSize.TOOL_BUTTON_WIDTH);
        return curImgV;
    }

    public void initCadToolBarContent() {
        // 按钮区域
        tpToolBtn = new TilePane();
        tpToolBtn.setAlignment(Pos.CENTER);
        tpToolBtn.setPadding(new Insets(10, 10, 10, 10));
        tpToolBtn.setPrefColumns(2);
        tpToolBtn.setHgap(5);
        tpToolBtn.setVgap(5);

        Image ImgPen = new Image(CommonPath.PEN, false);
        CadButton penButton = new CadButton("CadCurve");
        penButton.setGraphic(getIconImage(ImgPen));
        toolButton.add(penButton);

        Image ImgRubber = new Image(CommonPath.RUBBER);
        CadButton rubberButton = new CadButton("CadRubber");
        rubberButton.setGraphic(getIconImage(ImgRubber));
        toolButton.add(rubberButton);

        Image ImgBarrel = new Image(CommonPath.BARREL);
        CadButton barrelButton = new CadButton("CadFiller");
        barrelButton.setGraphic(getIconImage(ImgBarrel));
        toolButton.add(barrelButton);

        Image ImgText = new Image(CommonPath.TEXT);
        CadButton textButton = new CadButton("CadText");
        textButton.setGraphic(getIconImage(ImgText));
        toolButton.add(textButton);

        Image ImgLine = new Image(CommonPath.LINE);
        CadButton lineButton = new CadButton("CadLine");
        lineButton.setGraphic(getIconImage(ImgLine));
        toolButton.add(lineButton);

        Image ImgRectangleZ = new Image(CommonPath.RECTANGLE);
        CadButton zButton = new CadButton("CadRectangle");
        zButton.setGraphic(getIconImage(ImgRectangleZ));
        toolButton.add(zButton);

        Image ImgRectangleY = new Image(CommonPath.RECTANGLE_ROUNDCORNER);
        CadButton yButton = new CadButton("CadRectangle_RoundCorner");
        yButton.setGraphic(getIconImage(ImgRectangleY));
        toolButton.add(yButton);

        Image ImgOval = new Image(CommonPath.OVAL);
        CadButton ovalButton = new CadButton("CadOval");
        ovalButton.setGraphic(getIconImage(ImgOval));
        toolButton.add(ovalButton);

        DropShadow shadow = new DropShadow();
        for (Button curBt : toolButton) {
            curBt.setStyle("-fx-base:#aaaaaa;");
            curBt.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                curBt.setEffect(shadow);
            });

            curBt.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                curBt.setEffect(null);
            });

            curBt.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
                String name = ((CadButton) e.getSource()).getName();
                CadCurrentStat.type = ToolType.valueOf(name);
                System.out.println(CadCurrentStat.type);
                if (Objects.equals(name, "CadText")) {
                    detailPanel.setFont();
                } else if (Objects.equals(name, "CadLine") || Objects.equals(name, "CadCurve")
                        || Objects.equals(name, "CadRubber")) {
                    detailPanel.setRubber();

                } else if (Objects.equals(name, "CadFiller")) {
                    detailPanel.clear();
                } else {
                    detailPanel.setLine();
                }
                if (Objects.equals(name, "CadText")) {
                    TextInputDialog dialog = new TextInputDialog("");
                    dialog.setTitle("Please Input the Text You Want to Show");
                    dialog.setContentText("Input Text Content");
                    dialog.setHeaderText("After Changing the Font, Just Click on the Canvas");
                    Optional<String> result = dialog.showAndWait();
                    if (result.isPresent()) {
                        CadCurrentStat.textContent = result.get();
                    }
                }
            });
        }
        tpToolBtn.getChildren().addAll(toolButton);
    }
}
