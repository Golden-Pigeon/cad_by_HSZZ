package main.java.cad;

import javafx.scene.paint.Color;

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


    //TODO 导出到文件, 可以包含简单的加密, 比如异或加密
    public static boolean exportToFile(Record record, File file) {
        List<CadShape> actionList = record.getActionList();
        return saveShapeListToFile(record.getActionList(), 0, new File("save.save"));
    }

    /**
     * 把一个List<CadShape>写到文件中
     *
     * @param shapeList 要写的List
     * @param key       密钥
     * @param saveFile  保存到的文件
     * @return true - 保存成功; false - 遇到IO错误, 文件不存在或者存在但不可写
     */
    private static boolean saveShapeListToFile(List<CadShape> shapeList, int key, File saveFile) {
        if (saveFile == null || (saveFile.exists() && !saveFile.canWrite())) {
            return false;
        }
        StringBuffer fileContent = new StringBuffer();
        fileContent.append("JavaFX_CAD_HSZZ\n");
        fileContent.append(shapeList.size()).append("\n");
        for (CadShape currShape : shapeList) {
            if (currShape.type == PaintMode.CadCurve) {
                fileContent.append(currShape.type.toString() + "\n"
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
                fileContent.append(PaintMode.CadText.toString() + "\n" + currShape.lineColor.toString() + "\n");
                fileContent.append(currShape.lineWidth + "\n" + currShape.textContent + "\n");
            }
            fileContent.append(currShape.type.toString() + "\n");
            fileContent.append(currShape.lineColor + "\t" + currShape.fillColor + "\n");
            fileContent.append(currShape.lineWidth + "\n");
            fileContent.append(currShape.startPoint.getX() + " " + currShape.startPoint.getY() + " " +
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
    public static boolean importFromFile(Record record, int key, File saveFile) {
        return false;
    }

    public static boolean readShapeListFromFile(List<CadShape> shapeList, int key, File saveFile) {

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
            e.printStackTrace();
            return false;
        }

        String unDecryptedContent = new String(bytes, 0, readLength);
        StringBuilder decryptedContent = new StringBuilder();
        char[] charArray = unDecryptedContent.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            decryptedContent.append((char) (charArray[i] ^ key));
        }

        String[] lines = decryptedContent.toString().split("\n");
        String[] lineContent;
        if (!lines[0].equals("JavaFX_CAD_HSZZ")) {
            //TODO Alert - 密钥错误或者文件损坏
            return false;
        }
        int shapeNum = Integer.parseInt(lines[1]);

        String[] colors, points;
        int lineWidth;
        for (int i = 0; i < shapeNum; i++) {
            if (PaintMode.CadLine.toString().equals(lines[i]) ||
                    PaintMode.CadRectangle.toString().equals(lines[i])) {
                colors = lines[++i].split("\t");
                lineWidth = Integer.parseInt(lines[++i]);
                points = lines[++i].split(" ");
                shapeList.add(CadShape.getCadShape(PaintMode.CadLine,
                        new CadPoint(Double.parseDouble(points[0]), Double.parseDouble(points[1])),
                        new CadPoint(Double.parseDouble(points[2]), Double.parseDouble(points[3])),
                        Color.web(colors[0]), Color.web(colors[1]), lineWidth));
                continue;
            }
            if (PaintMode.CadCurve.toString().equals(lines[i])) {
                List<CadPoint> curvePoints = new ArrayList<>();
                Color curveColor = Color.web(lines[++i]);
                lineWidth = Integer.parseInt(lines[++i]);
                int pointNum = Integer.parseInt(lines[++i]);
                points = lines[++i].split(" ");
                for (int pointCnt = 0; pointCnt < pointNum; pointCnt++) {
                    curvePoints.add(new CadPoint(Double.parseDouble(points[pointCnt]),
                            Double.parseDouble(points[++pointCnt])));
                }
                shapeList.add(CadShape.getCadShape(PaintMode.CadCurve, curvePoints, curveColor, lineWidth));
                continue;
            }
            if(PaintMode.CadText.toString().equals(lines[i])) {
                Color textColor = Color.web(lines[++i]);
                lineWidth = Integer.parseInt(lines[++i]);
                shapeList.add(CadShape.getCadShape(PaintMode.CadText, lines[++i], textColor, lineWidth));
                continue;
            }
            //TODO Eraser类的导入(如需要)
        }
        return false;
    }
}
