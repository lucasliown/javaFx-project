package Controller;

import javafx.event.ActionEvent;

import Model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class operationsuccessfulController {
    @FXML
    private Button successful;

    public void successful(ActionEvent event) {// this method just let customer click on the confirm button
        Stage stage = (Stage) successful.getScene().getWindow();
        stage.close();





    }






}
