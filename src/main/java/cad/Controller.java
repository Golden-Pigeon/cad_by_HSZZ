package main.java.cad;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.java.cad.CommonDefinitions.CommonPath;
import main.java.cad.MainCadStageParts.CadStatusBar;
import main.java.cad.shape.CadEllipse;
import main.java.cad.shape.CadLine;
import main.java.cad.shape.CadRect;
import main.java.cad.util.CadMath;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;
import java.util.List;


public class Controller implements Initializable {

//    public Button lineButton;
//    public Button penButton;
//    public Button eclipseButton;
//    public Button eraseButton;
//    public Button rectButton;
//    public Button roundRectButton;
//    public Button textButton;

    private File parentDir;
    private String child;
    private double mouseX, mouseY;

    @FXML
    private BorderPane borderPane;

    private Record record;


    private HBox statusBar;

    @FXML
    private CheckMenuItem painterToolBarMenuItem;

    @FXML
    private CheckMenuItem colorToolBarMenuItem;


    @FXML
    private VBox colorToolBar;

    @FXML
    private VBox painterToolBar;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private ComboBox<String> sizeComboBox;

    @FXML
    private Slider sizeSlider;

    @FXML
    public void onMainPaneMousePressed(MouseEvent event) {

    }

    @FXML
    public void onPainterToolMenuItemAction(ActionEvent event) {
        if (painterToolBar.isVisible()) {
            painterToolBar.setVisible(false);
            painterToolBarMenuItem.setSelected(false);
        } else {
            painterToolBar.setVisible(true);
            painterToolBarMenuItem.setSelected(true);
        }
    }

    @FXML
    public void onColorToolMenuItemAction(ActionEvent actionEvent) {
        if (colorToolBar.isVisible()) {
            colorToolBar.setVisible(false);
            colorToolBarMenuItem.setSelected(false);
        } else {
            colorToolBar.setVisible(true);
            colorToolBarMenuItem.setSelected(true);
        }
    }

    @FXML
    public void onColorButtonClicked(ActionEvent actionEvent) {
        Button currButton = (Button) actionEvent.getSource();
        String name = currButton.getId();
        if (name.equals("preset_black"))
            Status.strokeColor = Color.web("#000000");
        if (name.equals("preset_white"))
            Status.strokeColor = Color.web("#ffffff");
        if (name.equals("preset_gary"))
            Status.strokeColor = Color.web("#c0c0c0");
        if (name.equals("preset_darkgray"))
            Status.strokeColor = Color.web("#696969");
        if (name.equals("preset_blue"))
            Status.strokeColor = Color.web("#00bfff");
        if (name.equals("preset_orange"))
            Status.strokeColor = Color.web("#ffa500");
        if (name.equals("preset_red"))
            Status.strokeColor = Color.web("#ff0000");
        if (name.equals("preset_gold"))
            Status.strokeColor = Color.web("#ffd700");
        if (name.equals("preset_green"))
            Status.strokeColor = Color.web("#00ff00");
        if (name.equals("preset_yellow"))
            Status.strokeColor = Color.web("#ffff00");
        System.out.println(Status.strokeColor);
    }

    @FXML
    public void onColorPickerFinished(ActionEvent actionEvent) {
        Status.strokeColor = colorPicker.getValue();
        System.out.println(Status.strokeColor.toString());
    }

    @FXML
    public void onSizeComboBoxClicked(MouseEvent mouseEvent) {
        List<String> sizeOptions = new ArrayList<>();
        for (int i = 2; i <= 20; i += 2) {
            sizeOptions.add(String.valueOf(i));
        }

        ObservableList<String> options =
                FXCollections.observableArrayList(
                        sizeOptions
                );
        sizeComboBox.setItems(options);
        sizeComboBox.setEditable(false);
    }

    @FXML
    public void onSizeComboBoxFinished(ActionEvent actionEvent) {
        String currComboBoxValue = sizeComboBox.getValue();
        if (currComboBoxValue != null) {
            Status.fontSize = (int) Double.parseDouble(currComboBoxValue);
            Status.lineWidth = (int) Double.parseDouble(currComboBoxValue);
            sizeSlider.setValue((int) Double.parseDouble(currComboBoxValue));
        }
    }

    @FXML
    public void onSizeSliderFinished(MouseEvent mouseEvent) {
        sizeComboBox.setValue(String.valueOf(sizeSlider.getValue()));
        Status.fontSize = (int) sizeSlider.getValue();
        Status.lineWidth = (int) sizeSlider.getValue();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        record = new Record();
        parentDir = new File(CommonPath.DEFAULT_SAVE_DIR);
        Date date = new Date();
        //TODO Optimize save file name format like JavaFX_CAD_YY_MM_DD_HR_MIN_SEC.hszz
        //在文件名中, 冒号":"是不被允许的, 需要替换掉
        //否则会报错
        child = date.toString().replace(' ', '_').replace(':', '_') + ".hszz";
        statusBar = new CadStatusBar().getCadStatusBar();
        borderPane.setBottom(statusBar);
    }

    @FXML
    public void onMouseMovedInMainPane(MouseEvent mouseEvent) {
        CadStatusBar.setStatusText("Tool: " + Status.paintMode.toString() + " @ "
                + String.format("%.1f, %.1fpx ", mouseEvent.getX(), mouseEvent.getY()));
        mouseX = mouseEvent.getX();
        mouseY = mouseEvent.getY();
    }

    public void onNewMenuItemAction(ActionEvent actionEvent) {
        //TODO: call a msgBox to set dir
    }

    public void onOpenMenuItemAction(ActionEvent actionEvent) {
        //TODO: call a msgBox to select file
    }

    public void onCloseMenuItemAction(ActionEvent actionEvent) {
        //TODO: save or not
        mainPane.setVisible(false);
    }

    public void onSaveMenuItemAction(ActionEvent actionEvent) {
        WritableImage image = mainPane.snapshot(new SnapshotParameters(), null);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png",
                    new File(parentDir, child.replace(".hszz", ".png")));
        } catch (IOException e) {
            FileImportExport.showIOExceptionAlert();
        }
        if (!FileImportExport.exportToFile(record, new File(parentDir, child))) {
            System.err.println("save failed");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("警告");
            alert.setHeaderText("保存失败");
            alert.setContentText("请检查保存路径是否合法");
            alert.showAndWait();
        }
    }

    @FXML
    public void onSaveAsMenuItemAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("保存到...");
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("导出的工作环境存档 - Workspace Save File", "*.hszz"),
                new FileChooser.ExtensionFilter("PNG格式的画面快照 - PNG Snapshot of the Canvas", "*.png")
        );
        Stage mainStage = (Stage) borderPane.getScene().getWindow();
        File saving = fileChooser.showSaveDialog(mainStage);
        if (saving == null) {
            return;
        }
        String expectedExtensionName = saving.getAbsolutePath().split("\\.")[1];//正则表达式里面 . -> \\.
        if (expectedExtensionName.toLowerCase().equals("png")) {
            WritableImage image = mainPane.snapshot(new SnapshotParameters(), null);
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", saving);
            } catch (IOException e) {
                FileImportExport.showIOExceptionAlert();
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("保存成功");
            alert.setHeaderText("成功保存至 " + saving.getName());
            alert.showAndWait();
            return;
        } else if (!FileImportExport.exportToFile(record, saving)) {
            System.err.println("save failed");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("警告");
            alert.setHeaderText("保存失败");
            alert.setContentText("请检查保存路径是否合法");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("保存成功");
        alert.setHeaderText("成功保存至" + saving.getName());
        alert.showAndWait();
        return;
    }

    public void onRefreshMenuItemAction(ActionEvent actionEvent) {
        //TODO: paint graphs from record again
    }

    @FXML
    public void onRestartMenuItemAction(ActionEvent actionEvent) {
        if (!Status.saved) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("重启程序");
            alert.setHeaderText("确定要重启吗");
            alert.setContentText("画布尚未保存，重启后将失去所有未保存内容");
            Optional<ButtonType> buttonType = alert.showAndWait();
            if (!buttonType.get().getButtonData().equals(ButtonBar.ButtonData.OK_DONE)) {
                actionEvent.consume();
                return;
            }

        }
        Stage mainStage = (Stage) borderPane.getScene().getWindow();
        mainStage.close();
        Platform.runLater(() -> {
            try {
                new Main().start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    public void onExitMenuItemAction(ActionEvent actionEvent) {
        Platform.exit();
    }

    @FXML
    public void onRedoMenuItemAction(ActionEvent actionEvent) {
/*
        List<CadShape> actionList = record.getActionList();
        List<CadShape> deleteList = record.getDeleteList();
        String id;
        CadShape shape;
        if(deleteList.isEmpty()) {

            if (actionList instanceof LinkedList){
                shape = ((LinkedList<CadShape>) actionList).getLast();
            }
            else {
                shape = actionList.get(actionList.size() - 1);
            }
            id = String.valueOf(shape.id);

            Node n = mainPane.lookup(id);
            if(n != null){
                n.setVisible(false);
                actionList.remove(shape);
                deleteList.add(shape);
            }
            else{
                System.err.println("redo failed");

            }
            actionEvent.consume();
        }
        else {
            if (deleteList instanceof LinkedList){
                shape = ((LinkedList<CadShape>) deleteList).getLast();
            }
            else {
                shape = deleteList.get(deleteList.size() - 1);
            }
            id = String.valueOf(shape.id);
            Node n = mainPane.lookup(id);
            if(n != null){
                n.setVisible(false);
                actionList.remove(shape);
                deleteList.add(shape);
            }
        }
*/
    }

    public void onUndoMenuItemAction(ActionEvent actionEvent) {
    }

    public void onCutMenuItemAction(ActionEvent actionEvent) {

    }

    public void onCopyMenuItemAction(ActionEvent actionEvent) {
    }

    public void onPasteMenuItemAction(ActionEvent actionEvent) {
    }

    public void onDeleteMenuItemAction(ActionEvent actionEvent) {

    }

    public void onSelectAllMenuItemAction(ActionEvent actionEvent) {
        Status.selectAll = true;
        mainPane.getChildren().forEach(node -> {
            if (node instanceof Shape) ((Shape) node).setStroke(Color.RED);
        });
    }

    public void onFindMenuItemAction(ActionEvent actionEvent) {
    }

    public void onListRecordMenuItemAction(ActionEvent actionEvent) {

    }

    public void onNewWindowMenuItemAction(ActionEvent actionEvent) {
        Platform.runLater(() -> {
            try {
                new Main().start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void onHelpMenuItemAction(ActionEvent actionEvent) {
    }

    @FXML
    public void onAboutMenuItemAction(ActionEvent actionEvent) {
        Alert aboutAlert = new Alert(Alert.AlertType.INFORMATION);
        Hyperlink githubLink = new Hyperlink(CommonPath.gitHubLink);
        githubLink.setText(CommonPath.gitHubLink);
        aboutAlert.setTitle("关于 JavaFX CAD Utility");
        aboutAlert.setHeaderText("JavaFX CAD Utility\n基于 Intellij IDEA, GitHub 与 Teamwork(团队协作)");
        aboutAlert.initStyle(StageStyle.UTILITY);

        FlowPane flowPane = new FlowPane();
        flowPane.getChildren().addAll(new Label("版本："
                + CommonPath.version + "\n"
                + "开发者: "
                + "郑镜竹 宋志元 翟凡荣 侯文轩\n"
                + "许可证: Apache License 2.0\n"
                + "GitHub链接(觉得不错的话, 就给个Star吧)\n"), githubLink);

        githubLink.setOnAction((event -> {
            try {
                Desktop.getDesktop().browse(new URL(CommonPath.gitHubLink).toURI());
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }));

        aboutAlert.getDialogPane().contentProperty().set(flowPane);
        aboutAlert.showAndWait();
    }


    public void onToolsButtonAction(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        switch (button.getId()) {
            case "lineButton":
                Status.paintMode = PaintMode.CadLine;
                break;
            case "penButton":
                Status.paintMode = PaintMode.CadCurve;
                break;
            case "ellipseButton":
                Status.paintMode = PaintMode.CadOval;
                break;
            case "eraserButton":
                Status.paintMode = PaintMode.CadEraser;
                break;
            case "rectButton":
                Status.paintMode = PaintMode.CadRectangle;
                break;
            case "roundRectButton":
                Status.paintMode = PaintMode.CadRectangle_RoundCorner;
                break;
            case "textButton":
                Status.paintMode = PaintMode.CadText;
                break;
        }
        CadStatusBar.setStatusText("Tool: " + Status.paintMode + "@"
                + String.format("%.1f, %.1fpx ", mouseX, mouseY));
    }

    public void onMainPaneMouseClicked(MouseEvent mouseEvent) {
        if (Status.selected != null) {
            Shape shape = ((Shape) mainPane.lookup("#" + Status.selected.getId()));
            shape.setStroke(Status.strokeColor);
            //System.out.println(Status.strokeColor);
            Status.selected = null;
        }
        if (Status.selectAll) {
            Status.selectAll = false;
            mainPane.getChildren().forEach(t -> {
                if (t instanceof Shape)
                    ((Shape) t).setStroke(Status.strokeColor);//TODO: recover the origin color
            });
        }
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();
        switch (Status.paintMode) {
            case CadText:
                //TODO: Enter Texts
            case CadLine:
                if (Status.startPoint == null) {
                    Status.startPoint = new CadPoint(x, y);
                } else {
                    double sx = Status.startPoint.getX();
                    double sy = Status.startPoint.getY();
                    CadShape shape = CadShape.getCadShape(PaintMode.CadLine, Status.startPoint, new CadPoint(x, y), Status.strokeColor, Status.fillColor, Status.lineWidth);
                    record.getActionList().add(shape);
                    Line line = new CadLine(sx, sy, x, y, shape, mainPane);
                    Status.startPoint = null;
                    mainPane.getChildren().add(line);
                }
                break;

            case CadRectangle:
                if (Status.startPoint == null) {
                    Status.startPoint = new CadPoint(x, y);
                } else {
                    double sx = Status.startPoint.getX();
                    double sy = Status.startPoint.getY();
                    CadShape shape = CadShape.getCadShape(PaintMode.CadRectangle, Status.startPoint, new CadPoint(x, y), Status.strokeColor, Status.fillColor, Status.lineWidth);
                    record.getActionList().add(shape);
                    Rectangle rect = new CadRect(sx, sy, x, y, shape, mainPane);
                    Status.startPoint = null;
                    mainPane.getChildren().add(rect);
                }
                break;

            case CadOval:
                if (Status.startPoint == null) {
                    Status.startPoint = new CadPoint(x, y);
                } else {
                    double sx = Status.startPoint.getX();
                    double sy = Status.startPoint.getY();
                    CadShape shape = CadShape.getCadShape(PaintMode.CadOval, Status.startPoint, new CadPoint(x, y), Status.strokeColor, Status.fillColor, Status.lineWidth);
                    record.getActionList().add(shape);
                    Ellipse ell = new CadEllipse(sx, sy, x, y, shape, mainPane);
                    Status.startPoint = null;
                    mainPane.getChildren().add(ell);
                }
                break;
        }
    }
}
