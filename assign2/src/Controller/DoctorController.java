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
import java.util.HashMap;
import java.util.ResourceBundle;

import static Model.CareHome.loginID;
import static Model.CareHome.loginName;

public class DoctorController implements Initializable{
    @FXML
    private Button logout;
    @FXML
    private Button nextPage;
    @FXML
    private Button clickhere;
    @FXML
    private Label message;
    @FXML
    private Button Forprescription;
    @FXML
    private Label welcome;
    @FXML
    private Button update;
    @FXML
    private Button giveMedicine;
    @FXML
    private Label givemedicineMessage;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        welcome.setText("Welcome !! ID:"+loginID+" "+"Name:"+loginName);//use the static to get the log detail

    }

    @FXML
    public void nextPage(ActionEvent event) throws IOException {//this page is for go to the check bed function
        Stage stageturn = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/checkBed.fxml"));
        stageturn.setTitle("check bed patient on a bed");
        Scene scene1 = new Scene(loader.load());
        stageturn.setScene(scene1);
        stageturn.show();

    }

    public void logout(ActionEvent event) throws IOException {//this method is for logout the doctor page
        Stage stage = (Stage) logout.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/Login.fxml"));
        stage.setTitle("Log in");
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();


    }

    public void clickhere(ActionEvent event){//the doctor can not have the authorized to move bed
        try {
            throw new authorized();
        }catch(authorized e){
            message.setText("you do not have authority");

        }


    }

    public void Forprescription(ActionEvent event) throws IOException {//call the add a new prescription function
        Stage stageturn = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/givePrescriptionbed.fxml"));
        stageturn.setTitle("attach a prescription");
        Scene scene1 = new Scene(loader.load());
        stageturn.setScene(scene1);
        stageturn.show();

    }

    public void update(ActionEvent event) throws IOException {//call the update function
        Stage stageturn = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/updatePrescription.fxml"));
        stageturn.setTitle("update a prescription");
        Scene scene1 = new Scene(loader.load());
        stageturn.setScene(scene1);
        stageturn.show();

    }

    public void giveMedicine(ActionEvent event){//the doctor do not have the authorized to give medicine
        try {
            throw new authorized();
        }catch(authorized e){
            givemedicineMessage.setText("you do not have authority");

        }
    }


}
