package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage estrade) throws Exception{
        estrade.setTitle("ElementZ");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("sample.fxml"));
        Pane panneau = (Pane) loader.load();
        Scene scene = new Scene(panneau);
        estrade.setScene(scene);
        estrade.show();
    }


    public static void main(String[] args) {

        launch(args);
    }
}