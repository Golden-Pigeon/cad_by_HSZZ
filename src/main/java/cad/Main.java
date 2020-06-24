package main.java.cad;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.java.cad.CommonDefinitions.CommonPath;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        primaryStage.setTitle("JavaFX CAD Utility");
        primaryStage.setScene(new Scene(root));
        primaryStage.getIcons().add(new Image(CommonPath.LOGO));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
