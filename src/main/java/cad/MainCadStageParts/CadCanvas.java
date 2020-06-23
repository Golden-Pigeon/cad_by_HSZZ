package main.java.cad.MainCadStageParts;

import main.java.cad.CadCurrentStat;
import main.java.cad.CadShapeController.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import main.java.cad.CommonDefinitions.CommonSize;
import main.java.cad.Record;

import java.util.ArrayList;
import java.util.List;

public class CadCanvas {
    private Group groupContent;
    private VBox vboxContainer;

    private Canvas currCanvas;
    private GraphicsContext gc;
    public static int currCanvasWidth;
    public static int currCanvasHeight;
    private List<Canvas> savedCanvas, deletedCanvas;
    private CadShapeCalc shapeCalc = new CadShapeCalc();

    public CadCanvas() {
        groupContent = new Group();
        vboxContainer = new VBox();
        vboxContainer.setAlignment(Pos.CENTER);
        vboxContainer.setPadding(new Insets(10, 20, 0, 0));
        vboxContainer.getChildren().add(groupContent);

        currCanvas = new Canvas(CommonSize.CANVAS_WIDTH, CommonSize.CANVAS_HEIGHT);
        gc = currCanvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, CommonSize.CANVAS_WIDTH, CommonSize.CANVAS_HEIGHT);
        gc.restore();
        groupContent.getChildren().add(currCanvas);

        shapeCalc.setShapeFill(currCanvas, Color.BLACK, false);
        currCanvasWidth = (int) currCanvas.getWidth();
        currCanvasHeight = (int) currCanvas.getHeight();
        savedCanvas = new ArrayList<>();
        deletedCanvas = new ArrayList<>();
        handleCanvasDrawingProcess();
    }

    public VBox getCurrCanvas() {
        return vboxContainer;
    }

    private void handleCanvasDrawingProcess() {
        currCanvas.setOnMouseMoved(event -> {
            CadStatusBar.setStatusText("Selected: " + CadCurrentStat.type + " @ "
                    + String.format("%.1f, %.1f px ", event.getX(), event.getY()));
        });
        currCanvas.setOnMouseExited(event -> {
            CadStatusBar.setStatusText("Selected: " + CadCurrentStat.type + " @ N/A");
        });

        //TODO 记得绘制完了使用Record.saveAction()添加到Record的actionList里面
        /**
         * 在这里判断类型并准备绘制
         *
         */
        currCanvas.setOnMousePressed(event -> {
        });

        /**
         * 如果是笔/橡皮, 则绘制
         */
        currCanvas.setOnMouseDragged(event -> {
            CadStatusBar.setStatusText("Selected: " + CadCurrentStat.type + " @ "
                    + String.format("%.1f, %.1fpx ", event.getX(), event.getY()));
        });

        /**
         * 在这里完成绘制
         */
        currCanvas.setOnMouseReleased(event -> {});
    }

    /**
     * 撤销操作
     * @return
     */
    boolean undo() {
        if (!savedCanvas.isEmpty()) {
            Canvas deleted = savedCanvas.get(savedCanvas.size()-1);
            deletedCanvas.add(deleted);
            groupContent.getChildren().remove(savedCanvas.get(savedCanvas.size() - 1));
            savedCanvas.remove(savedCanvas.size() - 1);
        }
        Record.undoAction();
        return true;
    }

    /**
     * 恢复操作
     * @return
     */
    boolean redo() {
        if(!deletedCanvas.isEmpty()) {
            savedCanvas.add(deletedCanvas.get(deletedCanvas.size()-1));
            deletedCanvas.remove(deletedCanvas.size()-1);
            return true;
        }
        return false;
    }

    /**
     * 清空
     */
    void clearCanvas() {
        groupContent.getChildren().clear();
        savedCanvas.clear();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, CommonSize.CANVAS_WIDTH, CommonSize.CANVAS_HEIGHT);
        gc.restore();
        groupContent.getChildren().add(currCanvas);
        Record.clearAll();
    }
}
