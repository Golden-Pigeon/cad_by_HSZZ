package main.java.cad;

import java.io.Serializable;

public class IntegratedShapeLists extends Record implements Serializable {
    private Object[] actionListArray;
    private Object[] deleteListArray;

    public IntegratedShapeLists(Record record) {
        actionListArray = (Object[]) record.getActionList().toArray();
        deleteListArray = (Object[]) record.getDeleteList().toArray();
    }
}
