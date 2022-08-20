package Controller;
import Model.CareHome;
import Model.Doctor;
import Model.SqlLink;
import Model.authorized;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static Model.CareHome.loginID;
import static Model.CareHome.loginName;

public class NurseController implements Initializable{
    @FXML
    private Button logout;
    @FXML
    private Button checkBed;
    @FXML
    private Button clickhere;
    @FXML
    private Button Forprescription;
    @FXML
    private Label message;
    @FXML
    private Label welcome;
    @FXML
    private Button update;
    @FXML
    private Label displayMessage;
    @FXML
    private Button giveMedicine;


    public void initialize(URL url, ResourceBundle resourceBundle) {//for check the login detail
        welcome.setText("Welcome !! ID:"+loginID+" "+"Name:"+loginName);

    }

    @FXML
    public void checkBed(ActionEvent event) throws IOException {//call the check the bed detail window
        Stage stageturn = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/checkBed.fxml"));
        stageturn.setTitle("check bed patient on a bed");
        Scene scene1 = new Scene(loader.load());
        stageturn.setScene(scene1);
        stageturn.show();

    }

    public void logout(ActionEvent event) throws IOException {//log out button for log out
        Stage stage = (Stage) logout.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/Login.fxml"));
        stage.setTitle("Log in");
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();


    }


    public void clickhere(ActionEvent event) throws IOException {//call the move patient window
        Stage stageturn = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/movePatient.fxml"));
        stageturn.setTitle("move patient to a differnet bed");
        Scene scene1 = new Scene(loader.load());
        stageturn.setScene(scene1);
        stageturn.show();
    }


    public void Forprescription(ActionEvent event){//nurse do not have authority to call the prescription
        try {
            throw new authorized();
        }catch(authorized e){
            message.setText("you do not have authority");

        }
    }



    public void update(ActionEvent event){//nurse do not have authority to update the prescription
        try {
            throw new authorized();
        }catch(authorized e){
            displayMessage.setText("you do not have authority");

        }


    }

    public void giveMedicine(ActionEvent event) throws IOException {//call the give medicine function to give medicine
        Stage stageturn = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/administerPrescription.fxml"));
        stageturn.setTitle("Administer Medicine");
        Scene scene1 = new Scene(loader.load());
        stageturn.setScene(scene1);
        stageturn.show();

    }



}
