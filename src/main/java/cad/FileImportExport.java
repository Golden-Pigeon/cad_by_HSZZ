package main.java.cad;

import javafx.scene.paint.Color;

class FileImportExport extends Record {

    //TODO 导出到文件, 可以包含简单的加密, 比如异或加密
    public boolean exportToFile() {
        Record.actionList.add(1, CadShape.getCadShape(ShapeType.CadText, "666", Color.BLACK));
        return false;
    }

    //TODO 从文件导入
    public boolean importFromFile() {
        return false;
    }
}
