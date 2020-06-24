package main.java.cad.MainCadStageParts;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.StageStyle;
import main.java.cad.CommonDefinitions.CommonPath;
import main.java.cad.MainCadStage;
import main.java.cad.FileImportExport;

public class CadMenuBar {

    private MainCadStage mainCadStage;
    private MenuBar menubar;
    private Menu file, edit, tools, window, server, coop, help;
    //coop尚未加入菜单(该任务优先级靠后)
    private MenuItem miOpen, miSavePNG, miSaveWorkSpace, miExit;
    private MenuItem miUndo, miReDo, miClear, miListRecord;
    private MenuItem miAbout;
    private MenuItem miGraphicTool, miAttributeTool;
    private RadioMenuItem miHideToolBar;
    private MenuItem miNewWindow, miPerference;
    private MenuItem miConnect, miGroup, miCustimizeAccount, miUpload, miDownload, miDisconnect;
    private MenuItem miStatBox, miHistory;


    public CadMenuBar(MainCadStage mainCadStage) {
        this.mainCadStage = mainCadStage;
        initCadMenuBarContent();
    }

    private void initCadMenuBarContent() {
        menubar = new MenuBar();

        file = new Menu();
        file.setText("File");
        file.setStyle("-fx-font-size:14;");

        miOpen = new MenuItem();
        miOpen.setText("Open");
        miOpen.setStyle("-fx-font-size:14;");
/*		miOpen.setOnAction((ActionEvent t) -> {
			//打开
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image", "*.png", "*.jpg", "*.jpeg"));
			fileChooser.setTitle("打开图片");// 打开图片
			File file = fileChooser.showOpenDialog(mainStage);
			if (file != null) {
				try {
					Image image = new Image(new FileInputStream(file));
					mainStage.getBorad().addImage(image);
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
			}
		});*/

        miSavePNG = new MenuItem();
        miSavePNG.setText("Save as PNG");
        miSavePNG.setStyle("-fx-font-size:14;");
        miSavePNG.setAccelerator(KeyCombination.keyCombination("Ctrl+s"));
        miSavePNG.setOnAction((ActionEvent t) -> {
                /*
                List<Canvas> list = mainCadStage.getBoard().getList();
                FileSaver fileSaver = new FileSaver(list, mainCadStage.getBoard().getW(), mainCadStage.getBoard().getH());
                fileSaver.saveToFile();
                 */

        });

        miSaveWorkSpace = new MenuItem();
        miSaveWorkSpace.setText("Save as a Workspace (Ctrl+W)");
        miSaveWorkSpace.setStyle("-fx-font-size:14;");
        miSaveWorkSpace.setAccelerator(KeyCombination.keyCombination("Ctrl+w"));
        miSaveWorkSpace.setOnAction((ActionEvent t) -> {
            FileImportExport.exportToFile();
        });

        miExit = new MenuItem();
        miExit.setText("Exit");
        miExit.setStyle("-fx-font-size:14;");
        miExit.setOnAction((ActionEvent t) -> {
            Platform.exit();
        });
        file.getItems().add(miOpen);
        file.getItems().add(miSavePNG);
        file.getItems().add(miSaveWorkSpace);
        file.getItems().add(miExit);

        edit = new Menu();
        edit.setText("Edit");
        edit.setStyle("-fx-font-size:14;");

        miUndo = new MenuItem("Undo (Ctrl+Z)");
        miUndo.setStyle("-fx-font-size:14;");
        miUndo.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.SHORTCUT_DOWN));
        miUndo.setOnAction((ActionEvent t) -> {
            mainCadStage.getCanvas().undo();
        });

        miReDo = new MenuItem("Redo");
        miReDo.setStyle("-fx-font-size:14;");
        miReDo.setOnAction((ActionEvent t) -> {

        });

        miClear = new MenuItem("Clear All");
        miClear.setStyle("-fx-font-size:14;");
        miClear.setOnAction((ActionEvent t) -> {
            mainCadStage.getCanvas().clearCanvas();
        });

        miListRecord = new MenuItem("List Action Records");
        miListRecord.setStyle("-fx-font-size:14;");
        miListRecord.setOnAction((ActionEvent t) -> {

        });

        edit.getItems().add(miUndo);
        edit.getItems().add(miReDo);
        edit.getItems().add(miClear);
        edit.getItems().add(miListRecord);

        tools = new Menu();
        tools.setText("Tools");
        tools.setStyle("-fx-font-size:14;");

        miGraphicTool = new MenuItem();
        miGraphicTool.setText("Graphic Tool");
        miGraphicTool.setStyle("-fx-font-size:14;");
        miAttributeTool = new MenuItem();
        miAttributeTool.setText("Attribute Tool");
        miAttributeTool.setStyle("-fx-font-size:14;");
        miHideToolBar = new RadioMenuItem();
        miHideToolBar.setStyle("-fx-font-size:14;");
        miHideToolBar.setText("Hide Tool Bar");
        miHideToolBar.setOnAction(ActionEvent -> {
            if(miHideToolBar.isSelected())
                mainCadStage.hideToolBar();
            else
                mainCadStage.restoreToolBar();
        });

        tools.getItems().addAll(miGraphicTool, miAttributeTool, miHideToolBar);

        window = new Menu();
        window.setText("Window");
        window.setStyle("-fx-font-size:14;");

        miNewWindow = new MenuItem();
        miNewWindow.setText("New Window");
        miNewWindow.setStyle("-fx-font-size:14;");

        window.getItems().addAll(miNewWindow);

        server = new Menu();
        server.setText("Server");
        server.setStyle("-fx-font-size:14;");

        miConnect = new MenuItem();
        miConnect.setText("Connect to Server");
        miConnect.setStyle("-fx-font-size:14;");
        miConnect.setOnAction((ActionEvent t) -> {

        });

        miDisconnect = new MenuItem();
        miDisconnect.setText("Disconnect from Server");
        miDisconnect.setStyle("-fx-font-size:14;");
        miDisconnect.setOnAction((ActionEvent t) -> {

        });

        miUpload = new MenuItem();
        miUpload.setText("Upload Workspace");
        miUpload.setStyle("-fx-font-size:14;");
        miUpload.setOnAction((ActionEvent t) -> {

        });

        miDownload = new MenuItem();
        miDownload.setText("Download Workspace");
        miDownload.setStyle("-fx-font-size:14;");
        miDownload.setOnAction((ActionEvent t) -> {

        });

        server.getItems().addAll(miConnect, miDisconnect, miUpload, miDownload);

        help = new Menu();
        help.setText("Help");
        help.setStyle("-fx-font-size:14;");

        miAbout = new MenuItem();
        miAbout.setText("About");
        miAbout.setStyle("-fx-font-size:14;");
        miAbout.setOnAction((ActionEvent t) -> {
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
                    + "License: GPL V3.0");
            aboutAlert.showAndWait();
        });
        help.getItems().add(miAbout);
        menubar.getMenus().addAll(file, edit, tools, window, server, help);
    }

    public MenuBar getMenuBar() {
        return menubar;
    }
}
