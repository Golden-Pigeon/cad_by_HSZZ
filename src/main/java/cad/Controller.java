package main.java.cad;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

import javax.swing.*;

public class Controller {


    @FXML
    private CheckMenuItem painterToolBarMenuItem;

    @FXML
    private CheckMenuItem colorToolBarMenuItem;

    @FXML
    private Button mouse;





    @FXML
    private Button pencil;

    @FXML
    private Button line;

    @FXML
    private VBox colorToolBar;

    @FXML
    private VBox painterToolBar;

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

    @FXML
    public void onPainterToolMenuItemAction(ActionEvent event){
        if(painterToolBar.isVisible()) {
            painterToolBar.setVisible(false);
            painterToolBarMenuItem.setSelected(false);
        }
        else {
            painterToolBar.setVisible(true);
            painterToolBarMenuItem.setSelected(true);
        }
    }

    @FXML
    public void onColorToolMenuItemAction(ActionEvent actionEvent) {
        if(colorToolBar.isVisible()) {
            colorToolBar.setVisible(false);
            colorToolBarMenuItem.setSelected(false);
        }
        else {
            colorToolBar.setVisible(true);
            colorToolBarMenuItem.setSelected(true);
        }
    }
}
