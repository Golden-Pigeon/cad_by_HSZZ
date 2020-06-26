package main.java.cad;

import javafx.scene.paint.Color;

import java.io.*;
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
     *
     * @param shapeList 要写的List
     * @param saveFile  保存到的文件
     * @return true - 保存成功; false - 遇到IO错误, 文件不存在或者不可写
     */
    private static boolean saveShapeListToFile(List<CadShape> shapeList, File saveFile) {
        if (saveFile == null || saveFile.canWrite()) {
            return false;
        }
        StringBuffer fileContent = new StringBuffer();
        fileContent.append("JavaFX_CAD_HSZZ\n");
        fileContent.append(shapeList.size()).append("\n");
        for (CadShape currShape : shapeList) {
            if (currShape.type == PaintMode.CadCurve) {
                fileContent.append(currShape.type.toString()).append("\n");
                fileContent.append(currShape.lineColor.toString()).append("\t").append(currShape.curvePoints.size()).append("\n");
                for (CadPoint currPoint : currShape.curvePoints) {
                    fileContent.append(currPoint.getX()).append(" ").append(currPoint.getY()).append("\t");
                }
                fileContent.append("\n");
                continue;
            }
            if (currShape.type == PaintMode.CadText) {
                fileContent.append(PaintMode.CadText.toString()).append("\n").append(currShape.lineColor.toString());
                fileContent.append(currShape.lineWidth).append("\n").append(currShape.textContent).append("\n");
            }
            fileContent.append(currShape.type.toString()).append("\n");
            fileContent.append(currShape.lineColor).append("\t").append(currShape.fillColor).append("\n");
            fileContent.append(currShape.lineWidth).append("\n");
            fileContent.append(currShape.startPoint.getX()).append(" ").append(currShape.startPoint.getY()).append("\t").append(currShape.endPoint.getX()).append(" ").append(currShape.endPoint.getY()).append("\n");

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

    /**
     * 使用序列化的方式把所有的List保存到一个文件里
     * @param record 要序列化的对应地Record类对象
     * @param saveFile 目的文件
     * @return true - 序列化成功; false - 文件不存在或遇到IO错误
     */
    public static boolean serializeShapeListsToFileSystem(Record record, File saveFile) {
        saveFile = new File("save.save");
        IntegratedShapeLists integratedShapeLists = new IntegratedShapeLists((record));
        try {
            BufferedOutputStream fileOutputStream = new BufferedOutputStream(new FileOutputStream(saveFile));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(integratedShapeLists);
            System.out.println(integratedShapeLists instanceof Serializable);
            objectOutputStream.close();
            fileOutputStream.flush();
            fileOutputStream.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deserializeShapeListsFromFileSystem(Record record, File saveFile) {
        return false;
    }

    //TODO 从文件导入
    public static Record importFromFile(Record record, File saveFile) {
        return null;
    }

    //TODO 从文件读取一个List, 计划采用序列化的方式进行
    private static List<CadShape> readShapeListFromFile(int key, File saveFile) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(saveFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        byte[] bytes = new byte[(int) saveFile.length()];
        int readLength;
        try {
            readLength = fileInputStream.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
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
            //TODO 密钥错误或者文件损坏
            return null;
        }
        int shapeNum = Integer.parseInt(lines[1]);

        for (int i = 0; i < shapeNum; i++) {
            //if(lines[i].equals())
        }
        return null;
    }
}
