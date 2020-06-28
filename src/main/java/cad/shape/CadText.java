package main.java.cad.shape;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import main.java.cad.CadShape;
import main.java.cad.PaintMode;
import main.java.cad.Record;
import main.java.cad.Status;

public class CadText extends Text {
    public CadText(double startX, double startY, String context, CadShape shape, Pane parent, Record record){
        super(startX, startY, context);
        setFont(Font.font(Status.fontType, Status.lineWidth));
        setStroke(Status.strokeColor);
        setId(String.valueOf(shape.getId()));
        setOnMouseClicked(event -> {
            System.out.println(getId() + " clicked");
            if (Status.paintMode == PaintMode.CadEraser) {
                Status.selected = null;
                Status.selectAll = false;
                Status.startPoint = null;
                parent.getChildren().forEach(t -> {
                    if (t instanceof Shape)
                        ((Shape) t).setStroke(Status.strokeColor);//TODO: recover the origin color
                });
                record.getActionList().remove(shape);
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
        });
    }
}
