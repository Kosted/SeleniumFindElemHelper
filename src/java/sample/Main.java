package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.MalformedURLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Selenium Helper");
        primaryStage.setScene(new Scene(root, 608, 508));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
//        try {
//            WebActions webActions = new WebActions();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
    }
}
