package main.java.cad;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 工具类, 用于导入导出工作空间
 * <p>
 * update 1.1 改为工具类, 修改相关参数及返回值
 *
 * @author 侯文轩 郑镜竹
 * @version 1.1
 */


class FileImportExport {

    public static boolean exportToFile(Record record, File file) {

        List<CadShape> actionList = record.getActionList();
        StringBuilder fileContent = new StringBuilder();
        BufferedWriter outputStream;
        try {
            outputStream = new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            showIOExceptionAlert();
            e.printStackTrace();
            return false;
        }

        fileContent.append("JavaFX_CAD_HSZZ\n").append(CadShape.getIdCnt()).append("\n");
        saveShapeListToStringBuilder(actionList, 0, fileContent);

        try {
            outputStream.write(fileContent.toString().toCharArray());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            showIOExceptionAlert();
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 把一个List<CadShape>写到文件中
     *
     * @param shapeList 要写的List
     * @param key       密钥
     * @return true - 保存成功; false - 遇到IO错误, 文件不存在或者存在但不可写
     */
    private static char[] saveShapeListToStringBuilder(List<CadShape> shapeList, int key, StringBuilder fileContent) {
        fileContent.append("---------\n");
        fileContent.append(shapeList.size()).append("\n");
        for (CadShape currShape : shapeList) {
            if (currShape.type == PaintMode.CadCurve) {
                fileContent.append(currShape.type.toString() + "\n"
                        + currShape.getId() + "\n"
                        + currShape.lineColor.toString() + "\n");
                fileContent.append(currShape.lineWidth + "\n");
                fileContent.append(currShape.curvePoints.size() + "\n");
                for (CadPoint currPoint : currShape.curvePoints) {
                    fileContent.append(currPoint.getX() + " " + currPoint.getY() + " ");
                }
                fileContent.append("\n");
                continue;
            }
            if (currShape.type == PaintMode.CadText) {
                fileContent.append(PaintMode.CadText.toString() + "\n"
                        + currShape.getId() + "\n"
                        + currShape.lineColor.toString() + "\n");
                fileContent.append(currShape.lineWidth + "\n" + currShape.textContent + "\n");
            }
            fileContent.append(currShape.type.toString() + "\n");
            fileContent.append(currShape.getId() + "\n");
            fileContent.append(currShape.lineColor + "\t" + currShape.fillColor + "\n");
            fileContent.append(currShape.lineWidth + "\n");
            fileContent.append(currShape.startPoint.getX() + " " + currShape.startPoint.getY() + " " +
                    currShape.endPoint.getX() + " " + currShape.endPoint.getY() + "\n");
        }
        char[] fileContentCharArray = fileContent.toString().toCharArray();

        for (int i = 0; i < fileContentCharArray.length; i++) {
            //fileContentCharArray[i] = (char) (fileContentCharArray[i] ^ key);
        }
        return fileContentCharArray;
    }

    /**
     * 从文件导入workspace
     * @param record 纪录类对象
     * @param key 密钥
     * @param saveFile 存档
     * @return true - 成功导入; false - 密钥错误, 文件损坏或遇到IO错误
     */
    public static boolean importFromFile(Record record, int key, File saveFile) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(saveFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        byte[] bytes = new byte[(int) saveFile.length()];
        int readLength;
        try {
            readLength = fileInputStream.read(bytes);
        } catch (IOException e) {
            showIOExceptionAlert();
            e.printStackTrace();
            return false;
        }

        String unDecryptedContent = new String(bytes, 0, readLength);
        StringBuilder decryptedContent = new StringBuilder();
        char[] charArray = unDecryptedContent.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            decryptedContent.append((char) (charArray[i] ^ key));
        }

        String[] segmentedLists = decryptedContent.toString().split("---------\n");
        String[] fileHeader = segmentedLists[0].split("\n");
        if (!segmentedLists[0].equals("JavaFX_CAD_HSZZ")) {
            Alert failedToDecryptAlert;
            //TODO Alert - 密钥错误或者文件损坏
            return false;
        }
        CadShape.setIdCnt(Integer.parseInt(fileHeader[1]));
        readShapeListFromStringArray(record.getActionList(), segmentedLists[1].split("\n"));
        return false;
    }

    public static boolean readShapeListFromStringArray(List<CadShape> shapeList, String[] lines) {

        int shapeNum = Integer.parseInt(lines[0]);
        int currShapeID;
        String[] colors, points;
        double lineWidth;

        for (int i = 1; i < lines.length; i++) {
            if (PaintMode.CadCurve.toString().equals(lines[i])) {
                List<CadPoint> curvePoints = new ArrayList<>();
                currShapeID = Integer.parseInt(lines[++i]);
                Color curveColor = Color.web(lines[++i]);
                lineWidth = Integer.parseInt(lines[++i]);
                int pointNum = Integer.parseInt(lines[++i]);
                points = lines[++i].split(" ");
                for (int pointCnt = 0; pointCnt < pointNum; pointCnt++) {
                    curvePoints.add(new CadPoint(Double.parseDouble(points[pointCnt]),
                            Double.parseDouble(points[++pointCnt])));
                }
                shapeList.add(CadShape.getCadShape(PaintMode.CadCurve, curvePoints, curveColor, lineWidth));
                shapeList.get(shapeList.size() - 1).setId(currShapeID);
                continue;
            }
            if (PaintMode.CadText.toString().equals(lines[i])) {
                currShapeID = Integer.parseInt(lines[++i]);
                Color textColor = Color.web(lines[++i]);
                lineWidth = Integer.parseInt(lines[++i]);
                shapeList.add(CadShape.getCadShape(PaintMode.CadText, lines[++i], textColor, lineWidth));
                shapeList.get(shapeList.size() - 1).setId(currShapeID);
            }
            if (PaintMode.CadLine.toString().equals(lines[i]) ||
                    PaintMode.CadRectangle.toString().equals(lines[i])) {
                currShapeID = Integer.parseInt(lines[++i]);
                colors = lines[++i].split("\t");
                lineWidth = Double.parseDouble(lines[++i]);
                points = lines[++i].split(" ");
                shapeList.add(CadShape.getCadShape(PaintMode.CadLine,
                        new CadPoint(Double.parseDouble(points[0]), Double.parseDouble(points[1])),
                        new CadPoint(Double.parseDouble(points[2]), Double.parseDouble(points[3])),
                        Color.web(colors[0]), Color.web(colors[1]), lineWidth));
                shapeList.get(shapeList.size() - 1).setId(currShapeID);
                continue;
            }
            //TODO Eraser类的导入(如需要)
        }
        return false;
    }

    /**
     * IO错误时, 弹出提示
     */
    public static void showIOExceptionAlert() {
        Alert IOExceptionAlert = new Alert(Alert.AlertType.ERROR);
        IOExceptionAlert.setTitle("An IO Error Occurred...");
        IOExceptionAlert.setHeaderText("An IO Error has been detected and reported, " +
                "and the saving/reading process has been aborted.");
        IOExceptionAlert.setContentText("Try:\n  Don't Panic\n" +
                "  Feel lucky about yourself(This is very rare!)\n" +
                "  Save again when you&your computer have calmed down\n" +
                "  And run a chkdsk if possible🍻");
        IOExceptionAlert.showAndWait();
    }
}
