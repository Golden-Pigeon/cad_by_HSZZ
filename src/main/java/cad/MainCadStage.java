package main.java.cad;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.java.cad.CommonDefinitions.CommonPath;
import main.java.cad.MainCadStageParts.CadCanvas;
import main.java.cad.MainCadStageParts.CadMenuBar;
import main.java.cad.MainCadStageParts.CadStatusBar;
import main.java.cad.MainCadStageParts.CadToolBar;

public class MainCadStage extends Stage {
    private Group mainStageRoot;
    private BorderPane mainStageContent;

    public CadCanvas getCanvas() {
        return canvas;
    }

    private CadCanvas canvas;

    public MainCadStage() {
        mainStageRoot = new Group();
        mainStageContent = new BorderPane();

        setScene(new Scene(mainStageRoot, Color.web("#808080")));
        setTitle("JavaFX CAD Utility");
        setResizable(true);
        //TODO 修改logo URI
        getIcons().add(new Image(CommonPath.LOGO));
        initMainCadStageContent();
        mainStageRoot.getChildren().add(mainStageContent);
        show();
    }

    private void initMainCadStageContent() {
        CadMenuBar menuBar = new CadMenuBar(this);
        menuBar.getMenuBar().prefWidthProperty().bind(this.widthProperty());
        mainStageContent.setTop(menuBar.getMenuBar());

        CadToolBar toolBar = new CadToolBar();
        mainStageContent.setLeft(toolBar.getCadToolBar());

        canvas = new CadCanvas();
        mainStageContent.setRight(canvas.getCurrCanvas());

        CadStatusBar statusBar = new CadStatusBar();
        mainStageContent.setBottom(statusBar.getCadStatusBar());
    }
}
