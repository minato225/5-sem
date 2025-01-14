package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        stage.setTitle("Train transport.");
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("icon.png"));
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
