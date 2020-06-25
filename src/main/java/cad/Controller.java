package main.java.cad;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PageOrientation;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jdk.net.SocketFlow;
import main.java.cad.CommonDefinitions.CommonPath;
import main.java.cad.MainCadStageParts.CadStatusBar;

import java.io.File;
import java.net.URL;
import java.util.*;


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
    public void onPainterToolMenuItemAction(ActionEvent event){
        if(painterToolBar.isVisible()) {
            painterToolBar.setVisible(false);
            painterToolBarMenuItem.setSelected(false);
        }
        else {
            painterToolBar.setVisible(true);
            painterToolBarMenuItem.setSelected(true);
        }
    }

    @FXML
    public void onColorToolMenuItemAction(ActionEvent actionEvent) {
        if(colorToolBar.isVisible()) {
            colorToolBar.setVisible(false);
            colorToolBarMenuItem.setSelected(false);
        }
        else {
            colorToolBar.setVisible(true);
            colorToolBarMenuItem.setSelected(true);
        }
    }

    @FXML
    public void onColorButtonClicked(ActionEvent actionEvent) {
        Button name = (Button) actionEvent.getSource();
        System.out.println(name.getId());
    }

    @FXML
    public void onColorPickerFinished(ActionEvent actionEvent) {
        Status.strokeColor = colorPicker.getValue();
        System.out.println(Status.strokeColor.toString());
    }

    @FXML
    public void onSizeComboBoxClicked(MouseEvent mouseEvent) {
        List<String> sizeOptions = new ArrayList<>();
        for (int i = 8; i <= 20; i += 2) {
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
        child = date.toString() + ".hszz";
        statusBar = new CadStatusBar().getCadStatusBar();
        borderPane.setBottom(statusBar);
    }

    @FXML
    public void onMouseMovedInMainPane(MouseEvent mouseEvent) {
        CadStatusBar.setStatusText(String.format("%.1f, %.1fpx ", mouseEvent.getX(), mouseEvent.getY()));
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
        if(!FileImportExport.exportToFile(record, new File(parentDir, child))){
            System.err.println("save failed");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("警告");
            alert.setHeaderText("保存失败");
            alert.setContentText("请检查保存路径是否合法");
            alert.showAndWait();
        }
    }

    public void onSaveAsMenuItemAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择保存到的文件");
        Stage mainStage = (Stage)borderPane.getScene().getWindow();
        File saving = fileChooser.showOpenDialog(mainStage);
        if(!FileImportExport.exportToFile(record, saving)){
            System.err.println("save failed");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("警告");
            alert.setHeaderText("保存失败");
            alert.setContentText("请检查保存路径是否合法");
            alert.showAndWait();
        }
    }

    public void onRefreshMenuItemAction(ActionEvent actionEvent) {
        //TODO: paint graphs from record again
    }

    public void onRestartMenuItemAction(ActionEvent actionEvent) {
        if(!Status.saved){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("重启程序");
            alert.setHeaderText("您确定要重启吗");
            alert.setContentText("画布尚未保存，重启后将失去所有未保存内容");
            Optional<ButtonType> buttonType = alert.showAndWait();
            if(!buttonType.get().getButtonData().equals(ButtonBar.ButtonData.OK_DONE)){
                actionEvent.consume();
                return;
            }

        }
        Stage mainStage = (Stage)borderPane.getScene().getWindow();
        mainStage.close();
        Platform.runLater(() -> {
            try {
                new Main().start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void onExitMenuItemAction(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void onRedoMenuItemAction(ActionEvent actionEvent) {
//        List<CadShape> actionList = record.getActionList();
//        List<CadShape> deleteList = record.getDeleteList();
//        String id;
//        CadShape shape;
//        if(deleteList.isEmpty()) {
//
//            if (actionList instanceof LinkedList){
//                shape = ((LinkedList<CadShape>) actionList).getLast();
//            }
//            else {
//                shape = actionList.get(actionList.size() - 1);
//            }
//            id = String.valueOf(shape.id);
//
//            Node n = mainPane.lookup(id);
//            if(n != null){
//                n.setVisible(false);
//                actionList.remove(shape);
//                deleteList.add(shape);
//            }
//            else{
//                System.err.println("redo failed");
//
//            }
//            actionEvent.consume();
//        }
//        else {
//            if (deleteList instanceof LinkedList){
//                shape = ((LinkedList<CadShape>) deleteList).getLast();
//            }
//            else {
//                shape = deleteList.get(deleteList.size() - 1);
//            }
//            id = String.valueOf(shape.id);
//            Node n = mainPane.lookup(id);
//            if(n != null){
//                n.setVisible(false);
//                actionList.remove(shape);
//                deleteList.add(shape);
//            }
//        }
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
        mainPane.getChildren().forEach(node -> {if(node instanceof Shape) ((Shape)node).setStroke(Color.RED); });
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

    public void onAboutMenuItemAction(ActionEvent actionEvent) {
        Alert aboutAlert = new Alert(Alert.AlertType.INFORMATION);
        aboutAlert.setTitle("About JavaFX CAD Utility");
        aboutAlert.setHeaderText("JavaFX CAD Utility\nBased on Intellij IDEA, GitHub and Teamwork");
        aboutAlert.initStyle(StageStyle.UTILITY);
        aboutAlert.setContentText("版本："
                + CommonPath.version + "\n"
                + "Developers: "
                + "郑镜竹 宋志元 翟凡荣 侯文轩\n"
                + "Available on GitHub: \n"
                + CommonPath.gitHubLink + "\n"
                + "License: Apache License 2.0");
        aboutAlert.showAndWait();
    }


    public void onToolsButtonAction(ActionEvent actionEvent) {
        Button button = (Button)actionEvent.getSource();
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
                Status.paintMode = PaintMode.CadEraser;//TODO: erase mode

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
    }

    public void onMainPaneMouseClicked(MouseEvent mouseEvent) {
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();
        switch (Status.paintMode){
            case CadText:
                //TODO: Enter Texts
            case CadLine:
                if(Status.startPoint == null){
                    Status.startPoint = new CadPoint(x, y);
                } else {
                    CadShape shape = CadShape.getCadShape(PaintMode.CadLine, Status.startPoint, new CadPoint(x, y), Status.strokeColor, Status.fillColor);
                    record.getActionList().add(shape);
                    Line line = new Line(Status.startPoint.getX(), Status.startPoint.getY(), x, y);
                    Status.startPoint = null;
                    line.setId(String.valueOf(shape.getId()));
                    line.setStrokeWidth(Status.lineWidth);//TODO: update Record to support line width
                    line.setOnMouseClicked(event -> {
                        if(Status.paintMode == PaintMode.CadEraser){
                            line.setVisible(false);
                            //TODO: improve delete operations
                        }
                        else {
                            Status.selected = shape;
                            Status.selectAll = false;
                            mainPane.getChildren().forEach(t ->{
                                ((Shape)t).setStroke(Status.strokeColor);//TODO: recover the origin color
                            });
                            line.setStroke(Color.RED);
                        }
                    });
                    mainPane.getChildren().add(line);
                }
        }
    }
}
