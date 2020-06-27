package main.java.cad.shape;


import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import main.java.cad.CadShape;
import main.java.cad.PaintMode;
import main.java.cad.Status;
import main.java.cad.util.CadMath;


public class CadRect extends Rectangle {
    public CadRect(double startX, double startY, double endX, double endY, CadShape shape, Pane parent){
        super(Math.min(startX, endX), Math.min(startY, endY),
                Math.abs(endX - startX), Math.abs(endY - startY));
        setOnMouseClicked(event -> {
            System.out.println(getId() + " clicked");
            if(!(CadMath.isSqueezed(event.getX(), startX, endX, getStrokeWidth()) &&
                    CadMath.isSqueezed(event.getY(), startY, endY, getStrokeWidth()))) {
                if (Status.paintMode == PaintMode.CadEraser) {
                    parent.getChildren().remove(this);
                } else {
                    Status.selected = shape;
                    Status.selectAll = false;
                    Status.startPoint = null;
                    parent.getChildren().forEach(t -> {
                        if (t instanceof Shape)
                            ((Shape) t).setStroke(Status.strokeColor);//TODO: recover the origin color
                    });
                    setStroke(Color.RED);
                }
                System.out.println("consumed");
                event.consume();
            }
            else {
                //TODO: complete the fillColor selection system to make a test
                if(Status.paintMode == PaintMode.CadFiller){
                    setFill(Status.fillColor);
                    System.out.println("consumed");
                    event.consume();
                }
            }

        });
        setStroke(Status.strokeColor);
        setFill(Status.fillColor);
        setId(String.valueOf(shape.getId()));
        setStrokeWidth(Status.lineWidth);
    }
}
