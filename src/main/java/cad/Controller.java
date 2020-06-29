package main.java.cad;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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
import javafx.scene.shape.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.java.cad.CommonDefinitions.CommonPath;
import main.java.cad.MainCadStageParts.CadStatusBar;
import main.java.cad.shape.CadCircle;
import main.java.cad.shape.CadEllipse;
import main.java.cad.shape.CadLine;
import main.java.cad.shape.CadRect;
import main.java.cad.shape.*;
import main.java.cad.util.CadPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.List;


public class Controller implements Initializable {
    public ComboBox<String> typeComboBox;

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
        if (typeComboBox.getValue().equals("轮廓")) {
            switch (name) {
                case "preset_black":
                    Status.strokeColor = Color.web("#000000");
                    break;
                case "preset_white":
                    Status.strokeColor = Color.web("#ffffff");
                    break;
                case "preset_gary":
                    Status.strokeColor = Color.web("#c0c0c0");
                    break;
                case "preset_darkgray":
                    Status.strokeColor = Color.web("#696969");
                    break;
                case "preset_blue":
                    Status.strokeColor = Color.web("#00bfff");
                    break;
                case "preset_orange":
                    Status.strokeColor = Color.web("#ffa500");
                    break;
                case "preset_red":
                    Status.strokeColor = Color.web("#ff0000");
                    break;
                case "preset_gold":
                    Status.strokeColor = Color.web("#ffd700");
                    break;
                case "preset_green":
                    Status.strokeColor = Color.web("#00ff00");
                    break;
                case "preset_yellow":
                    Status.strokeColor = Color.web("#ffff00");
                    break;

            }
            System.out.println("STROKE: " + Status.strokeColor);
        } else {
            switch (name) {
                case "preset_black":
                    Status.fillColor = Color.web("#000000");
                    break;
                case "preset_white":
                    Status.fillColor = Color.web("#ffffff");
                    break;
                case "preset_gary":
                    Status.fillColor = Color.web("#c0c0c0");
                    break;
                case "preset_darkgray":
                    Status.fillColor = Color.web("#696969");
                    break;
                case "preset_blue":
                    Status.fillColor = Color.web("#00bfff");
                    break;
                case "preset_orange":
                    Status.fillColor = Color.web("#ffa500");
                    break;
                case "preset_red":
                    Status.fillColor = Color.web("#ff0000");
                    break;
                case "preset_gold":
                    Status.fillColor = Color.web("#ffd700");
                    break;
                case "preset_green":
                    Status.fillColor = Color.web("#00ff00");
                    break;
                case "preset_yellow":
                    Status.fillColor = Color.web("#ffff00");
                    break;
            }
            System.out.println("FILL: " + Status.fillColor);
        }

    }

    @FXML
    public void onColorPickerFinished(ActionEvent actionEvent) {
        if (typeComboBox.getValue().equals("stroke")) {
            Status.strokeColor = colorPicker.getValue();
            System.out.println("轮廓");
        } else {
            Status.fillColor = colorPicker.getValue();
            System.out.println("填充");
        }
        System.out.println(colorPicker.toString());
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

    public void onTypeComboBoxClicked(MouseEvent event) {
        List<String> typeOptions = new ArrayList<>();
        typeOptions.add("轮廓");
        typeOptions.add("填充");
        ObservableList<String> options = FXCollections.observableArrayList(typeOptions);
        typeComboBox.setItems(options);
        typeComboBox.setEditable(false);
    }

    @FXML
    public void onSizeSliderFinished(MouseEvent mouseEvent) {
        sizeComboBox.setValue(String.valueOf(sizeSlider.getValue()));
        Status.fontSize = (int) sizeSlider.getValue();
        Status.lineWidth = (int) sizeSlider.getValue();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainPane.setVisible(false);
        borderPane.setBackground(new Background(new BackgroundFill(Color.web("#aaaaaa"), null, null)));
        painterToolBar.setVisible(false);
        colorToolBar.setVisible(false);
        Status.mainPane = mainPane;
        record = new Record();
        parentDir = new File(CommonPath.DEFAULT_SAVE_DIR);
        Date date = new Date();
        typeComboBox.setValue("轮廓");
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
        if(Status.startPoint != null){
            double startX = Status.startPoint.getX();
            double startY = Status.startPoint.getY();
            double endX = mouseEvent.getX();
            double endY = mouseEvent.getY();
            switch (Status.paintMode){
                case CadLine:
                    if(Status.lastShape != null)
                        mainPane.getChildren().remove(Status.lastShape);
                    Status.lastShape = new Line(startX, startY, endX, endY);
                    mainPane.getChildren().add(Status.lastShape);
                    Status.lastShape.setStroke(Status.strokeColor);
                    break;
                case CadCircle:
                    if(Status.lastShape != null)
                        mainPane.getChildren().remove(Status.lastShape);
                    Status.lastShape = new Circle(startX, startY, Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2)));
                    mainPane.getChildren().add(Status.lastShape);
                    Status.lastShape.setStroke(Status.strokeColor);
                    Status.lastShape.setFill(Color.TRANSPARENT);
                    break;
                case CadRectangle:
                case CadRectangle_RoundCorner:
                    if(Status.lastShape != null)
                        mainPane.getChildren().remove(Status.lastShape);
                    Status.lastShape = new Rectangle(Math.min(startX, endX), Math.min(startY, endY),
                            Math.abs(endX - startX), Math.abs(endY - startY));
                    mainPane.getChildren().add(Status.lastShape);
                    Status.lastShape.setStroke(Status.strokeColor);
                    Status.lastShape.setFill(Color.TRANSPARENT);
                    break;
                case CadOval:
                    if(Status.lastShape != null)
                        mainPane.getChildren().remove(Status.lastShape);
                    Status.lastShape = new Ellipse((startX + endX) / 2, (startY + endY) / 2,
                            Math.abs((endX - startX) / 2), Math.abs((endY - startY) / 2));
                    mainPane.getChildren().add(Status.lastShape);
                    Status.lastShape.setStroke(Status.strokeColor);
                    Status.lastShape.setFill(Color.TRANSPARENT);
                    break;


            }
        }
    }

    public void onNewMenuItemAction(ActionEvent actionEvent) {
        borderPane.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        mainPane.setVisible(true);
        painterToolBar.setVisible(true);
        colorToolBar.setVisible(true);
        colorToolBarMenuItem.setSelected(true);
        painterToolBarMenuItem.setSelected(true);
    }

    public void onOpenMenuItemAction(ActionEvent actionEvent) {

        record = new Record();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("请选择要导入的工作环境...");
        fileChooser.setInitialDirectory(new File(CommonPath.DEFAULT_SAVE_DIR));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("导出的工作环境存档 - Workspace Save File", "*.hszz")
        );
        Stage mainStage = (Stage) borderPane.getScene().getWindow();
        mainPane.getChildren().clear();
        File saveFile = fileChooser.showOpenDialog(mainStage);
        if (saveFile == null)
            return;
        if (!FileImportExport.importFromFile(record, saveFile)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("导入工作空间失败");
            alert.setHeaderText("由于导入操作遇到错误, 未能从文件" + saveFile.getName() + "导入工作空间");
            alert.setContentText("请检查文件是否有效");
            alert.showAndWait();
            return;
        }
        borderPane.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        mainPane.setVisible(true);
        painterToolBar.setVisible(true);
        colorToolBar.setVisible(true);
        colorToolBarMenuItem.setSelected(true);
        painterToolBarMenuItem.setSelected(true);
        for (CadShape currShape : record.getActionList()) {
            if (currShape.type.equals(PaintMode.CadLine)) {
                Line newLine = new CadLine(currShape.startPoint, currShape.endPoint, currShape, mainPane, record);
                mainPane.getChildren().add(newLine);
            }
            if (currShape.type.equals(PaintMode.CadRectangle)) {
                Rectangle newRect = new CadRect(currShape.startPoint, currShape.endPoint, currShape, mainPane, record);
                mainPane.getChildren().add(newRect);
            }
            if (currShape.type.equals(PaintMode.CadOval)) {
                Ellipse newEll = new CadEllipse(currShape.startPoint, currShape.endPoint, currShape, mainPane, record);
                mainPane.getChildren().add(newEll);
            }
            if (currShape.type.equals(PaintMode.CadRectangle_RoundCorner)) {
                Rectangle newRect = new CadRect(currShape.startPoint, currShape.endPoint, currShape, mainPane, record);
                newRect.setArcHeight(currShape.getLineWidth());
                newRect.setArcWidth(currShape.getLineWidth());
                mainPane.getChildren().add(newRect);
            }
            if (currShape.type.equals(PaintMode.CadCircle)) {
                Circle newCircle = new CadCircle(currShape.startPoint, currShape.endPoint, currShape, mainPane, record);
                mainPane.getChildren().add(newCircle);
            }
            if(currShape.type.equals(PaintMode.CadText)) {
                Text newText = new CadText(currShape.startPoint, currShape, mainPane, record);
                mainPane.getChildren().add(newText);
            }
            if (currShape.type.equals(PaintMode.CadCurve)) {
                for (int i = 0; i < currShape.curvePoints.size() - 1; i++) {
                    Line lineSeg = new Line(currShape.curvePoints.get(i).getX(), currShape.curvePoints.get(i).getY(),
                            currShape.curvePoints.get(i + 1).getX(), currShape.curvePoints.get(i + 1).getY());
                    lineSeg.setStrokeWidth(currShape.getLineWidth());
                    lineSeg.setStroke(currShape.lineColor);
                    mainPane.getChildren().add(lineSeg);
                }
            }
        }
        System.out.println("Import Process Finished Successfully");
    }

    public void onCloseMenuItemAction(ActionEvent actionEvent) {
        Status.deleteCache.clear();
        if (!Status.saved) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("关闭画布");
            alert.setHeaderText("确定要关闭吗");
            alert.setContentText("画布尚未保存，关闭后将失去所有未保存内容");
            Optional<ButtonType> buttonType = alert.showAndWait();
            if (!buttonType.get().getButtonData().equals(ButtonBar.ButtonData.OK_DONE)) {
                actionEvent.consume();
                return;
            }

        }
        record = new Record();
        mainPane.setVisible(false);
        borderPane.setBackground(new Background(new BackgroundFill(Color.web("#aaaaaa"), null, null)));
        painterToolBar.setVisible(false);
        colorToolBar.setVisible(false);
    }

    public void onSaveMenuItemAction(ActionEvent actionEvent) {
        File saveDir = new File(CommonPath.DEFAULT_SAVE_DIR);
        if(!saveDir.exists()) {
            saveDir.mkdir();
        }
        child = new Date().toString().replace(' ', '_').replace(':', '_') + ".hszz";
        record.getDeleteList().clear();
        Status.deleteCache.clear();
        WritableImage image = mainPane.snapshot(new SnapshotParameters(), null);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png",
                    new File(parentDir, child.replace(".hszz", ".png")));
            Status.saved = true;
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
        record.getDeleteList().clear();
        Status.deleteCache.clear();
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
            Status.saved = true;
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
        Status.saved = true;
    }

    public void onRefreshMenuItemAction(ActionEvent actionEvent) {
        record.getDeleteList().clear();
        Status.deleteCache.clear();
    }

    @FXML
    public void onRestartMenuItemAction(ActionEvent actionEvent) {
        record.getDeleteList().clear();
        Status.deleteCache.clear();
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
        if(!Status.saved){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("退出程序");
            alert.setHeaderText("确定要关闭吗");
            alert.setContentText("画布尚未保存，退出后将失去所有未保存内容");
            Optional<ButtonType> buttonType = alert.showAndWait();
            if (!buttonType.get().getButtonData().equals(ButtonBar.ButtonData.OK_DONE)) {
                actionEvent.consume();
                return;
            }

        }
        Platform.exit();
    }

    @FXML
    public void onUndoMenuItemAction(ActionEvent actionEvent) {

        List<CadShape> actionList = record.getActionList();
        List<CadShape> deleteList = record.getDeleteList();
        String id;
        CadShape shape;
        if(!actionList.isEmpty()) {
            if (actionList instanceof LinkedList) {
                shape = ((LinkedList<CadShape>) actionList).getLast();
            } else {
                shape = actionList.get(actionList.size() - 1);
            }
            Iterator<Node> ite = mainPane.getChildren().iterator();
            id = String.valueOf(shape.getId());
//                    System.out.println("line " + id);
            while (ite.hasNext()) {
                Node n = ite.next();
                String item = n.getId();
//                        System.out.println("item " + item);
                if (n instanceof Shape && item.equals(id)) {
//                            System.out.println("removed");
                    Status.deleteCache.add((Shape) n);
                    ite.remove();
                }
            }
//        id = String.valueOf(shape.getId());
//        Node n = mainPane.lookup(id);
//        if(n != null){
//            mainPane.getChildren().remove(n);
            actionList.remove(shape);
            deleteList.add(shape);
//        }

        }
    }

    public void onRedoMenuItemAction(ActionEvent actionEvent) {
        List<CadShape> actionList = record.getActionList();
        List<CadShape> deleteList = record.getDeleteList();
        String id;
        CadShape shape;
        if(!deleteList.isEmpty()){
            if (deleteList instanceof LinkedList) {
                shape = ((LinkedList<CadShape>) deleteList).getLast();
            } else {
                shape = deleteList.get(deleteList.size() - 1);
            }
            ListIterator<Shape> ite = Status.deleteCache.listIterator();
            while (ite.hasNext()){
                Shape s = ite.next();
                if(s.getId().equals(String.valueOf(shape.getId()))){
                    mainPane.getChildren().add(s);
                    ite.remove();
                }
            }
            actionList.add(shape);
            deleteList.remove(shape);
        }
    }

    public void onDeleteMenuItemAction(ActionEvent actionEvent) {
        if(Status.selectAll) {
            mainPane.getChildren().clear();
            Status.selected = null;
            Status.selectAll = false;
        }
        if(Status.selected != null){
            Iterator<Node> ite = mainPane.getChildren().iterator();
            String id = String.valueOf(Status.selected.getId());
//                    System.out.println("line " + id);
            while (ite.hasNext()) {
                Node n = ite.next();
                String item = n.getId();
//                        System.out.println("item " + item);
                if (n instanceof Shape && item.equals(id)) {
//                            System.out.println("removed");
                    ite.remove();
                }
            }
            record.getActionList().remove(Status.selected);
            Status.selected = null;
        }
    }

    public void onSelectAllMenuItemAction(ActionEvent actionEvent) {
        Status.selectAll = true;
        mainPane.getChildren().forEach(node -> {
            if (node instanceof Shape) ((Shape) node).setStroke(Color.RED);
        });
    }
    //TODO: list record
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
        Alert helpInfoAlert = new Alert(Alert.AlertType.INFORMATION);
        helpInfoAlert.setTitle("帮助");
        helpInfoAlert.initOwner(mainPane.getScene().getWindow());
        helpInfoAlert.getDialogPane().setPrefWidth(700);
        helpInfoAlert.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
        helpInfoAlert.setHeaderText("关于JavaFX CAD Utility的帮助");
        helpInfoAlert.setContentText("绘制图形:\n" +
                "    除圆形之外的其他图形: 点击-确定起始点(或者多边形的一个顶点), 点击-确定终点(或多边形对角线上的顶点)\n" +
                "    圆形: 点击-确定圆心, 点击-确定圆上一点\n" +
                "    曲线: 点击并拖动-绘制曲线\n" +
                "选择颜色:\n" +
                "    右侧选择颜色\n    stroke-边线颜色  fill-填充颜色\n" +
                "    在选择了如曲线这类\"实心\"的图形工具时, fill下选择的颜色将被忽略\n" +
                "选择线条宽度:\n" +
                "    左下的选择框与滑动块均可以选择线条宽度, 两者同步且等效");
        helpInfoAlert.showAndWait();
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
            case "circleButton":
                Status.paintMode = PaintMode.CadCircle;
                break;
            case "fillButton":
                Status.paintMode = PaintMode.CadFiller;
                break;
        }
        CadStatusBar.setStatusText("Tool: " + Status.paintMode + "@"
                + String.format("%.1f, %.1fpx ", mouseX, mouseY));
    }

    public void onMainPaneMouseClicked(MouseEvent mouseEvent) {
        if (!Status.paintMode.equals(PaintMode.CadCurve) && Status.selected != null) {
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
                Status.saved = false;
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("插入");
                dialog.setHeaderText("插入文字");
                dialog.setContentText("键入想插入的文字");
                Optional<String> words = dialog.showAndWait();
                if(!words.isPresent())
                    return;
                CadShape textShape = CadShape.getCadShape(PaintMode.CadText, new CadPoint(x, y), words.get(), Status.strokeColor, Status.lineWidth + 13);
                record.getActionList().add(textShape);
                assert textShape != null;
                Text text = new CadText(x, y, words.get(), textShape, mainPane, record);
                text.setStroke(Status.strokeColor);
                mainPane.getChildren().add(text);

                break;
            case CadLine:
                Status.saved = false;
                Status.deleteCache.clear();
                record.getDeleteList().clear();
                if (Status.startPoint == null) {
                    Status.startPoint = new CadPoint(x, y);
                } else {
                    if(Status.lastShape != null){
                        mainPane.getChildren().remove(Status.lastShape);
                        Status.lastShape = null;
                    }
                    double sx = Status.startPoint.getX();
                    double sy = Status.startPoint.getY();
                    CadShape shape = CadShape.getCadShape(PaintMode.CadLine, Status.startPoint, new CadPoint(x, y), Status.strokeColor, Status.fillColor, Status.lineWidth);
                    record.getActionList().add(shape);
                    Line line = new CadLine(sx, sy, x, y, shape, mainPane, record);
                    Status.startPoint = null;
                    mainPane.getChildren().add(line);
                }
                break;

            case CadRectangle:
                Status.saved = false;
                Status.deleteCache.clear();
                record.getDeleteList().clear();
                if (Status.startPoint == null) {
                    Status.startPoint = new CadPoint(x, y);
                } else {
                    if(Status.lastShape != null){
                        mainPane.getChildren().remove(Status.lastShape);
                        Status.lastShape = null;
                    }
                    double sx = Status.startPoint.getX();
                    double sy = Status.startPoint.getY();
                    CadShape shape = CadShape.getCadShape(PaintMode.CadRectangle, Status.startPoint, new CadPoint(x, y), Status.strokeColor, Status.fillColor, Status.lineWidth);
                    record.getActionList().add(shape);
                    Rectangle rect = new CadRect(sx, sy, x, y, shape, mainPane, record);
                    Status.startPoint = null;
                    mainPane.getChildren().add(rect);
                }
                break;

            case CadOval:
                Status.saved = false;
                Status.deleteCache.clear();
                record.getDeleteList().clear();
                if (Status.startPoint == null) {
                    Status.startPoint = new CadPoint(x, y);
                } else {
                    if(Status.lastShape != null){
                        mainPane.getChildren().remove(Status.lastShape);
                        Status.lastShape = null;
                    }
                    double sx = Status.startPoint.getX();
                    double sy = Status.startPoint.getY();
                    CadShape shape = CadShape.getCadShape(PaintMode.CadOval, Status.startPoint, new CadPoint(x, y), Status.strokeColor, Status.fillColor, Status.lineWidth);
                    record.getActionList().add(shape);
                    Ellipse ell = new CadEllipse(sx, sy, x, y, shape, mainPane, record);
                    Status.startPoint = null;
                    mainPane.getChildren().add(ell);
                }
                break;
            case CadRectangle_RoundCorner:
                Status.saved = false;
                Status.deleteCache.clear();
                record.getDeleteList().clear();
                if (Status.startPoint == null) {
                    Status.startPoint = new CadPoint(x, y);
                } else {
                    if(Status.lastShape != null){
                        mainPane.getChildren().remove(Status.lastShape);
                        Status.lastShape = null;
                    }
                    double sx = Status.startPoint.getX();
                    double sy = Status.startPoint.getY();
                    CadShape shape = CadShape.getCadShape(PaintMode.CadRectangle_RoundCorner, Status.startPoint, new CadPoint(x, y), Status.strokeColor, Status.fillColor, Status.lineWidth);
                    record.getActionList().add(shape);
                    Rectangle rect = new CadRect(sx, sy, x, y, shape, mainPane, record);
                    rect.setArcHeight(Status.lineWidth);
                    rect.setArcWidth(Status.lineWidth);
                    Status.startPoint = null;
                    mainPane.getChildren().add(rect);
                }
                break;
            case CadCircle:
                Status.saved = false;
                Status.deleteCache.clear();
                record.getDeleteList().clear();
                if (Status.startPoint == null) {
                    Status.startPoint = new CadPoint(x, y);
                } else {
                    if(Status.lastShape != null){
                        mainPane.getChildren().remove(Status.lastShape);
                        Status.lastShape = null;
                    }
                    double sx = Status.startPoint.getX();
                    double sy = Status.startPoint.getY();
                    CadShape shape = CadShape.getCadShape(PaintMode.CadCircle, Status.startPoint, new CadPoint(x, y), Status.strokeColor, Status.fillColor, Status.lineWidth);
                    record.getActionList().add(shape);
                    Circle cir = new CadCircle(sx, sy, x, y, shape, mainPane, record);
                    Status.startPoint = null;
                    mainPane.getChildren().add(cir);
                }
                break;

        }
    }

    public void onMainPaneMousePressed(MouseEvent event) {
        if (Status.paintMode == PaintMode.CadCurve) {
            Status.saved = false;
            Status.penDrawable = true;
            if (Status.points == null) {
                Status.points = new ArrayList<>();
                Status.points.add(new CadPoint(event.getX(), event.getY()));
            }
            if (Status.tempLines == null) {
                Status.tempLines = new ArrayList<>();
            }

        }
    }

    public void onMainPaneMouseDragged(MouseEvent event) {
        if (Status.penDrawable) {
            Status.deleteCache.clear();
            record.getDeleteList().clear();
            double x = event.getX();
            double y = event.getY();
            CadPoint last = Status.points.get(Status.points.size() - 1);
            assert last != null : "last is null";
            double lx = last.getX();
            double ly = last.getY();
            if ((lx - x) * (lx - x) + (ly - y) * (ly - y) > 2) {
                Line line = new Line(lx, ly, x, y);
                line.setStrokeWidth(Status.lineWidth);
                line.setStroke(Status.strokeColor);
                mainPane.getChildren().add(line);
                Status.tempLines.add(line);
                Status.points.add(new CadPoint(x, y));
            }
        }
    }

    public void onMainPaneMouseReleased(MouseEvent event) {
        if (Status.paintMode.equals(PaintMode.CadCurve)) {
            Status.penDrawable = false;
            CadShape shape = CadShape.getCadShape(PaintMode.CadCurve, Status.points, Status.strokeColor, Status.lineWidth);
            record.getActionList().add(shape);
            Status.tempLines.forEach(line -> {
                assert shape != null;
                line.setId(String.valueOf(shape.getId()));
            });
            EventHandler<MouseEvent> handler = mouseEvent -> {
                Line line = (Line) mouseEvent.getSource();
                System.out.println(line.getId() + " clicked");
                if (Status.paintMode == PaintMode.CadEraser) {
                    Status.selected = null;
                    Status.selectAll = false;
                    Status.startPoint = null;
                    mainPane.getChildren().forEach(t -> {
                        if (t instanceof Shape)
                            ((Shape) t).setStroke(Status.strokeColor);//TODO: recover the origin color
                    });
                    System.out.println("eraser");
                    Iterator<Node> ite = mainPane.getChildren().iterator();
                    record.getActionList().remove(shape);
                    String id = line.getId();
//                    System.out.println("line " + id);
                    while (ite.hasNext()) {
                        Node n = ite.next();
                        String item = n.getId();
//                        System.out.println("item " + item);
                        if (n instanceof Line && item.equals(id)) {
//                            System.out.println("removed");
                            ite.remove();
                        }
                    }
                } else {
                    Status.selected = shape;
                    Status.selectAll = false;
                    Status.startPoint = null;
                    mainPane.getChildren().forEach(t -> {
                        if (t instanceof Shape)
                            ((Shape) t).setStroke(Status.strokeColor);//TODO: recover the origin color
                    });
                    mainPane.getChildren().forEach(t -> {
                        if (t instanceof Line && t.getId().equals(line.getId()))
                            ((Line) t).setStroke(Color.RED);
                    });
                }
                event.consume();
            };
            Status.tempLines.forEach(line -> line.setOnMouseClicked(handler));
            Status.tempLines = null;
            Status.points = null;
            Status.penDrawable = false;
        }
    }


}
