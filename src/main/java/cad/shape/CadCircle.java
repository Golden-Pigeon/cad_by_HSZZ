package main.java.cad.shape;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import main.java.cad.CadShape;
import main.java.cad.PaintMode;
import main.java.cad.Status;

public class CadCircle extends Circle {
    public CadCircle(double startX, double startY, double endX, double endY, CadShape shape, Pane parent){
        super(startX, startY, Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2)));
        setOnMouseClicked(event -> {
            System.out.println(getId() + " clicked");
            double x = event.getX();
            double y = event.getY();
            double r = getRadius();
            if((Math.pow(x - startX, 2) + Math.pow(y - startY, 2) > Math.pow(r - getStrokeWidth(), 2))){
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
