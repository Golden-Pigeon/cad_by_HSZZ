package main.java.cad.MainCadStageParts;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class CadStatusBar {
    private HBox statusContainer;
    private static Label statusText = new Label("");

    public CadStatusBar() {
        statusContainer = new HBox();
        statusContainer.setAlignment(Pos.CENTER_RIGHT);
        statusContainer.setPadding(new Insets(5, 30, 5, 100));
        statusText.setFont(Font.font ("Microsoft YaHei", 16));
        statusContainer.getChildren().add(statusText);
    }

    public HBox getCadStatusBar() {
        return statusContainer;
    }

    public static void setStatusText(String text) {
        statusText.setText(text);
    }

    public HBox getCasStatusBar() {
        return statusContainer;
    }
}
