package main.java.cad.MainCadStageParts;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import main.java.cad.CadCurrentStat;
import main.java.cad.CommonDefinitions.CommonSize;

public class CadPropertyPanel {
    private VBox content;
    private ComboBox<Integer> fontSize;
    private ComboBox<String> fontFamily;
    private ComboBox<String> lineSize;
    private ComboBox<Integer> rubberSize;
    private ComboBox<Integer> emptyComboBox;
    private ObservableList<Integer> fontSizeItems = FXCollections.observableArrayList();
    private ObservableList<String> fontFamilyItems = FXCollections.observableArrayList();
    private ObservableList<String> lineSizeItems = FXCollections.observableArrayList();
    private ObservableList<Integer> rubberSizeItems = FXCollections.observableArrayList();

    public CadPropertyPanel() {
        content = new VBox();
        content.setAlignment(Pos.CENTER);
        // 初始化字号
        fontSize = new ComboBox<Integer>();
        fontSize.setStyle("-fx-base:#888888;-fx-background-color:#666666;");
        fontSize.setPrefWidth(CommonSize.DETAIL_WIDTH);
        for (int i = 8; i <= 36; i += 2) {
            fontSizeItems.add(i);
        }
        fontSize.setItems(fontSizeItems);
        fontSize.getSelectionModel().select(0);
        fontSize.valueProperty().addListener((ov, oldv, newv) -> {
            CadCurrentStat.textFontSize = newv;
        });

        // 初始化字体
        fontFamily = new ComboBox<String>();
        for (int i = 0; i < Font.getFamilies().size(); i++) {
            fontFamilyItems.add(Font.getFamilies().get(i));
        }
        fontFamily.setStyle("-fx-base:#888888;-fx-background-color:#666666;");
        fontFamily.setPrefWidth(CommonSize.DETAIL_WIDTH);
        fontFamily.setItems(fontFamilyItems);
        fontFamily.getSelectionModel().select(0);
        fontFamily.valueProperty().addListener((ov, oldv, newv) -> {
            CadCurrentStat.textFontFamily = (newv);
        });

        // 初始化线条
        lineSize = new ComboBox<String>();
        for (int i = 1; i < 14; i += 3) {
            lineSizeItems.add(Integer.toString(i));
        }
        lineSizeItems.add("Fill");
        lineSize.setStyle("-fx-base:#888888;-fx-background-color:#666666;");
        lineSize.setPrefWidth(CommonSize.DETAIL_WIDTH);
        lineSize.setItems(lineSizeItems);
        lineSize.getSelectionModel().select(0);
        lineSize.valueProperty().addListener((ov, oldv, newv) -> {
            CadCurrentStat.lineWidth = Integer.parseInt((newv));
        });

        // 初始化橡皮擦
        rubberSize = new ComboBox<Integer>();
        for (int i = 1; i < 14; i += 3) {
            rubberSizeItems.add(i);
        }
        rubberSize.setStyle("-fx-base:#888888;-fx-background-color:#666666;");
        rubberSize.setPrefWidth(CommonSize.DETAIL_WIDTH);
        rubberSize.setItems(rubberSizeItems);
        rubberSize.getSelectionModel().select(0);
        rubberSize.valueProperty().addListener((ov, oldv, newv) -> {
            CadCurrentStat.rubberWidth = (newv);
        });

        //初始化两个空的
        emptyComboBox = new ComboBox<Integer>();
        emptyComboBox.setStyle("-fx-base:#888888;-fx-background-color:#666666;");
        emptyComboBox.setPrefWidth(CommonSize.DETAIL_WIDTH);
    }

    public void setLine() {
        content.getChildren().clear();
        lineSize.getSelectionModel().select(0);
        CadCurrentStat.lineWidth = 1;
        content.getChildren().add(lineSize);
        content.getChildren().add(emptyComboBox);
    }

    public void setFont() {
        content.getChildren().clear();
        fontFamily.getSelectionModel().select(0);
        fontSize.getSelectionModel().select(0);
        CadCurrentStat.textFontFamily = "AIGDT";
        CadCurrentStat.textFontSize = 8;
        content.getChildren().add(fontSize);
        content.getChildren().add(fontFamily);
    }

    public void setRubber() {
        content.getChildren().clear();
        rubberSize.getSelectionModel().select(0);
        CadCurrentStat.rubberWidth = 3;
        content.getChildren().add(rubberSize);
        content.getChildren().add(emptyComboBox);
    }

    public void clear() {
        content.getChildren().clear();
    }

    public VBox getDetailPanel() {
        return content;
    }
}
