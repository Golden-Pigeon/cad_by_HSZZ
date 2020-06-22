package main.java.cad;

import java.util.ArrayList;
import java.util.List;

class FileImportExport {
    List<CadShape> saveList = new ArrayList<>();

    public boolean pushToBack(CadShape inShape) {
        if (inShape == null)
            return false;
        saveList.add(inShape);
        return true;
    }

    public boolean popFromBack() {
        if(saveList.size() == 0)
            return false;
        saveList.remove(saveList.size() - 1);
        return true;
    }

    //TODO 导出到文件, 可以包含简单的加密, 比如异或加密
    public boolean exportToFile() {
        return false;
    }

    //TODO 从文件导入
    public boolean importFromFile(){
        return false;
    }
}
