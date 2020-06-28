package main.java.cad;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.paint.Color;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        int key = showPasswordDialog();
        if (key < 0)
            return false;

        key = 0;
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
        saveShapeListToStringBuilder(actionList, fileContent);

        char[] fileContentCharArray = fileContent.toString().toCharArray();
        for (int i = 0; i < fileContentCharArray.length; i++) {
            fileContentCharArray[i] = (char) (fileContentCharArray[i] ^ key);
        }
        try {
            outputStream.write(fileContentCharArray);
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
     * @param shapeList   要写的List
     * @param fileContent 目的StringBuilder
     */
    private static void saveShapeListToStringBuilder(List<CadShape> shapeList, StringBuilder fileContent) {
        fileContent.append("---------\n");
        fileContent.append(shapeList.size()).append("\n");
        for (CadShape currShape : shapeList) {
            if (currShape.type == PaintMode.CadCurve) {
                fileContent.append(currShape.type.toString()).append("\n").append(currShape.getId())
                        .append("\n").append(currShape.lineColor.toString()).append("\n");
                fileContent.append(currShape.lineWidth).append("\n");
                fileContent.append(currShape.curvePoints.size()).append("\n");
                for (CadPoint currPoint : currShape.curvePoints) {
                    fileContent.append(currPoint.getX()).append(" ").append(currPoint.getY()).append(" ");
                }
                fileContent.append("\n");
                continue;
            }
            if (currShape.type == PaintMode.CadText) {
                fileContent.append(PaintMode.CadText.toString()).append("\n").append(currShape.getId())
                        .append("\n").append(currShape.lineColor.toString()).append("\n");
                fileContent.append(currShape.lineWidth).append("\n").append(currShape.textContent).append("\n");
                fileContent.append(currShape.startPoint.getX()).append(" ")
                        .append(currShape.startPoint.getY()).append("\n");
                continue;
            }
            fileContent.append(currShape.type.toString()).append("\n");
            fileContent.append(currShape.getId()).append("\n");
            fileContent.append(currShape.lineColor).append("\t").append(currShape.fillColor).append("\n");
            fileContent.append(currShape.lineWidth).append("\n");
            fileContent.append(currShape.startPoint.getX()).append(" ").append(currShape.startPoint.getY()).append(" ")
                    .append(currShape.endPoint.getX()).append(" ").append(currShape.endPoint.getY()).append("\n");
        }
    }

    /**
     * 从文件导入workspace
     *
     * @param record   纪录类对象
     * @param saveFile 存档
     * @return true - 成功导入; false - 密钥错误, 文件损坏或遇到IO错误
     */
    public static boolean importFromFile(Record record, File saveFile) {

        int key = showPasswordDialog();
        if (key < 0)
            return false;

        key = 0;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(saveFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        int readLength = -1;
        byte[] bytes = new byte[(int) saveFile.length()];
        try {
            readLength = fileInputStream.read(bytes);
        } catch (IOException e) {
            showIOExceptionAlert();
            e.printStackTrace();
            return false;
        }
        String wholeContent = new String(bytes, 0, readLength);
        char[] charArray = wholeContent.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            charArray[i] = (char) (charArray[i] ^ key);
            //charArray[i] = (char) bytes[i];
        }

        String decryptedContent = new String(charArray);
        String[] segmentedLists = decryptedContent.split("---------\n");
        String[] fileHeader = segmentedLists[0].split("\n");
        if (!fileHeader[0].equals("JavaFX_CAD_HSZZ")) {
            Alert failedToDecryptAlert = new Alert(Alert.AlertType.ERROR);
            failedToDecryptAlert.setTitle("导入工作环境存档时遇到了一些问题...");
            failedToDecryptAlert.setHeaderText("导入操作存在一定问题, 因此该存档未能被导入");
            failedToDecryptAlert.setContentText("这可能是由以下原因导致的:\n  - 错误的密钥(很可能, 请好好检查一下)\n" +
                    "-  存档文件已被损坏(这就没办法了...)" +
                    "-  其他未知的原因\n我们的程序在GitHub上开源(可以从Help - About看到链接), 如果有需要请提交Issue)");
            failedToDecryptAlert.showAndWait();
            return false;
        }
        CadShape.setIdCnt(Integer.parseInt(fileHeader[1]));
        readShapeListFromStringArray(record.getActionList(), segmentedLists[1].split("\n"));
        return true;
    }

    @SuppressWarnings("UnnecessaryContinue")
    public static void readShapeListFromStringArray(List<CadShape> shapeList, String[] lines) {

        int shapeNum = Integer.parseInt(lines[0]);
        int currShapeID;
        String[] colors, points;
        double lineWidth;

        for (int i = 1; i < lines.length; i++) {
            if (PaintMode.CadCurve.toString().equals(lines[i])) {
                List<CadPoint> curvePoints = new ArrayList<>();
                currShapeID = Integer.parseInt(lines[++i]);
                Color curveColor = Color.web(lines[++i]);
                lineWidth = Double.parseDouble(lines[++i]);
                int pointNum = Integer.parseInt(lines[++i]);
                points = lines[++i].split(" ");
                for (int pointCnt = 0; pointCnt < pointNum * 2; pointCnt++) {
                    curvePoints.add(new CadPoint(Double.parseDouble(points[pointCnt]),
                            Double.parseDouble(points[++pointCnt])));
                }
                shapeList.add(CadShape.getCadShape(PaintMode.CadCurve, curvePoints, curveColor, lineWidth));
                shapeList.get(shapeList.size() - 1).setId(currShapeID);
                System.out.println(shapeList.get(shapeList.size() - 1).curvePoints.size());
                continue;
            }
            if (PaintMode.CadText.toString().equals(lines[i])) {
                currShapeID = Integer.parseInt(lines[++i]);
                Color textColor = Color.web(lines[++i]);
                lineWidth = Double.parseDouble(lines[++i]);
                String textContent = lines[++i];
                String[] pointsArray = lines[++i].split(" ");
                CadPoint textPoint = new CadPoint(Double.parseDouble(pointsArray[0]), Double.parseDouble(pointsArray[1]));
                shapeList.add(CadShape.getCadShape(PaintMode.CadText, textPoint, textContent, textColor, lineWidth));
                shapeList.get(shapeList.size() - 1).setId(currShapeID);
                continue;
            }
            PaintMode currShapeType = PaintMode.getPaintModeByName(lines[i]);
            currShapeID = Integer.parseInt(lines[++i]);
            colors = lines[++i].split("\t");
            lineWidth = Double.parseDouble(lines[++i]);
            points = lines[++i].split(" ");
            shapeList.add(CadShape.getCadShape(currShapeType,
                    new CadPoint(Double.parseDouble(points[0]), Double.parseDouble(points[1])),
                    new CadPoint(Double.parseDouble(points[2]), Double.parseDouble(points[3])),
                    Color.web(colors[0]), Color.web(colors[1]), lineWidth));
            shapeList.get(shapeList.size() - 1).setId(currShapeID);
            continue;
        }
    }

    /**
     * IO错误时, 弹出提示
     */
    public static void showIOExceptionAlert() {
        Alert IOExceptionAlert = new Alert(Alert.AlertType.ERROR);
        IOExceptionAlert.setTitle("我们遇到了一个IO错误...");
        IOExceptionAlert.setHeaderText("我们检测到系统报告了一个IO错误, " +
                "因此当前的导入/导出操作已终止.");
        IOExceptionAlert.setContentText("您可以尝试:\n  保持淡定\n" +
                "  高兴一点儿(这个异常可不常见!)\n" +
                "  当你和你的电脑都冷静下来的时候再试一次\n" +
                "  如果需要的话, 进行一次磁盘检查(比如使用chkdsk命令)");
        IOExceptionAlert.showAndWait();
    }

    public static int showPasswordDialog() {
        TextInputDialog dialog = new TextInputDialog("2020");
        int password = -1;
        dialog.setTitle("输入密码以保护导出的工作环境存档");
        dialog.setHeaderText("请输入2~6位大于零的纯数字密码, 来保护导出的工作环境存档");
        dialog.setContentText("密码: ");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try {
                System.out.println(result.get());
                password = Integer.parseInt(result.get());
            } catch (NumberFormatException e) {
                showInvalidPasswordAlert();
                return -1;
            }
            if (password < 10 || password > 999999) {
                showInvalidPasswordAlert();
                return -1;
            }
            return password;
        }
        return -1;
    }

    private static void showInvalidPasswordAlert() {
        Alert invalidPasswordAlert = new Alert(Alert.AlertType.ERROR);
        invalidPasswordAlert.setTitle("密码格式错误");
        invalidPasswordAlert.setHeaderText("很抱歉, 您输入的密码格式错误\n导入/导出操作已终止");
        invalidPasswordAlert.setContentText("您可以:\n  检查输入的密码格式是否为2~6位纯数字,且大于零\n  确认密码后重试");
        invalidPasswordAlert.showAndWait();
    }
}
