package main.java.cad;

import javafx.scene.paint.Color;

import java.util.List;

/**
 *
 * 工具类, 用于导入导出工作空间
 *
 * update 1.1 改为工具类, 修改相关参数及返回值
 *
 *
 * @author 侯文轩 郑镜竹
 * @version 1.1
 */


class FileImportExport {


    //TODO 导出到文件, 可以包含简单的加密, 比如异或加密
    public static boolean exportToFile(Record record) {
        List<CadShape> actionList = record.getActionList();
        actionList.add(1, CadShape.getCadShape(ShapeType.CadText, "666", Color.BLACK));
        return false;
    }

    //TODO 从文件导入
    public static Record importFromFile() {
        return null;
    }
}
