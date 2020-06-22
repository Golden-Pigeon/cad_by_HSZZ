package main.java.cad;

import java.util.LinkedList;
import java.util.List;

/**
 * 纪录类, 保存用户操作与撤销/删除列表
 *
 * @author 侯文轩
 * @version 1.0
 */
public class Record {

    //TODO 写注释

    private List<CadShape> actionList = new LinkedList<>();// 当前已保存的操作
    private List<CadShape> deleteList = new LinkedList<>();// 当前已撤销/删除的操作

    /**
     * 保存操作
     *
     * @param inShape 当前已完成的图形
     * @return true, 如果保存成功; false, 参数为null
     */
    public boolean saveAction(CadShape inShape) {
        if (inShape == null)
            return false;
        actionList.add(inShape);
        return true;
    }

    public boolean undoAction() {
        if (actionList.size() == 0)
            return false;
        CadShape deletedShape = actionList.get(actionList.size() - 1);
        actionList.remove(actionList.size() - 1);
        deleteList.add(deletedShape);
        return true;
    }

    public boolean recoverDeletedShape(int deletedShapeID) {
        for (CadShape currShape : deleteList) {
            if (currShape.id == deletedShapeID) {
                deleteList.remove(currShape);
                for (int i = 0; i < actionList.size(); i++) {
                    if (actionList.get(i).id > deletedShapeID) {
                        actionList.add(i, currShape);
                        return true;
                    }
                }
                actionList.add(currShape);
                return true;
            }
        }
        return false;
    }
}
