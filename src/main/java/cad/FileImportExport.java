package main.java.cad;

import javafx.scene.paint.Color;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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


    //TODO 导出到文件, 可以包含简单的加密, 比如异或加密
    public static boolean exportToFile(Record record, File file) {
        List<CadShape> actionList = record.getActionList();
        actionList.add(0, CadShape.getCadShape(PaintMode.CadText, "666", Color.BLACK));
        return false;
    }

    /**
     * 把一个List<CadShape>写到文件中
     * @param shapeList 要写的List
     * @param saveFile 保存到的文件
     * @return true - 保存成功; false - 遇到IO错误, 文件不存在或者不可写
     */
    private static boolean saveShapeListToFile(List<CadShape> shapeList, File saveFile) {
        if (saveFile == null || saveFile.canWrite()) {
            return false;
        }
        StringBuffer fileContent = new StringBuffer();
        fileContent.append("JavaFX_CAD_HSZZ\n");
        fileContent.append(shapeList.size() + "\n");
        for (CadShape currShape : shapeList) {
            if (currShape.type == PaintMode.CadCurve) {
                fileContent.append(currShape.type.toString() + "\n" + currShape.lineColor.toString());
                fileContent.append(currShape.curvePoints.size() + "\n");
                for (CadPoint currPoint : currShape.curvePoints) {
                    fileContent.append(currPoint.getX() + " " + currPoint.getY() + "\t");
                }
                fileContent.append("\n");
                continue;
            }
            if (currShape.type == PaintMode.CadText) {
                fileContent.append(PaintMode.CadText.toString() + "\n" + currShape.lineColor.toString());
                fileContent.append(currShape.lineWidth + "\n" + currShape.textContent + "\n");
            }
            fileContent.append(currShape.type.toString() + "\n");
            fileContent.append(currShape.lineColor + "\t" + currShape.fillColor + "\n");
            fileContent.append(currShape.lineWidth + "\n");
            fileContent.append(currShape.startPoint.getX() + " " + currShape.startPoint.getY() + "\t" +
                    currShape.endPoint.getX() + " " + currShape.endPoint.getY() + "\n");

            char[] fileContentCharArray = fileContent.toString().toCharArray();

            for (int i = 0; i < fileContentCharArray.length; i++) {
                //fileContentCharArray[i] = (char) (fileContentCharArray[i] ^ key);
            }

            try {
                BufferedWriter outFile = new BufferedWriter(new FileWriter(saveFile));
                outFile.write(fileContentCharArray);
                outFile.flush();
                outFile.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    //TODO 从文件导入
    public static Record importFromFile(File file) {
        return null;
    }
}
