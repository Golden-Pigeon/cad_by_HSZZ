package main.java.cad.shape;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import main.java.cad.CadShape;
import main.java.cad.PaintMode;
import main.java.cad.Record;
import main.java.cad.Status;

public class CadLine extends Line {
    public CadLine(double startX, double startY, double endX, double endY, CadShape shape, Pane parent, Record record){
        super(startX, startY, endX, endY);
        setOnMouseClicked(event -> {
            System.out.println(getId() + " clicked");
            if(Status.paintMode == PaintMode.CadEraser){
                Status.selected = null;
                Status.selectAll = false;
                Status.startPoint = null;
                parent.getChildren().forEach(t -> {
                    if (t instanceof Shape)
                        ((Shape) t).setStroke(Status.strokeColor);//TODO: recover the origin color
                });
                record.getActionList().remove(shape);
                parent.getChildren().remove(this);
            }
            else {
                Status.selected = shape;
                Status.selectAll = false;
                Status.startPoint = null;
                parent.getChildren().forEach(t ->{
                    if(t instanceof Shape)
                        ((Shape)t).setStroke(Status.strokeColor);//TODO: recover the origin color
                });
                setStroke(Color.RED);
            }
            event.consume();
        });
        setStroke(Status.strokeColor);
        setId(String.valueOf(shape.getId()));
        setStrokeWidth(Status.lineWidth);
    }
}
