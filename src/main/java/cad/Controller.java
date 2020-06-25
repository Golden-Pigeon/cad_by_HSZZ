package main.java.cad;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class Controller {


    @FXML
    private CheckMenuItem painterToolBarMenuItem;

    @FXML
    private CheckMenuItem colorToolBarMenuItem;


    @FXML
    private VBox colorToolBar;

    @FXML
    private VBox painterToolBar;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private ComboBox<String> sizeComboBox;

    @FXML
    private Slider sizeSlider;

    @FXML
    public void onPanePressed(MouseEvent event) {

    }

    @FXML
    public void onPainterToolMenuItemAction(ActionEvent event) {
        if (painterToolBar.isVisible()) {
            painterToolBar.setVisible(false);
            painterToolBarMenuItem.setSelected(false);
        } else {
            painterToolBar.setVisible(true);
            painterToolBarMenuItem.setSelected(true);
        }
    }

    @FXML
    public void onColorToolMenuItemAction(ActionEvent actionEvent) {
        if (colorToolBar.isVisible()) {
            colorToolBar.setVisible(false);
            colorToolBarMenuItem.setSelected(false);
        } else {
            colorToolBar.setVisible(true);
            colorToolBarMenuItem.setSelected(true);
        }
    }

    @FXML
    public void onColorButtonClicked(ActionEvent actionEvent) {
        Button name = (Button) actionEvent.getSource();
        System.out.println(name.getId());
    }

    @FXML
    public void onColorPickerFinished(ActionEvent actionEvent) {
        CadCurrentStat.color = colorPicker.getValue();
        System.out.println(CadCurrentStat.color.toString());
    }

    @FXML
    public void onSizeComboBoxClicked(MouseEvent mouseEvent) {
        List<String> sizeOptions = new ArrayList<>();
        for (int i = 8; i <= 20; i += 2) {
            sizeOptions.add(String.valueOf(i));
        }

        ObservableList<String> options =
                FXCollections.observableArrayList(
                        sizeOptions
                );
        sizeComboBox.setItems(options);
        sizeComboBox.setEditable(false);
    }

    @FXML
    public void onSizeComboBoxFinished(ActionEvent actionEvent) {
        String currComboBoxValue = sizeComboBox.getValue();
        if (currComboBoxValue != null) {
            CadCurrentStat.textFontSize = (int) Double.parseDouble(currComboBoxValue);
            CadCurrentStat.lineWidth = (int) Double.parseDouble(currComboBoxValue);
            sizeSlider.setValue((int) Double.parseDouble(currComboBoxValue));
        }
    }

    @FXML
    public void onSizeSliderFinished(MouseEvent mouseEvent) {
        sizeComboBox.setValue(String.valueOf(sizeSlider.getValue()));
        CadCurrentStat.textFontSize = (int) sizeSlider.getValue();
        CadCurrentStat.lineWidth = (int) sizeSlider.getValue();
    }
}
