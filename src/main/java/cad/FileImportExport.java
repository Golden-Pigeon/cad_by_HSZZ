package main.java.cad;

import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileImportExport extends Record {

    //TODO 导出到文件, 可以包含简单的加密, 比如异或加密
    public static boolean exportToFile() {
        int key = 1;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Workspace To...");
        fileChooser.setInitialDirectory(new File("."));
        SimpleDateFormat dateformatter = new SimpleDateFormat("yy-MM-dd");
        String fileNameTime = dateformatter.format(new Date());
        fileChooser.setInitialFileName("JFXCAD_" + fileNameTime + ".save");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Global Save File", "*.save"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File saveFile = fileChooser.showSaveDialog(null);
        if (saveFile == null) {
            Alert failedToSaveAlert = new Alert(Alert.AlertType.ERROR);
            failedToSaveAlert.setTitle("Failed to Save");
            failedToSaveAlert.initStyle(StageStyle.UTILITY);
            failedToSaveAlert.setHeaderText("Failed to Save Because Given Invalid Path");
            failedToSaveAlert.setContentText("Try Again with a Valid Path : )");
            failedToSaveAlert.showAndWait();
            return false;
        }

        StringBuffer fileContent = new StringBuffer();
        fileContent.append("JavaFX_CAD_HSZZ\n");
        for (CadShape currShape : Record.actionList) {
            if (currShape.type == ShapeType.CadCurve) {
                fileContent.append(currShape.type.toString() + "\n" + currShape.lineColor.toString());
                fileContent.append(currShape.curvePoints.size() + "\n");
                for (CadPoint currPoint : currShape.curvePoints) {
                    fileContent.append(currPoint.x + " " + currPoint.y + "\t");
                }
                fileContent.append("\n");
                continue;
            }
            if (currShape.type == ShapeType.CadText) {
                fileContent.append(ShapeType.CadText.toString() + "\n" + currShape.lineColor.toString());
                fileContent.append(currShape.lineWidth + "\n" + currShape.textContent + "\n");
            }
            fileContent.append(currShape.type.toString() + "\n");
            fileContent.append(currShape.lineColor + "\t" + currShape.fillColor + "\n");
            fileContent.append(currShape.lineWidth + "\n");
            fileContent.append(currShape.startPoint.x + " " + currShape.startPoint.y + "\t" +
                    currShape.endPoint.x + " " + currShape.endPoint.y + "\n");

            char[] fileContentCharArray = fileContent.toString().toCharArray();

            for(int i = 0;i < fileContentCharArray.length; i++){
                //fileContentCharArray[i] = (char) (fileContentCharArray[i] ^ key);
            }

            try {
                BufferedWriter outFile = new BufferedWriter(new FileWriter(saveFile));
                outFile.write(fileContentCharArray);
                outFile.flush();
                outFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return false;
    }

    public static boolean exportToPNG() {
        return false;
    }

    //TODO 从文件导入
    public static boolean importFromFile() {
        return false;
    }
}
