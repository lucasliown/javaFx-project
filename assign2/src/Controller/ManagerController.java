package Controller;

import Model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ManagerController implements Initializable {

    private CareHome careHome;
    @FXML
    private Button selectBed;
    @FXML
    private ChoiceBox<String> addAccountType;
    @FXML
    private TextField addID;
    @FXML
    private TextField addName;
    @FXML
    private TextField addPassword;
    @FXML
    private Button submitAdd;
    @FXML
    private Label messageAddSatff;
    private Manager manager;
    @FXML
    private Button LogoutButton;
    @FXML
    private ChoiceBox<String> modifyAccountType;
    @FXML
    private ChoiceBox<String> ModifyID;
    @FXML
    private TextField changeID;
    @FXML
    private TextField changeName;
    @FXML
    private TextField changePassword;
    @FXML
    private Button submitModify;
    @FXML
    private Label searchAccountMessage;
    @FXML
    private Label changeStatusLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        careHome = new CareHome();
        manager = new Manager(0, null, null);
        careHome.initiationCareHome();
        addAccountType.getItems().add("Nurse");//add the choice in the choice box
        addAccountType.getItems().add("Doctor");//add the choice in the choice box
        modifyAccountType.getItems().add("Nurse");//add the choice in the choice box
        modifyAccountType.getItems().add("Doctor");//add the choice in the choice box
        addModify();

    }

    public void addModify() {//this method is use listener to change the staff id
        modifyAccountType.valueProperty().addListener((observableValue, get, change) ->
        {
            if (change != null) {
                ModifyID.getItems().clear();

                if (change.equals("Doctor")) {
                    ModifyID.getItems().addAll(manager.outPutDoctor("Doctor"));
                } else if (change.equals("Nurse")) {
                    ModifyID.getItems().addAll(manager.outPutNurse("Nurse"));
                }
            }
        });

    }


    public void inputCanNotNull() throws CanNotNullException {//check the input validation
        if (addID.getText().isBlank() || addName.getText().isBlank() || addPassword.getText().isBlank() ||
                addAccountType.getValue() == null) {

            throw new CanNotNullException();

        }

    }

    public void inputCanNotNull2() throws CanNotNullException {//check the input validation
        if (ModifyID.getValue() == null || modifyAccountType.getValue() == null) {

            throw new CanNotNullException();

        }

    }

    public void inputCanNotNull3() throws CanNotNullException {//check the input validation
        if (changeID.getText().isBlank() || changeName.getText().isBlank()
                || modifyAccountType.getValue() == null || changePassword.getText().isBlank()) {

            throw new CanNotNullException();

        }

    }


    public void selectBed(ActionEvent event) throws IOException {//select the bed button to turn on the new window
        Stage stagebed = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/Showbed.fxml"));
        stagebed.setTitle("Add patient to a bed");
        Scene scene1 = new Scene(loader.load());
        stagebed.setScene(scene1);
        stagebed.show();

    }

    public void submitAdd(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {//submit the add staff
        try {
            inputCanNotNull();//check the input validation
        } catch (CanNotNullException e) {
            messageAddSatff.setText("please enter Staff detail");
            return;
        }
        careHome = new CareHome();
        manager = new Manager(0, null, null);
        careHome.initiationCareHome();
        int checkData = careHome.validateSearchSaff(addID.getText(), addName.getText());//call the method in the careHome for search the staff id
        if (checkData == 1 || checkData == 2 || checkData == 3) {//check the staff id is the same or not
            messageAddSatff.setText("you cannot add same name \nor same ID");
        } else if (checkData == 4) {//in this situation you can add staff
            manager.addStaff(addID.getText(), addName.getText(), addPassword.getText(), addAccountType.getValue());
            messageAddSatff.setText("add staff successful!!!");
            careHome.initiationCareHome();
            modifyAccountType.getItems().clear();
            modifyAccountType.getItems().add("Nurse");
            modifyAccountType.getItems().add("Doctor");
            Stage stageOperation = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/operationsuccessful.fxml"));
            stageOperation.setTitle("Congratulation");
            Scene scene1 = new Scene(loader.load());
            stageOperation.setScene(scene1);
            stageOperation.show();
        }


    }

    public void searchButton() {//this method is for search the staff detail
        try {
            inputCanNotNull2();//check the input validation
        } catch (CanNotNullException e) {
            searchAccountMessage.setText("please enter Staff detail");
            return;
        }
        careHome = new CareHome();
        manager = new Manager(0, null, null);
        careHome.initiationCareHome();
        String result = manager.searchByID(modifyAccountType.getValue(), ModifyID.getValue());//search the specific detail in the model
        searchAccountMessage.setText(result);
        changeID.setText(manager.searchByIDForChangeID(modifyAccountType.getValue(), ModifyID.getValue()));//set the id in the text filed
        changeName.setText(manager.searchByIDForChangeName(modifyAccountType.getValue(), ModifyID.getValue()));//set the name in the text filed
        changePassword.setText(manager.searchByIDForChangePassword(modifyAccountType.getValue(), ModifyID.getValue()));//set the password in the text filed
    }

    public void submitModify() throws SQLException, ClassNotFoundException, IOException {//this method is for modify the staff ID
        try {
            inputCanNotNull3();//check the input validation
        } catch (CanNotNullException e) {
            changeStatusLabel.setText("please enter Staff detail");
            return;
        }
        if (changeID.getText().equals("No result") || changeName.getText().equals("No result") || changePassword.getText().equals("No result")) {
            changeStatusLabel.setText("we can't modify ID that no exist");
            return;
        }
        careHome = new CareHome();
        manager = new Manager(0, null, null);
        careHome.initiationCareHome();
        String result = manager.modifyStaff(changeID.getText(), changeName.getText(), changePassword.getText(), modifyAccountType.getValue());//call the method in the model
        changeStatusLabel.setText(result);
        if (result.equals("modify successfully")) {
            Stage stageOperation = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/operationsuccessful.fxml"));
            stageOperation.setTitle("Congratulation");
            Scene scene1 = new Scene(loader.load());
            stageOperation.setScene(scene1);
            stageOperation.show();
        }

    }


    public void LogoutButton(ActionEvent event) throws IOException {//logout method
        Stage stage = (Stage) LogoutButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/Login.fxml"));
        stage.setTitle("Log in");
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();

    }

    public void moreFunctions(ActionEvent event) throws IOException {//call the other window for the more function
        Stage stageOperation = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/manageShift.fxml"));
        stageOperation.setTitle("more function");
        Scene scene1 = new Scene(loader.load());
        stageOperation.setScene(scene1);
        stageOperation.show();

    }


}
