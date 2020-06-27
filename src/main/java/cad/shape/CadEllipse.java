package main.java.cad.shape;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;
import main.java.cad.CadShape;
import main.java.cad.PaintMode;
import main.java.cad.Status;
import main.java.cad.util.CadMath;

public class CadEllipse extends Ellipse {
    public CadEllipse(double startX, double startY, double endX, double endY, CadShape shape, Pane parent){
        super((startX + endX) / 2, (startY + endY) / 2,
                Math.abs((endX - startX) / 2), Math.abs((endY - startY) / 2));
        setOnMouseClicked(event -> {
            System.out.println(getId() + " clicked");
            if(!CadMath.inEllipse(getCenterX(), getCenterY(), getRadiusX(), getRadiusY(),
                    event.getX(), event.getY(), getStrokeWidth())) {
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
                event.consume();
                System.out.println("consumed");
            }
            else {
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
