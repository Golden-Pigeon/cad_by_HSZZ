package main.java.cad;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

public class Controller {

    @FXML
    private Button mouse;


    @FXML
    private Button pencil;

    @FXML
    private Button line;

    @FXML
    private AnchorPane mainPane;

    @FXML
    public void onButtonClicked(MouseEvent event) {
        Button button = (Button) event.getSource();

        if(button.getId().equals("line")) {
            System.out.println("clicked");
            Status.isLineMode = true;
        }
    }

    @FXML
    public void onPanePressed(MouseEvent event) {
        if(Status.isLineMode ){
            if(Status.startX == - 1.0 && Status.startY == -1.0) {
                Status.startX = event.getX();
                Status.startY = event.getY();
            }
            else {
                Line line = new Line(Status.startX, Status.startY, event.getX(), event.getY());
                Status.startX = -1.0;
                Status.startY = -1.0;
                mainPane.getChildren().add(line);
            }
            event.consume();
        }



    }




}
