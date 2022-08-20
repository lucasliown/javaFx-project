package Controller;

import Model.CareHome;
import Model.Doctor;
import Model.SqlLink;
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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.ResourceBundle;


public class LoginController {
    private CareHome careHome;
    @FXML
    private Button LoginButton;
    @FXML
    private Label loginMessage;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Button exitButton;



    public void LoginButtonOnAction(ActionEvent event) throws IOException {// this method is for login
        careHome = new CareHome();
        if (usernameTextField.getText().isBlank() == true && passwordTextField.getText().isBlank() == true) {//check the validation
            loginMessage.setText("please enter your username\n and password");
        } else if (careHome.validateLogin(usernameTextField.getText(), passwordTextField.getText()) == 1) {//if is nurse
            loginMessage.setText(" log in fail");
            Stage stage = (Stage) LoginButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/nurseMenu.fxml"));
            stage.setTitle("Nurse Menu");
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();

        } else if (careHome.validateLogin(usernameTextField.getText(), passwordTextField.getText()) == 2) {//if is doctor
            loginMessage.setText(" log in fail");
            Stage stage = (Stage) LoginButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/doctorMenu.fxml"));
            stage.setTitle("Doctor Menu");
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();


        } else if (careHome.validateLogin(usernameTextField.getText(), passwordTextField.getText()) == 3) {//if is manager
            loginMessage.setText(" log in fail");
            Stage stage = (Stage) LoginButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/managerMenu.fxml"));
            stage.setTitle("Manager Menu");
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();


        } else if (careHome.validateLogin(usernameTextField.getText(), passwordTextField.getText()) == 4) {//if the password or name is not correct

            loginMessage.setText("invalid login, please re-enter");

        }


    }


    public void exit(ActionEvent event) {//this method is for exit this page
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();


    }

    public Button getLoginButton() {
        return LoginButton;
    }

    public void setLoginButton(Button loginButton) {
        LoginButton = loginButton;
    }

    public Label getLoginMessage() {
        return loginMessage;
    }

    public void setLoginMessage(Label loginMessage) {
        this.loginMessage = loginMessage;
    }

    public TextField getUsernameTextField() {
        return usernameTextField;
    }

    public void setUsernameTextField(TextField usernameTextField) {
        this.usernameTextField = usernameTextField;
    }

    public PasswordField getPasswordTextField() {
        return passwordTextField;
    }

    public void setPasswordTextField(PasswordField passwordTextField) {
        this.passwordTextField = passwordTextField;
    }

    public Button getExitButton() {
        return exitButton;
    }

    public void setExitButton(Button exitButton) {
        this.exitButton = exitButton;
    }

    public CareHome getCareHome() {
        return careHome;
    }

    public static void setCareHome(CareHome careHome) {
        careHome = careHome;
    }

}







