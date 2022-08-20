package Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{//this method is the start point to start the program
        Parent root = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
        primaryStage.setTitle("Log in");
        primaryStage.setScene(new Scene(root, 520, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        Application.launch(args);
    }
}
